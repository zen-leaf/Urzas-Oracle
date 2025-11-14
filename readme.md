# Urza's Oracle

This is the repository for Urza's Oracle, a web application that allows user to browse and comment on Magic the Gathering cards.

## Features

- #### Browse Magic the Gathering cards.
    - List browsing from search, with detail page containing interactive card functions.
    - Supports a one-per-user comment on each card, serving as a sort of review or opinion on the element.
- #### Keyword-based search:
    - Supports both standard name search (i.e. "Forest"), keyword-based search (i.e. "name:Nezumi cmc>2"), and a combination of the two.
- #### Basic deckbuilding functions
    - Create decks, supporting names and descriptions
    - Add cards in bulk from external text lists
    - Automatic check for game format legality
- #### Moderation
    - Purge or rename users and decks 

## Setup

Download and extract the project in a folder, then run it from your IDE.

A mySQL local account is required to handle the database communications, please verify the username and password match yours in the application.properties file.

Upon first startup, an admin account will be created (with ID: UrzaAdmin, and PW: password), alongside an automatic population of a selected batch of cards to browse in order to test the functions of the website. If you wish to change the set of cards that are saved to the database, modify "urza.bulkfetchtype" inside application.properties.

