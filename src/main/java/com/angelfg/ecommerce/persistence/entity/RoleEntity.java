package com.angelfg.ecommerce.persistence.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RoleEntity {

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id_role", nullable = false)

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "roles_sequence")
    @SequenceGenerator(name = "roles_sequence", sequenceName = "roles_sequence", allocationSize = 1)
    @Column(name = "id_role", nullable = false)
    private Long idRole;

    @Column(nullable = false)
    private String name;

    @CreatedDate
    @Column(nullable = false, columnDefinition = "timestamp")
    private LocalDateTime created_at = LocalDateTime.now();

    @Column(nullable = false, columnDefinition = "boolean")
    private Boolean disabled = false;

}
