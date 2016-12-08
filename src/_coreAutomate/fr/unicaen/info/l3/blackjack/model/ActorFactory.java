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

package fr.unicaen.info.l3.blackjack.model;

import fr.unicaen.info.l3.blackjack.model.actor.BJActor;
import fr.unicaen.info.l3.blackjack.model.actor.Dealer;
import fr.unicaen.info.l3.blackjack.model.actor.PlayerBot;
import fr.unicaen.info.l3.blackjack.model.actor.PlayerHuman;

/**
 * Usine d'acteur
 */
public final class ActorFactory {

    /**
     * Référence vers le dealer de la partie courante
     */
    private BJActor dealer;

    /**
     * Référence vers les règles du jeu
     */
    private GameRulesBlackJack rulesBJ;

    /**
     * Constructeur logique
     *
     * @param rules Référence vers les règles du jeu
     */
    public ActorFactory(GameRulesBlackJack rules) {
        this.rulesBJ = rules;
        this.dealer = new Dealer(this.rulesBJ);
    }

    /**
     * Constructeur d'acteur.
     *
     * @param type Type d'acteur
     * @return Retourne un nouvel acteur pour le jeu suivant le type passer en paramètre
     */
    public BJActor getActor(Type type) {
        switch (type) {
            case Dealer:
                return this.dealer;
            case Bot:
                return new PlayerBot(GameRulesBlackJack.DEFAULT_WALLET, this.dealer, this.rulesBJ);
            case Human:
                return new PlayerHuman(GameRulesBlackJack.DEFAULT_WALLET, this.dealer, this.rulesBJ);
            default:
                return null;

        }
    }

    /**
     * Permet de changer les règles du jeu
     *
     * @param rulesBJ Nouvelle règle du jeu
     */
    public void changeRulesFactory(GameRulesBlackJack rulesBJ) {
        this.rulesBJ = rulesBJ;
    }

    /**
     * Type de joueur
     */
    public enum Type {
        /**
         * Croupier
         */
        Dealer,

        /**
         * Joueur humain
         */
        Human,

        /**
         * Joueur machine
         */
        Bot
    }

}
