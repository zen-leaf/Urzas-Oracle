package com.mambocosmo.urzasoracle.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mambocosmo.urzasoracle.entities.CardFace;

@Repository
public interface CardFaceRepository extends JpaRepository<CardFace, String> {
}