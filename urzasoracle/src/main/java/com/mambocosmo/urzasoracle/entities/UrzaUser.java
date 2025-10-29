package com.mambocosmo.urzasoracle.entities;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
@EqualsAndHashCode(callSuper=true)
public class UrzaUser extends GenericEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @UuidGenerator
    private UUID id;

    private String username;
    
    private String password;

    private String email;

    private String displayName;

    private LocalDate registerDate;
    
    private String role;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "owner", cascade = { CascadeType.ALL }, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    // @JoinColumn(referencedColumnName = "owner")
    private Set<CardCollection> userDecks;

}
