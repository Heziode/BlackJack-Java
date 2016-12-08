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

import fr.unicaen.info.l3.blackjack.controller.Game;
import fr.unicaen.info.l3.blackjack.model.GameRulesBlackJack;
import fr.unicaen.info.l3.blackjack.strategyplayer.AlgoSimple;
import fr.unicaen.info.l3.blackjack.strategyplayer.Strategy;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Classe de Test de la classe Player
 */
public class PlayerTest {

    /**
     * Nombre de decks
     */
    private static final int numberOfDecks = 2;

    /**
     * Nombre de joueurs
     */
    private static final int numberOfPlayers = 2;

    /**
     * Valeur du porte-monnaie
     */
    private static final int wallet = 200;

    /**
     * Valeur de la mise de la main
     */
    private static final int bet = 50;

    /**
     * Attribut Player à tester
     */
    private Player player;

    /**
     * Base de règles du Blackjack
     */
    private GameRulesBlackJack gameRules;

    /**
     * Attribut Game qui sera l'environnement dans lequel le joueur sera testé
     */
    private Game game;

    /**
     * Initialisation des attributs avant les tests
     */
    @Before
    public void setUp() {
        game = new Game(numberOfPlayers, numberOfDecks);
        gameRules = new GameRulesBlackJack();
        player = new PlayerBot(wallet, game.getDealer(), gameRules);
        player.getCurrentHand().getBet().set(bet);
    }

    /**
     * Test sur la méthode getDealer
     * elle est censée renvoyer le dealer de la partie en cours dans lequel le joueur est
     *
     * @throws Exception
     */
    @Test
    public void getDealer() throws Exception {
        assertNotNull(player.getDealer());
        assertEquals(game.getDealer(), player.getDealer());
    }

    /**
     * Test sur la méthode setAcceptInsurance
     * elle renvoie un boolean qui indique si le joueur a accepté l'assurance
     * proposée par le dealer si ce dernier pioche un AS
     *
     * @throws Exception
     */
    @Test
    public void setAcceptInsurance() throws Exception {
        player.setAcceptInsurance(true);
        assertTrue(player.getAcceptInsurance());
    }

    /**
     * Test sur la méthode setBetInsurance
     * elle permet de fixer la mise de la main si le joueur accepte l'assurance
     *
     * @throws Exception
     */
    @Test
    public void setBetInsurance() throws Exception {
        player.setBetInsurance();

        assertEquals(wallet - bet / 2, (int) player.getWallet());
    }

    /**
     * Test sur la méthode getIntCurrentHand
     * elle renvoie le numéro de la main courante
     *
     * @throws Exception
     */
    @Test
    public void getIntCurrentHand() throws Exception {
        assertNotNull(player.getIntCurrentHand());
        assertEquals(0, player.getIntCurrentHand());
    }

    /**
     * Test sur la méthode getCurrentHand
     * elle renvoie la main courante du joueur
     *
     * @throws Exception
     */
    @Test
    public void getCurrentHand() throws Exception {
        assertNotNull(player.getCurrentHand());
    }

    /**
     * Test sur la méthode setWallet
     * elle permet de fixer la valeur du porte-monnaie du joueur
     *
     * @throws Exception
     */
    @Test
    public void setWallet() throws Exception {
        player.setWallet(wallet);

        assertEquals(wallet, (int) player.getWallet());
    }

    /**
     * Test de la méthode setStrategy
     * elle permet de fixer la strategie que le joueur doit adopter
     *
     * @throws Exception
     */
    @Test
    public void setStrategy() throws Exception {
        Strategy strategy = new AlgoSimple();

        player.setStrategy(strategy);

        assertEquals(strategy, player.getStrategy());
    }

    /**
     * Test de la méthode canAction
     * elle permet de savoir si le joueur à une strategie ou non
     *
     * @throws Exception
     */
    @Test
    public void canAction() throws Exception {
        assertNotNull(player.canAction());

        Strategy strategy = new AlgoSimple();

        player.setStrategy(strategy);

        assertTrue(player.canAction());
    }

    /**
     * Test de la méthode isEndOfTurn
     * elle renvoie un boolean pour savoir si c'est la fin du tour du joueur courant
     *
     * @throws Exception
     */
    @Test
    public void isEndOfTurn() throws Exception {
        assertFalse(player.isEndOfTurn());
    }

    /**
     * Test de la méthode resetEndOfTurn
     * elle remet à zéro les conditions de fin de tour du joueur
     *
     * @throws Exception
     */
    @Test
    public void resetEndOfTurn() throws Exception {
        player.resetEndOfTurn();

        assertFalse(player.PASS_FIRST_HAND);
        assertFalse(player.PASS_SECOND_HAND);
    }

    /**
     * Test de la méthode earnCurrentHandBet
     * elle met à jour le porte-monnaie du joueur
     * elle rajoute les gains au porte-monnaie
     *
     * @throws Exception
     */
    @Test
    public void earnCurrentHandBet() throws Exception {
        player.earnCurrentHandBet();
        assertEquals(wallet + bet, (int) player.getWallet());
    }
}