package com.mambocosmo.urzasoracle.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mambocosmo.urzasoracle.entities.Card;

@Repository
public interface CardRepository extends JpaRepository<Card, UUID> {

}
