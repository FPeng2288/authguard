package com.fpeng2288.authguard.security;

import com.fpeng2288.authguard.common.ErrorCode;
import com.fpeng2288.authguard.exception.BusinessException;
import com.fpeng2288.authguard.model.entity.User;
import com.fpeng2288.authguard.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * ClassName: UserDetailsServiceImpl
 * Package: com.fpeng2288.authguard.security
 * Description: Implementation of Spring Security's UserDetailsService
 *
 * @author Fan Peng
 * Create 2024/12/24 15:09
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    /**
     * Load user by username
     *
     * @param username the username to load
     * @return UserDetails instance
     * @throws UsernameNotFoundException if user not found
     */
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsernameWithRoles(username)
                .orElseThrow(() -> new BusinessException(ErrorCode.INVALID_CREDENTIALS));

        return UserDetailsImpl.build(user);
    }
}
