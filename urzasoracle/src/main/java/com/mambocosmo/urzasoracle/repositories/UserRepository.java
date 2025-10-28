package com.mambocosmo.urzasoracle.repositories;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import com.mambocosmo.urzasoracle.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, UUID>{

    

    User findByUsernameAndPassword(String username,String password);

    User findByEmail(String email);
    User findByUsername(String username);
    List<User> findByRole(String role);

   
    User save(Map<String,String> map);

  
    @Query("UPDATE User u SET u.role = :role WHERE u.username= :username")
    User addRole(String username,String role);

   
}
