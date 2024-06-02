package com.example.demo.controller;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Setter
@Getter
public class UpdateRequest {
    private List<UUID> userIds;
    private Map<String, Object> updateData;

}

