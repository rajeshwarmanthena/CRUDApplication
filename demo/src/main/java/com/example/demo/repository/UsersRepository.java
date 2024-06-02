package com.example.demo.repository;


import java.util.List;
import java.util.UUID;

import com.example.demo.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users, UUID> {

    Users findByMobNum(String mobNum);
    List<Users> findByManagerId(UUID managerId);

}
