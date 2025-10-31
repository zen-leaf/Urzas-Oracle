package com.mambocosmo.urzasoracle.converters;

import org.springframework.stereotype.Service;

import com.mambocosmo.urzasoracle.DTO.CardCollectionDTO;
import com.mambocosmo.urzasoracle.entities.CardCollection;
import com.mambocosmo.urzasoracle.entities.CardDeckPK;
import com.mambocosmo.urzasoracle.entities.CardInDeck;
import com.mambocosmo.urzasoracle.misc.enums.Format;

import lombok.Data;

@Data
@Service
public class CardCollectionConverter implements GenericConverter<CardCollection, CardCollectionDTO> {

    private final UrzaUserConverter URZAUSERCONVERTER;
    private final CardConverter CARDCONVERTER;

    @Override
    public CardCollection fromDToE(CardCollectionDTO dto) {
        CardCollection e = new CardCollection();
        e.setId(dto.getId());
        if (dto.getOwner() != null) // is this necessary
            e.setOwner(getURZAUSERCONVERTER().fromDToE(dto.getOwner()));
        e.setName(dto.getName());
        e.setDescription(dto.getDescription());
        e.setMainDeckFormat(Format.valueOf(dto.getMainDeckFormat()));

        dto.getCardInDeck().forEach((key,value) -> { // converting dto's Map<CardDTO,String(quantity)> to List<CardInDeck> 
            CardInDeck tempCid = new CardInDeck();
            tempCid.setId(
                new CardDeckPK( e, getCARDCONVERTER().fromDToE(key))
            );
            tempCid.setQuantity(value);
            e.getCardList().add(tempCid);
        });
        return e;
    }

    @Override
    public CardCollectionDTO fromEToD(CardCollection e) {
        CardCollectionDTO dto = new CardCollectionDTO();
        dto.setId(e.getId());
        if (e.getOwner() != null) // ditto as above
            dto.setOwner(getURZAUSERCONVERTER().fromEToD(e.getOwner()));
        dto.setName(e.getName());
        dto.setDescription(e.getDescription());
        dto.setMainDeckFormat(e.getMainDeckFormat().toString());

        e.getCardList().forEach(entry -> { // converting List<CardInDeck> to dto's Map<CardDTO,String(quantity)>
            dto.getCardInDeck().put(
                getCARDCONVERTER().fromEToD(entry.getId().getCard()),
                entry.getQuantity());
        });
        return dto;
    }
}
