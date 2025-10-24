package com.mambocosmo.urzasoracle.converters;

import com.mambocosmo.urzasoracle.DTO.GenericDTO;
import com.mambocosmo.urzasoracle.entities.Entity;

public interface GenericConverter<E extends Entity, D extends GenericDTO> {
    public E fromDToE(D dto);
    public D fromEToD(E e);
}
