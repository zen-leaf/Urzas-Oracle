package com.mambocosmo.urzasoracle.repositories;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import com.mambocosmo.urzasoracle.entities.UrzaUser;

@Repository
public interface UserRepository extends JpaRepository<UrzaUser, UUID>{

    

    UrzaUser findByUsernameAndPassword(String username,String password);

    UrzaUser findByEmail(String email);
    UrzaUser findByUsername(String username);
    List<UrzaUser> findByRole(String role);

   
    UrzaUser save(Map<String,String> map);

  
    @Query("UPDATE User u SET u.role = :role WHERE u.username= :username")
    UrzaUser addRole(String username,String role);

   
}
