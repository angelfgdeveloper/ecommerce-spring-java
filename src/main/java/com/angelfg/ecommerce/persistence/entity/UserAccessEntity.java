package com.angelfg.ecommerce.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_access")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class UserAccessEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user_access", nullable = false)
    private Long idUserAccess;

    @CreatedDate
    @Column(nullable = false, columnDefinition = "timestamp")
    private LocalDateTime created_at;

    @Column(nullable = false, columnDefinition = "boolean")
    private Boolean disabled = false;

}
