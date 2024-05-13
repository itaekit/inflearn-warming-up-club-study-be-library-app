package com.warmingup.libraryapp.repository.user;

import com.warmingup.libraryapp.dto.user.response.UserResponse;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserJdbcRepository {
    private final JdbcTemplate jdbcTemplate;

    public boolean findUserByName(String name) {
        String findSql = "SELECT * FROM user WHERE name = ?";
        return jdbcTemplate.query(findSql, (rs, rowNum) -> 0, name).isEmpty();
    }

    public boolean findUserById(Long id) {
        String findSql = "SELECT * FROM user WHERE id = ?";
        return jdbcTemplate.query(findSql, (rs, rowNum) -> 0, id).isEmpty();
    }

    public UserJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void saveUser(String name, Integer age) {
        String sql = "INSERT INTO user (name, age) VALUES (?, ?)";
        jdbcTemplate.update(sql, name, age);
    }

    public List<UserResponse> getUsers() {
        String sql = "SELECT * FROM user";

        List<UserResponse> userResponseList = jdbcTemplate.query(sql, (rs, rowNum) -> {
                Long id = rs.getLong("id");
                String name = rs.getString("name");
                Integer age = rs.getInt("age");
                return new UserResponse(id, name, age);
            }
        );

        return userResponseList;
    }

    public void updateUser(String name, Long id) {
        if (findUserById(id)) {
            throw new IllegalArgumentException(String.format("존재하지 않는 ID(%f)입니다.", id));
        }

        String sql = "UPDATE user SET name = ? WHERE id = ?";
        jdbcTemplate.update(sql, name, id);
    }

    public void deleteUser(String name) {
        if (findUserByName(name)) {
            throw new IllegalArgumentException(String.format("존재하지 않는 이름(%s)입니다.", name));
        }

        String sql = "DELETE FROM user WHERE name = ?";
        jdbcTemplate.update(sql, name);
    }
}
