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

import cardgamebase.model.card.Shuffle;
import cardgamebase.model.card.SimpleCard;
import cardgamebase.model.card.SimpleDeck;
import fr.unicaen.info.l3.blackjack.model.BJHand;
import fr.unicaen.info.l3.blackjack.model.GameRulesBlackJack;

/**
 * Croupier du jeu
 */
public final class Dealer extends BJActor {

    /**
     * Paquet de cartes
     */
    private SimpleDeck deck;

    /**
     * Constructeur logique
     *
     * @param gameRules Référence vers les règles du jeu
     */
    public Dealer(GameRulesBlackJack gameRules) {
        super(gameRules);
        this.deck = new SimpleDeck();
    }

    /**
     * Retourne la carte du dealer qui sera visible
     *
     * @return Retourne la carte visible
     */
    private SimpleCard getCardVisible() {
        return this.getCurrentHand().getHandCards().get(1);
    }

    /**
     * Permets de savoir si la carte visible est un as
     *
     * @return Retourne true si la carte visible du joueur est un as, false sinon
     */
    private boolean cardVisibleIsAs() {
        return getCardVisible().isAS();
    }

    /**
     * Retourne la valeur de la carte visible
     *
     * @return Retourne la valeur de la carte visible
     */
    public int getValueCardVisible() {
        return this.getCardVisible().getValue();
    }

    /**
     * Récupération du deck
     *
     * @return Retourne la pioche
     */
    public SimpleDeck getDeck() {
        return deck;
    }

    /**
     * Échange entre les deux decks
     *
     * @param deck Nouvelle valeur de la pioche
     */
    public void setDeck(SimpleDeck deck) {
        this.deck = deck;
    }

    /**
     * Remplissage de la pioche
     *
     * @param nbPackage Nombre de paquet de cartes que va contenir la pioche
     */
    public void fill(int nbPackage) {
        this.deck.fill(nbPackage);
    }

    /**
     * Le dealer propose une assurance si sa carte visible est un as
     *
     * @return Retourne true si la carte visible est un as, false sinon
     */
    public boolean proposeInsurance() {
        return cardVisibleIsAs();
    }

    /**
     * Le dealer donne une carte en la retirant du deck
     *
     * @return Retourne la carte tirée
     */
    public SimpleCard giveCard() {
        if (!this.deck.isEmpty()) {
            return this.deck.draw();
        }
        else {
            return null;
        }
    }

    /**
     * Mélange optimal du deck
     */
    public void shuffleDeck() {
        this.deck.setDeck(Shuffle.optimalShuffle(this.deck.getDeck()));
    }

    /**
     * Le dealer prend des cartes pour compléter sa main
     */
    public void takeCard() {

        SimpleCard card = giveCard();
        if (getCurrentHand().getHandCards().size() == 0) {
            card.setVisible(false);
        }
        this.getCurrentHand().addCard(card);
    }

    /**
     * Retourne la main du dealer
     *
     * @return Retourne la main du dealer
     */
    public BJHand getCurrentHand() {
        try {
            return this.hands.get(Player.FIRST_HAND);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    /**
     * Ajoute la défausse à la pioche
     *
     * @param discard Référence vers la défausse
     */
    public void addDiscard(SimpleDeck discard) {
        for (int i = discard.getDeck().size() - 1; i >= 0; i--) {
            deck.addCard(discard.getDeck().remove(i));
        }
    }

    /**
     * Met à jour l'ensemble des cartes du dealer pour la visualisation finale
     */
    public void refreshAllCards() {
        getHandsList().get(0).refreshScore();
    }

    @Override
    public void play() {

        if (getCurrentHand().getHandValue() > GameRulesBlackJack.MIN_VALUE_OF_DEALER_HAND) {
            getCurrentHand().getHandCards().get(0).setVisible(true);
            return;
        }
        else {
            takeCard();
            play();
        }
    }
}