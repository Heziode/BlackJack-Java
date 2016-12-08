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

/**
 * Énumération des différents cas de victoire
 */
public enum Winner {

    /**
     * Victoire avec un blackjack
     */
    WINBJ,

    /**
     * Perte avec assurance
     */
    LOSEINSURANCE,

    /**
     * Égalité avec assurance
     */
    TIEINSURANCE,

    /**
     * Victoire simple
     */
    WIN,

    /**
     * Égalité simple
     */
    TIE,

    /**
     * Perte simple
     */
    LOSE;

    /**
     * Choisie le type de victoire ou de défaite d'un joueur suivant un dealer
     *
     * @param player Référence vers un joueur
     * @param dealer Référence vers le dealer du jeu
     * @return Retourne le nouvel état du joueur passer en paramètre
     */
    public static Winner chooseWinner(Player player, Dealer dealer) {

        if (player.checkHandValueSup21()) {
            return LOSE;
        }
        else if (dealer.checkHandValueSup21()) {
            return WIN;
        }
        else if (player.gotABlackJack() && dealer.gotABlackJack()) {
            return TIEINSURANCE;
        }
        else if (dealer.gotABlackJack() && !player.gotABlackJack()) {
            return LOSEINSURANCE;
        }
        else if (!dealer.gotABlackJack() && player.gotABlackJack()) {
            return WINBJ;
        }
        else if (player.getCurrentHand().getHandValue() > dealer.getCurrentHand().getHandValue()) {
            return WIN;
        }
        else if (player.getCurrentHand().getHandValue() == dealer.getCurrentHand().getHandValue()) {
            return TIE;
        }
        else {
            return LOSE;
        }
    }

    /**
     * Conséquence d'un Winner. Applique les gains ou les pertes suivant le type de victoire ou de défaite du joueur.
     *
     * @param player Référence vers un joueur
     */
    public final void consequence(Player player) {
        switch (this) {
            case WINBJ:
                player.setWallet(player.getWallet() +
                        (player.getCurrentHand().getBet().get() * GameRulesBlackJack.PROFIT_BLACKJACK));
                player.getCurrentHand().setResult(WIN);
                break;
            case WIN:
                player.setWallet(player.getWallet() +
                        (player.getCurrentHand().getBet().get() * GameRulesBlackJack.PROFIT_NORMAL));
                player.getCurrentHand().setResult(WIN);
                break;

            case LOSEINSURANCE:
                player.getCurrentHand().setResult(LOSE);
                break;
            case LOSE:
                player.getCurrentHand().setResult(LOSE);
                break;

            case TIEINSURANCE:
                player.earnCurrentHandBet();
                player.getCurrentHand().setResult(TIE);
                break;
            case TIE:
                player.earnCurrentHandBet();
                player.getCurrentHand().setResult(TIE);
                break;

        }
        player.setWallet(Math.ceil(player.getWallet()));
        player.loseCurrentHandBet();
    }
}
