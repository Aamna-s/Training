package com.example.Application.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.function.EntityResponse;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Slf4j
@Service
@RestController
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private  final UserDataService jdbc;

    public UserService(UserRepository userRepository, UserDataService jdbc) {
        this.userRepository = userRepository;
        this.jdbc=jdbc;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional <User> user =userRepository.findByUsername(username);
        if(user.isEmpty()){

            log.warn("invlid user: {}", username.replace('\n', ' '));
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

    @GetMapping("/students/{username}")
    public ResponseEntity<User> findStudent(@RequestParam("us") String username) {

        User user = jdbc.findByUserName(username);
        return ResponseEntity.ok(user);
    }
    private String lastWeather = "";


}

