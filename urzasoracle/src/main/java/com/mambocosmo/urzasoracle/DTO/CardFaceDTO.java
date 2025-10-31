package com.mambocosmo.urzasoracle.DTO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CardFaceDTO implements GenericDTO {

    private UUID id;
    private String name;
    private String mana_cost;
    private String type_line;
    private String oracle_text;
    private String power;
    private String toughness;
    private String loyalty;
    private String artist;
    private String illustration_id;
    private Map<String, String> images;

    public CardFaceDTO(String type) {
        switch (type) {
            case "blank" -> {
                List<String> imgtypes = List.of("small", "normal", "large", "png", "art_crop", "border_crop");
                images = new HashMap<>();
                imgtypes.forEach(e -> images.put(e,
                        "https://backs.scryfall.io/large/5/9/597b79b3-7d77-4261-871a-60dd17403388.jpg?1665006177"));
            }
        }

    }

}
