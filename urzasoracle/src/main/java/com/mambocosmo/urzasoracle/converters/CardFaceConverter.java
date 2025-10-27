package com.mambocosmo.urzasoracle.converters;

import org.springframework.stereotype.Service;

import com.mambocosmo.urzasoracle.DTO.CardFaceDTO;
import com.mambocosmo.urzasoracle.entities.CardFace;

@Service
public class CardFaceConverter implements GenericConverter<CardFace,CardFaceDTO>{

    @Override
    public CardFace fromDToE(CardFaceDTO dto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'fromDToE'");
    }

    @Override
    public CardFaceDTO fromEToD(CardFace e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'fromEToD'");
    }

}
