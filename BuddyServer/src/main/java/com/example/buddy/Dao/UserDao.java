package com.example.buddy.Dao;

import com.example.buddy.Dto.User;
import com.example.buddy.config.BaseException;
import com.example.buddy.Dto.PostUserReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class UserDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public int addUser(PostUserReq postUserReq) {
        String addUserQuery = "insert into User(ID, pwd, nickname, email, birthdate, phone, address, web) values (?, ?, ?, ?, ?, ?, ?, ?);";
        Object[] addUserParams = {postUserReq.getID(), postUserReq.getPwd(), postUserReq.getNickname(), postUserReq.getEmail(), postUserReq.getBirthdate(), postUserReq.getPhone(), postUserReq.getAddress(), postUserReq.getWeb()};

        return jdbcTemplate.update(addUserQuery, addUserParams);
    }

    public int countUserByID(String ID) {
        String countUserByIDQuery = "select count(*) from User where ID = ?";

        int result = jdbcTemplate.queryForObject(countUserByIDQuery, int.class, ID);
        return result;
    }

    public int countUserByNickname(String nickname) {
        String countUserByNicknameQuery = "select count(*) from User where nickname = ?;";
        String countUserByNicknameParams = nickname;

        return jdbcTemplate.queryForObject(countUserByNicknameQuery, int.class, countUserByNicknameParams);
    }

    public Integer getLastInsertUserID() {
        String getLastInsertUserID = "select last_insert_id()";

        return jdbcTemplate.queryForObject(getLastInsertUserID, int.class);
    }

    public String getPasswordByID(String ID) {
        String getPasswordByIDQuery = "select pwd from User where ID = ?;";

        String result = jdbcTemplate.queryForObject(getPasswordByIDQuery, String.class, ID);
        return result;
    }

    public int getTestUser() {
        String getTestUserQuery = "select u_id from User where u_id = 1;";

        return jdbcTemplate.queryForObject(getTestUserQuery, int.class);
    }

    public User getUserByID(String id) {
        String getUserByIDQeury = "select * from User where ID = ?;";

        return jdbcTemplate.queryForObject(getUserByIDQeury,
                (rs, rowNum) -> new User(
                        rs.getInt("u_id"),
                        rs.getString("id"),
                        rs.getString("pwd"),
                        rs.getString("status"),
                        rs.getString("nickname"),
                        rs.getString("email"),
                        rs.getString("birthdate"),
                        rs.getString("phone"),
                        rs.getTimestamp("recent_login"),
                        rs.getInt("online"),
                        rs.getString("address"),
                        rs.getString("web"),
                        rs.getInt("login_num"),
                        rs.getString("quote")),
                id);
    }

    public int updateLogInStatus(Integer u_id) {
        String updateLogInStatusQuery = "update User set recent_login = now(), online = 1, login_num = login_num + 1 where u_id = ?";

        return jdbcTemplate.update(updateLogInStatusQuery, u_id);
    }
}
