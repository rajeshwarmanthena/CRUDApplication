package com.example.demo.model;


import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
@Entity
public class Users{

    @Id
    @GeneratedValue
    private UUID userId;


    private String fullName;
    private String mobNum;
    private String panNum;
    private UUID managerId;
    private boolean isActive = true;
    @Setter
    private LocalDateTime createdAt;
    @Setter
    private LocalDateTime updatedAt;

    public Users() {

    }


    @Override
    public String toString() {
        return "User [userId=" + userId + ", fullName=" + fullName + ", mobNum=" + mobNum + ", panNum=" + panNum
                + ", managerId=" + managerId + ", isActive=" + isActive + ", createdAt=" + createdAt + ", updatedAt="
                + updatedAt + "]";
    }





}
