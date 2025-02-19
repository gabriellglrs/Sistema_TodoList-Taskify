package com.dev.gabriellucas.taskify.entities;

import com.dev.gabriellucas.taskify.enums.RoleType;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.HashSet;
import java.util.Set;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tb_role")
public class Role implements GrantedAuthority {
     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Long id;

     @Enumerated(EnumType.STRING)
     private RoleType authority;

     @ManyToMany(mappedBy = "roles")
     private Set<Usuario> users = new HashSet<>();

     @Override
     public String getAuthority() {
          return authority.name();
     }

     public Role(RoleType authority) {
          this.authority = authority;
     }
}
