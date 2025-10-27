package com.mambocosmo.urzasoracle.converters;

import org.springframework.stereotype.Service;

import com.mambocosmo.urzasoracle.DTO.CardCollectionDTO;
import com.mambocosmo.urzasoracle.entities.CardCollection;

@Service
public class CardCollectionConverter  implements GenericConverter<CardCollection, CardCollectionDTO>{

    @Override
    public CardCollection fromDToE(CardCollectionDTO dto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'fromDToE'");
    }

    @Override
    public CardCollectionDTO fromEToD(CardCollection e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'fromEToD'");
    }
    
}
