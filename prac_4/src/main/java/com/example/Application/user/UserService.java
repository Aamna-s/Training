package com.example.Application.user;

import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional <User> user =userRepository.findByUsername(username);
        if(user.isEmpty()){
            throw new UsernameNotFoundException("incorrect user or password");
        }
        return new org.springframework.security.core.userdetails.User(user.get().getUsername(),
                user.get().getPassword(),AuthorityUtils.commaSeparatedStringToAuthorityList(user.get().getRoles()));

    }

    public Optional<User> findById(Long userId) {
        return userRepository.findById(userId);
    }
    public List<User> findAll(Integer page, Integer size)
    {
        if (page<0) { page=0; }
        if (size>1000){size=1000;}
        return userRepository.findAll(PageRequest.of(page,size)).getContent();
    }
}

