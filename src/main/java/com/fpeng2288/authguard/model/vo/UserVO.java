package com.fpeng2288.authguard.model.vo;

import lombok.Builder;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Set;

/**
 * ClassName: UserVO
 * Package: com.fpeng2288.authguard.model.vo
 * Description:
 *
 * @author Fan Peng
 * Create 2024/12/23 23:55
 * @version 1.0
 */
@Data
@Builder
public class UserVO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String username;
    private String maskedEmail;
    private Set<String> roles;
    private ZonedDateTime createdAt;
}

