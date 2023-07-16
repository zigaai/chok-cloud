package com.zigaai.authorization.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SystemUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // User user = userMapper.getByUsername(username);
        // if (user == null) {
        //     throw new UsernameNotFoundException(username);
        // }
        // List<Role> roleList = roleMapper.listByUserId(user.getId());
        // return SystemUser.of(user, roleList);
        return User.withUsername(username)
                .password("{bcrypt}$2a$10$Afa6quBp8sJRqX7l.L5wf.nnnp6NdYFqvV1YdbZTGCx28s23xgsva")
                .build();
    }
}
