package com.example.bookmanager.Controller;

import com.example.bookmanager.Annotation.RequireAdmin;
import com.example.bookmanager.DTO.ChangePassword;
import com.example.bookmanager.DTO.UserRequest;
import com.example.bookmanager.Exception.BusinessException;
import com.example.bookmanager.Service.UserService;
import com.example.bookmanager.Utils.Result;
import com.example.bookmanager.Utils.ResultData;
import com.example.bookmanager.Utils.ThreadLocalUtil;
import com.example.bookmanager.Utils.UserClaims;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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
        try {
            return ResultData.success("Login successfully", userService.login(userRequest.getUsername(), userRequest.getPassword()));
        } catch (Exception e) {
            throw new BusinessException(2, 400, "Invalid username or password");
        }
    }

    @RequireAdmin
    @GetMapping
    public Result getUserList() {
        try {
            return ResultData.success("Get user list successfully", userService.getUserList());
        } catch (Exception e) {
            throw new BusinessException(4, 400, "Failed to get user list: " + e.getMessage());
        }
    }

    @GetMapping("/info")
    public Result getUserInfo() {
        return Result.success("info");
    }

    @DeleteMapping
    public Result cancelUser() {
        UserClaims claims = ThreadLocalUtil.get();
        try {
            userService.deleteUser(claims.getUsername());
            return Result.success("Delete user successfully");
        } catch (Exception e) {
            throw new BusinessException(4, 400, "Failed to delete user: " + e.getMessage());
        }
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

    @RequireAdmin
    @PatchMapping("/setAdmin/{id}")
    public Result setAdmin(@PathVariable("id") Long id) {
        try {
            userService.setAdmin(id);
            return Result.success("Set admin successfully");
        } catch (BusinessException e) {
            throw e;
        }catch (Exception e) {
            throw new BusinessException(4, 400, "Failed to set admin: " + e.getMessage());
        }
    }

    @RequireAdmin
    @PatchMapping("/unsetAdmin/{id}")
    public Result unsetAdmin(@PathVariable("id") Long id) {
        try {
            userService.unsetAdmin(id);
            return Result.success("Unset admin successfully");
        } catch (BusinessException e) {
            throw e;
        }catch (Exception e) {
            throw new BusinessException(4, 400, "Failed to unset admin: " + e.getMessage());
        }
    }

    @RequireAdmin
    @PatchMapping("/ban/{id}")
    public Result banUser(@PathVariable("id") Long id) {
        try {
            userService.banUser(id);
            return Result.success("Ban user successfully");
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            throw new BusinessException(4, 400, "Failed to ban user: " + e.getMessage());
        }
    }

    @RequireAdmin
    @PatchMapping("/unban/{id}")
    public Result unbanUser(@PathVariable("id") Long id) {
        try {
            userService.unbanUser(id);
            return Result.success("Unban user successfully");
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            throw new BusinessException(4, 400, "Failed to unban user: " + e.getMessage());
        }
    }

    @PatchMapping("/password")
    public Result changePassword(@RequestBody @Valid ChangePassword userRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new BusinessException(3, 400, bindingResult.getAllErrors().getFirst().getDefaultMessage());
        }
        try {
            userService.changePassword(userRequest);
            return Result.success("Change password successfully");
        } catch (BusinessException e) {
            throw e;
        }catch (Exception e) {
            throw new BusinessException(4, 400, "Failed to change password: " + e.getMessage());
        }
    }
}
