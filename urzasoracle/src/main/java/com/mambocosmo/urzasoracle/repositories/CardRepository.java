package com.mambocosmo.urzasoracle.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mambocosmo.urzasoracle.entities.Card;

@Repository
public interface CardRepository extends JpaRepository<Card, UUID>, JpaSpecificationExecutor<Card> {

    Page<Card> findByName(String name, Pageable pageable);

    List<Card> findByName(String name);

    @Query(value = "SELECT * FROM cards c ORDER BY RAND()", nativeQuery = true)
    Page<Card> findRandomSubSet(int limit, Pageable pageable);

    // @Query("SELECT c FROM cards c ORDER BY RAND( ) LIMIT 100")
    // Page<Card> findRandomSubSet(Pageable pageable);

    Page<Card> findByNameContainingIgnoreCase(String namePart, Pageable pageable);

    // Page<Card>
    // findByNameOrFlavor_nameOrPrinted_nameOrFlavor_textContainingIgnoreCase(String
    // namePart, Pageable pageable);

    List<Card> findByNameContainingIgnoreCase(String namePart);
}
