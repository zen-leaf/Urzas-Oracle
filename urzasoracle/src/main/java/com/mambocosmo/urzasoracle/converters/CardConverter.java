package com.mambocosmo.urzasoracle.converters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.mambocosmo.urzasoracle.DTO.CardDTO;
import com.mambocosmo.urzasoracle.DTO.CardFaceDTO;
import com.mambocosmo.urzasoracle.entities.Card;
import com.mambocosmo.urzasoracle.entities.CardFace;
import com.mambocosmo.urzasoracle.misc.enums.Format;

import lombok.Data;

@Service
@Data
public class CardConverter implements GenericConverter<Card, CardDTO> {

    private final ArtistConverter ARTISTCONVERTER;
    private final CardFaceConverter CARDFACECONVERTER;
    private final CardPartConverter CARDPARTCONVERTER;
    private final CardExpansionSetConverter CARDSETCONVERTER;
    private final List<Format> relevantFormat = List.of(Format.standard, Format.pioneer, Format.modern, Format.legacy,
            Format.vintage, Format.commander, Format.alchemy, Format.historic, Format.timeless, Format.pauper,
            Format.penny, Format.premodern);

    @Override
    public Card fromDToE(CardDTO dto) {

        // Card e = new Card();
        // e.setId(dto.getId());
        // e.setName(dto.getName());
        // e.setMana_cost(dto.getManaCost());
        // e.setType_line(dto.getTypeline());
        
        // return e;
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'fromDToE'");
    }

    @Override
    public CardDTO fromEToD(Card e) {
        // System.out.println("CARDFACELIST " + e.getCard_faces());
        CardDTO dto = new CardDTO();
        dto.setId(e.getId());
        dto.setName(e.getName());
        String actualmana = e.getMana_cost();
        // System.out.println(actualmana);
        if (e.getMana_cost() == null || e.getMana_cost().equals("")) {
            for (CardFace cf : e.getCard_faces()) {
                actualmana = (cf.getMana_cost().equals("") ? "" : cf.getMana_cost()) + " " + actualmana;
                System.out.println(actualmana);
            }
        }
        actualmana = (actualmana.equals("") ? "{0}" : actualmana);
        // System.out.println(actualmana);
        actualmana = actualmana.strip().replace("} {", "} // {");
        dto.setManaCost(actualmana);
        dto.setTypeline(e.getType_line());
        dto.setImages(e.getImage_uris().isEmpty() ? e.getCard_faces().get(1).getImage_uris() : e.getImage_uris());
        dto.setCardFaces(e.getCard_faces().stream().map(e1 -> getCARDFACECONVERTER().fromEToD(e1)).toList());

        CardFaceDTO defaultBack = e.getCard_faces().size() == 0 || e.getCard_faces().get(0).getImage_uris().isEmpty()
                ? new CardFaceDTO("blank")
                : dto.getCardFaces().get(0);

        dto.setBackFace(defaultBack);
        dto.setReleased_at(String.valueOf(e.getReleased()));
        dto.setFlavorText(e.getFlavor_text());
        dto.setOracleText(e.getOracle_text());
        dto.setColorIdentity(e.getColor_identity().stream().map(entry -> entry.toString()).toList());
        dto.setRarity(e.getRarity());
        dto.setCollectorNumber(e.getCollector_number());
        Map<String, String> tempLegal = new HashMap<>();
        e.getLegalities().forEach((key, value) -> {
            if (relevantFormat.contains(key)) {
                tempLegal.put(key.toString().toLowerCase(), value);
            }
        });
        dto.setLegalities(tempLegal);
        // System.out.println("ARTISTREFF " + e.getArtistRef());
        dto.setArtistIds(new ArrayList<>());
        dto.setArtistNames(new ArrayList<>());

        e.getArtistRef().forEach(entry -> {
            dto.getArtistIds().add(entry.getId());
            dto.getArtistNames().add(entry.getName());
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
