package com.mambocosmo.urzasoracle.repositories;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Repository;

import com.mambocosmo.urzasoracle.entities.UrzaUser;

@Repository
public interface UrzaUserRepository extends JpaRepository<UrzaUser, UUID> {

    UrzaUser findByUsernameAndPassword(String username, String password);

    UrzaUser findByEmail(String email);

    UrzaUser findByUsername(String username);

    List<UrzaUser> findByAuthorities(List<? extends GrantedAuthority> role);

    UrzaUser save(Map<String, String> map);

    // UrzaUser addRole();

}
