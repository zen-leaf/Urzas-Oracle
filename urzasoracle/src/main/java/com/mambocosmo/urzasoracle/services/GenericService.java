package com.mambocosmo.urzasoracle.services;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.JpaRepository;

import com.mambocosmo.urzasoracle.DTO.GenericDTO;
import com.mambocosmo.urzasoracle.converters.GenericConverter;
import com.mambocosmo.urzasoracle.entities.GenericEntity;

import lombok.Getter;

@Getter
public abstract class GenericService<E extends GenericEntity, D extends GenericDTO, C extends GenericConverter<E, D>, R extends JpaRepository<E, UUID>> {
    @Autowired
    private R REPOSITORY;
        @Autowired
    private ApplicationContext CONTEXT;
    @Autowired
    private C CONVERTER;

    public abstract E construct(Map<String, String> fromData);

    public boolean save(Map<String, String> fromData) {
        E e = construct(fromData);
        return save(e);
    }

    public boolean save(E fromEntity) {
        try {
            REPOSITORY.save(fromEntity);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public List<D> getAll() {
        List<E> out = REPOSITORY.findAll();
        List<D> outDTO = out.stream().map(e -> {
            return CONVERTER.fromEToD(e);
        }).toList();
        return outDTO;
    }

    public D getByID(UUID id) {
        Optional<E> e = REPOSITORY.findById(id);
        if (!e.isPresent()) {
            return null;
        }
        return CONVERTER.fromEToD(e.get());
    }
    public E getEntityByID(UUID id) {
        Optional<E> e = REPOSITORY.findById(id);
        if (!e.isPresent()) {
            return null;
        }
        return e.get();
    }

    public boolean delete(UUID id) {
        try {
            REPOSITORY.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
