
package com.example.demo.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;


import com.example.demo.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Users;
import com.example.demo.service.UsersService;

@RestController
public class UsersController {

    @Autowired
    private UsersService usersService;
    @Autowired
    private ManagerService managerService;

    @PostMapping("/create_user")
    public ResponseEntity<String> createUser(@RequestBody Users users) {
        try {
            if (users.getFullName() == null || users.getFullName().isEmpty())
                throw new IllegalArgumentException("Invalid Full Name");
            users.setMobNum(users.getMobNum().replaceAll("[\\s\\-()]", ""));
            // Check for country code +91 or prefix 0 and remove it
            if (users.getMobNum().startsWith("+91")) {
                users.setMobNum(users.getMobNum().substring(3));
            } else if (users.getMobNum().startsWith("91")) {
                users.setMobNum(users.getMobNum().substring(2));
            } else if (users.getMobNum().startsWith("0")) {
                users.setMobNum(users.getMobNum().substring(1));
            }
            // Check if the remaining number is exactly 10 digits
            if (users.getMobNum().length() != 10 || !users.getMobNum().matches("\\d{10}")) {
                throw new IllegalArgumentException("Invalid mobile number");
            }
            if (users.getPanNum() == null) {
                throw new IllegalArgumentException("PAN cannot be null");
            }
            // Convert to uppercase
            users.setPanNum(users.getPanNum().toUpperCase());
            // Regex to match the PAN format (e.g., AABCP1234C)
            String panRegex = "[A-Z]{5}[0-9]{4}[A-Z]";
            if (!users.getPanNum().matches(panRegex)) {
                throw new IllegalArgumentException("Invalid PAN format");
            }
            if (managerService.findManager(users.getManagerId())) {
                throw new IllegalArgumentException("Manager not found");
            }
            users.setCreatedAt(LocalDateTime.now());
            Users createdUser = usersService.createUser(users);
            return ResponseEntity.status(HttpStatus.CREATED).body("User created with ID: " + createdUser.getUserId());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/get_user")
    public ResponseEntity<?> getUsers(@RequestBody(required = false) Users getRequest) {
        if (getRequest == null) {
            // If no request body is provided, retrieve all users from the database
            List<Users> users = usersService.getUsers();
            return ResponseEntity.ok(users);
        } else if (getRequest.getUserId() != null) {
            // If user_id is provided, retrieve the user by user_id
            Users user = usersService.getUserById(getRequest.getUserId());
            if (user == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(user);
        } else if (getRequest.getMobNum() != null) {
            // If mob_num is provided, retrieve the user by mob_num
            Users user = usersService.getUserByMobNum(getRequest.getMobNum());
            if (user == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(user);
        } else if (getRequest.getManagerId() != null) {
            // If manager_id is provided, retrieve all users with that manager
            List<Users> users = usersService.getUsersByManagerId(getRequest.getManagerId());
            return ResponseEntity.ok(users);
        }
        return ResponseEntity.badRequest().body("Invalid request");
    }


    @PostMapping("/delete_user")
    public ResponseEntity<?> deleteUser(@RequestBody Users request) {
        // Check if user_id or mob_num is provided
        if (request.getUserId() != null) {
            // If user_id is provided, attempt to delete user by user_id
            try {
                usersService.deleteUser(request.getUserId());
                return ResponseEntity.ok("User deleted with ID: " + request.getUserId());
            } catch (Exception e) {
                return ResponseEntity.badRequest().body(" User not found");
            }

        } else if (request.getMobNum() != null) {
            try {
                usersService.deleteUserByMobNum(request.getMobNum());
                return ResponseEntity.ok("User deleted with MobileNumber " + request.getMobNum());
            } catch (Exception e) {
                return ResponseEntity.badRequest().body(" User not found");
            }
        }
        return ResponseEntity.badRequest().body(" User not found");
    }


@PostMapping("/update_user")
public ResponseEntity<?> updateUser(@RequestBody UpdateRequest updateRequest) {
    try {
        List<UUID> userIds = updateRequest.getUserIds();
        Map<String, Object> updateData = updateRequest.getUpdateData();

        // Check if user_ids and update_data are provided
        if (userIds == null || updateData == null) {
            return ResponseEntity.badRequest().body("UserIds and updateData are required.");
        }

         //Check if bulk update is attempted and validate
        if (userIds.size() > 1 && !updateData.containsKey("managerId")) {
            return ResponseEntity.badRequest().body("Bulk update for manager_id is only allowed for manager id.");
        }
        if (userIds.size() > 1 && updateData.containsKey("managerId")) {
            for (UUID userId : userIds){
                Users user = usersService.getUserById(userId);
                UUID updatedID = UUID.fromString((String) updateData.get("managerId"));
                user.setManagerId(updatedID);
            }
            return ResponseEntity.badRequest().body("Bulk update for manager_id is only allowed for manager id.");
        }

        for (UUID userId : userIds) {
            Users user = usersService.getUserById(userId);
            if (user == null) {
                return (ResponseEntity<?>) ResponseEntity.notFound();
            }
            if (updateData.containsKey("fullName")) {
                user.setFullName((String) updateData.get("fullName"));
            }
            if (updateData.containsKey("mob_num")) {
                String mobNum = (String) updateData.get("mobNum");
                // Validate and format mobile number
                // Your validation logic here
                user.setMobNum(mobNum);
            }
            if (updateData.containsKey("pan_num")) {
                String panNum = (String) updateData.get("panNum");
                user.setPanNum(panNum);
            }
            if (updateData.containsKey("managerId")) {
                UUID managerId = (UUID) updateData.get("managerId");
                // Check if manager exists
                if (managerService.findManager(managerId)) {
                    return ResponseEntity.badRequest().body("Manager with ID " + managerId + " not found.");
                }

            }

            // Update the user
            usersService.updateUser(user);
            user.setUpdatedAt(LocalDateTime.now());
        }

        return ResponseEntity.ok("Users updated successfully.");
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update users: " + e.getMessage());
    }
}


}





