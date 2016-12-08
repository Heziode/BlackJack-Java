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

package fr.unicaen.info.l3.blackjack.strategyplayer;

import fr.unicaen.info.l3.blackjack.model.BJHand;
import fr.unicaen.info.l3.blackjack.model.GameRulesBlackJack;
import fr.unicaen.info.l3.blackjack.model.actor.Player;

/**
 * Action effectuée lorsque la stratégie choisit est : AlgoSimple.<br>
 * Pioche jusqu'à ce que la valeur de la main soit supérieure soit égale à 17.
 */
public final class AlgoSimple implements Strategy {

    @Override
    public void action(Player player) {
        //  le premier coup : deux cas possible piocher ou passer
        BJHand currentBJHand = player.getCurrentHand();
        if (currentBJHand.getHandValue() >= GameRulesBlackJack.MIN_VALUE_OF_DEALER_HAND) {
            player.setStrategy(new Pass());
            player.action();
            return;
        }
        else {
            player.setStrategy(new Draw());
            player.action();
            player.setStrategy(new AlgoSimple());
            player.action();
        }
    }
}
