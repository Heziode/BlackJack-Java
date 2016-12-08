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
import fr.unicaen.info.l3.blackjack.strategyplayer.Draw;

/**
 * Distribution des cartes à chaque joueur et au dealer
 */
public final class Distribution implements State {

    @Override
    public void running(Game game) {
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < game.getPlayerList().size(); j++) {
                game.getPlayerList().get(j).setStrategy(new Draw());
                game.getPlayerList().get(j).action();
                if (game.getPlayerList().get(j).isPlayable()) {
                    game.getPlayerList().get(j).setStrategy(null);
                }
            }
            game.getDealer().takeCard();
        }
        game.setState(new Decision());
    }

    @Override
    public boolean isWell() {
        return false;
    }
}
