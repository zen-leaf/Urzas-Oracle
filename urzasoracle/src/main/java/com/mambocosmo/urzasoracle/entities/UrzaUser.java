package com.mambocosmo.urzasoracle.entities;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
@Table(name = "users")
@EqualsAndHashCode(callSuper = true)
public class UrzaUser extends GenericEntity implements UserDetails {

    @Id
    private UUID id;

    private String username;

    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = ""))
    List<String> authorities;

    private String email;

    private String displayName;

    private LocalDate registerDate;

    @Override
    public List<? extends GrantedAuthority> getAuthorities() {

        return authorities.stream().map(e -> new SimpleGrantedAuthority(e)).toList();
    }

    public void setAuthorities(Collection<? extends GrantedAuthority> in) {
        this.authorities = in.stream().map(e -> e.getAuthority()).toList();
    }

    public void setAuthorities(List<String> in) {
        this.authorities = in;

    }

    @Override
    public String getPassword() {

        return this.password;
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

    // AGGIUNGI QUESTO METODO
    public boolean isAdmin() {
        return authorities != null && authorities.stream()
            .anyMatch(auth -> "ROLE_ADMIN".equals(auth.getAuthority()));
    }

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "owner", cascade = { CascadeType.ALL }, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<CardCollection> userDecks;
}
