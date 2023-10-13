package com.angelfg.ecommerce.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Table(name = "privileges")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class PrivilegeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_privilege", nullable = false)
    private Long idPrivilege;

    @Column(nullable = false)
    private String name;

    @CreatedDate
    @Column(nullable = false, columnDefinition = "timestamp")
    private LocalDateTime created_at;

    @Column(nullable = false, columnDefinition = "boolean")
    private Boolean disabled = false;

}
