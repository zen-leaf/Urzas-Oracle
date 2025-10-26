package com.mambocosmo.urzasoracle.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mambocosmo.urzasoracle.entities.Artist;

@Repository
public interface ArtistRepository extends JpaRepository<Artist, String> {

}
