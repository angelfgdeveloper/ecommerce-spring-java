package com.angelfg.ecommerce.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_access")
@Getter
@Setter
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class UserAccessEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user_access", nullable = false)
    private Long idUserAccess;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private RoleEntity role;

    @ManyToOne
    @JoinColumn(name = "privilege_id")
    private PrivilegeEntity privilege;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Column(nullable = false, columnDefinition = "timestamp")
    private LocalDateTime created_at = LocalDateTime.now();

    @Column(nullable = false, columnDefinition = "boolean")
    private Boolean disabled = false;

    public UserAccessEntity(RoleEntity role, PrivilegeEntity privilege, UserEntity user) {
        this.role = role;
        this.privilege = privilege;
        this.user = user;
    }
}
