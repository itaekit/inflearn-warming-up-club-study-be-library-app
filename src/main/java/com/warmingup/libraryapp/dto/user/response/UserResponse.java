package com.warmingup.libraryapp.dto.user.response;

import com.warmingup.libraryapp.domain.user.User;

public class UserResponse {
    private final Long id;
    private final String name;
    private final Integer age;

    public UserResponse(Long id, User user) {
        this.id = id;
        this.name = user.getName();
        this.age = user.getAge();
    }

    public UserResponse(Long id, String name, Integer age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }
}
