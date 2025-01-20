package com.example.bookmanager.Controller;

import com.example.bookmanager.DTO.UserRequest;
import com.example.bookmanager.Exception.BusinessException;
import com.example.bookmanager.Service.UserService;
import com.example.bookmanager.Utils.JWTUtil;
import com.example.bookmanager.Utils.Result;
import com.example.bookmanager.Utils.UserClaims;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /*
    * sign up a user
    * */
    @PostMapping("/signup")
    public Result<String> signup(@Valid @RequestBody UserRequest userRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return Result.error(7, bindingResult.getAllErrors().getFirst().getDefaultMessage());
        }
        // check if username exists
        if (userService.isUsernameExists(userRequest.getUsername())) {
            throw new BusinessException(4, "Username already exists");
        }
        // not exist
        userService.signup(userRequest.getUsername(), userRequest.getPassword());
        return Result.success(null, "Signup successfully");
    }

    /*
    * login
    * */
    @GetMapping("/login")
    public Result<Map<String, String>> login(@RequestBody UserRequest userRequest) {
        if (userService.isUsernameExists(userRequest.getUsername())) {
            if (userService.validateLogin(userRequest.getUsername(), userRequest.getPassword())) {
                String token = JWTUtil.generateToken(new UserClaims(userRequest.getUsername()));
                Map<String, String> map = Map.of("token", token);
                return Result.success(map, "Login successfully");
            }
        }
        throw new BusinessException(5, "Invalid username or password");
    }
}
