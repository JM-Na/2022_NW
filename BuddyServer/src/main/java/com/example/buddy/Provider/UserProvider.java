package com.example.buddy.Provider;

import com.example.buddy.Dto.PostUserReq;
import com.example.buddy.Dto.PostUserRes;
import com.example.buddy.Dto.User;
import com.example.buddy.config.BaseException;
import com.example.buddy.Dao.UserDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.buddy.config.BaseResponseStatus.DATABASE_ERROR;
import static com.example.buddy.config.BaseResponseStatus.INSERT_FAIL_USER;

@Service
public class UserProvider {

    private final UserDao userDao;

    @Autowired
    public UserProvider(UserDao userDao) {
        this.userDao = userDao;
    }
    public int countUserByID(String u_id) throws BaseException {
        try {
            return userDao.countUserByID(u_id);
        } catch(Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public int countUserByNickname(String nickname) throws BaseException {
        try {
            return userDao.countUserByNickname(nickname);
        } catch(Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 마지막으로 추가된 행의 u_id 반환
    public int getLastInsertUserID() throws BaseException {
        Integer u_id;
        try {
            u_id = userDao.getLastInsertUserID();
        } catch(Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
        if(u_id == null) throw new BaseException(INSERT_FAIL_USER);

        return u_id.intValue();
    }

    public PostUserRes getTestUser() {
        int data = userDao.getTestUser();
        PostUserRes postUserRes = new PostUserRes();
        postUserRes.setU_id(data);
        return postUserRes;
    }

    // ID로 유저 데이터 반환
    public User getUserByID(String id) throws BaseException {
        try {
            return userDao.getUserByID(id);
        } catch(Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
