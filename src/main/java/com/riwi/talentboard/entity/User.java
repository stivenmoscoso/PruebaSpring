package com.riwi.talentboard.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.riwi.talentboard.enums.Role;
import java.util.Collection;
import java.util.List;

@Entity
@Table (name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Formateamos el Rol como "ROLE_ADMIN", "ROLE_RECRUITER", etc. para Spring Security
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }
    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return true; }
}
