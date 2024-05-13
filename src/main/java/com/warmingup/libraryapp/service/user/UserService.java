package com.warmingup.libraryapp.service.user;

import com.warmingup.libraryapp.dto.user.request.UserCreateRequest;
import com.warmingup.libraryapp.dto.user.request.UserUpdateRequest;
import com.warmingup.libraryapp.dto.user.response.UserResponse;
import com.warmingup.libraryapp.repository.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void saveUser(UserCreateRequest request) {
        userRepository.saveUser(request.getName(), request.getAge());
    }

    public List<UserResponse> getUsers() {
        return userRepository.getUsers();
    }

    public void updateUser(UserUpdateRequest request) {
        userRepository.updateUser(request.getName(), request.getId());
    }

    public void deleteUser(String name) {
        userRepository.deleteUser(name);
    }
}
