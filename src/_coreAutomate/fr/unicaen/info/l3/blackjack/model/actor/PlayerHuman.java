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

import fr.unicaen.info.l3.blackjack.model.GameRulesBlackJack;
import javafx.beans.property.SimpleBooleanProperty;

/**
 * Joueur humain
 */
public final class PlayerHuman extends Player {

    /**
     * Constructeur logique
     *
     * @param wallet    Portemonnaie du joueur
     * @param dealer    Référence vers le dealer
     * @param gameRules Référence vers les règles du jeu
     */
    public PlayerHuman(int wallet, BJActor dealer, GameRulesBlackJack gameRules) {
        super(wallet, dealer, gameRules);
        this.playable = true;
        currentPlayer = new SimpleBooleanProperty(false);
    }

    @Override
    public void play() {
        if (!currentPlayer.getValue() || !canAction()) {
            currentPlayer.setValue(true);
        }
        else {
            action();
        }

        if (isEndOfTurn()) {
            currentPlayer.setValue(false);
        }
        else {
            currentPlayer.setValue(true);
        }
    }
}