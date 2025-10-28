package com.mambocosmo.urzasoracle.converters;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.mambocosmo.urzasoracle.DTO.CardDTO;
import com.mambocosmo.urzasoracle.entities.Card;

import lombok.Data;

@Service
@Data
public class CardConverter implements GenericConverter<Card, CardDTO> {

    private final ArtistConverter ARTISTCONVERTER;
    private final CardFaceConverter CARDFACECONVERTER;
    private final CardPartConverter CARDPARTCONVERTER;
    private final CardExpansionSetConverter CARDSETCONVERTER;

    @Override
    public Card fromDToE(CardDTO dto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'fromDToE'");
    }

    @Override
    public CardDTO fromEToD(Card e) {
        // System.out.println("CARDFACELIST " + e.getCard_faces());
        CardDTO dto = new CardDTO();
        dto.setId(e.getId());
        dto.setName(e.getName());
        dto.setImages(e.getImage_uris());
        // System.out.println("ARTISTREFF " + e.getArtistRef());
        dto.setArtistRef(new ArrayList<>());
        if (e.getArtistRef() != null)
            e.getArtistRef().forEach(entry -> {
                dto.getArtistRef().add(getARTISTCONVERTER().fromEToD(entry));
            });

        dto.setCardParts(new ArrayList<>());
        if (e.getAll_parts() != null) {
            e.getAll_parts().forEach(entry -> {
                dto.getCardParts().add(getCARDPARTCONVERTER().fromEToD(entry));
            });
        }
        dto.setCardFaces(new ArrayList<>());
        if (e.getCard_faces() != null) {
            e.getCard_faces().forEach(entry -> {
                dto.getCardFaces().add(getCARDFACECONVERTER().fromEToD(entry));
            });
        }
        dto.setExpansionSet(getCARDSETCONVERTER().fromEToD(e.getExpansion()));

        return dto;
    }

}
