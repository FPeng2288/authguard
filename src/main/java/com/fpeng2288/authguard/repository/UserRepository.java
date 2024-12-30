package com.fpeng2288.authguard.repository;

import com.fpeng2288.authguard.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * ClassName: UserRepository
 * Package: com.fpeng2288.authguard.repository
 * Description:
 *
 * @author Fan Peng
 * Create 2024/12/23 23:41
 * @version 1.0
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Find user by username
     * @param username The username to search for
     * @return An Optional containing the User if found, or empty if not found
     */
    Optional<User> findByUsername(String username);

    /**
     * Check if a user with the given username exists
     * @param username The username to check
     * @return true if a user with the username exists, false otherwise
     */
    boolean existsByUsername(String username);

    /**
     * Check if a user with the given email exists
     * @param email The email to check
     * @return true if a user with the email exists, false otherwise
     */
    boolean existsByEmail(String email);

    /**
     * Find a user by their username with roles
     * @param username The username to search for
     * @return An Optional containing the User if found, or empty if not found
     */
    @Query("SELECT u FROM User u LEFT JOIN FETCH u.userRoles ur LEFT JOIN FETCH ur.role WHERE u.username = :username")
    Optional<User> findByUsernameWithRoles(String username);
}
