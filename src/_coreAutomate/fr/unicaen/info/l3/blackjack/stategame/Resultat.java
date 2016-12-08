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

package fr.unicaen.info.l3.blackjack.stategame;

import fr.unicaen.info.l3.blackjack.controller.Game;
import fr.unicaen.info.l3.blackjack.model.actor.Player;
import fr.unicaen.info.l3.blackjack.model.actor.Winner;

/**
 * Calcul le résultat de chaque joueur afin de savoir s'ils ont gagné ou perdu
 */
public final class Resultat implements State {

    @Override
    public void running(Game game) {
        for (Player player : game.getPlayerList()) {
            Winner.chooseWinner(player, game.getDealer()).consequence(player);

            if (player.hasSecondHand()) {
                player.nextHand();
                Winner.chooseWinner(player, game.getDealer()).consequence(player);
            }

            player.resetEndOfTurn();
        }

        game.endOfParty.setValue(true);
    }

    @Override
    public boolean isWell() {
        return false;
    }
}
