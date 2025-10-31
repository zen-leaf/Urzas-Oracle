package com.mambocosmo.urzasoracle.services;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.mambocosmo.urzasoracle.DTO.CardCollectionDTO;
import com.mambocosmo.urzasoracle.DTO.CardInDeckDTO;
import com.mambocosmo.urzasoracle.converters.CardCollectionConverter;
import com.mambocosmo.urzasoracle.entities.Card;
import com.mambocosmo.urzasoracle.entities.CardCollection;
import com.mambocosmo.urzasoracle.entities.CardInDeck;
import com.mambocosmo.urzasoracle.entities.UrzaUser;
import com.mambocosmo.urzasoracle.repositories.CardCollectionRepository;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Service
@Data
@EqualsAndHashCode(callSuper=true)
public class CardCollectionService extends GenericService<CardCollection, CardCollectionDTO, CardCollectionConverter, CardCollectionRepository> {

    private final UrzaUserService URZAUSERSERVICE;

    @Override
    public CardCollection construct(Map<String, String> fromData) {
        CardCollection cc = new CardCollection();
        try{
            cc = getCONTEXT().getBean(CardCollection.class, fromData);
        }
        catch(Exception e){
            System.out.println("Error generating CardCollection from map!");
            e.printStackTrace();
        }
        return cc;
    }

    public List<CardCollection> getByName(String name){
        return getREPOSITORY().findByName(name);
    }

    @Override
    public boolean save(CardCollection fromEntity){
        try{
            String userNameFromDTO = fromEntity.getOwner().getUsername();
            fromEntity.setOwner(getURZAUSERSERVICE().findByUsername(userNameFromDTO));
            getREPOSITORY().save(fromEntity);
        } catch(Exception ex){
            ex.printStackTrace();
            System.out.println("Error saving CardCollection!");
            return false;
        }
        return true;
    }

    // ========== NUOVI METODI PER DECK ==========

    public List<CardInDeckDTO> getCardsByDeck(UUID deckId) {
        CardCollection deck = getREPOSITORY().findById(deckId).orElse(null);
        if (deck == null) return List.of();
        
        return deck.getCardList().stream()
                .map(entity -> {
                    CardInDeckDTO dto = new CardInDeckDTO();
                    dto.setCardId(entity.getId().getCard().getId());
                    dto.setCardName(entity.getId().getCard().getName());
                    dto.setTypeline(entity.getId().getCard().getType_line());
                    dto.setQuantity(entity.getQuantity());
                    if (entity.getId().getCard().getImage_uris() != null) {
                        dto.setImageUrl(entity.getId().getCard().getImage_uris().get("normal"));
                    }
                    return dto;
                })
                .collect(java.util.stream.Collectors.toList());
    }

    public CardCollectionDTO createDeck(UUID userId, String name, String description, String mainDeckFormat) {
        UrzaUser user = getCONTEXT().getBean(UrzaUserService.class).getEntityByID(userId);
        if (user == null) return null;

        CardCollection deck = new CardCollection();
        deck.setName(name);
        deck.setDescription(description != null ? description : "");
        deck.setMainDeckFormat(com.mambocosmo.urzasoracle.misc.enums.Format.valueOf(mainDeckFormat));
        deck.setOwner(user);

        save(deck);
        return getCONVERTER().fromEToD(deck);
    }

    public CardCollectionDTO updateDeck(UUID deckId, UUID userId, String name, String description) {
        CardCollection deck = getREPOSITORY().findById(deckId).orElse(null);
        if (deck == null) return null;

        deck.setName(name);
        if (description != null) deck.setDescription(description);
        save(deck);

        return getCONVERTER().fromEToD(deck);
    }

    public boolean deleteDeck(UUID deckId, UUID userId) {
        CardCollection deck = getREPOSITORY().findById(deckId).orElse(null);
        if (deck == null) return false;

        delete(deckId);
        return true;
    }

    public List<CardCollectionDTO> getDecksByUser(UUID userId) {
        UrzaUser user = getCONTEXT().getBean(UrzaUserService.class).getEntityByID(userId);
        if (user == null) return List.of();

        return user.getUserDecks().stream()
                .map(getCONVERTER()::fromEToD)
                .collect(java.util.stream.Collectors.toList());
    }

    public CardInDeckDTO addCardToDeck(UUID deckId, UUID userId, UUID cardId, String quantity) {
        CardCollection deck = getREPOSITORY().findById(deckId).orElse(null);
        if (deck == null) return null;

        Card card = getCONTEXT().getBean(CardService.class).getREPOSITORY().findById(cardId).orElse(null);
        if (card == null) return null;

        int qty = Integer.parseInt(quantity);
        if (qty < 1 || qty > 4) return null;

        int total = deck.getCardList().stream().mapToInt(c -> Integer.parseInt(c.getQuantity())).sum();
        if (total + qty > 100) return null;

        CardInDeck cid = new CardInDeck(deck, card, quantity);
        deck.getCardList().add(cid);
        save(deck);

        CardInDeckDTO dto = new CardInDeckDTO();
        dto.setCardId(card.getId());
        dto.setCardName(card.getName());
        dto.setTypeline(card.getType_line());
        dto.setQuantity(quantity);
        if (card.getImage_uris() != null) {
            dto.setImageUrl(card.getImage_uris().get("normal"));
        }

        return dto;
    }

    public boolean removeCardFromDeck(UUID deckId, UUID userId, UUID cardId) {
        CardCollection deck = getREPOSITORY().findById(deckId).orElse(null);
        if (deck == null) return false;

        CardInDeck card = deck.getCardList().stream()
                .filter(c -> c.getId().getCard().getId().equals(cardId))
                .findFirst()
                .orElse(null);

        if (card == null) return false;

        deck.getCardList().remove(card);
        save(deck);
        return true;
    }

    public CardInDeckDTO increaseCardQuantity(UUID deckId, UUID userId, UUID cardId) {
        CardCollection deck = getREPOSITORY().findById(deckId).orElse(null);
        if (deck == null) return null;

        CardInDeck card = deck.getCardList().stream()
                .filter(c -> c.getId().getCard().getId().equals(cardId))
                .findFirst()
                .orElse(null);

        if (card == null) return null;

        int qty = Integer.parseInt(card.getQuantity());
        if (qty < 4) {
            card.setQuantity(String.valueOf(qty + 1));
            save(deck);
        }

        CardInDeckDTO dto = new CardInDeckDTO();
        dto.setCardId(card.getId().getCard().getId());
        dto.setCardName(card.getId().getCard().getName());
        dto.setTypeline(card.getId().getCard().getType_line());
        dto.setQuantity(card.getQuantity());
        if (card.getId().getCard().getImage_uris() != null) {
            dto.setImageUrl(card.getId().getCard().getImage_uris().get("normal"));
        }

        return dto;
    }

    public CardInDeckDTO decreaseCardQuantity(UUID deckId, UUID userId, UUID cardId) {
        CardCollection deck = getREPOSITORY().findById(deckId).orElse(null);
        if (deck == null) return null;

        CardInDeck card = deck.getCardList().stream()
                .filter(c -> c.getId().getCard().getId().equals(cardId))
                .findFirst()
                .orElse(null);

        if (card == null) return null;

        int qty = Integer.parseInt(card.getQuantity());
        if (qty > 1) {
            card.setQuantity(String.valueOf(qty - 1));
            save(deck);
        }

        CardInDeckDTO dto = new CardInDeckDTO();
        dto.setCardId(card.getId().getCard().getId());
        dto.setCardName(card.getId().getCard().getName());
        dto.setTypeline(card.getId().getCard().getType_line());
        dto.setQuantity(card.getQuantity());
        if (card.getId().getCard().getImage_uris() != null) {
            dto.setImageUrl(card.getId().getCard().getImage_uris().get("normal"));
        }

        return dto;
    }
}
