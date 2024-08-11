package com.lin.linloanauthmodule.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lin.commons.model.enums.UserStatus;
import com.lin.commons.model.enums.UserType;
import com.lin.commons.model.request.LoginRequest;
import com.lin.commons.model.request.ProfileDtoData;
import com.lin.commons.model.request.SignUpRequest;
import com.lin.commons.model.request.VerifyOtpRequest;
import com.lin.commons.model.response.LinLoanResponse;
import com.lin.commons.model.response.LoginResponse;
import com.lin.commons.model.response.SignupResponse;
import com.lin.commons.utils.Utils;
import com.lin.linloanauthmodule.domain.entity.User;
import com.lin.linloanauthmodule.domain.entity.UserOTP;
import com.lin.linloanauthmodule.domain.repository.UserOTPRepository;
import com.lin.linloanauthmodule.domain.repository.UserRepository;
import com.lin.linloanauthmodule.messaging.KafkaService;
import com.lin.linloanauthmodule.security.JwtServiceInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Objects;

import static com.lin.commons.helpers.Helpers.linLoanresponseEntity;
import static com.lin.commons.utils.MessageUtils.OTP_TOPIC;
import static com.lin.commons.utils.MessageUtils.PROFILE_TOPIC;


@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserOTPRepository userOTPRepository;
    @Autowired
    private OTPService otpService;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private KafkaService messagePublisher;

    @Autowired
    private JwtServiceInfo serviceInfo;
    @Autowired
    private ObjectMapper mapper;

    @Override
    public ResponseEntity<LinLoanResponse<LoginResponse>> login(LoginRequest request) {
        var response =new LoginResponse();
        try {
            User user = userRepository.findByEmail(request.getEmail()).orElse(null);
            if (Objects.isNull(user)) {
                return linLoanresponseEntity(null, "User is null", HttpStatus.BAD_REQUEST);
            }
            if (!user.getStatus().equals(UserStatus.CONFIRMED)) {
                return linLoanresponseEntity(null, "User Not confirmed", HttpStatus.CONFLICT);
            }
            if (encoder.matches(request.getPassword(), user.getPassword())) {
                LOGGER.info("<<<<<<<<  Password Match login() >>>>>>>{}", UserServiceImpl.class);
                Authentication authentication = new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());
                String token = serviceInfo.generateToken(authentication,user.getRole());
                LOGGER.info("<<<<<<<<  JWT TOKEN {} >>>>>>>  {} ",token, UserServiceImpl.class);
                String refreshToken = serviceInfo.generateRefreshToken(authentication,user.getRole());

                ProfileDtoData data = mapper.readValue(user.getProfileDtoData(), ProfileDtoData.class);
                response.setEmail(request.getEmail());
                response.setNin(data.getNin());
                response.setName(data.getFirstName() + " "+ data.getLastName());
                response.setDate(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE));
                response.setJwt(token);
                response.setPhone(data.getPhone());
                response.setRefreshToken(refreshToken);
                return linLoanresponseEntity(response,"", HttpStatus.OK);
            }
            return linLoanresponseEntity(null, "Password does not match", HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            return linLoanresponseEntity(null, e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @Override
    public ResponseEntity<LinLoanResponse<String>> verifyOTP(VerifyOtpRequest request){
        try {
            User user = userRepository.findByEmail(request.getEmail()).orElse(null);
            if (Objects.nonNull(user)) {
                if (user.getStatus().equals(UserStatus.CONFIRMED)){
                    return linLoanresponseEntity(null, "User Already Confirmed", HttpStatus.BAD_REQUEST);
                }
                UserOTP userOTP = userOTPRepository.findByOtpAndUser(request.getOtp(),user);
                if (Objects.isNull(userOTP)){
                    return linLoanresponseEntity("OTP does not found", "", HttpStatus.BAD_REQUEST);

                }
                if (!otpService.isOTPValid(userOTP)) {
                    user.setStatus(UserStatus.CONFIRMED);
                    userRepository.save(user);
                    messagePublisherProfile(mapper.readValue(user.getProfileDtoData(), ProfileDtoData.class));
                    return linLoanresponseEntity("User have been confirmed", "", HttpStatus.OK);
                }
                    return linLoanresponseEntity("OTP have Expired","", HttpStatus.BAD_REQUEST);
            }
            return linLoanresponseEntity("User does not Exist","", HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            return linLoanresponseEntity(null, e.getMessage(), HttpStatus.BAD_REQUEST);

        }

    }
    @Override
    public ResponseEntity<LinLoanResponse<String>> resendOTP(String request){
        try {
            User user = userRepository.findByEmail(request).orElse(null);
            if (Objects.nonNull(user)) {
                LOGGER.info("<<<<<<<<  >>>>>>>{}", user.toString());
                if (user.getStatus().equals(UserStatus.CONFIRMED)){
                    return linLoanresponseEntity(null, "User Already Confirmed", HttpStatus.BAD_REQUEST);
                }
                    UserOTP otp =  userOTPRepository.findUserOTPByUser(user);
                    var userOTP = generateOTP(user,otp);

                /*        * Kafka Publisher Method
                 */
                    messagePublisher(userOTP);
                    return linLoanresponseEntity("OTP have been sent to "+request, "Success", HttpStatus.OK);
                }

            return linLoanresponseEntity("User does not Exist", "", HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            return linLoanresponseEntity(null, e.getMessage(), HttpStatus.BAD_REQUEST);

        }

    }

    @Override
    public ResponseEntity<LinLoanResponse<SignupResponse>> register(SignUpRequest request) {
        try {
            User user = userRepository.findByEmail(request.getEmail()).orElse(null);
            if (Objects.nonNull(user)) {
                LOGGER.info("<<<<<<<<  User Already Exist >>>>>>>{}", UserServiceImpl.class.getName());
                return linLoanresponseEntity(null, "User Already Exist", HttpStatus.CONFLICT);
            }
          if (userRepository.existsByPhone(request.getPhone())) {
                LOGGER.info("<<<<<<<<  Phone Number Exist >>>>>>>{}", UserServiceImpl.class.getName());
                return linLoanresponseEntity(null, " Phone Number Exist", HttpStatus.CONFLICT);
            }
            /*        * New User Indicated
             */
            var newUser = new User();
            var profileDtoData = profileDtoData(request);
            newUser.setEmail(request.getEmail());
            newUser.setRole(UserType.USER);
            newUser.setStatus(UserStatus.PENDING);
            newUser.setPassword(encoder.encode(request.getPassword()));
            newUser.setProfileDtoData(mapper.writeValueAsString(profileDtoData));


            LOGGER.info("<<<<<<<<  Saving TO DB >>>>>>>{}", UserServiceImpl.class);
            var save = userRepository.save(newUser);
            UserOTP otp = new UserOTP();
            var userOTP = generateOTP(save,otp);
            /*        * Kafka Publisher Method
             */
            messagePublisher(userOTP);
            return linLoanresponseEntity(new SignupResponse("User Register Completed", request.getEmail()),"", HttpStatus.OK);
        }catch (Exception e){
            return linLoanresponseEntity(null, e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    private ProfileDtoData profileDtoData(SignUpRequest request){
        var profileDtoData = new ProfileDtoData();
        profileDtoData.setNin(request.getNin());
        profileDtoData.setFirstName(request.getFirstName());
        profileDtoData.setLastName(request.getLastName());
        profileDtoData.setPhone(request.getPhone());
        return profileDtoData;
    }

    private void messagePublisher(UserOTP userOTP){
        HashMap<Object,Object> data = new HashMap<>();
        data.put("OTP", userOTP.getOtp());
        data.put("email", userOTP.getUser().getEmail());
        data.put("id",userOTP.getUser().getId());
        LOGGER.info("<<<<<<<<  Sending OTP Message from >>>>>>>{}", UserServiceImpl.class.getName());
        messagePublisher.sendOTPMessage(data,OTP_TOPIC);
    }
    private void messagePublisherProfile(ProfileDtoData profileData){
        LOGGER.info("<<<<<<<<  Sending PROFILE Message from >>>>>>>{}", UserServiceImpl.class.getName());
        messagePublisher.sendOTPMessage(profileData,PROFILE_TOPIC);
    }
    private UserOTP generateOTP(User user,UserOTP otp){
        otp.setUser(user);
        otp.setExpire(LocalDateTime.now().plusMinutes(5));
        otp.setDateTime(LocalDateTime.now());
        otp.setOtp(Utils.generateOtp());
        return userOTPRepository.save(otp);

    }
}
