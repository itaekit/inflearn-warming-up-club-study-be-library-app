package com.warmingup.libraryapp.controller.user;

import com.warmingup.libraryapp.domain.user.User;
import com.warmingup.libraryapp.dto.user.request.UserCreateRequest;
import com.warmingup.libraryapp.dto.user.response.UserResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {

    private final List<User> userList = new ArrayList<>();

    @PostMapping("/user")
    public void saveUser(@RequestBody UserCreateRequest request) {
        userList.add(new User(request.getName(), request.getAge()));
    }

    @GetMapping("/user")
    public List<UserResponse> getUsers() {
        List<UserResponse> userResponseList = new ArrayList<>();
        for (int i = 0; i < userList.size(); ++i) {
            userResponseList.add(new UserResponse(Long.valueOf(i + 1), userList.get(i)));
        }

        return userResponseList;
    }
}
