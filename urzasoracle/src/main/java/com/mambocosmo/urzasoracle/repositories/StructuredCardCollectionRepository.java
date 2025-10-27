package com.mambocosmo.urzasoracle.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mambocosmo.urzasoracle.entities.StructuredCardCollection;

@Repository
public interface StructuredCardCollectionRepository  extends JpaRepository<StructuredCardCollectionRepository, UUID>{

    

    List<StructuredCardCollection> findByName(String name);
 }
