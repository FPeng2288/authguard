package com.fpeng2288.authguard.repository;

import com.fpeng2288.authguard.model.entity.Role;
import com.fpeng2288.authguard.model.enums.ERole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * ClassName: RoleRepository
 * Package: com.fpeng2288.authguard.repository
 * Description:
 *
 * @author Fan Peng
 * Create 2024/12/23 23:42
 * @version 1.0
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    /**
     * Find role by name
     * @param name The name of the role
     * @return An Optional containing the Role if found, or empty if not found
     */
    Optional<Role> findByName(ERole name);
}
