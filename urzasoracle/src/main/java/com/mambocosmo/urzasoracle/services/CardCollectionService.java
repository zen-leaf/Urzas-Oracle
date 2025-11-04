package com.mambocosmo.urzasoracle.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mambocosmo.urzasoracle.DTO.CardCollectionDTO;
import com.mambocosmo.urzasoracle.DTO.CardInDeckDTO;
import com.mambocosmo.urzasoracle.DTO.DeckLegalityReportDTO;
import com.mambocosmo.urzasoracle.converters.CardCollectionConverter;
import com.mambocosmo.urzasoracle.converters.CardConverter;
import com.mambocosmo.urzasoracle.converters.CardInDeckConverter;
import com.mambocosmo.urzasoracle.entities.Card;
import com.mambocosmo.urzasoracle.entities.CardCollection;
import com.mambocosmo.urzasoracle.entities.CardInDeck;
import com.mambocosmo.urzasoracle.entities.UrzaUser;
import com.mambocosmo.urzasoracle.misc.enums.Format;
import com.mambocosmo.urzasoracle.repositories.CardCollectionRepository;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Service
@Data
@EqualsAndHashCode(callSuper = true)
public class CardCollectionService
        extends GenericService<CardCollection, CardCollectionDTO, CardCollectionConverter, CardCollectionRepository> {

    private final CardInDeckConverter CARDINDECKCONVERTER;
    private final CardConverter CARDCONVERTER;
    private final CardService CARDSERVICE;

    @Override
    public CardCollection construct(Map<String, String> fromData) {
        CardCollection cc = new CardCollection();
        try {
            cc = getCONTEXT().getBean(CardCollection.class, fromData);
        } catch (Exception e) {
            System.out.println("Error generating CardCollection from map!");
            e.printStackTrace();
        }
        return cc;
    }

    public boolean bulksave(String in, String toDeckID) {
        final String regex = "^(\\d+)\\s+(.+?)\\s*\\(";
        final Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);

        Map<String, Integer> cardQuantities = new HashMap<>();

        final Matcher matcher = pattern.matcher(in);

        while (matcher.find()) {
            try {
                int quantity = Integer.parseInt(matcher.group(1));
                String cardName = matcher.group(2).trim();
                cardQuantities.put(cardName, quantity);

            } catch (NumberFormatException e) {
                System.err.println("Error parsing quantity for line starting with: " + matcher.group(0));
            }
        }
        CardCollection deck = getEntityByID(UUID.fromString(toDeckID));

        // System.out.println(cardQuantities);
        cardQuantities.forEach((card, quantity) -> {
            if (getCARDSERVICE().getEntityByName(card).size() > 0) {
                addCardToDeck(deck.getId(), deck.getOwner().getId(),
                        getCARDSERVICE().getEntityByName(card).get(0).getId(), quantity);
            }
        });

        return false;
    }

    public List<CardCollection> getByName(String name) {
        return getREPOSITORY().findByName(name);
    }

    @Override
    public boolean save(CardCollection fromEntity) {
        try {
            getREPOSITORY().save(fromEntity);
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Error saving CardCollection!");
            return false;
        }
        return true;
    }

    public Integer getTotalCardsByDeckId(UUID deckId) {
        CardCollection deck = getREPOSITORY().findById(deckId).orElse(null);
        if (deck == null)
            return 0;

        return deck.getCardList().stream()
                .mapToInt(card -> card.getQuantity())
                .sum();
    }

    public List<CardInDeckDTO> getCardsByDeck(UUID deckId) {
        CardCollection deck = getREPOSITORY().findById(deckId).orElse(null);
        if (deck == null)
            return List.of();

        return deck.getCardList().stream()
                .map(entity -> {
                    return getCARDINDECKCONVERTER().fromEToD(entity);
                })
                .collect(java.util.stream.Collectors.toList());
    }

    public CardCollectionDTO createDeck(UUID userId, String name, String description, String mainDeckFormat) {
        UrzaUser user = getCONTEXT().getBean(UrzaUserService.class).getEntityByID(userId);
        if (user == null)
            return null;

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
        if (deck == null)
            return null;

        deck.setName(name);
        if (description != null)
            deck.setDescription(description);
        save(deck);

        return getCONVERTER().fromEToD(deck);
    }

    public boolean deleteDeck(UUID deckId, UUID userId) {
        CardCollection deck = getREPOSITORY().findById(deckId).orElse(null);
        if (deck == null)
            return false;

        delete(deckId);
        return true;
    }

    public List<CardCollectionDTO> getDecksByUser(UUID userId) {
        UrzaUser user = getCONTEXT().getBean(UrzaUserService.class).getEntityByID(userId);
        if (user == null)
            return List.of();

        return user.getUserDecks().stream()
                .map(getCONVERTER()::fromEToD)
                .collect(java.util.stream.Collectors.toList());
    }

    // ✅ FIXATO: Metodo che causa errore 500
    @Transactional
    public CardInDeckDTO addCardToDeck(UUID deckId, UUID userId, UUID cardId, Integer quantity) {
        // Carica deck
        CardCollection deck = getREPOSITORY().findById(deckId).orElse(null);
        if (deck == null || !deck.getOwner().getId().equals(userId))
            return null;

        // Carica carta
        Card card = getCONTEXT().getBean(CardService.class).getREPOSITORY().findById(cardId).orElse(null);
        if (card == null)
            return null;

        // Validazione quantità (rimuovi limite 4 per basic lands)
        int qty = quantity;
        if (qty < 1)
            return null;

        // ✅ FIXATO: Permetti > 4 copie per basic lands
        boolean isBasicLand = card.getType_line() != null &&
                card.getType_line().toLowerCase().contains("basic land");
        if (!isBasicLand && qty > 4)
            return null;

        // Verifica limite 100 carte totali
        int total = deck.getCardList().stream().mapToInt(c -> c.getQuantity()).sum();
        if (total + qty > 100)
            return null;

        // ✅ FIXATO: Cerca se carta esiste già
        CardInDeck existingCard = deck.getCardList().stream()
                .filter(c -> c.getId().getCard().getId().equals(cardId))
                .findFirst()
                .orElse(null);

        if (existingCard != null) {
            // Incrementa quantità esistente
            existingCard.setQuantity(existingCard.getQuantity() + qty);
        } else {
            // Crea nuova entry
            CardInDeck cid = new CardInDeck(deck, card, qty);
            deck.getCardList().add(cid);
        }

        // ✅ Salva (cascade gestisce CardInDeck)
        save(deck);

        // Ritorna DTO
        CardInDeckDTO dto = new CardInDeckDTO();
        dto.setCardId(card.getId());
        dto.setCardName(card.getName());
        dto.setTypeline(card.getType_line());
        dto.setQuantity(existingCard != null ? existingCard.getQuantity() : qty);
        dto.setRefCard(getCARDCONVERTER().fromEToD(card));
        if (card.getImage_uris() != null) {
            dto.setImageUrl(card.getImage_uris().get("normal"));
        }

        return dto;
    }

    public boolean removeCardFromDeck(UUID deckId, UUID userId, UUID cardId) {
        CardCollection deck = getREPOSITORY().findById(deckId).orElse(null);
        if (deck == null)
            return false;

        CardInDeck card = deck.getCardList().stream()
                .filter(c -> c.getId().getCard().getId().equals(cardId))
                .findFirst()
                .orElse(null);

        if (card == null)
            return false;

        deck.getCardList().remove(card);
        save(deck);
        return true;
    }

    public CardInDeckDTO increaseCardQuantity(UUID deckId, UUID userId, UUID cardId) {
        CardCollection deck = getREPOSITORY().findById(deckId).orElse(null);
        if (deck == null)
            return null;

        CardInDeck card = deck.getCardList().stream()
                .filter(c -> c.getId().getCard().getId().equals(cardId))
                .findFirst()
                .orElse(null);

        if (card == null)
            return null;

        int qty = card.getQuantity();
        if (qty < 4) {
            card.setQuantity(qty + 1);
            save(deck);
        }

        return getCARDINDECKCONVERTER().fromEToD(card);
    }

    public CardInDeckDTO decreaseCardQuantity(UUID deckId, UUID userId, UUID cardId) {
        CardCollection deck = getREPOSITORY().findById(deckId).orElse(null);
        if (deck == null)
            return null;

        CardInDeck card = deck.getCardList().stream()
                .filter(c -> c.getId().getCard().getId().equals(cardId))
                .findFirst()
                .orElse(null);

        if (card == null)
            return null;

        int qty = card.getQuantity();
        if (qty > 1) {
            card.setQuantity(qty - 1);
            save(deck);
        }

        return getCARDINDECKCONVERTER().fromEToD(card);
    }

    public List<CardCollectionDTO> getAllDecks() {
        return getREPOSITORY().findAll().stream()
                .map(getCONVERTER()::fromEToD)
                .collect(Collectors.toList());
    }

    public CardCollectionDTO cloneDeck(UUID originalDeckId, UUID targetUserId) {
        CardCollection originalDeck = getREPOSITORY().findById(originalDeckId).orElse(null);
        if (originalDeck == null)
            return null;

        UrzaUser targetUser = getCONTEXT().getBean(UrzaUserService.class).getEntityByID(targetUserId);
        if (targetUser == null)
            return null;

        CardCollection clonedDeck = new CardCollection();
        clonedDeck.setName(originalDeck.getName() + " (copia)");
        clonedDeck.setDescription(originalDeck.getDescription());
        clonedDeck.setMainDeckFormat(originalDeck.getMainDeckFormat());
        clonedDeck.setOwner(targetUser);

        for (CardInDeck card : originalDeck.getCardList()) {
            CardInDeck clonedCard = new CardInDeck(
                    clonedDeck,
                    card.getId().getCard(),
                    card.getQuantity());
            clonedDeck.getCardList().add(clonedCard);
        }

        save(clonedDeck);
        return getCONVERTER().fromEToD(clonedDeck);
    }

    public DeckLegalityReportDTO checkDecklegality(CardCollection e) {
        Format dFormat = e.getMainDeckFormat();
        DeckLegalityReportDTO report = new DeckLegalityReportDTO("LEGAL");
        for (CardInDeck c : e.getCardList()) {
            if (!c.getCard().getType_line().toLowerCase().contains("basic land")
                    || (c.getQuantity() > 4)
                    || (c.getQuantity() > 1
                            && (dFormat.equals(Format.commander) || dFormat.equals(Format.oathbreaker)))) {
                report = new DeckLegalityReportDTO("ILLEGAL", c.getCard().getName(),
                        "One or more cards in the deck have more copies than allowed by the selected format.");
            }
            if (c.getCard().getLegalities().get(dFormat) != "legal") {
                report = new DeckLegalityReportDTO(
                        c.getCard().getLegalities().get(dFormat).toUpperCase().replaceAll("_", " "),
                        c.getCard().getName(),
                        "One or more cards in your deck are not legal in the selected format");
            }

            return report;
        }
        return report;
    }

    public DeckLegalityReportDTO checkDeckLegality(UUID deckid) {
        CardCollection deck = getREPOSITORY().findById(deckid).orElse(null);
        return checkDecklegality(deck);
    }
}
