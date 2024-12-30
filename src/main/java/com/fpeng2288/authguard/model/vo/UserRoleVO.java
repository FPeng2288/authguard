package com.fpeng2288.authguard.model.vo;

import lombok.Builder;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * ClassName: UserRoleVO
 * Package: com.fpeng2288.authguard.model.vo
 * Description:
 *
 * @author Fan Peng
 * Create 2024/12/24 0:08
 * @version 1.0
 */
@Data
@Builder
public class UserRoleVO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String username;
    private String roleName;
    private String grantedBy;
    private ZonedDateTime grantedAt;
}