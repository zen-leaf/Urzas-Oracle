package com.mambocosmo.urzasoracle.converters;

import org.springframework.stereotype.Service;

import com.mambocosmo.urzasoracle.DTO.CardCollectionDTO;
import com.mambocosmo.urzasoracle.entities.CardCollection;

import lombok.Data;

@Service
@Data
public class CardCollectionConverter implements GenericConverter<CardCollection, CardCollectionDTO> {

    private final CardConverter CARDCONVERTER;

    @Override
    public CardCollection fromDToE(CardCollectionDTO dto) {
        CardCollection cc = new CardCollection();
        cc.setId(dto.getId());
        cc.setName(dto.getName());
        cc.setDescription(dto.getDescription());
        cc.setMainDeckFormat(dto.getMainDeckFormat());
        cc.setCommander(getCARDCONVERTER().fromDToE(dto.getCommander()));
        cc.setPreview(getCARDCONVERTER().fromDToE(dto.getBannerCard()));
        return cc;
    }

    @Override
    public CardCollectionDTO fromEToD(CardCollection e) {
        CardCollectionDTO cc = new CardCollectionDTO();
        cc.setId(e.getId());
        cc.setOwner(e.getOwner() != null ? e.getOwner().getUsername() : "");
        cc.setName(e.getName());
        cc.setDescription(e.getDescription());
        cc.setMainDeckFormat(e.getMainDeckFormat());
        if (e.getPreview() != null) {
            cc.setBannerCard(getCARDCONVERTER().fromEToD(e.getPreview()));
        }
        if (e.getCommander() != null) {
            cc.setCommander(getCARDCONVERTER().fromEToD(e.getCommander()));
        }
        return cc;
    }
}
