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

import fr.unicaen.info.l3.blackjack.model.BJHand;
import fr.unicaen.info.l3.blackjack.model.GameRulesBlackJack;
import fr.unicaen.info.l3.blackjack.strategyplayer.AlgoSimple;
import fr.unicaen.info.l3.blackjack.strategyplayer.DivisePair;
import fr.unicaen.info.l3.blackjack.strategyplayer.DoubleBet;
import fr.unicaen.info.l3.blackjack.strategyplayer.Pass;

/**
 * Joueur robot
 */
public final class PlayerBot extends Player {

    /**
     * Constructeur logique
     *
     * @param wallet    Portemonnaie du joueur
     * @param dealer    Référence vers le dealer
     * @param gameRules Référence vers les règles du jeu
     */
    public PlayerBot(int wallet, BJActor dealer, GameRulesBlackJack gameRules) {
        super(wallet, dealer, gameRules);
        int bet = (int) (Math.random() * wallet) + 1;
        getCurrentHand().setBet(bet);
    }

    /**
     * Choisie la stratégie à applique suivant sa main et la main du dealer
     */
    private void selectStrategy() {
        BJHand currentBJHand = getCurrentHand();
        int valueCardVisibleDealer = dealer.getValueCardVisible();

        if (currentBJHand.isPair()) {
            // le cas avec une paire
            if (currentBJHand.getHandValue() > 18) {
                setStrategy(new Pass());
                return;
            }
            else if (currentBJHand.getHandValue() > 10 && currentBJHand.getHandValue() <= 18) {
                if (!hasSecondHand()) {
                    setStrategy(new DivisePair());
                    return;
                }
                else {
                    setStrategy(new DoubleBet());
                    return;
                }
            }
            else if ((currentBJHand.getHandValue() >= 8 && currentBJHand.getHandValue() <= 10)) {
                setStrategy(new DoubleBet());
                return;
            }
            else {
                if (!hasSecondHand()) {
                    setStrategy(new DivisePair());
                    return;
                }
                else {
                    setStrategy(new DoubleBet());
                }
            }
        }
        else if (currentBJHand.gotOneAsInHand()) {
            // le cas avec un as
            if (currentBJHand.getHandValue() > 17) {
                setStrategy(new Pass());
                return;
            }
            else if (valueCardVisibleDealer > 6) {
                setStrategy(new AlgoSimple());
                return;
            }
            else if (currentBJHand.getHandValue() == 17 && valueCardVisibleDealer == 6) {
                setStrategy(new DoubleBet());
                return;
            }
            else if ((currentBJHand.getHandValue() / valueCardVisibleDealer) >= 6 ||
                    ((currentBJHand.getHandValue() == 12 ||
                            currentBJHand.getHandValue() == 13) && (valueCardVisibleDealer == 2 ||
                            valueCardVisibleDealer == 3))) {

                setStrategy(new AlgoSimple());
                return;
            }
            else {
                setStrategy(new DoubleBet());
                return;
            }
        }
        else {
            // la cas pas de paire ni d'as
            if (currentBJHand.getHandValue() > 17) {
                setStrategy(new Pass());
                return;
            }
            else if (currentBJHand.getHandValue() < 10) {
                setStrategy(new AlgoSimple());
                return;
            }
            else {
                setStrategy(new DoubleBet());
                return;
            }
        }
    }

    @Override
    public void play() {
        while (!isEndOfTurn()) {
            selectStrategy();
            action();
        }
    }
}