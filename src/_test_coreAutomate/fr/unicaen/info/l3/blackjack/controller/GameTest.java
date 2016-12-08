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

package fr.unicaen.info.l3.blackjack.controller;

import fr.unicaen.info.l3.blackjack.stategame.State;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Classe de test de la classe Game du controller
 */
public class GameTest {

    /**
     * Nombre de joueurs
     */
    private static final int numberOfPlayers = 2;

    /**
     * Nombre de decks
     */
    private static final int numberOfDecks = 2;

    /**
     * Attribut game à tester
     */
    private Game game;

    /**
     * Etat du jeu
     */
    private State state;

    /**
     * Initialisation avant les tests
     */
    @Before
    public void init() {
        game = new Game(numberOfPlayers, numberOfDecks);
    }

    /**
     * test de la méthode getPlayerList()
     *
     * @throws Exception
     */
    @Test
    public void testGetPlayerList() throws Exception {
        assertNotNull(game.getPlayerList());

        // on compte le joueur humain dans la liste de joueurs
        assertEquals(numberOfPlayers + 1, game.getPlayerList().size());
    }

    /**
     * test de la méthode getDealer()
     *
     * @throws Exception
     */
    @Test
    public void testGetDealer() throws Exception {
        assertNotNull(game.getDealer());

        assertEquals(numberOfDecks * 52, game.getDealer().getDeck().getDeck().size());
    }
}