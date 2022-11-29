package com.example.buddy.Controller;

import com.example.buddy.Dto.LogInUserReq;
import com.example.buddy.Dto.LogInUserRes;
import com.example.buddy.config.BaseException;
import com.example.buddy.config.BaseResponse;
import com.example.buddy.Dto.PostUserReq;
import com.example.buddy.Dto.PostUserRes;
import com.example.buddy.Provider.UserProvider;
import com.example.buddy.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import static com.example.buddy.config.BaseResponseStatus.*;
import static com.example.buddy.util.ValidationRegex.isRegexBirthDate;
import static com.example.buddy.util.ValidationRegex.isRegexEmail;

@RestController
public class UserController {

    private final UserProvider userProvider;
    private final UserService userService;

    @Autowired
    public UserController(UserService userService, UserProvider userProvider) {
        this.userProvider = userProvider;
        this.userService = userService;
    }

    @ResponseBody
    @PostMapping("/user")
    public BaseResponse<PostUserRes> signUp(@RequestBody PostUserReq postUserReq) {

        System.out.println(postUserReq.getID());
        System.out.println(postUserReq.getPwd());
        System.out.println(postUserReq.getPwd_retype());
        System.out.println(postUserReq.getNickname());
        System.out.println(postUserReq.getEmail());
        System.out.println(postUserReq.getBirthdate());

        if(postUserReq.getID() == null ||
        postUserReq.getPwd() == null ||
        postUserReq.getEmail() == null ||
        postUserReq.getAddress() == null ||
        postUserReq.getNickname() == null ||
        postUserReq.getBirthdate() == null ||
        postUserReq.getPhone() == null ||
        postUserReq.getWeb() == null ||
        postUserReq.getPwd_retype() == null) {
            return new BaseResponse<>(REQUEST_ERROR);
        }

        if(!isRegexEmail(postUserReq.getEmail())) {
            return new BaseResponse<>(POST_USERS_INVALID_EMAIL);
        }

        if(!postUserReq.getPwd().equals(postUserReq.getPwd_retype())) {
            return new BaseResponse<>(POST_USERS_PASSWORD_NOT_MATCH);
        }

        if(!isRegexBirthDate(postUserReq.getBirthdate())) {
            return new BaseResponse<>(POST_USERS_INVALID_BIRTH_DATE);
        }

        try {
            PostUserRes postUserRes = userService.signUp(postUserReq);
            return new BaseResponse<>(postUserRes);

        } catch(BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    @ResponseBody
    @PostMapping("/user/log-in")
    public BaseResponse<LogInUserRes> logIn(@RequestBody LogInUserReq logInUserReq) {
        try {
            if(logInUserReq.getID() == null || logInUserReq.getPwd() == null) {
                return new BaseResponse<>(REQUEST_ERROR);
            }
            return new BaseResponse<>(userService.logIn(logInUserReq));
        } catch(BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    @ResponseBody
    @GetMapping("/test")
    public BaseResponse<String> test() {
        return new BaseResponse<>("success");
    }

    @ResponseBody
    @GetMapping("/user/test")
    public BaseResponse<PostUserRes> userTest() {
        return new BaseResponse<PostUserRes>(userProvider.getTestUser());
    }

}
