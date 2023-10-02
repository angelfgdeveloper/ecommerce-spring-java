package com.angelfg.ecommerce.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "\"user\"")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user", nullable = false)
    private Long idUser;

    @Column(nullable = false)
    private String name;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(length = 100, nullable = false, unique = true)
    private String email;

    @Column(nullable = false, length = 200)
    private String password;

    @Column(nullable = false, columnDefinition = "boolean")
    private Boolean locked;

    @Column(nullable = false, columnDefinition = "boolean")
    private Boolean disabled;

}
