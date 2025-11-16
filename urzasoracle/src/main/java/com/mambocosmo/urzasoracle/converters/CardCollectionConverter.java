package com.mambocosmo.urzasoracle.converters;

import org.springframework.stereotype.Service;

import com.mambocosmo.urzasoracle.DTO.CardCollectionDTO;
import com.mambocosmo.urzasoracle.entities.CardCollection;
import com.mambocosmo.urzasoracle.entities.CardInDeck;
import com.mambocosmo.urzasoracle.entities.UrzaUser;
import com.mambocosmo.urzasoracle.misc.enums.Format;
import com.mambocosmo.urzasoracle.repositories.CardRepository;

import lombok.Data;

@Service
@Data
public class CardCollectionConverter implements GenericConverter<CardCollection, CardCollectionDTO> {

    private final CardConverter CARDCONVERTER;
    private final CardRepository CARDREPOSITORY;

    @Override
    public CardCollection fromDToE(CardCollectionDTO dto) {
        CardCollection e = new CardCollection();
        UrzaUser tempU = new UrzaUser();
        tempU.setUsername(dto.getOwner());

        e.setId(dto.getId());
        if (dto.getOwner() != null) // is this necessary
            e.setOwner(tempU);
        e.setName(dto.getName());
        e.setDescription(dto.getDescription());
        e.setMainDeckFormat(Format.valueOf(dto.getMainDeckFormat()));

        dto.getCardInDeck().forEach((key,value) -> { // converting dto's Map<CardDTO,String(quantity)> to List<CardInDeck> 
            CardInDeck tempCid = new CardInDeck(e, getCARDREPOSITORY().findById(key.getId()).orElse(null), value);
            e.getCardList().add(tempCid);
        });

        e.setCommander(getCARDCONVERTER().fromDToE(dto.getCommander()));
        e.setPreview(getCARDCONVERTER().fromDToE(dto.getBannerCard()));
        return e;
    }

    @Override
    public CardCollectionDTO fromEToD(CardCollection e) {
        CardCollectionDTO dto = new CardCollectionDTO();
        dto.setId(e.getId());
        if (e.getOwner() != null) // ditto as above
            dto.setOwner(e.getOwner().getUsername());
        dto.setName(e.getName());
        dto.setDescription(e.getDescription());
        dto.setMainDeckFormat(e.getMainDeckFormat().toString());

        e.getCardList().forEach(entry -> { // converting List<CardInDeck> to dto's Map<CardDTO,String(quantity)>
            dto.getCardInDeck().put(
                getCARDCONVERTER().fromEToD(entry.getId().getCard()),
                entry.getQuantity());
        });
        
        if (e.getPreview() != null) {
            dto.setBannerCard(getCARDCONVERTER().fromEToD(e.getPreview()));
        }
        if (e.getCommander() != null) {
            dto.setCommander(getCARDCONVERTER().fromEToD(e.getCommander()));
        }
        return dto;
    }
}
