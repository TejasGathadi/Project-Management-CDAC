package com.cdacProject.taskhive.service;

import com.cdacProject.taskhive.config.JwtProvider;
import com.cdacProject.taskhive.model.User;
import com.cdacProject.taskhive.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authorization.method.AuthorizeReturnObject;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public User findUserProfileByJwt(String jwt) throws Exception {

        String email = JwtProvider.getEmailFromToken(jwt);


        return findUserByEmail(email);
    }

    @Override
    public User findUserByEmail(String email) throws Exception {

        User user = userRepository.findByEmail(email);

        if(user == null){
            throw new Exception("User Not Found ");
        }

        return user;
    }

    @Override
    public User findUserById(Long userId) throws Exception {

        Optional<User> optionalUser = userRepository.findById(userId);
        if(optionalUser.isEmpty()){
            throw new Exception("User Not Found");
        }

        return optionalUser.get();
    }

    @Override
    public User updateUsersProjectSize(User user, int number) throws Exception {

        user.setProjectSize(user.getProjectSize() + number);

        return userRepository.save(user);

    }
}
