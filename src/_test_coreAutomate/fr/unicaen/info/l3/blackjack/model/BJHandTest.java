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

package fr.unicaen.info.l3.blackjack.model;

import cardgamebase.model.card.Color;
import cardgamebase.model.card.SimpleCard;
import fr.unicaen.info.l3.blackjack.model.actor.Winner;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

/**
 * Classe de test de la classe BJHand
 */
public class BJHandTest {

    /**
     * Attribut BjHand à tester
     */
    private BJHand bjHand;

    /**
     * Cartes de la main
     */
    private SimpleCard card1, card2;

    /**
     * Initialisation des attributs avant les tests
     */
    @Before
    public void setUp() {
        bjHand = new BJHand();

        card1 = new SimpleCard(Color.CLUB, 10);
        card2 = new SimpleCard(Color.SPADES, 5);

        bjHand.addCard(card1);
        bjHand.addCard(card2);
    }

    /**
     * Méthode de test de la méthode isPair
     * qui renvoie un boolean pour savoir si la main est pair ou non
     */
    @Test
    public void isPair() {
        assertEquals(false, bjHand.isPair());
    }

    /**
     * Méthode de test sur le mutateur de bet
     * qui change la valeur de l'attribut bet
     */
    @Test
    public void setBet() {
        int bet = (500);
        bjHand.setBet(bet);
        assertEquals(bet, bjHand.getBet().get());
    }

    /**
     * Méthode de test sur l'assesseur de score
     * qui renvoie le score de la main courante
     */
    @Test
    public void getScore() {
        assertEquals(15, bjHand.getScore().get());
    }

    /**
     * Méthode de test sur la méthode gotOneAsInHand
     * qui renvoie un boolean qui indique si il y a un as dans la main ou non
     */
    @Test
    public void gotOneAsInHand() {
        bjHand.addCard(new SimpleCard(Color.HEART, 1));
        assertEquals(true, bjHand.gotOneAsInHand());
    }


    /**
     * Méthode de test sur le mutateur de result
     * qui change la valeur de l'attribut result
     */
    @Test
    public void setResult() {
        bjHand.setResult(Winner.WIN);
        assertEquals(Winner.WIN, bjHand.getResult().get());
    }

    /**
     * Méthode de test sur la méthode getHandValue
     * qui retourne la valeur de la main et uniquement des cartes visibles
     */
    @Test
    public void getHandValue() {
        bjHand.addCard(new SimpleCard(Color.HEART, 8, false));
        assertEquals(15, bjHand.getHandValue());
    }

    /**
     * Méthode de test sur la méthode handValueSuperiorTo21
     * qui renvoie un boolean qui indique si la valeur de la main est supérieure à 21
     */
    @Test
    public void handValueSuperiorTo21() {
        assertEquals(false, bjHand.handValueSuperiorTo21());
    }

    /**
     * Méthode de test sur la méthode refreshScore
     * qui met à jour le score de la main
     */
    @Test
    public void refreshScore() {
        bjHand.addCard(new SimpleCard(Color.HEART, 8));
        assertEquals(15 + 8, bjHand.getScore().get());
    }
}