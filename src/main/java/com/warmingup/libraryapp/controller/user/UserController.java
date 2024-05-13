package com.warmingup.libraryapp.controller.user;

import com.warmingup.libraryapp.domain.user.User;
import com.warmingup.libraryapp.dto.user.request.UserCreateRequest;
import com.warmingup.libraryapp.dto.user.request.UserUpdateRequest;
import com.warmingup.libraryapp.dto.user.response.UserResponse;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.bind.annotation.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {

    private final JdbcTemplate jdbcTemplate;

    public UserController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostMapping("/user")
    public void saveUser(@RequestBody UserCreateRequest request) {
        String sql = "INSERT INTO user (name, age) VALUES (?, ?)";
        jdbcTemplate.update(sql, request.getName(), request.getAge());
    }

    @GetMapping("/user")
    public List<UserResponse> getUsers() {
        String sql = "SELECT * FROM user";
        List<UserResponse> userResponseList = jdbcTemplate.query(sql, new RowMapper<UserResponse>() {
            @Override
            public UserResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
                Long id = rs.getLong("id");
                String name = rs.getString("name");
                Integer age = rs.getInt("age");
                return new UserResponse(id, name, age);
            }
        });

        return userResponseList;
    }

    @PutMapping("/user")
    public void updateUser(@RequestBody UserUpdateRequest request) {
        String findSql = "SELECT * FROM user WHERE id = ?";
        boolean userNotExist = jdbcTemplate.query(findSql, (rs, rowNum) -> 0, request.getId()).isEmpty();

        if (userNotExist) {
            throw new IllegalArgumentException(String.format("존재하지 않는 ID(%f)입니다.", request.getId()));
        }

        String sql = "UPDATE user SET name = ? WHERE id = ?";
        jdbcTemplate.update(sql, request.getName(), request.getId());
    }

    @DeleteMapping("/user")
    public void deleteUser(@RequestParam String name) {
        String findSql = "SELECT * FROM user WHERE name = ?";
        boolean userNotExist = jdbcTemplate.query(findSql, (rs, rowNum) -> 0, name).isEmpty();

        if (userNotExist) {
            throw new IllegalArgumentException(String.format("존재하지 않는 이름(%s)입니다.", name));
        }

        String sql = "DELETE FROM user WHERE name = ?";
        jdbcTemplate.update(sql, name);
    }
}
