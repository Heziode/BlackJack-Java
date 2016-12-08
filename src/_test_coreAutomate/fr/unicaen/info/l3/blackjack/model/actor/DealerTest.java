/*
 * (The MIT License)
 *
 * Copyright (c) 2016 - Dauprat Quentin, Duval Florian, Girault Jeoffrey & Michel Fabien
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and
 * to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of
 * the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO
 * THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS
 * IN THE SOFTWARE.
 */

package fr.unicaen.info.l3.blackjack.model.actor;

import cardgamebase.model.card.SimpleDeck;
import cardgamebase.model.card.SimpleHand;
import fr.unicaen.info.l3.blackjack.controller.Game;
import fr.unicaen.info.l3.blackjack.model.GameRulesBlackJack;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Classe de test de la classe Dealer
 */
public class DealerTest {

    /**
     * Nombre de decks
     */
    private static final int numberOfDecks = 2;

    /**
     * Nombre de joueurs
     */
    private static final int numberOfPlayers = 2;

    /**
     * Attribut game
     */
    private Game game;

    /**
     * Attribut deck
     */
    private SimpleDeck deck;

    /**
     * Initialisation des tests
     */
    @Before
    public void init() {
        game = new Game(numberOfPlayers, numberOfDecks);
        deck = new SimpleDeck();
    }


    /**
     * Test de la méthode getValueCardVisible
     * qui retourne la valeur de la carte visible
     *
     * @throws Exception
     */
    @Test
    public void getValueCardVisible() throws Exception {
        game.running();
        assertNotNull(game.getDealer().getValueCardVisible());
        assertTrue(
                game.getDealer().getValueCardVisible() >= 0 &&
                        game.getDealer().getValueCardVisible() <= GameRulesBlackJack.CARD_VALUE_KING
        );
    }

    /**
     * Test de la méthode setDeck
     * mutateur de l'attribut deck
     *
     * @throws Exception
     */
    @Test
    public void setDeck() throws Exception {
        game.getDealer().setDeck(deck);
        assertEquals(deck, game.getDealer().getDeck());
    }

    /**
     * Test de la méthode fill
     * Remplissage de la pioche
     *
     * @throws Exception
     */
    @Test
    public void fill() throws Exception {
        game.getDealer().fill(numberOfDecks);
        assertNotNull(game.getDealer().getDeck());
        assertEquals(numberOfDecks * 52, game.getDealer().getDeck().getDeck().size());
    }

    /**
     * Test de la méthode giveCard
     * Le dealer donne une carte en la retirant du deck
     *
     * @throws Exception
     */
    @Test
    public void giveCard() throws Exception {
        game.getDealer().giveCard();
        assertEquals(numberOfDecks * 52 - 1, game.getDealer().getDeck().getDeck().size());
    }

    /**
     * Test de la méthode shuffleDeck
     * Melange optimal du deck
     *
     * @throws Exception
     */
    @Test
    public void shuffleDeck() throws Exception {

        deck.fill(1);

        game.getDealer().shuffleDeck();
        SimpleDeck shuffleDeck = game.getDealer().getDeck();

        boolean value = false;
        int i = 0;
        while ((value == false)) {
            if (deck.getDeck().get(i) != shuffleDeck.getDeck().get(i)) {
                value = true;
            }

            i++;

            if (i == shuffleDeck.getDeck().size()) {
                break;
            }
        }

        assertTrue(value);
    }

    /**
     * Test de la méthode takeCard
     * Le dealer prend des cartes pour compléter sa main
     *
     * @throws Exception
     */
    @Test
    public void takeCard() throws Exception {
        SimpleHand hand = new SimpleHand();
        for (int i = 0; i < game.getDealer().getCurrentHand().getHandCards().size(); i++) {
            hand.addCard(game.getDealer().getCurrentHand().getHandCards().get(i));
        }

        game.getDealer().takeCard();

        assertNotEquals(hand.getHandCards(), game.getDealer().getCurrentHand().getHandCards());
    }

    /**
     * Test de la méthode getCurrentHand
     * Retourne la main du dealer
     *
     * @throws Exception
     */
    @Test
    public void getCurrentHand() throws Exception {
        assertNotNull(game.getDealer().getCurrentHand());
    }

    /**
     * Test de la méthode getIntCurrentHand
     *
     * @throws Exception
     */
    @Test
    public void getIntCurrentHand() throws Exception {
        assertNotNull(game.getDealer().getIntCurrentHand());
    }
}