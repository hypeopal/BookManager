package com.example.bookmanager.Controller;

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
            return Result.error(7, bindingResult.getAllErrors().getFirst().getDefaultMessage());
        }
        // check if username exists
        if (userService.isUsernameExists(userRequest.getUsername())) {
            throw new BusinessException(4, "Username already exists");
        }
        // not exists
        userService.signup(userRequest.getUsername(), userRequest.getPassword());
        return Result.success("Signup successfully");
    }

    /*
    * login
    * */
    @GetMapping("/login")
    public Result login(@RequestBody UserRequest userRequest) {
        if (userService.isUsernameExists(userRequest.getUsername())) {
            if (userService.validateLogin(userRequest.getUsername(), userRequest.getPassword())) {
                String token = JWTUtil.generateToken(new UserClaims(userRequest.getUsername()));
                Map<String, String> map = Map.of("token", token);
                return ResultData.success("Login successfully", map);
            }
        }
        throw new BusinessException(5, "Invalid username or password");
    }

    @GetMapping("/info")
    public Result getUserInfo() {
        UserClaims claims = ThreadLocalUtil.get();
        ThreadLocalUtil.remove();
        return Result.success(claims.getUsername() + " info");
    }

    @DeleteMapping
    public Result deleteUser() {
        UserClaims claims = ThreadLocalUtil.get();
        userService.deleteUser(claims.getUsername());
        ThreadLocalUtil.remove();
        return Result.success("Delete user successfully");
    }
}
