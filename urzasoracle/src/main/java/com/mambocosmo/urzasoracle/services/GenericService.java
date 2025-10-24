package com.mambocosmo.urzasoracle.services;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.JpaRepository;

import com.mambocosmo.urzasoracle.DTO.GenericDTO;
import com.mambocosmo.urzasoracle.converters.GenericConverter;
import com.mambocosmo.urzasoracle.entities.Entity;

import lombok.Data;
import lombok.Getter;

@Getter
@Data
public abstract class GenericService<IDType, E extends Entity, D extends GenericDTO, C extends GenericConverter<E, D>, R extends JpaRepository<E, IDType>> {
    private final R REPOSITORY;
    private final ApplicationContext CONTEXT;
    private final C CONVERTER;

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

    public D getByID(IDType id) {
        Optional<E> e = REPOSITORY.findById(id);
        if (!e.isPresent()) {
            return null;
        }
        return CONVERTER.fromEToD(e.get());
    }

    public boolean delete(IDType id) {
        try {
            REPOSITORY.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
