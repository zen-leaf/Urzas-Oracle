package com.mambocosmo.urzasoracle.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mambocosmo.urzasoracle.entities.Card;

@Repository
public interface CardRepository extends JpaRepository<Card, UUID> {

        
    

    List<Card> findByName(String name);
    List<Card> findByNameContainingIgnoreCase(String namePart);
}
