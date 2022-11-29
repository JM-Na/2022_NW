package com.example.buddy.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Integer u_id;
    private String ID;
    private String pwd;
    private String status;
    private String nickname;
    private String email;
    private String birthdate;
    private String phone;
    private Timestamp recent_login;
    private Integer online;
    private String address;
    private String web;
    private Integer login_num;
    private String quote;
}
