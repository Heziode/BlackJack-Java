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

import cardgamebase.model.card.SimpleCard;
import cardgamebase.model.card.SimpleHand;
import fr.unicaen.info.l3.blackjack.model.actor.Winner;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * Définis une main pour le jeu Blackjack
 */
public final class BJHand extends SimpleHand {

    /**
     * Contient la mise de la main
     */
    private IntegerProperty bet;

    /**
     * Contient le score de la main
     */
    private IntegerProperty score;

    /**
     * Permets de connaitre l'état de la main en fin de partie (gagnant, perdant, égaliter)
     */
    private ObjectProperty<Winner> result;

    /**
     * Default constructor
     */
    public BJHand() {
        super();
        this.bet = new SimpleIntegerProperty();
        this.score = new SimpleIntegerProperty();
        this.result = new SimpleObjectProperty<>();
    }

    /**
     * Permets de savoir si la main comporte une paire
     *
     * @return Retourne true si la main est une paire, false sinon
     */
    public boolean isPair() {
        return handCards.size() == 2 && (handCards.get(0).getValue() == handCards.get(1).getValue());
    }

    /**
     * Récupère la mise de la main
     *
     * @return Retourne un Integer Property (entier observable) comportant la mise
     * @see IntegerProperty
     */
    public IntegerProperty getBet() {
        return bet;
    }

    /**
     * Définis la mise de la main
     *
     * @param bet Valeur de la mise
     */
    public void setBet(int bet) {
        this.bet.setValue(bet);
    }

    /**
     * Accesseur de score
     *
     * @return Retourne la valeur de score
     */
    public IntegerProperty getScore() {
        return this.score;
    }

    /**
     * Vérifie si la main contient au moins un as
     *
     * @return Retourne true si la main contient un as, false sinon
     */
    public boolean gotOneAsInHand() {
        for (SimpleCard card : this.handCards) {
            if (card.isAS()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Accesseur observable de result
     *
     * @return Retourne le résultat de la main (pour savoir si elle est gagnante ou perdante)
     */
    public ObjectProperty<Winner> getResult() {
        return this.result;
    }

    /**
     * Mutateur de result
     *
     * @param result Nouveulle valeur de result
     */
    public void setResult(Winner result) {
        this.result.setValue(result);
    }

    /**
     * Récupère la valeur d'une main en ne tenant compte que des cartes visibles
     *
     * @return Retourne la valeur de la main
     */
    public int getHandValue() {
        int value = 0;
        int nbAS = 0;
        for (SimpleCard card : handCards) {
            if (card.isVisible()) {
                if (card.isAS()) {
                    nbAS++;
                    value += GameRulesBlackJack.MAX_VALUE_OF_AS;
                }
                else if (card.getValue() < GameRulesBlackJack.VALUE_OF_SHAPES) {
                    value += card.getValue();
                }
                else {
                    value += GameRulesBlackJack.VALUE_OF_SHAPES;
                }
            }
        }
        for (int i = 0; i < nbAS; i++) {
            if (value > GameRulesBlackJack.MAX_VALUE_OF_HAND) {
                value -= GameRulesBlackJack.VALUE_OF_SHAPES;
            }
        }
        return value;
    }

    /**
     * Retourne la validité de la main
     *
     * @return Retourne true si la main n'est plus valide, false sinon
     */
    public boolean handValueSuperiorTo21() {
        return getHandValue() > GameRulesBlackJack.MAX_VALUE_OF_HAND;
    }

    /**
     * Met à jour le score
     */
    public void refreshScore() {
        score.setValue(getHandValue());
    }

    @Override
    public void addCard(SimpleCard card) {
        this.handCards.add(card);
        score.setValue(getHandValue());

        if (score.getValue() > GameRulesBlackJack.MAX_VALUE_OF_HAND) {
            result.setValue(Winner.LOSE);
        }
    }
}