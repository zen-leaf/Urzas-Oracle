package com.mambocosmo.urzasoracle.converters;

import org.springframework.stereotype.Service;

import com.mambocosmo.urzasoracle.DTO.CommentOnCardDTO;
import com.mambocosmo.urzasoracle.entities.CommentOnCard;

@Service
public class CommentOnCardConverter implements GenericConverter<CommentOnCard, CommentOnCardDTO>{

    @Override
    public CommentOnCard fromDToE(CommentOnCardDTO dto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'fromDToE'");
    }

    @Override
    public CommentOnCardDTO fromEToD(CommentOnCard e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'fromEToD'");
    }

}
