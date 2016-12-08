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

package fr.unicaen.info.l3.blackjack.controller;

import cardgamebase.model.card.SimpleDeck;
import fr.unicaen.info.l3.blackjack.model.ActorFactory;
import fr.unicaen.info.l3.blackjack.model.ActorFactory.Type;
import fr.unicaen.info.l3.blackjack.model.GameRulesBlackJack;
import fr.unicaen.info.l3.blackjack.model.actor.Dealer;
import fr.unicaen.info.l3.blackjack.model.actor.Player;
import fr.unicaen.info.l3.blackjack.model.actor.PlayerBot;
import fr.unicaen.info.l3.blackjack.model.actor.PlayerHuman;
import fr.unicaen.info.l3.blackjack.stategame.Distribution;
import fr.unicaen.info.l3.blackjack.stategame.State;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Classe gérant une partie
 */
public final class Game {

    /**
     * Minimum de joueurs
     */
    public static final int MIN_PLAYERS = 0;

    /**
     * Maximum de joueurs
     */
    public static final int MAX_PLAYERS = 4;

    /**
     * Minimum de jeux de cartes
     */
    public static final int MIN_DECKS = 1;

    /**
     * Maximum de jeux de cartes
     */
    public static final int MAX_DECKS = 5;

    /**
     * Détermine si la partie est terminée ou non
     */
    public BooleanProperty endOfParty;

    /**
     * Défausse
     */
    private SimpleDeck discard;

    /**
     * Liste des joueurs de la partie
     */
    private List<Player> playerList;

    /**
     * Dealer de la partie
     */

    private Dealer dealer;

    /**
     * Usine d'acteur de la partie
     */
    private ActorFactory factory;

    /**
     * État de la partie
     */
    private ObjectProperty<State> state;

    /**
     * Constructeur logique
     *
     * @param numberOfPlayers Nombre de joueurs
     * @param numberOfDecks   Nombre de jeux de cartes
     */
    public Game(int numberOfPlayers, int numberOfDecks) {
        this.playerList = new LinkedList<Player>();
        this.factory = new ActorFactory(new GameRulesBlackJack(2, -1));
        this.dealer = (Dealer) factory.getActor(Type.Dealer);
        this.state = new SimpleObjectProperty<State>();
        this.state.set(new Distribution());
        this.endOfParty = new SimpleBooleanProperty(false);
        this.discard = new SimpleDeck();

        this.initializeDealer(numberOfDecks);
        this.initializePlayer(numberOfPlayers);
    }

    /**
     * Accesseur de state
     *
     * @return Retourne l'état courant du jeu
     */
    public ObjectProperty<State> getState() {
        return state;
    }

    /**
     * Modification de l'etat du jeu
     *
     * @param state Nouvelle etat du jeu
     */
    public void setState(State state) {
        this.state.set(state);
    }

    /**
     * Lance le jeu selon son état
     */
    public void running() {
        state.getValue().running(this);
    }

    /**
     * Récupération de la liste des joueurs
     *
     * @return Retourne la liste des joueurs
     */
    public List<Player> getPlayerList() {
        return playerList;
    }

    /**
     * Récuperation du dealer du jeu
     *
     * @return Retourne le dealer de la partie
     */
    public Dealer getDealer() {
        return dealer;
    }

    /**
     * Initialise un dealer avec un certain nombre de jeux de cartes
     *
     * @param numberOfDecks Nombre de jeux de cartes
     */
    private void initializeDealer(int numberOfDecks) {
        this.dealer.fill(numberOfDecks);
        this.dealer.shuffleDeck();
    }

    /**
     * Initialise des joueurs avec un portefeuille par défaut
     *
     * @param nbPlayer Nombre de joueurs
     */
    private void initializePlayer(int nbPlayer) {
        factory.changeRulesFactory(new GameRulesBlackJack(2, -1));

        Random randomNum = new Random();
        int humanPosition = nbPlayer > 0 ? randomNum.nextInt(nbPlayer) : nbPlayer;

        for (int i = 0; i <= nbPlayer; i++) {
            if (i == humanPosition) {
                this.playerList.add((PlayerHuman) factory.getActor(Type.Human));
            }
            else {
                this.playerList.add((PlayerBot) factory.getActor(Type.Bot));
            }
        }
    }

    /**
     * Initialise les mises des joueurs robots avec une valeur aléatoire
     */
    public void initializeBotBet() {
        for (int i = 0; i < playerList.size(); i++) {
            if (!playerList.get(i).isPlayable()) {
                int min = 1;
                int max = GameRulesBlackJack.DEFAULT_WALLET;
                Random rand = new Random();

                int bet = rand.nextInt(max - min + 1) + min;

                playerList.get(i).getCurrentHand().setBet(bet);
                playerList.get(i).setWallet(playerList.get(i).getWallet() - bet);
            }
        }
    }

    /**
     * Réinitialise une partie
     */
    public void reset() {
        dealer.resetHand(discard);
        for (Player player : playerList) {
            player.resetHand(discard);
        }
        dealer.addDiscard(discard);
        state.set(new Distribution());
        endOfParty.setValue(false);
    }

    /**
     * Récupère le joueur humain. Part du principe qu'il n'y a qu'un seul joueur humain dans la partie
     *
     * @return Retourne le joueur humain
     */
    public PlayerHuman getHumanPlayer() {
        for (Player p : playerList) {
            if (p.isPlayable()) {
                return (PlayerHuman) p;
            }
        }

        return null;
    }
}