package com.snackshop.controller;

import com.snackshop.dto.Result;
import com.snackshop.entity.SystemLog;
import com.snackshop.repository.SystemLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/log")
public class SystemLogController {

    @Autowired
    private SystemLogRepository systemLogRepository;

    @GetMapping("/list")
    public Result<List<SystemLog>> list(@RequestParam(required = false) Long userId,
                                        @RequestParam(required = false) String action) {
        if (userId != null) {
            return Result.success(systemLogRepository.findByUserIdOrderByCreateTimeDesc(userId));
        }
        if (action != null) {
            return Result.success(systemLogRepository.findByActionOrderByCreateTimeDesc(action));
        }
        return Result.success(systemLogRepository.findAllByOrderByCreateTimeDesc());
    }
}
