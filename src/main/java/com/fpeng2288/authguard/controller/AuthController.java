package com.fpeng2288.authguard.controller;

import com.fpeng2288.authguard.common.BaseResponse;
import com.fpeng2288.authguard.common.ResultUtils;
import com.fpeng2288.authguard.model.dto.JwtResponse;
import com.fpeng2288.authguard.model.dto.LoginRequest;
import com.fpeng2288.authguard.model.dto.RegisterRequest;
import com.fpeng2288.authguard.model.vo.UserVO;
import com.fpeng2288.authguard.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * ClassName: AuthController
 * Package: com.fpeng2288.authguard.controller
 * Description: Controller for handling authentication requests
 *
 * @author Fan Peng
 * Create 2024/12/24 15:31
 * @version 1.0
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public BaseResponse<JwtResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResultUtils.success(authService.authenticateUser(request));
    }

    @PostMapping("/register")
    public BaseResponse<UserVO> register(@Valid @RequestBody RegisterRequest request) {
        return ResultUtils.success(authService.registerUser(request));
    }

    @GetMapping("/user/info")
    @PreAuthorize("hasRole('ROLE_USER')")
    public BaseResponse<UserVO> getUserInfo(@RequestParam String username) {
        return ResultUtils.success(authService.getUserInfo(username));
    }

    @GetMapping("/test")
    @PreAuthorize("hasRole('ROLE_USER')")
    public BaseResponse<String> test() {
        return ResultUtils.success("Access granted");
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public BaseResponse<String> adminOnly() {
        return ResultUtils.success("Admin access granted");
    }
}