package io.github.ndimovt.WaterTempApp.model;

import io.github.ndimovt.WaterTempApp.roles.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@Entity
@Getter
@Setter
@Table(name = "scientist")
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "username",length = 50, nullable = false)
    @Pattern(regexp = "[A-Za-z0-9]+", message = "Username can contain only letters and numbers!")
    @NotBlank(message = "Username must not be blank!")
    private String username;
    @Column(nullable = false, name = "password")
    @Pattern(regexp = "^.{6,12}$", message = "Password must be between 6 and 12 symbols long!")
    @NotBlank(message = "Password must not be null!")
    private String password;
    @Column(name = "name", length = 15, nullable = false)
    @Pattern(regexp = "[A-Za-z]+", message = "Name can contain only letters!")
    @NotBlank(message = "Name can't be blank!")
    private String name;
    @Column(name = "surname", length = 15, nullable = false)
    @Pattern(regexp = "[A-Za-z]+", message = "Surname can contain only letters!")
    @NotBlank(message = "Surname can't be blank!")
    private String surname;
    @Column(name = "role")
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.role.name()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
