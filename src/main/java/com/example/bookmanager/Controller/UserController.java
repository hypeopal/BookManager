package com.example.bookmanager.Controller;

import com.example.bookmanager.Annotation.RequireAdmin;
import com.example.bookmanager.DTO.UserRequest;
import com.example.bookmanager.Exception.BusinessException;
import com.example.bookmanager.Service.UserService;
import com.example.bookmanager.Utils.*;
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
    * sign up an account
    * */
    @PostMapping("/signup")
    public Result signup(@Valid @RequestBody UserRequest userRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new BusinessException(3, 400, bindingResult.getAllErrors().getFirst().getDefaultMessage());
        }
        // check if username exists
        if (userService.isUsernameExists(userRequest.getUsername())) {
            throw new BusinessException(2, 400, "Username already exists");
        }
        // not exists
        userService.signup(userRequest.getUsername(), userRequest.getPassword());
        return Result.success("Signup successfully");
    }

    /*
    * login
    * */
    @GetMapping("/login")
    public Result login(@Valid @RequestBody UserRequest userRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new BusinessException(3, 400, bindingResult.getAllErrors().getFirst().getDefaultMessage());
        }
        if (userService.isUsernameExists(userRequest.getUsername())) {
            UserClaims claims = userService.validateLogin(userRequest.getUsername(), userRequest.getPassword());
            if (claims != null) {
                String token = JWTUtil.generateToken(claims);
                Map<String, String> map = Map.of("token", token);
                return ResultData.success("Login successfully", map);
            }
        }
        throw new BusinessException(2, 400, "Invalid username or password");
    }

    @GetMapping("/info")
    public Result getUserInfo() {
        UserClaims claims = ThreadLocalUtil.get();
        return Result.success(claims.getUsername() + " info");
    }

    @DeleteMapping
    public Result cancelUser() {
        UserClaims claims = ThreadLocalUtil.get();
        userService.deleteUser(claims.getUsername());
        return Result.success("Delete user successfully");
    }

    @RequireAdmin
    @DeleteMapping("/{username}")
    public Result deleteUser(@PathVariable("username") String username) {
        try {
            userService.deleteUser(username);
            return Result.success("Delete user successfully");
        } catch (Exception e) {
            throw new BusinessException(4, 400, "Failed to delete user: " + e.getMessage());
        }
    }
}
