package com.epam.lab.rto.service;

import com.epam.lab.rto.dto.User;
import com.epam.lab.rto.repository.interfaces.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private IUserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        User currentUser = userRepository.getUserByEmail(userEmail);

        if (Objects.isNull(currentUser)) {
            throw new UsernameNotFoundException("Неверный логин или пароль");
        }

        List<GrantedAuthority> grantList = new ArrayList<>();
        GrantedAuthority authority = new SimpleGrantedAuthority(currentUser.getRole().toString());
        grantList.add(authority);

        return new org.springframework.security.core.userdetails
                .User(currentUser.getEmail(), currentUser.getPassword(), grantList);
    }
}
