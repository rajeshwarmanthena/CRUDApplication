package com.example.demo.service;


import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Users;
import com.example.demo.repository.UsersRepository;

@Service
public class UsersService {



    @Autowired
    private UsersRepository usersRepository;

    public Users createUser(Users users) {
        return usersRepository.save(users);
   }
    public List<Users> getUsers() {
        return usersRepository.findAll();
    }

    public Users getUserById(UUID userId) {
        return usersRepository.findById(userId).orElse(null);
    }

    public Users getUserByMobNum(String mobNum) {
        return usersRepository.findByMobNum(mobNum);
    }
    public List<Users> getUsersByManagerId(UUID managerId) {
        return usersRepository.findByManagerId(managerId);
    }
    public void deleteUser(UUID userId) {
        Optional<Users> userOptional = usersRepository.findById(userId);
        if (userOptional.isPresent()) {
            usersRepository.deleteById(userId);
            System.out.println("User with ID " + userId + " deleted successfully.");
        } else {
            System.out.println("User with ID " + userId + " does not exist in the table.");
        }
    }
    public void updateUser(Users users) {
        usersRepository.save(users);
    }

    public void deleteUserByMobNum(String mobNum) {
        Users user = usersRepository.findByMobNum(mobNum);
        if (user != null) {
            // If the user exists, delete it from the database
            usersRepository.delete(user);
        }
    }

}
