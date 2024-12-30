package com.fpeng2288.authguard.repository;

import com.fpeng2288.authguard.model.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * ClassName: UserRoleRepository
 * Package: com.fpeng2288.authguard.repository
 * Description:
 *
 * @author Fan Peng
 * Create 2024/12/23 23:45
 * @version 1.0
 */
@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

}
