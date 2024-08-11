package com.lin.linloanauthmodule.config;

import org.springframework.security.core.userdetails.User;
import com.lin.linloanauthmodule.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import lombok.SneakyThrows;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
@RequiredArgsConstructor
@Component
public class AppUserConfigService implements UserDetailsService {
    private final UserRepository userRepository;


        @Override
        @SneakyThrows
        public UserDetails loadUserByUsername(String appName)  {
            com.lin.linloanauthmodule.domain.entity.User appChannel = userRepository.findByEmail(appName).get();
            if(Objects.isNull(appChannel)){
                throw new RuntimeException("AppChannel Not found");
            }
            return new User(appChannel.getEmail(),appChannel.getPassword(),getAuthorities(appChannel));
        }

        private Collection<GrantedAuthority> getAuthorities(com.lin.linloanauthmodule.domain.entity.User user){
            return Collections.singletonList(new SimpleGrantedAuthority(user.getEmail()));
        }
    }

