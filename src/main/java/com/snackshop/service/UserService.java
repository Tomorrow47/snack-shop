package com.snackshop.service;

import com.snackshop.entity.User;
import com.snackshop.entity.SystemLog;
import com.snackshop.repository.UserRepository;
import com.snackshop.repository.SystemLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SystemLogRepository systemLogRepository;

    public User register(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("用户名已存在");
        }
        return userRepository.save(user);
    }

    public User login(String username, String password) {
        User user = userRepository.findByUsernameAndStatus(username, 1)
                .orElseThrow(() -> new RuntimeException("用户不存在或已禁用"));
        if (!user.getPassword().equals(password)) {
            throw new RuntimeException("密码错误");
        }
        user.setLastLoginTime(LocalDateTime.now());
        userRepository.save(user);

        // 记录登录日志
        SystemLog log = new SystemLog();
        log.setUserId(user.getId());
        log.setUserType("user");
        log.setAction("login");
        log.setDetail("用户登录: " + user.getUsername());
        systemLogRepository.save(log);

        return user;
    }

    public User updateUser(Long id, User user) {
        User existing = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("用户不存在"));
        if (user.getNickname() != null) existing.setNickname(user.getNickname());
        if (user.getPhone() != null) existing.setPhone(user.getPhone());
        if (user.getDormitory() != null) existing.setDormitory(user.getDormitory());
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            existing.setPassword(user.getPassword());
        }
        return userRepository.save(existing);
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public void toggleStatus(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("用户不存在"));
        user.setStatus(user.getStatus() == 1 ? 0 : 1);
        userRepository.save(user);
    }
}
