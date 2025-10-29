package com.mambocosmo.urzasoracle.configuration;

import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.mambocosmo.urzasoracle.entities.Artist;
import com.mambocosmo.urzasoracle.entities.Card;
import com.mambocosmo.urzasoracle.entities.CardCollection;
import com.mambocosmo.urzasoracle.entities.CardExpansionSet;
import com.mambocosmo.urzasoracle.entities.CardPart;
import com.mambocosmo.urzasoracle.entities.User;
import com.mambocosmo.urzasoracle.services.UserService;

import lombok.Data;

@Data
@Configuration
public class EntityConfiguration {

    private final UserService USERSERVICE;

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
        User u = getUSERSERVICE().getByUsername("dummy_user2");
        cc.setOwner(u);
        return cc;
    }
    
    @Bean
    @Scope("prototype")
    public User user(Map<String, String> fromData) {
        User u = new User();
        u.fromMap(fromData);
        return u;
    }

}
