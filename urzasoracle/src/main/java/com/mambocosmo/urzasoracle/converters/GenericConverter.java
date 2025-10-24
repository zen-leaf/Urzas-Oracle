package com.mambocosmo.urzasoracle.converters;

import com.mambocosmo.urzasoracle.DTO.GenericDTO;
import com.mambocosmo.urzasoracle.entities.GenericEntity;

public interface GenericConverter<E extends GenericEntity, D extends GenericDTO> {
    public E fromDToE(D dto);

    public D fromEToD(E e);
}
