package com.fpeng2288.authguard.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;

/**
 * ClassName: UserRole
 * Package: com.fpeng2288.authguard.model.entity
 * Description:
 *
 * @author Fan Peng
 * Create 2024/12/23 23:31
 * @version 1.0
 */
@Entity
@Table(name = "user_roles")
@Getter
@Setter
@NoArgsConstructor
public class UserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnoreProperties("userRoles")
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @JsonIgnoreProperties("userRoles")
    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @Column(name = "granted_by", length = 50)
    private String grantedBy;

    @Column(name = "expires_at")
    private ZonedDateTime expiresAt;

    @Column(name = "created_at")
    private ZonedDateTime createdAt;

    public UserRole(User user, Role role, String grantedBy) {
        this.user = user;
        this.role = role;
        this.grantedBy = grantedBy;
    }

    public UserRole(User user, Role role, String grantedBy, ZonedDateTime expiresAt) {
        this.user = user;
        this.role = role;
        this.grantedBy = grantedBy;
        this.expiresAt = expiresAt;
    }

    @PrePersist
    protected void onCreate() {
        createdAt = ZonedDateTime.now();
    }

    @Override
    public String toString() {
        return "UserRole{" +
                "id=" + id +
                ", grantedBy='" + grantedBy + '\'' +
                ", createdAt=" + createdAt +
                ", expiresAt=" + expiresAt +
                '}';
    }
}