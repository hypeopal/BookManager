package com.example.bookmanager.Controller;

import com.example.bookmanager.DTO.UserRequest;
import com.example.bookmanager.Service.UserService;
import com.example.bookmanager.Utils.Result;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public Result<String> signup(@RequestBody UserRequest userRequest) {
        // check if username exists
        if (userService.isUsernameExists(userRequest.getUsername())) {
            return Result.error(4, "Username already exists");
        }
        // not exist
        userService.signup(userRequest.getUsername(), userRequest.getPassword());
        return Result.success(null, "Signup successful");
    }

    @GetMapping("/login")
    public Result<String> login(@RequestBody UserRequest userRequest) {
        if (userService.isUsernameExists(userRequest.getUsername())) {
            if (userService.validateLogin(userRequest.getUsername(), userRequest.getPassword())) {
                return Result.success(null, "Login successfully");
            } else {
                return Result.error(5, "Invalid username or password");
            }
        } else {
            return Result.error(6, "User not found");
        }
    }
}
