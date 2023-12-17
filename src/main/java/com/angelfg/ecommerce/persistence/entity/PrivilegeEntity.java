package com.angelfg.ecommerce.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Table(name = "privileges")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class PrivilegeEntity {

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id_privilege", nullable = false)

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "privileges_sequence")
    @SequenceGenerator(name = "privileges_sequence", sequenceName = "privileges_sequence", allocationSize = 1)
    @Column(name = "id_privilege", nullable = false)
    private Long idPrivilege;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, columnDefinition = "timestamp")
    private LocalDateTime created_at = LocalDateTime.now();

    @Column(nullable = false, columnDefinition = "boolean")
    private Boolean disabled = false;

}
