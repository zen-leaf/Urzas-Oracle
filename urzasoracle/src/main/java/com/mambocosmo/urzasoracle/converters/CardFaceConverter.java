package com.mambocosmo.urzasoracle.converters;

import org.springframework.stereotype.Service;

import com.mambocosmo.urzasoracle.DTO.CardFaceDTO;
import com.mambocosmo.urzasoracle.entities.CardFace;

@Service
public class CardFaceConverter implements GenericConverter<CardFace, CardFaceDTO> {

    @Override
    public CardFace fromDToE(CardFaceDTO dto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'fromDToE'");
    }

    @Override
    public CardFaceDTO fromEToD(CardFace e) {
        CardFaceDTO dto = new CardFaceDTO();
        dto.setId(e.getId());
        dto.setName(e.getName());
        dto.setMana_cost(e.getMana_cost());
        dto.setType_line(e.getType_line());
        dto.setOracle_text(e.getOracle_text());
        dto.setPower(e.getPower());
        dto.setToughness(e.getToughness());
        dto.setLoyalty(e.getLoyalty());
        dto.setArtist(e.getArtist());
        dto.setIllustration_id(e.getIllustration_id());
        dto.setImages(e.getImage_uris());
                // System.out.println("converting card face!\n"+dto);

        return dto;
    }

}
