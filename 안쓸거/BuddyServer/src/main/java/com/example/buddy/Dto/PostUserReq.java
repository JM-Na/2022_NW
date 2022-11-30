package com.example.buddy.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// 회원가입시 사용하는 모델
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostUserReq {
    private String ID;
    private String pwd;
    private String pwd_retype;
    private String nickname;
    private String email;
    private String birthdate;
    private String phone;
    private String address;
    private String web;
}
