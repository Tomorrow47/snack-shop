package com.snackshop.service;

import com.snackshop.entity.Admin;
import com.snackshop.entity.SystemLog;
import com.snackshop.repository.AdminRepository;
import com.snackshop.repository.SystemLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private SystemLogRepository systemLogRepository;

    public Admin login(String username, String password) {
        Admin admin = adminRepository.findByUsernameAndStatus(username, 1)
                .orElseThrow(() -> new RuntimeException("管理员不存在或已禁用"));
        if (!admin.getPassword().equals(password)) {
            throw new RuntimeException("密码错误");
        }
        admin.setLastLoginTime(LocalDateTime.now());
        adminRepository.save(admin);

        SystemLog log = new SystemLog();
        log.setUserId(admin.getId());
        log.setUserType("admin");
        log.setAction("login");
        log.setDetail("管理员登录: " + admin.getUsername());
        systemLogRepository.save(log);

        return admin;
    }

    public Admin findById(Long id) {
        return adminRepository.findById(id).orElse(null);
    }
}
