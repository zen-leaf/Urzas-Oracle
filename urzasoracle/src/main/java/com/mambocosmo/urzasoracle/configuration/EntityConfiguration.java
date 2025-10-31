package com.mambocosmo.urzasoracle.configuration;

import java.util.Map;
import java.util.UUID;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.mambocosmo.urzasoracle.entities.Artist;
import com.mambocosmo.urzasoracle.entities.Card;
import com.mambocosmo.urzasoracle.entities.CardCollection;
import com.mambocosmo.urzasoracle.entities.CardExpansionSet;
import com.mambocosmo.urzasoracle.entities.CardPart;
import com.mambocosmo.urzasoracle.entities.UrzaUser;
import com.mambocosmo.urzasoracle.misc.Utils.SearchCriteria;
import com.mambocosmo.urzasoracle.services.UrzaUserService;

import lombok.Data;

@Data
@Configuration
public class EntityConfiguration {

    private final UrzaUserService URZAUSERSERVICE;

    @Bean
    @Scope("prototype")
    public Card card(Map<String, String> fromData) {
        Card c = new Card();
        c.fromMap(fromData);
        return c;
    }

    @Bean
    @Scope("prototype")
    public CardPart cardPart(Map<String, String> fromData) {
        CardPart cp = new CardPart();
        cp.fromMap(fromData);
        return cp;
    }

    @Bean
    @Scope("prototype")
    public CardExpansionSet cardExpansionSet(Map<String, String> fromData) {
        CardExpansionSet ces = new CardExpansionSet();
        ces.fromMap(fromData);
        return ces;
    }

    @Bean
    @Scope("prototype")
    public Artist artist(Map<String, String> fromData) {
        Artist a = new Artist();
        a.fromMap(fromData);
        return a;
    }

    @Bean
    @Scope("prototype")
    public CardCollection cardCollection(Map<String, String> fromData) {
        CardCollection cc = new CardCollection();
        cc.fromMap(fromData);
        // TODO placeholder per testing
        // if(fromData.containsKey("user_id"))
        //     UrzaUser u = getURZAUSERSERVICE().findById(UUID.fromString(fromData.get("user_id")));
        // cc.setOwner(u);
        return cc;
    }

    @Bean
    @Scope("prototype")
    public UrzaUser urzaUser(Map<String, String> fromData) {
        UrzaUser u = new UrzaUser();
        u.fromMap(fromData);
        return u;
    }

    @Bean
    @Scope("prototype")
    public SearchCriteria searchCriteria() {
        SearchCriteria sc = new SearchCriteria();

        return sc;
    }

    // @Bean
    // @Scope("prototype")
    // public CardInDeck cardInDeck(Map<String, String> fromData) {
    // CardInDeck u = new CardInDeck();
    // u.fromMap(fromData);
    // return u;
    // }

}
