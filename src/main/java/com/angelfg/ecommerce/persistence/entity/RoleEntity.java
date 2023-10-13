package com.angelfg.ecommerce.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class RoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_role", nullable = false)
    private Long idRole;

    @Column(nullable = false)
    private String name;

    @CreatedDate
    @Column(nullable = false, columnDefinition = "timestamp")
    private LocalDateTime created_at;

    @Column(nullable = false, columnDefinition = "boolean")
    private Boolean disabled = false;

}
