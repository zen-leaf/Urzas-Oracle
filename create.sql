
    create table artist_cards (
        artistref_id varchar(255) not null,
        cardref_id varchar(255) not null,
        primary key (artistref_id, cardref_id)
    ) engine=InnoDB;

    create table artists (
        artist_id varchar(255) not null,
        artist_name varchar(255),
        primary key (artist_id)
    ) engine=InnoDB;

    create table card_artists (
        artist_id varchar(255) not null,
        card_id varchar(255) not null,
        primary key (artist_id, card_id)
    ) engine=InnoDB;

    create table card_attraction_lights_table (
        attraction_lights tinyint check (attraction_lights between 0 and 5),
        card_id varchar(255) not null
    ) engine=InnoDB;

    create table card_color_identity_table (
        color_identity tinyint check (color_identity between 0 and 4),
        card_id varchar(255) not null
    ) engine=InnoDB;

    create table card_color_indicator_table (
        color_indicator tinyint check (color_indicator between 0 and 4),
        card_id varchar(255) not null
    ) engine=InnoDB;

    create table card_color_table (
        colors tinyint check (colors between 0 and 4),
        card_id varchar(255) not null
    ) engine=InnoDB;

    create table card_face_color_table (
        card_face_id varchar(255) not null,
        colors varchar(255)
    ) engine=InnoDB;

    create table card_face_image_uris (
        card_face_id varchar(255) not null,
        image_uris varchar(255),
        image_uris_key varchar(255) not null,
        primary key (card_face_id, image_uris_key)
    ) engine=InnoDB;

    create table card_faces (
        artist varchar(255),
        artist_id varchar(255),
        card_face_id varchar(255) not null auto_increment,
        card_id varchar(255),
        flavor_text varchar(255),
        illustration_id varchar(255),
        mana_cost varchar(255),
        name varchar(255),
        oracle_text varchar(255),
        power varchar(255),
        toughness varchar(255),
        type_line varchar(255),
        primary key (card_face_id)
    ) engine=InnoDB;

    create table card_finishes (
        card_id varchar(255) not null,
        finishes varchar(255)
    ) engine=InnoDB;

    create table card_frame_effects_table (
        card_id varchar(255) not null,
        frame_effects varchar(255)
    ) engine=InnoDB;

    create table card_images (
        card_id varchar(255) not null,
        image_type varchar(255) not null,
        image_uris varchar(255),
        primary key (card_id, image_type)
    ) engine=InnoDB;

    create table card_keyword_table (
        card_id varchar(255) not null,
        keywords varchar(255)
    ) engine=InnoDB;

    create table card_legal_formats (
        format tinyint not null check (format between 0 and 20),
        card_id varchar(255) not null,
        legalities varchar(255),
        primary key (format, card_id)
    ) engine=InnoDB;

    create table card_part_relation (
        cardref_id varchar(255) not null,
        part_id varchar(255) not null,
        primary key (cardref_id, part_id)
    ) engine=InnoDB;

    create table card_parts (
        card_id varchar(255) not null,
        component varchar(255),
        id varchar(255) not null,
        name varchar(255),
        part_id varchar(255) not null,
        type_line varchar(255),
        uri varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table card_collection (
        dtype varchar(31) not null,
        description varchar(255),
        id varchar(255) not null,
        name varchar(255),
        legal_in varbinary(255),
        owner varbinary(255),
        primary key (id)
    ) engine=InnoDB;

    create table card_in_deck (
        card_id varchar(255) not null,
        deck_id varchar(255) not null,
        quantity varchar(255),
        primary key (card_id, deck_id)
    ) engine=InnoDB;

    create table cards (
        booster bit,
        border_color tinyint check (border_color between 0 and 5),
        digital bit,
        foil bit,
        frame tinyint check (frame between 0 and 4),
        full_art bit,
        game_changer bit,
        highres_image bit,
        nonfoil bit,
        oversized bit,
        promo bit,
        reprint bit,
        reserved bit,
        reserverd bit,
        textless bit,
        variation bit,
        card_back_id varchar(255),
        card_id varchar(255) not null,
        cmc varchar(255),
        collector_number varchar(255),
        flavor_name varchar(255),
        flavor_text varchar(255),
        hand_modifier varchar(255),
        illustration_id varchar(255),
        image_status varchar(255),
        lang varchar(255),
        layout varchar(255),
        life_modifier varchar(255),
        loyalty varchar(255),
        mana_cost varchar(255),
        name varchar(255),
        oracle_id varchar(255),
        oracle_text varchar(255),
        power varchar(255),
        printed_name varchar(255),
        printed_text varchar(255),
        printed_type_line varchar(255),
        prints_search_uri varchar(255),
        rarity varchar(255),
        released_at varchar(255),
        scryfall_uri varchar(255),
        set_id varchar(255),
        toughness varchar(255),
        type_line varchar(255),
        ulings_uri varchar(255),
        uri varchar(255),
        variation_of varchar(255),
        watermark varchar(255),
        produced_mana varbinary(255),
        primary key (card_id)
    ) engine=InnoDB;

    create table expansion_sets (
        digital bit,
        foil_only bit,
        nonfoil_only bit,
        set_type tinyint check (set_type between 0 and 22),
        card_count varchar(255),
        code varchar(255),
        expansion_id varchar(255) not null,
        icon_svg_uri varchar(255),
        name varchar(255),
        released_at varchar(255),
        scryfall_uri varchar(255),
        search_uri varchar(255),
        uri varchar(255),
        primary key (expansion_id)
    ) engine=InnoDB;

    create table user (
        register_date date,
        display_name varchar(255),
        email varchar(255),
        id varchar(255) not null,
        password varchar(255),
        username varchar(255),
        primary key (id)
    ) engine=InnoDB;

    alter table artist_cards 
       add constraint FKw7ccwovneynv7eh1qqjreyfo 
       foreign key (cardref_id) 
       references cards (card_id);

    alter table artist_cards 
       add constraint FK3b6fkjn7qxi1hwnem424cv31d 
       foreign key (artistref_id) 
       references artists (artist_id);

    alter table card_artists 
       add constraint FKd67j8pdf3sx9h53fba8h20uw1 
       foreign key (artist_id) 
       references artists (artist_id);

    alter table card_artists 
       add constraint FK2ep1ufvnxcv8ew7f486xirepa 
       foreign key (card_id) 
       references cards (card_id);

    alter table card_attraction_lights_table 
       add constraint FKdnxg7qf6wwdp6r1peuamqss54 
       foreign key (card_id) 
       references cards (card_id);

    alter table card_color_identity_table 
       add constraint FKsgx3axjr29jauvxthdiehbm8s 
       foreign key (card_id) 
       references cards (card_id);

    alter table card_color_indicator_table 
       add constraint FKhcecsd909u60hkig5vncj4e1r 
       foreign key (card_id) 
       references cards (card_id);

    alter table card_color_table 
       add constraint FKe3gsj1b4wxrt4b9qia8lvytbk 
       foreign key (card_id) 
       references cards (card_id);

    alter table card_face_color_table 
       add constraint FK2t645neoxa6wx1srhsput41mk 
       foreign key (card_face_id) 
       references card_faces (card_face_id);

    alter table card_face_image_uris 
       add constraint FKccjryrhvalsce53mkmh8pqfkw 
       foreign key (card_face_id) 
       references card_faces (card_face_id);

    alter table card_faces 
       add constraint FK1u6px8jx7qn7r49548ngk7gmf 
       foreign key (card_id) 
       references cards (card_id);

    alter table card_finishes 
       add constraint FKhk9v1su36vi4xm9x36ge4p4y9 
       foreign key (card_id) 
       references cards (card_id);

    alter table card_frame_effects_table 
       add constraint FKk66s8tp68rsmpwggkexllcqus 
       foreign key (card_id) 
       references cards (card_id);

    alter table card_images 
       add constraint FK5yh529h49s8o4kc7lnoi2xex0 
       foreign key (card_id) 
       references cards (card_id);

    alter table card_keyword_table 
       add constraint FKbtyq7mjvq9e8va7booj6w2vpa 
       foreign key (card_id) 
       references cards (card_id);

    alter table card_legal_formats 
       add constraint FKhyrpue2r0h7342vshw17cks6u 
       foreign key (card_id) 
       references cards (card_id);

    alter table card_part_relation 
       add constraint FKfd7lwcionu49ikqj3n2vplh9j 
       foreign key (cardref_id) 
       references cards (card_id);

    alter table card_part_relation 
       add constraint FKq0640cghxtufe014wq2qeeog3 
       foreign key (part_id) 
       references card_parts (id);

    alter table card_parts 
       add constraint FKnoxxk836epyff6k54s41724e6 
       foreign key (part_id) 
       references card_parts (id);

    alter table card_parts 
       add constraint FKs10vif6p7f2wu5c2utwpdmito 
       foreign key (card_id) 
       references cards (card_id);

    alter table card_in_deck 
       add constraint FKsx2b4hd2rl9wuj9vm2tx7qhur 
       foreign key (card_id) 
       references cards (card_id);

    alter table card_in_deck 
       add constraint FKhq7kypr1x2chl6o58wso8vli 
       foreign key (deck_id) 
       references card_collection (id);

    alter table cards 
       add constraint FKfj1sgtywkbmg3u1aevop6oyks 
       foreign key (set_id) 
       references expansion_sets (expansion_id);

    create table artist_cards (
        artistref_id varchar(255) not null,
        cardref_id varchar(255) not null,
        primary key (artistref_id, cardref_id)
    ) engine=InnoDB;

    create table artists (
        artist_id varchar(255) not null,
        artist_name varchar(255),
        primary key (artist_id)
    ) engine=InnoDB;

    create table card_artists (
        artist_id varchar(255) not null,
        card_id varchar(255) not null,
        primary key (artist_id, card_id)
    ) engine=InnoDB;

    create table card_attraction_lights_table (
        attraction_lights tinyint check (attraction_lights between 0 and 5),
        card_id varchar(255) not null
    ) engine=InnoDB;

    create table card_color_identity_table (
        color_identity tinyint check (color_identity between 0 and 4),
        card_id varchar(255) not null
    ) engine=InnoDB;

    create table card_color_indicator_table (
        color_indicator tinyint check (color_indicator between 0 and 4),
        card_id varchar(255) not null
    ) engine=InnoDB;

    create table card_color_table (
        colors tinyint check (colors between 0 and 4),
        card_id varchar(255) not null
    ) engine=InnoDB;

    create table card_face_color_table (
        card_face_id varchar(255) not null,
        colors varchar(255)
    ) engine=InnoDB;

    create table card_face_image_uris (
        card_face_id varchar(255) not null,
        image_uris varchar(255),
        image_uris_key varchar(255) not null,
        primary key (card_face_id, image_uris_key)
    ) engine=InnoDB;

    create table card_faces (
        artist varchar(255),
        artist_id varchar(255),
        card_face_id varchar(255) not null,
        card_id varchar(255),
        flavor_text varchar(255),
        illustration_id varchar(255),
        mana_cost varchar(255),
        name varchar(255),
        oracle_text varchar(255),
        power varchar(255),
        toughness varchar(255),
        type_line varchar(255),
        primary key (card_face_id)
    ) engine=InnoDB;

    create table card_finishes (
        card_id varchar(255) not null,
        finishes varchar(255)
    ) engine=InnoDB;

    create table card_frame_effects_table (
        card_id varchar(255) not null,
        frame_effects varchar(255)
    ) engine=InnoDB;

    create table card_images (
        card_id varchar(255) not null,
        image_type varchar(255) not null,
        image_uris varchar(255),
        primary key (card_id, image_type)
    ) engine=InnoDB;

    create table card_keyword_table (
        card_id varchar(255) not null,
        keywords varchar(255)
    ) engine=InnoDB;

    create table card_legal_formats (
        format tinyint not null check (format between 0 and 20),
        card_id varchar(255) not null,
        legalities varchar(255),
        primary key (format, card_id)
    ) engine=InnoDB;

    create table card_part_relation (
        cardref_id varchar(255) not null,
        part_id varchar(255) not null,
        primary key (cardref_id, part_id)
    ) engine=InnoDB;

    create table card_parts (
        card_id varchar(255) not null,
        component varchar(255),
        id varchar(255) not null,
        name varchar(255),
        part_id varchar(255) not null,
        type_line varchar(255),
        uri varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table card_collection (
        dtype varchar(31) not null,
        description varchar(255),
        id varchar(255) not null,
        name varchar(255),
        legal_in varbinary(255),
        owner varbinary(255),
        primary key (id)
    ) engine=InnoDB;

    create table card_in_deck (
        card_id varchar(255) not null,
        deck_id varchar(255) not null,
        quantity varchar(255),
        primary key (card_id, deck_id)
    ) engine=InnoDB;

    create table cards (
        booster bit,
        border_color tinyint check (border_color between 0 and 5),
        digital bit,
        foil bit,
        frame tinyint check (frame between 0 and 4),
        full_art bit,
        game_changer bit,
        highres_image bit,
        nonfoil bit,
        oversized bit,
        promo bit,
        reprint bit,
        reserved bit,
        reserverd bit,
        textless bit,
        variation bit,
        card_back_id varchar(255),
        card_id varchar(255) not null,
        cmc varchar(255),
        collector_number varchar(255),
        flavor_name varchar(255),
        flavor_text varchar(255),
        hand_modifier varchar(255),
        illustration_id varchar(255),
        image_status varchar(255),
        lang varchar(255),
        layout varchar(255),
        life_modifier varchar(255),
        loyalty varchar(255),
        mana_cost varchar(255),
        name varchar(255),
        oracle_id varchar(255),
        oracle_text varchar(255),
        power varchar(255),
        printed_name varchar(255),
        printed_text varchar(255),
        printed_type_line varchar(255),
        prints_search_uri varchar(255),
        rarity varchar(255),
        released_at varchar(255),
        scryfall_uri varchar(255),
        set_id varchar(255),
        toughness varchar(255),
        type_line varchar(255),
        ulings_uri varchar(255),
        uri varchar(255),
        variation_of varchar(255),
        watermark varchar(255),
        produced_mana varbinary(255),
        primary key (card_id)
    ) engine=InnoDB;

    create table expansion_sets (
        digital bit,
        foil_only bit,
        nonfoil_only bit,
        set_type tinyint check (set_type between 0 and 22),
        card_count varchar(255),
        code varchar(255),
        expansion_id varchar(255) not null,
        icon_svg_uri varchar(255),
        name varchar(255),
        released_at varchar(255),
        scryfall_uri varchar(255),
        search_uri varchar(255),
        uri varchar(255),
        primary key (expansion_id)
    ) engine=InnoDB;

    create table user (
        register_date date,
        display_name varchar(255),
        email varchar(255),
        id varchar(255) not null,
        password varchar(255),
        username varchar(255),
        primary key (id)
    ) engine=InnoDB;

    alter table artist_cards 
       add constraint FKw7ccwovneynv7eh1qqjreyfo 
       foreign key (cardref_id) 
       references cards (card_id);

    alter table artist_cards 
       add constraint FK3b6fkjn7qxi1hwnem424cv31d 
       foreign key (artistref_id) 
       references artists (artist_id);

    alter table card_artists 
       add constraint FKd67j8pdf3sx9h53fba8h20uw1 
       foreign key (artist_id) 
       references artists (artist_id);

    alter table card_artists 
       add constraint FK2ep1ufvnxcv8ew7f486xirepa 
       foreign key (card_id) 
       references cards (card_id);

    alter table card_attraction_lights_table 
       add constraint FKdnxg7qf6wwdp6r1peuamqss54 
       foreign key (card_id) 
       references cards (card_id);

    alter table card_color_identity_table 
       add constraint FKsgx3axjr29jauvxthdiehbm8s 
       foreign key (card_id) 
       references cards (card_id);

    alter table card_color_indicator_table 
       add constraint FKhcecsd909u60hkig5vncj4e1r 
       foreign key (card_id) 
       references cards (card_id);

    alter table card_color_table 
       add constraint FKe3gsj1b4wxrt4b9qia8lvytbk 
       foreign key (card_id) 
       references cards (card_id);

    alter table card_face_color_table 
       add constraint FK2t645neoxa6wx1srhsput41mk 
       foreign key (card_face_id) 
       references card_faces (card_face_id);

    alter table card_face_image_uris 
       add constraint FKccjryrhvalsce53mkmh8pqfkw 
       foreign key (card_face_id) 
       references card_faces (card_face_id);

    alter table card_faces 
       add constraint FK1u6px8jx7qn7r49548ngk7gmf 
       foreign key (card_id) 
       references cards (card_id);

    alter table card_finishes 
       add constraint FKhk9v1su36vi4xm9x36ge4p4y9 
       foreign key (card_id) 
       references cards (card_id);

    alter table card_frame_effects_table 
       add constraint FKk66s8tp68rsmpwggkexllcqus 
       foreign key (card_id) 
       references cards (card_id);

    alter table card_images 
       add constraint FK5yh529h49s8o4kc7lnoi2xex0 
       foreign key (card_id) 
       references cards (card_id);

    alter table card_keyword_table 
       add constraint FKbtyq7mjvq9e8va7booj6w2vpa 
       foreign key (card_id) 
       references cards (card_id);

    alter table card_legal_formats 
       add constraint FKhyrpue2r0h7342vshw17cks6u 
       foreign key (card_id) 
       references cards (card_id);

    alter table card_part_relation 
       add constraint FKfd7lwcionu49ikqj3n2vplh9j 
       foreign key (cardref_id) 
       references cards (card_id);

    alter table card_part_relation 
       add constraint FKq0640cghxtufe014wq2qeeog3 
       foreign key (part_id) 
       references card_parts (id);

    alter table card_parts 
       add constraint FKnoxxk836epyff6k54s41724e6 
       foreign key (part_id) 
       references card_parts (id);

    alter table card_parts 
       add constraint FKs10vif6p7f2wu5c2utwpdmito 
       foreign key (card_id) 
       references cards (card_id);

    alter table card_in_deck 
       add constraint FKsx2b4hd2rl9wuj9vm2tx7qhur 
       foreign key (card_id) 
       references cards (card_id);

    alter table card_in_deck 
       add constraint FKhq7kypr1x2chl6o58wso8vli 
       foreign key (deck_id) 
       references card_collection (id);

    alter table cards 
       add constraint FKfj1sgtywkbmg3u1aevop6oyks 
       foreign key (set_id) 
       references expansion_sets (expansion_id);

    create table artist_cards (
        artistref_id varchar(255) not null,
        cardref_id varchar(255) not null,
        primary key (artistref_id, cardref_id)
    ) engine=InnoDB;

    create table artists (
        artist_id varchar(255) not null,
        artist_name varchar(255),
        primary key (artist_id)
    ) engine=InnoDB;

    create table card_artists (
        artist_id varchar(255) not null,
        card_id varchar(255) not null,
        primary key (artist_id, card_id)
    ) engine=InnoDB;

    create table card_attraction_lights_table (
        attraction_lights tinyint check (attraction_lights between 0 and 5),
        card_id varchar(255) not null
    ) engine=InnoDB;

    create table card_color_identity_table (
        color_identity tinyint check (color_identity between 0 and 4),
        card_id varchar(255) not null
    ) engine=InnoDB;

    create table card_color_indicator_table (
        color_indicator tinyint check (color_indicator between 0 and 4),
        card_id varchar(255) not null
    ) engine=InnoDB;

    create table card_color_table (
        colors tinyint check (colors between 0 and 4),
        card_id varchar(255) not null
    ) engine=InnoDB;

    create table card_face_color_table (
        card_face_id varchar(255) not null,
        colors varchar(255)
    ) engine=InnoDB;

    create table card_face_image_uris (
        card_face_id varchar(255) not null,
        image_uris varchar(255),
        image_uris_key varchar(255) not null,
        primary key (card_face_id, image_uris_key)
    ) engine=InnoDB;

    create table card_faces (
        artist varchar(255),
        artist_id varchar(255),
        card_face_id varchar(255) not null,
        card_id varchar(255),
        flavor_text varchar(255),
        illustration_id varchar(255),
        mana_cost varchar(255),
        name varchar(255),
        oracle_text varchar(255),
        power varchar(255),
        toughness varchar(255),
        type_line varchar(255),
        primary key (card_face_id)
    ) engine=InnoDB;

    create table card_finishes (
        card_id varchar(255) not null,
        finishes varchar(255)
    ) engine=InnoDB;

    create table card_frame_effects_table (
        card_id varchar(255) not null,
        frame_effects varchar(255)
    ) engine=InnoDB;

    create table card_images (
        card_id varchar(255) not null,
        image_type varchar(255) not null,
        image_uris varchar(255),
        primary key (card_id, image_type)
    ) engine=InnoDB;

    create table card_keyword_table (
        card_id varchar(255) not null,
        keywords varchar(255)
    ) engine=InnoDB;

    create table card_legal_formats (
        format tinyint not null check (format between 0 and 20),
        card_id varchar(255) not null,
        legalities varchar(255),
        primary key (format, card_id)
    ) engine=InnoDB;

    create table card_part_relation (
        cardref_id varchar(255) not null,
        part_id varchar(255) not null,
        primary key (cardref_id, part_id)
    ) engine=InnoDB;

    create table card_parts (
        card_id varchar(255) not null,
        component varchar(255),
        id varchar(255) not null,
        name varchar(255),
        part_id varchar(255) not null,
        type_line varchar(255),
        uri varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table card_collection (
        dtype varchar(31) not null,
        description varchar(255),
        id varchar(255) not null,
        name varchar(255),
        legal_in varbinary(255),
        owner varbinary(255),
        primary key (id)
    ) engine=InnoDB;

    create table card_in_deck (
        card_id varchar(255) not null,
        deck_id varchar(255) not null,
        quantity varchar(255),
        primary key (card_id, deck_id)
    ) engine=InnoDB;

    create table cards (
        booster bit,
        border_color tinyint check (border_color between 0 and 5),
        digital bit,
        foil bit,
        frame tinyint check (frame between 0 and 4),
        full_art bit,
        game_changer bit,
        highres_image bit,
        nonfoil bit,
        oversized bit,
        promo bit,
        reprint bit,
        reserved bit,
        reserverd bit,
        textless bit,
        variation bit,
        card_back_id varchar(255),
        card_id varchar(255) not null,
        cmc varchar(255),
        collector_number varchar(255),
        flavor_name varchar(255),
        flavor_text varchar(255),
        hand_modifier varchar(255),
        illustration_id varchar(255),
        image_status varchar(255),
        lang varchar(255),
        layout varchar(255),
        life_modifier varchar(255),
        loyalty varchar(255),
        mana_cost varchar(255),
        name varchar(255),
        oracle_id varchar(255),
        oracle_text varchar(255),
        power varchar(255),
        printed_name varchar(255),
        printed_text varchar(255),
        printed_type_line varchar(255),
        prints_search_uri varchar(255),
        rarity varchar(255),
        released_at varchar(255),
        scryfall_uri varchar(255),
        set_id varchar(255),
        toughness varchar(255),
        type_line varchar(255),
        ulings_uri varchar(255),
        uri varchar(255),
        variation_of varchar(255),
        watermark varchar(255),
        produced_mana varbinary(255),
        primary key (card_id)
    ) engine=InnoDB;

    create table expansion_sets (
        digital bit,
        foil_only bit,
        nonfoil_only bit,
        set_type tinyint check (set_type between 0 and 22),
        card_count varchar(255),
        code varchar(255),
        expansion_id varchar(255) not null,
        icon_svg_uri varchar(255),
        name varchar(255),
        released_at varchar(255),
        scryfall_uri varchar(255),
        search_uri varchar(255),
        uri varchar(255),
        primary key (expansion_id)
    ) engine=InnoDB;

    create table user (
        register_date date,
        display_name varchar(255),
        email varchar(255),
        id varchar(255) not null,
        password varchar(255),
        username varchar(255),
        primary key (id)
    ) engine=InnoDB;

    alter table artist_cards 
       add constraint FKw7ccwovneynv7eh1qqjreyfo 
       foreign key (cardref_id) 
       references cards (card_id);

    alter table artist_cards 
       add constraint FK3b6fkjn7qxi1hwnem424cv31d 
       foreign key (artistref_id) 
       references artists (artist_id);

    alter table card_artists 
       add constraint FKd67j8pdf3sx9h53fba8h20uw1 
       foreign key (artist_id) 
       references artists (artist_id);

    alter table card_artists 
       add constraint FK2ep1ufvnxcv8ew7f486xirepa 
       foreign key (card_id) 
       references cards (card_id);

    alter table card_attraction_lights_table 
       add constraint FKdnxg7qf6wwdp6r1peuamqss54 
       foreign key (card_id) 
       references cards (card_id);

    alter table card_color_identity_table 
       add constraint FKsgx3axjr29jauvxthdiehbm8s 
       foreign key (card_id) 
       references cards (card_id);

    alter table card_color_indicator_table 
       add constraint FKhcecsd909u60hkig5vncj4e1r 
       foreign key (card_id) 
       references cards (card_id);

    alter table card_color_table 
       add constraint FKe3gsj1b4wxrt4b9qia8lvytbk 
       foreign key (card_id) 
       references cards (card_id);

    alter table card_face_color_table 
       add constraint FK2t645neoxa6wx1srhsput41mk 
       foreign key (card_face_id) 
       references card_faces (card_face_id);

    alter table card_face_image_uris 
       add constraint FKccjryrhvalsce53mkmh8pqfkw 
       foreign key (card_face_id) 
       references card_faces (card_face_id);

    alter table card_faces 
       add constraint FK1u6px8jx7qn7r49548ngk7gmf 
       foreign key (card_id) 
       references cards (card_id);

    alter table card_finishes 
       add constraint FKhk9v1su36vi4xm9x36ge4p4y9 
       foreign key (card_id) 
       references cards (card_id);

    alter table card_frame_effects_table 
       add constraint FKk66s8tp68rsmpwggkexllcqus 
       foreign key (card_id) 
       references cards (card_id);

    alter table card_images 
       add constraint FK5yh529h49s8o4kc7lnoi2xex0 
       foreign key (card_id) 
       references cards (card_id);

    alter table card_keyword_table 
       add constraint FKbtyq7mjvq9e8va7booj6w2vpa 
       foreign key (card_id) 
       references cards (card_id);

    alter table card_legal_formats 
       add constraint FKhyrpue2r0h7342vshw17cks6u 
       foreign key (card_id) 
       references cards (card_id);

    alter table card_part_relation 
       add constraint FKfd7lwcionu49ikqj3n2vplh9j 
       foreign key (cardref_id) 
       references cards (card_id);

    alter table card_part_relation 
       add constraint FKq0640cghxtufe014wq2qeeog3 
       foreign key (part_id) 
       references card_parts (id);

    alter table card_parts 
       add constraint FKnoxxk836epyff6k54s41724e6 
       foreign key (part_id) 
       references card_parts (id);

    alter table card_parts 
       add constraint FKs10vif6p7f2wu5c2utwpdmito 
       foreign key (card_id) 
       references cards (card_id);

    alter table card_in_deck 
       add constraint FKsx2b4hd2rl9wuj9vm2tx7qhur 
       foreign key (card_id) 
       references cards (card_id);

    alter table card_in_deck 
       add constraint FKhq7kypr1x2chl6o58wso8vli 
       foreign key (deck_id) 
       references card_collection (id);

    alter table cards 
       add constraint FKfj1sgtywkbmg3u1aevop6oyks 
       foreign key (set_id) 
       references expansion_sets (expansion_id);

    create table artist_cards (
        artistref_id varchar(255) not null,
        cardref_id varchar(255) not null,
        primary key (artistref_id, cardref_id)
    ) engine=InnoDB;

    create table artists (
        artist_id varchar(255) not null,
        artist_name varchar(255),
        primary key (artist_id)
    ) engine=InnoDB;

    create table card_artists (
        artist_id varchar(255) not null,
        card_id varchar(255) not null,
        primary key (artist_id, card_id)
    ) engine=InnoDB;

    create table card_attraction_lights_table (
        attraction_lights tinyint check (attraction_lights between 0 and 5),
        card_id varchar(255) not null
    ) engine=InnoDB;

    create table card_color_identity_table (
        color_identity tinyint check (color_identity between 0 and 4),
        card_id varchar(255) not null
    ) engine=InnoDB;

    create table card_color_indicator_table (
        color_indicator tinyint check (color_indicator between 0 and 4),
        card_id varchar(255) not null
    ) engine=InnoDB;

    create table card_color_table (
        colors tinyint check (colors between 0 and 4),
        card_id varchar(255) not null
    ) engine=InnoDB;

    create table card_face_color_table (
        card_face_id varchar(255) not null,
        colors varchar(255)
    ) engine=InnoDB;

    create table card_face_image_uris (
        card_face_id varchar(255) not null,
        image_uris varchar(255),
        image_uris_key varchar(255) not null,
        primary key (card_face_id, image_uris_key)
    ) engine=InnoDB;

    create table card_faces (
        artist varchar(255),
        artist_id varchar(255),
        card_face_id varchar(255) not null,
        card_id varchar(255),
        flavor_text varchar(255),
        illustration_id varchar(255),
        mana_cost varchar(255),
        name varchar(255),
        oracle_text varchar(255),
        power varchar(255),
        toughness varchar(255),
        type_line varchar(255),
        primary key (card_face_id)
    ) engine=InnoDB;

    create table card_finishes (
        card_id varchar(255) not null,
        finishes varchar(255)
    ) engine=InnoDB;

    create table card_frame_effects_table (
        card_id varchar(255) not null,
        frame_effects varchar(255)
    ) engine=InnoDB;

    create table card_images (
        card_id varchar(255) not null,
        image_type varchar(255) not null,
        image_uris varchar(255),
        primary key (card_id, image_type)
    ) engine=InnoDB;

    create table card_keyword_table (
        card_id varchar(255) not null,
        keywords varchar(255)
    ) engine=InnoDB;

    create table card_legal_formats (
        format tinyint not null check (format between 0 and 20),
        card_id varchar(255) not null,
        legalities varchar(255),
        primary key (format, card_id)
    ) engine=InnoDB;

    create table card_part_relation (
        cardref_id varchar(255) not null,
        part_id varchar(255) not null,
        primary key (cardref_id, part_id)
    ) engine=InnoDB;

    create table card_parts (
        card_id varchar(255) not null,
        component varchar(255),
        id varchar(255) not null,
        name varchar(255),
        part_id varchar(255) not null,
        type_line varchar(255),
        uri varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table card_collection (
        dtype varchar(31) not null,
        description varchar(255),
        id varchar(255) not null,
        name varchar(255),
        legal_in varbinary(255),
        owner varbinary(255),
        primary key (id)
    ) engine=InnoDB;

    create table card_in_deck (
        card_id varchar(255) not null,
        deck_id varchar(255) not null,
        quantity varchar(255),
        primary key (card_id, deck_id)
    ) engine=InnoDB;

    create table cards (
        booster bit,
        border_color tinyint check (border_color between 0 and 5),
        digital bit,
        foil bit,
        frame tinyint check (frame between 0 and 4),
        full_art bit,
        game_changer bit,
        highres_image bit,
        nonfoil bit,
        oversized bit,
        promo bit,
        reprint bit,
        reserved bit,
        reserverd bit,
        textless bit,
        variation bit,
        card_back_id varchar(255),
        card_id varchar(255) not null,
        cmc varchar(255),
        collector_number varchar(255),
        flavor_name varchar(255),
        flavor_text varchar(255),
        hand_modifier varchar(255),
        illustration_id varchar(255),
        image_status varchar(255),
        lang varchar(255),
        layout varchar(255),
        life_modifier varchar(255),
        loyalty varchar(255),
        mana_cost varchar(255),
        name varchar(255),
        oracle_id varchar(255),
        oracle_text varchar(255),
        power varchar(255),
        printed_name varchar(255),
        printed_text varchar(255),
        printed_type_line varchar(255),
        prints_search_uri varchar(255),
        rarity varchar(255),
        released_at varchar(255),
        scryfall_uri varchar(255),
        set_id varchar(255),
        toughness varchar(255),
        type_line varchar(255),
        ulings_uri varchar(255),
        uri varchar(255),
        variation_of varchar(255),
        watermark varchar(255),
        produced_mana varbinary(255),
        primary key (card_id)
    ) engine=InnoDB;

    create table expansion_sets (
        digital bit,
        foil_only bit,
        nonfoil_only bit,
        set_type tinyint check (set_type between 0 and 22),
        card_count varchar(255),
        code varchar(255),
        expansion_id varchar(255) not null,
        icon_svg_uri varchar(255),
        name varchar(255),
        released_at varchar(255),
        scryfall_uri varchar(255),
        search_uri varchar(255),
        uri varchar(255),
        primary key (expansion_id)
    ) engine=InnoDB;

    create table user (
        register_date date,
        display_name varchar(255),
        email varchar(255),
        id varchar(255) not null,
        password varchar(255),
        username varchar(255),
        primary key (id)
    ) engine=InnoDB;

    alter table artist_cards 
       add constraint FKw7ccwovneynv7eh1qqjreyfo 
       foreign key (cardref_id) 
       references cards (card_id);

    alter table artist_cards 
       add constraint FK3b6fkjn7qxi1hwnem424cv31d 
       foreign key (artistref_id) 
       references artists (artist_id);

    alter table card_artists 
       add constraint FKd67j8pdf3sx9h53fba8h20uw1 
       foreign key (artist_id) 
       references artists (artist_id);

    alter table card_artists 
       add constraint FK2ep1ufvnxcv8ew7f486xirepa 
       foreign key (card_id) 
       references cards (card_id);

    alter table card_attraction_lights_table 
       add constraint FKdnxg7qf6wwdp6r1peuamqss54 
       foreign key (card_id) 
       references cards (card_id);

    alter table card_color_identity_table 
       add constraint FKsgx3axjr29jauvxthdiehbm8s 
       foreign key (card_id) 
       references cards (card_id);

    alter table card_color_indicator_table 
       add constraint FKhcecsd909u60hkig5vncj4e1r 
       foreign key (card_id) 
       references cards (card_id);

    alter table card_color_table 
       add constraint FKe3gsj1b4wxrt4b9qia8lvytbk 
       foreign key (card_id) 
       references cards (card_id);

    alter table card_face_color_table 
       add constraint FK2t645neoxa6wx1srhsput41mk 
       foreign key (card_face_id) 
       references card_faces (card_face_id);

    alter table card_face_image_uris 
       add constraint FKccjryrhvalsce53mkmh8pqfkw 
       foreign key (card_face_id) 
       references card_faces (card_face_id);

    alter table card_faces 
       add constraint FK1u6px8jx7qn7r49548ngk7gmf 
       foreign key (card_id) 
       references cards (card_id);

    alter table card_finishes 
       add constraint FKhk9v1su36vi4xm9x36ge4p4y9 
       foreign key (card_id) 
       references cards (card_id);

    alter table card_frame_effects_table 
       add constraint FKk66s8tp68rsmpwggkexllcqus 
       foreign key (card_id) 
       references cards (card_id);

    alter table card_images 
       add constraint FK5yh529h49s8o4kc7lnoi2xex0 
       foreign key (card_id) 
       references cards (card_id);

    alter table card_keyword_table 
       add constraint FKbtyq7mjvq9e8va7booj6w2vpa 
       foreign key (card_id) 
       references cards (card_id);

    alter table card_legal_formats 
       add constraint FKhyrpue2r0h7342vshw17cks6u 
       foreign key (card_id) 
       references cards (card_id);

    alter table card_part_relation 
       add constraint FKfd7lwcionu49ikqj3n2vplh9j 
       foreign key (cardref_id) 
       references cards (card_id);

    alter table card_part_relation 
       add constraint FKq0640cghxtufe014wq2qeeog3 
       foreign key (part_id) 
       references card_parts (id);

    alter table card_parts 
       add constraint FKnoxxk836epyff6k54s41724e6 
       foreign key (part_id) 
       references card_parts (id);

    alter table card_parts 
       add constraint FKs10vif6p7f2wu5c2utwpdmito 
       foreign key (card_id) 
       references cards (card_id);

    alter table card_in_deck 
       add constraint FKsx2b4hd2rl9wuj9vm2tx7qhur 
       foreign key (card_id) 
       references cards (card_id);

    alter table card_in_deck 
       add constraint FKhq7kypr1x2chl6o58wso8vli 
       foreign key (deck_id) 
       references card_collection (id);

    alter table cards 
       add constraint FKfj1sgtywkbmg3u1aevop6oyks 
       foreign key (set_id) 
       references expansion_sets (expansion_id);
