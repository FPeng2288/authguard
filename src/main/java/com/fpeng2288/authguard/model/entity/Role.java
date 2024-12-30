package com.fpeng2288.authguard.model.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fpeng2288.authguard.model.enums.ERole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * ClassName: Role
 * Package: com.fpeng2288.authguard.model.entity
 * Description:
 *
 * @author Fan Peng
 * Create 2024/12/23 23:31
 * @version 1.0
 */
@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true, length = 20)
    private ERole name;

    @Column(name = "created_at")
    private ZonedDateTime createdAt;

    @JsonManagedReference("role-users")
    @OneToMany(
            mappedBy = "role",
            fetch = FetchType.LAZY
    )
    private Set<UserRole> userRoles = new HashSet<>();

    @PrePersist
    protected void onCreate() {
        createdAt = ZonedDateTime.now();
    }
}
