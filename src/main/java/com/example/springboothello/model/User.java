package com.example.springboothello.model;

import lombok.Data;

@Data
public class User {
    private Long id;
    private String name;
    private String email;
    private String createdAt;
    // 可以根据需要添加更多字段
}