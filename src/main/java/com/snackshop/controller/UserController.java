package com.snackshop.controller;

import com.snackshop.dto.Result;
import com.snackshop.entity.User;
import com.snackshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public Result<User> register(@RequestBody User user) {
        try {
            return Result.success(userService.register(user));
        } catch (RuntimeException e) {
            return Result.fail(e.getMessage());
        }
    }

    @PostMapping("/login")
    public Result<User> login(@RequestBody Map<String, String> params) {
        try {
            String username = params.get("username");
            String password = params.get("password");
            User user = userService.login(username, password);
            // 不返回密码
            user.setPassword(null);
            return Result.success(user);
        } catch (RuntimeException e) {
            return Result.fail(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public Result<User> getById(@PathVariable Long id) {
        return userService.findById(id)
                .map(u -> { u.setPassword(null); return Result.success(u); })
                .orElse(Result.fail("用户不存在"));
    }

    @GetMapping("/list")
    public Result<List<User>> list() {
        List<User> users = userService.findAll();
        users.forEach(u -> u.setPassword(null));
        return Result.success(users);
    }

    @PutMapping("/{id}")
    public Result<User> update(@PathVariable Long id, @RequestBody User user) {
        try {
            User updated = userService.updateUser(id, user);
            updated.setPassword(null);
            return Result.success(updated);
        } catch (RuntimeException e) {
            return Result.fail(e.getMessage());
        }
    }

    @PutMapping("/{id}/toggle")
    public Result<Void> toggleStatus(@PathVariable Long id) {
        userService.toggleStatus(id);
        return Result.success();
    }
}
