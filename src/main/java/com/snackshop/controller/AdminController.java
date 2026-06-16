package com.snackshop.controller;

import com.snackshop.dto.Result;
import com.snackshop.entity.Admin;
import com.snackshop.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/login")
    public Result<Admin> login(@RequestBody Map<String, String> params) {
        try {
            String username = params.get("username");
            String password = params.get("password");
            Admin admin = adminService.login(username, password);
            admin.setPassword(null);
            return Result.success(admin);
        } catch (RuntimeException e) {
            return Result.fail(e.getMessage());
        }
    }
}
