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

import cardgamebase.model.card.SimpleCard;
import fr.unicaen.info.l3.blackjack.model.BJHand;
import fr.unicaen.info.l3.blackjack.model.actor.Player;

/**
 * Action effectuée lorsque la stratégie choisit est : DivisePair.<br>
 * Sépare la main en deux mains distinct ayant chacune au minimum une carte en commun pour un même joueur.
 */
public final class DivisePair implements Strategy {

    @Override
    public void action(Player player) {

        SimpleCard pair = player.getCurrentHand().getHandCards().remove(1);
        BJHand secondBJHand = new BJHand();

        player.getHandsList().add(secondBJHand);
        secondBJHand.addCard(pair);
        secondBJHand.setBet(player.getCurrentHand().getBet().getValue());

        player.setStrategy(new Draw());
        player.action();
        player.nextHand();
        player.setStrategy(new Draw());
        player.action();
        player.nextHand();

    }
}
