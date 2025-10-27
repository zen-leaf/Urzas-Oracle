package com.mambocosmo.urzasoracle.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mambocosmo.urzasoracle.entities.CardExpansionSet;

@Repository
public interface CardExpansionSetRepository extends JpaRepository<CardExpansionSet, UUID> {
    List<CardExpansionSet> findByName(String name);

}
