package com.mambocosmo.urzasoracle.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mambocosmo.urzasoracle.entities.CardCollection;

@Repository
public interface CardCollectionRepository extends JpaRepository<CardCollection, UUID> {
    
    Page<CardCollection> findByName(String name, Pageable pageable);
    
    List<CardCollection> findByName(String name);
    
    Page<CardCollection> findByDescription(String description, Pageable pageable);

 
}
