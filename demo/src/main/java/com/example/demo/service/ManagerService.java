package com.example.demo.service;

import com.example.demo.repository.ManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ManagerService {
    @Autowired
    private  ManagerRepository managerRepository;
     public  boolean findManager(UUID managerId){
         return managerRepository.findById(managerId).isEmpty();
     }
}
