package com.mambocosmo.urzasoracle.configuration;

import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.mambocosmo.urzasoracle.entities.Artist;
import com.mambocosmo.urzasoracle.entities.Card;
import com.mambocosmo.urzasoracle.entities.CardExpansionSet;
import com.mambocosmo.urzasoracle.entities.FreeCardCollection;
import com.mambocosmo.urzasoracle.entities.StructuredCardCollection;

@Configuration
public class EntityConfiguration {

    @Bean
    @Scope("prototype")
    public Card card(Map<String, String> fromData) {
        Card c = new Card();
        c.fromMap(fromData);
        return c;
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
    public StructuredCardCollection structuredCardCollection(Map<String, String> fromData) {
        StructuredCardCollection scc = new StructuredCardCollection();
        scc.fromMap(fromData);
        return scc;
    }

    @Bean
    @Scope("prototype")
    public FreeCardCollection freeCardCollection(Map<String, String> fromData) {
        FreeCardCollection fcc = new FreeCardCollection();
        fcc.fromMap(fromData);
        return fcc;

    }
}
