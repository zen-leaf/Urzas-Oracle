package com.mambocosmo.urzasoracle.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CardRepository extends JpaRepository<Card, UUID> {

    Page<Card> findByName(String name, Pageable pageable);

    List<Card> findByName(String name);

    Page<Card> findByNameContainingIgnoreCase(String namePart, Pageable pageable);

    List<Card> findByNameContainingIgnoreCase(String namePart);
}
