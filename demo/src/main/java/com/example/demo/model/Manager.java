package com.example.demo.model;


import java.util.UUID;

import jakarta.persistence.*;
import lombok.Setter;

@Setter
@Entity
public class Manager {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID managerId;
    private String name;
    private boolean isActive;

    public Manager() {}
    public boolean getIsActive() {
        return isActive;
    }

    @Override
    public String toString() {
        return "Manager [managerId=" + managerId + ", name=" + name + ", isActive=" + isActive + "]";
    }
}