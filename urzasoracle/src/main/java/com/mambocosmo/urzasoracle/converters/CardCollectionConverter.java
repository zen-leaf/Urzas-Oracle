package com.mambocosmo.urzasoracle.converters;

import org.springframework.stereotype.Service;

import com.mambocosmo.urzasoracle.DTO.CardCollectionDTO;
import com.mambocosmo.urzasoracle.entities.CardCollection;

@Service
public class CardCollectionConverter  implements GenericConverter<CardCollection, CardCollectionDTO>{

    @Override
    public CardCollection fromDToE(CardCollectionDTO dto) {
        CardCollection cc = new CardCollection();
            cc.setId(dto.getId());
            cc.setOwner(dto.getOwner());
            cc.setName(dto.getName());
            cc.setDescription(dto.getDescription());
            cc.setLegalIn(dto.getLegalIn());
            cc.setCardList(dto.getCardList());
        return cc;
    }

    @Override
    public CardCollectionDTO fromEToD(CardCollection e) {
        CardCollectionDTO cc = new CardCollectionDTO();
            cc.setId(e.getId());
            cc.setOwner(e.getOwner());
            cc.setName(e.getName());
            cc.setDescription(e.getDescription());
            cc.setLegalIn(e.getLegalIn());
            cc.setCardList(e.getCardList());
        return cc;
    }
    
}
