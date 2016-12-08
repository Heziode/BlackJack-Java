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

import cardgamebase.model.card.SimpleDeck;
import fr.unicaen.info.l3.blackjack.model.BJHand;
import fr.unicaen.info.l3.blackjack.model.GameRulesBlackJack;
import fr.unicaen.info.l3.blackjack.strategyplayer.Strategy;

/**
 * Classe définissant un joueur abstrait
 */
public abstract class Player extends BJActor {

    /**
     * Définit si le joueur à passer sa première main
     */
    public boolean PASS_FIRST_HAND = false;

    /**
     * Définit si le joueur à passer sa second main
     */
    public boolean PASS_SECOND_HAND = false;

    /**
     * Référence sur un dealer
     */
    protected Dealer dealer;

    /**
     * Définit si le joueur à prit une assurance
     */
    private boolean playerAcceptInsurance;

    /**
     * Portemonnaie du joueur
     */
    private double wallet;

    /**
     * Stratégie du joueur (piocher, passer, etc.)
     *
     * @see Strategy
     */
    private Strategy strategy;

    /**
     * Constructeur logique
     *
     * @param wallet    Portemonnaie du joueur
     * @param dealer    Référence vers le dealer
     * @param gameRules Référence vers les règles du jeu
     */
    public Player(double wallet, BJActor dealer, GameRulesBlackJack gameRules) {
        super(gameRules);
        this.wallet = wallet;
        this.dealer = (Dealer) dealer;
    }

    /**
     * Accesseur de dealer<br>
     * Est final pour empêcher la redéfinition.
     *
     * @return Retourne le dealer
     */
    public final Dealer getDealer() {
        return dealer;
    }

    /**
     * Accesseur de playerAcceptInsurance<br>
     * Est final pour empêcher la redéfinition.
     *
     * @return Retourne playerAcceptInsurance
     */
    final boolean getAcceptInsurance() {
        return this.playerAcceptInsurance;
    }

    /**
     * Mutateur de playerAcceptInsurance<br>
     * Est final pour empêcher la redéfinition.
     *
     * @param accept Nouveulle valeur de playerAcceptInsurance
     */
    public final void setAcceptInsurance(boolean accept) {
        this.playerAcceptInsurance = accept;
    }

    /**
     * Mutateur de betInsurance<br>
     * Est final pour empêcher la redéfinition.
     */
    public final void setBetInsurance() {
        wallet -= getCurrentHand().getBet().intValue() / 2;
    }

    /**
     * Retourne la main courante<br>
     * Est final pour empêcher la redéfinition.
     *
     * @return Retourne la main courante
     */
    public final BJHand getCurrentHand() {
        try {
            return this.hands.get(this.getIntCurrentHand());
        } catch (IndexOutOfBoundsException e) {
            System.err.println(e);
            return null;
        }
    }

    /**
     * Accesseur de wallet<br>
     * Est final pour empêcher la redéfinition.
     *
     * @return Retourne la valeur du portemonnaie
     */
    public final double getWallet() {
        return wallet;
    }

    /**
     * Mutateur de wallet<br>
     * Est final pour empêcher la redéfinition.
     *
     * @param wallet Nouvelle valeur de wallet
     */
    public final void setWallet(double wallet) {
        this.wallet = wallet;
    }

    /**
     * Accesseur de strategy<br>
     * Est final pour empêcher la redéfinition.
     *
     * @return Retourne la valeur de strategy
     */
    public final Strategy getStrategy() {
        return strategy;
    }

    /**
     * Mutateur de strategy<br>
     * Est final pour empêcher la redéfinition.
     *
     * @param strategy Nouvelle valeur de strategie
     */
    public final void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    /**
     * Définit si un joueur à choisie une action ou non<br>
     * Est final pour empêcher la redéfinition.
     *
     * @return Retourne true si le joueur à choisie une action, false sinon
     */
    final boolean canAction() {
        return strategy != null;
    }

    /**
     * Exécute l'action du joueur<br>
     * Est final pour empêcher la redéfinition.
     */
    public final void action() {
        if (canAction()) {
            strategy.action(this);
        }
    }

    /**
     * Détermine s'il sagit de la fin du tour du joueur ou non<br>
     * Est final pour empêcher la redéfinition.
     *
     * @return Retourne true si c'est la fin du tour du joueur, false sinon
     */
    public final boolean isEndOfTurn() {
        return PASS_FIRST_HAND && PASS_SECOND_HAND;
    }

    /**
     * Réinitialise la fin du tour du joueur<br>
     * Est final pour empêcher la redéfinition.
     */
    public final void resetEndOfTurn() {
        PASS_FIRST_HAND = false;
        PASS_SECOND_HAND = false;
    }

    /**
     * Récupération de la mise du joueur<br>
     * Est final pour empêcher la redéfinition.
     */
    final void earnCurrentHandBet() {
        wallet += this.hands.get(getIntCurrentHand()).getBet().getValue();
    }

    /**
     * Remise a zero de la mise de la main<br>
     * Est final pour empêcher la redéfinition.
     */
    final void loseCurrentHandBet() {
        this.getCurrentHand().setBet(0);

        playerAcceptInsurance = false;
    }

    /**
     * Détermine si le joueur à une second main ou non<br>
     * Est final pour empêcher la redéfinition.
     *
     * @return Retourne true si le joueur à une second main, false sinon
     */
    public final boolean hasSecondHand() {
        return this.hands.size() > 1;
    }

    @Override
    public final void resetHand(SimpleDeck discard) {
        super.resetHand(discard);
        resetEndOfTurn();
    }
}