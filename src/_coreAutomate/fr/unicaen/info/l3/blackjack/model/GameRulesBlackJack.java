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

import cardgamebase.model.SimpleGameRules;

/**
 * Classe contenant certaines constantes liées au jeu
 */
public final class GameRulesBlackJack extends SimpleGameRules {

    /**
     * Valeur maximum d'une main
     */
    public static final int MAX_VALUE_OF_HAND = 21;

    /**
     * Valeur minimale du dealer pour la fin de partie
     */
    public static final int MIN_VALUE_OF_DEALER_HAND = 17;

    /**
     * Valeur maximale que peut avoir un as
     */
    public static final int MAX_VALUE_OF_AS = 11;

    /**
     * Profit généré avec un blackjack
     */
    public static final double PROFIT_BLACKJACK = 2.5;

    /**
     * Profit généré normalement
     */
    public static final double PROFIT_NORMAL = 2.0;

    /**
     * Valeur des figures
     */
    public static final int VALUE_OF_SHAPES = 10;

    /**
     * Définis le montant par défaut du portemonnaie
     */
    public static final int DEFAULT_WALLET = 100;

    /**
     * Constructeur par défaut.<br>
     * Appel {@link #GameRulesBlackJack(int, int)} paramétré par 1 et -1 afin de définir
     * un compotement par defaut qui est : un joueur peut avoir qu'une main de taille infinie.
     */
    public GameRulesBlackJack() {
        super();
    }

    /**
     * Constructeur logique.<br>
     * Appel {@link #GameRulesBlackJack(int, int)}. Définit une taille de main infinie.
     *
     * @param numberOfHand Nombre de mains qu'un joueur peut avoir
     */
    public GameRulesBlackJack(int numberOfHand) {
        super(numberOfHand);
    }

    /**
     * Constructeur logique
     *
     * @param numberOfHand       Nombre de mains qu'un joueur peut avoir
     * @param numberOfCardByHand Nombre de carte qu'une main peut obtenir, <b>-1</b> correspondant à infinie.
     */
    public GameRulesBlackJack(int numberOfHand, int numberOfCardByHand) {
        super(numberOfHand, numberOfCardByHand);
    }
}
