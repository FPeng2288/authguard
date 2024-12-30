package com.fpeng2288.authguard.service;

import com.fpeng2288.authguard.model.dto.JwtResponse;
import com.fpeng2288.authguard.model.dto.LoginRequest;
import com.fpeng2288.authguard.model.dto.RegisterRequest;
import com.fpeng2288.authguard.model.vo.UserVO;

/**
 * ClassName: AuthService
 * Package: com.fpeng2288.authguard.service
 * Description:
 *
 * @author Fan Peng
 * Create 2024/12/24 15:31
 * @version 1.0
 */
public interface AuthService {
    /**
     * Authenticate user and return JwtResponse
     * @param loginRequest the login request containing credentials
     * @return JwtResponse containing masked user info and token
     */
    JwtResponse authenticateUser(LoginRequest loginRequest);

    /**
     * Register a new user and return UserVO
     * @param registerRequest the registration request
     * @return UserVO containing masked user info
     */
    UserVO registerUser(RegisterRequest registerRequest);

    /**
     * Get user info by username
     * @param username the username to get info for
     * @return UserVO containing masked user info
     */
    UserVO getUserInfo(String username);
}