package com.example.buddy.Service;

import com.example.buddy.Dto.LogInUserReq;
import com.example.buddy.Dto.LogInUserRes;
import com.example.buddy.config.BaseException;
import com.example.buddy.Dao.UserDao;
import com.example.buddy.Dto.PostUserReq;
import com.example.buddy.Dto.PostUserRes;
import com.example.buddy.Provider.UserProvider;

import com.example.buddy.util.AES128;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Service;

import static com.example.buddy.config.BaseResponseStatus.*;
import static com.example.buddy.config.secret.Secret.USER_INFO_PASSWORD_KEY;

@Service
public class UserService {

    private final UserDao userDao;
    private final UserProvider userProvider;
    private final AES128 key;

    @Autowired
    public UserService(UserProvider userProvider, UserDao userDao) {
        this.userDao = userDao;
        this.userProvider = userProvider;
        this.key = new AES128(USER_INFO_PASSWORD_KEY);
    }
    public PostUserRes signUp(PostUserReq postUserReq) throws BaseException {
        int userIDCount, userNicknameCount;
        // ID와 닉네임 중복 검사
        try {
            userIDCount = userProvider.countUserByID(postUserReq.getID());
            userNicknameCount = userProvider.countUserByNickname(postUserReq.getNickname());
        }catch(Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
        if(userIDCount != 0) throw new BaseException(POST_USERS_EXISTS_ID);
        if(userNicknameCount != 0) throw new BaseException(POST_USERS_EXISTS_NICKNAME);

        // 비밀번호 암호화
        try {
            postUserReq.setPwd(key.encrypt(postUserReq.getPwd()));
        } catch (Exception exception) {
            throw new BaseException(PASSWORD_ENCRYPTION_ERROR);
        }

        int addUser;
        int u_id;
        // 유저 목록에 유저 추가
        try {
            addUser = userDao.addUser(postUserReq);
            u_id = userProvider.getLastInsertUserID();
        }catch(Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }

        // 업데이트 된 행의 개수 저장, 0이면 업데이트 되지 않은 것으로 예외처리
        if(addUser == 0) throw new BaseException(INSERT_FAIL_USER);
        PostUserRes postUserRes = new PostUserRes();
        postUserRes.setU_id(u_id);

        return postUserRes;
    }

    public LogInUserRes logIn(LogInUserReq logInUserReq) throws BaseException {
        String queryPwd;
        try {
            queryPwd = userDao.getPasswordByID(logInUserReq.getID());
        } catch(IncorrectResultSizeDataAccessException exception) {
            throw new BaseException(FAILED_TO_LOGIN);
        } catch(Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }

        boolean isPwdMatched;
        // 비밀번호 복호화한 뒤 일치 확인
        try {
            isPwdMatched = logInUserReq.getPwd().equals(key.decrypt(queryPwd));
        } catch(Exception exception) {
            throw new BaseException(PASSWORD_DECRYPTION_ERROR);
        }

        // 비밀번호 일치하지 않으면 예외처리
        if(!isPwdMatched) {
            throw new BaseException(FAILED_TO_LOGIN);
        }

        try {
            LogInUserRes logInUserRes = new LogInUserRes(userProvider.getUserByID(logInUserReq.getID()).getU_id());
            int patchResult = userDao.updateLogInStatus(logInUserRes.getU_id());
            if(patchResult == 0) throw new BaseException(DATABASE_ERROR);

            return logInUserRes;
        } catch(Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
