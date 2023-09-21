package com.feeham.social.accounts.service;

import com.feeham.social.accounts.model.dto.UserCreateDTO;
import com.feeham.social.accounts.model.dto.UserReadDTO;
import com.feeham.social.accounts.model.entity.User;
import com.feeham.social.accounts.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    @Override
    public User getUser(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.orElse(null);
    }

    @Override
    public UserReadDTO readUser(String email) {
        User user = getUser(email);
        if (user == null) {
            return null;
        }
        return new UserReadDTO(user.getEmail(), user.getFirstName(), user.getLastName());
    }

    @Override
    public boolean createUser(UserCreateDTO userCreate) {
        User user = new User();
        user.setEmail(userCreate.getEmail());
        user.setPasswordHash(encoder.encode(userCreate.getPassword()));
        user.setFirstName(userCreate.getFirstName());
        user.setLastName(userCreate.getLastName());
        userRepository.save(user);
        return true;
    }

    @Override
    public boolean updateUser(String email, UserCreateDTO userUpdateDTO) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setFirstName(userUpdateDTO.getFirstName());
            user.setLastName(userUpdateDTO.getLastName());
            userRepository.save(user);
            return true;
        }
        return false;
    }


    @Override
    public boolean deleteUser(String email) {
        User user = getUser(email);
        if(user == null) return false;
        userRepository.deleteById(user.getUserId());
        return true;
    }
}
