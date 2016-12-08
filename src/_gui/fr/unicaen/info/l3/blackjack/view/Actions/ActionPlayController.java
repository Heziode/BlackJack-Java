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

package fr.unicaen.info.l3.blackjack.view.Actions;

import fr.unicaen.info.l3.blackjack.controller.Game;
import fr.unicaen.info.l3.blackjack.controller.GameView;
import fr.unicaen.info.l3.blackjack.model.actor.Player;
import fr.unicaen.info.l3.blackjack.model.actor.Winner;
import fr.unicaen.info.l3.blackjack.ressources.Ressources;
import fr.unicaen.info.l3.blackjack.ressources.language.BlackjackKeys;
import fr.unicaen.info.l3.blackjack.strategyplayer.DivisePair;
import fr.unicaen.info.l3.blackjack.strategyplayer.DoubleBet;
import fr.unicaen.info.l3.blackjack.strategyplayer.Draw;
import fr.unicaen.info.l3.blackjack.strategyplayer.Insurance;
import fr.unicaen.info.l3.blackjack.strategyplayer.Pass;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

/**
 * Classe gérant l'affichage des actions ne(s) utilisateurs pas
 */
public final class ActionPlayController implements Action {

    /**
     * Bouton pour finir son tour
     */
    @FXML
    private Button pass_btn;

    /**
     * Bouton pour demander une carte
     */
    @FXML
    private Button ask_btn;

    /**
     * Bouton pour doubler sa mise
     */
    @FXML
    private Button double_btn;

    /**
     * Bouton pour demander une assurance
     */
    @FXML
    private Button insurance_btn;

    /**
     * Bouton pour Diviser sa main lorsqu'on a une paire
     */
    @FXML
    private Button divide_btn;

    /**
     * Bouton pour quitter une partie
     */
    @FXML
    private Button quit_btn;

    /**
     * Classe de gestion d'une partie
     */
    private Game game;

    /**
     * Référence vers l'application principale (le conteneur)
     */
    private GameView main;

    /**
     * Référence sur l'utilisateur courant
     */
    private Player currentPlayer;
    /**
     * Compte le nombre d'actions faite par le joueur
     */
    private IntegerProperty nbAction;
    /**
     * Détermine si le joueur peut demander une assurance (pour empêcher plusieurs demandes d'assurance)
     */
    private boolean canInsurance;
    /**
     * Détermine si le joueur peut diviser sa main (pour empêcher plusieurs divisions de main)
     */
    private boolean canDivide;

    /**
     * Accesseur de game
     *
     * @return Retourne la référence vers le jeu
     */
    public Game getGame() {
        return game;
    }

    /**
     * Est appelé par l'application principale pour donner une référence sur elle-même
     *
     * @param main Référence vers la fenêtre du jeu
     */
    public void setMain(GameView main) {
        this.main = main;
    }

    /**
     * Initialise le contrôleur de classe. Automatiquement appelé après que le fichier fxml soit chargé
     */
    @FXML
    private void initialize() {
        pass_btn.setText(Ressources.getString(BlackjackKeys.BUTTON_PASS));
        ask_btn.setText(Ressources.getString(BlackjackKeys.BUTTON_ASK));
        double_btn.setText(Ressources.getString(BlackjackKeys.BUTTON_DOUBLE));
        insurance_btn.setText(Ressources.getString(BlackjackKeys.BUTTON_INSURANCE));
        divide_btn.setText(Ressources.getString(BlackjackKeys.BUTTON_DIVIDE));
        quit_btn.setText(Ressources.getString(BlackjackKeys.BUTTON_QUIT));

        nbAction = new SimpleIntegerProperty(0);

        canInsurance = true;
        canDivide = true;
    }

    /**
     * Change l'état des boutons (Activer/désactiver)
     */
    public void changeStatesButtons() {
        pass_btn.setDisable(false);
        ask_btn.setDisable(false);

        double_btn.setDisable(!(currentPlayer.getWallet() >= currentPlayer.getCurrentHand().getBet().getValue()));
        insurance_btn.setDisable(!(game.getDealer().proposeInsurance() && canInsurance && (nbAction.getValue() == 0) &&
                (currentPlayer.getWallet() >= Math.ceil((currentPlayer.getCurrentHand().getBet().getValue() / 2)))));
        divide_btn.setDisable(!(currentPlayer.getCurrentHand().isPair() && canDivide &&
                (currentPlayer.getWallet() >= currentPlayer.getCurrentHand().getBet().getValue())));
    }

    @Override
    public void init(Game game, GameView main) {
        this.game = game;
        this.main = main;

        this.currentPlayer = game.getHumanPlayer();

        for (int i = 0; i < currentPlayer.getHandsList().size(); i++) {
            currentPlayer.getHandsList().get(i).getResult().addListener((observable, oldValue, newValue) -> {
                if (newValue == Winner.LOSE && !currentPlayer.PASS_FIRST_HAND) {
                    handlePass();
                }
            });

            currentPlayer.getCurrentHand().getScore().addListener((observable, oldValue, newValue) -> {
                double_btn.setDisable(true);
            });

            currentPlayer.getCurrentHandProperty().addListener((observable, oldValue, newValue) -> {
                changeStatesButtons();
            });
            nbAction.addListener((observable, oldValue, newValue) -> {
                changeStatesButtons();
            });
        }
    }

    /**
     * Initialise la mise du joueur humain
     *
     * @param humanBet Mise du joueur humain
     */
    public void iniHumanBet(int humanBet) {
        for (Player player : this.game.getPlayerList()) {
            if (player.isPlayable()) {
                player.setWallet(player.getWallet() - humanBet);
                player.getCurrentHand().setBet(humanBet);
                break;
            }
            else {
                double bet = Math.random() * (player.getWallet() - 0);
                player.setWallet(player.getWallet() - bet);
                player.getCurrentHand().setBet(humanBet);
            }
        }
    }

    /**
     * Appeler lorsque le joueur clique sur le bouton : Passer
     */
    @FXML
    private void handlePass() {
        nbAction.setValue(0);
        changeStatesButtons();
        currentPlayer.setStrategy(new Pass());

        game.running();
    }

    /**
     * Appeler lorsque le joueur clique sur le bouton : Demander
     */
    @FXML
    private void handleAskCard() {
        nbAction.setValue(nbAction.getValue() + 1);
        currentPlayer.setStrategy(new Draw());
        game.running();
    }

    /**
     * Appeler lorsque le joueur clique sur le bouton : Doubler
     */
    @FXML
    private void handleDouble() {
        nbAction.setValue(nbAction.getValue() + 1);
        currentPlayer.setStrategy(new DoubleBet());
        game.running();
    }

    /**
     * Appeler lorsque le joueur clique sur le bouton : Assurance
     */
    @FXML
    private void handleInsurance() {
        canInsurance = false;
        nbAction.setValue(nbAction.getValue() + 1);
        currentPlayer.setStrategy(new Insurance());
        game.running();
    }

    /**
     * Appeler lorsque le joueur clique sur le bouton : Diviser
     */
    @FXML
    private void handleDivide() {
        canDivide = false;
        nbAction.setValue(nbAction.getValue() + 1);
        currentPlayer.setStrategy(new DivisePair());
        game.running();

        currentPlayer.getHandsList().get(1).getResult().addListener((observable, oldValue, newValue) -> {
            if (newValue == Winner.LOSE && !currentPlayer.PASS_SECOND_HAND) {
                handlePass();
            }
        });
    }

    /**
     * Appeler lorsque le joueur clique sur le bouton : Quitter
     */
    @FXML
    private void handleQuit() {
        main.primaryStage.close();

    }
}
