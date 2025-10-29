package apolov2.insightsApolo.v2.infrastructure.adapter.out.jpa.entity;

import apolov2.insightsApolo.v2.core.domain.util.Genero;
import apolov2.insightsApolo.v2.core.domain.util.Role;
import apolov2.insightsApolo.v2.infrastructure.adapter.out.jpa.converter.RoleConverter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "usuarios")
@Entity(name = "Usuario")
@EqualsAndHashCode(of = "id")
public class UsuarioEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String cpf;
    private LocalDate dataNascimento;
    private String telefone;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private Genero genero;

    private String email;
    private String senha;

    @Convert(converter = RoleConverter.class)
    @Column(name = "role", nullable = false)
    private Role role = Role.CLIENTE;

    @PrePersist
    @PreUpdate
    private void validateRole() {
        if (this.role == null) {
            this.role = Role.CLIENTE;
        }
    }

    @PostLoad
    private void ensureRoleIsSet() {
        if (this.role == null) {
            this.role = Role.CLIENTE;
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return email;
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
