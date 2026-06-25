package com.riwi.talentboard.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.management.relation.Role;
import java.util.List;

@Entity
@Table (name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected int id;

    @Column(unique = true, nullable = false)
    protected String username;

    @Column(nullable = false)
    private String password;

    @Column(unique = true,  nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @OneToMany(mappedBy = "responsibleUser", cascade = CascadeType.ALL)
    private List<Vacancy> vacancies;
}
