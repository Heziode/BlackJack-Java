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

import cardgamebase.model.actor.SimplePlayer;
import cardgamebase.model.card.SimpleCard;
import cardgamebase.model.card.SimpleDeck;
import fr.unicaen.info.l3.blackjack.model.BJHand;
import fr.unicaen.info.l3.blackjack.model.GameRulesBlackJack;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

/**
 * Classe abstraite d'un acteur du jeu
 */
public abstract class BJActor extends SimplePlayer<SimpleDeck, BJHand, SimpleCard, GameRulesBlackJack> {

    /**
     * Boolean qui détermine qui est le joueur en train de jouer. Est public pour faciliter l'accès aux classes qui s'en
     * servir (car c'est les autres objets qui changent son état). Est écoutable pour ajouter des évènements au
     * contrôleur pour la vue.
     */
    BooleanProperty currentPlayer;

    /**
     * Permets de déterminer s'il s'agit d'un joueur humain ou non (nécessitant une GUI pour les interactions ou non)
     */
    boolean playable;

    /**
     * Constructeur logique
     *
     * @param gameRules Référence vers les règles du jeu
     */
    BJActor(GameRulesBlackJack gameRules) {
        super(gameRules);
        this.playable = false;
        init();
    }

    /**
     * Initialise les propriétés de l'acteur
     */
    private void init() {
        this.hands.add(new BJHand());
        this.currentPlayer = new SimpleBooleanProperty(false);
    }

    /**
     * Vide toutes les cartes de l'acteur
     *
     * @param discard Référence vers la défausse du jeu
     */
    public void resetHand(SimpleDeck discard) {
        for (BJHand hand : hands) {
            for (int j = hand.size() - 1; j >= 0; j--) {
                discard.addCard(hand.getHandCards().remove(j));
            }
        }
        reinitializeHand();
        init();
    }


    /**
     * Vérifie si l'acteur possède un BlackJack<br>
     * Est final pour empêcher la redéfinition.
     *
     * @return Retourne true s'il en possède un
     */
    final boolean gotABlackJack() {
        return getCurrentHand().getHandCards().size() == 2 &&
                getCurrentHand().getHandValue() == GameRulesBlackJack.MAX_VALUE_OF_HAND;
    }

    /**
     * Vérifie si la valeur de la main du joueur dépasse 21<br>
     * Est final pour empêcher la redéfinition.
     *
     * @return Retourne true si la valeur de la main est strictement supérieure à 21, false sinon
     */
    final boolean checkHandValueSup21() {
        return this.getCurrentHand().handValueSuperiorTo21();
    }

    /**
     * Définit si le joueur est jouable par un humain ou non<br>
     * Est final pour empêcher la redéfinition.
     *
     * @return Retourne true si le joueur est jouable par un humain, false sinon
     */
    public final boolean isPlayable() {
        return playable;
    }

}