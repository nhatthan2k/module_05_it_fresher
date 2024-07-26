package com.ra.service.impl;

import com.ra.model.dto.request.UserRegister;
import com.ra.model.entity.Roles;
import com.ra.model.entity.Users;
import com.ra.repository.UserRepository;
import com.ra.service.RoleService;
import com.ra.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceIMPL implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleService roleService;


    @Override
    public Users handleRegister(UserRegister userRegister) {
        Logger logger = LoggerFactory.getLogger(getClass());

        if (userRepository.existsByUsername(userRegister.getUsername())) {
            throw new IllegalArgumentException("Username đã tồn tại!");
        }

        if (userRepository.existsByEmail(userRegister.getEmail())) {
            throw new IllegalArgumentException("Email đã tồn tại!");
        }

        Users user = new Users();
        user.setFullName(userRegister.getFullName());
        user.setUsername(userRegister.getUsername());
        user.setEmail(userRegister.getEmail());
        user.setPassword(userRegister.getPassword());
        user.setPhone(userRegister.getPhone());
        user.setAddress(userRegister.getAddress());
        user.setCreated(new Date(new java.util.Date().getTime()));
        user.setStatus(true);
        user.setRoles();

        try {
            return userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            logger.error("Error saving user: " + e.getRootCause().getMessage());
            throw new IllegalArgumentException("An error occurred while saving the user: " + e.getRootCause().getMessage());
        }

    }
    @Override
    public Page<Users> getAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public Users findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public Users save(Users users) {
        return userRepository.save(users);
    }

    @Override
    public Users updateAcc(Users user, Long id) {
        Users userOld = findById(id);
        if(!user.getUsername().equals(userOld.getUsername())) {
            if (userRepository.existsByUsername(user.getUsername())) {
                throw new RuntimeException("Username is exists");
            }
        }

        Set<Roles> roles = userOld.getRoles();

        Users users = Users.builder()
                .id(id)
                .fullName(user.getFullName())
                .username(user.getUsername())
                .password(userOld.getPassword())
                .email(user.getEmail())
                .avatar(user.getAvatar())
                .phone(user.getPhone())
                .address(user.getAddress())
                .created(userOld.getCreated())
                .updated(new Date(new java.util.Date().getTime()))
                .status(true)
                .roles(roles)
                .build();
        return userRepository.save(users);
    }

    @Override
    public Optional<Users> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public List<Users> searchByName(String keyword) {
        return userRepository.searchAllByUsername(keyword);
    }

}
