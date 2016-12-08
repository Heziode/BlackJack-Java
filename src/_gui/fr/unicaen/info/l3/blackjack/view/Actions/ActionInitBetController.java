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
import fr.unicaen.info.l3.blackjack.ressources.Ressources;
import fr.unicaen.info.l3.blackjack.ressources.language.BlackjackKeys;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.text.MessageFormat;

/**
 * Contrôleur pour le choix de la mise
 */
public final class ActionInitBetController implements Action {

    /**
     * Champ de saisie de la mise
     */
    @FXML
    private TextField bet_field;

    /**
     * Bouton confirmant la mise
     */
    @FXML
    private Button confirm_btn;

    /**
     * Bouton pour quitter une partie
     */
    @FXML
    private Button quit_btn;

    /**
     * Label affichant le portemonaie du joueur
     */
    @FXML
    private Label wallet_lbl;

    /**
     * Label affichant la mise du joueur
     */
    @FXML
    private Label bet_lbl;

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
     * Contiendra la mise du joueur
     */
    private IntegerProperty bet;

    /**
     * Accesseur de bet
     *
     * @return Retourne la mise du joueur
     */
    public int getBet() {
        return bet.get();
    }

    /**
     * Accesseur observable de bet
     *
     * @return Retourne la mise (observable) du joueur
     */
    public IntegerProperty betProperty() {
        return bet;
    }

    /**
     * Mutateur de currentPlayer
     *
     * @param currentPlayer Joueur courant
     */
    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    /**
     * Initializes le contrôleur de classe. Automatiquement appelé après que le fichier fxml soit chargé
     */
    @FXML
    private void initialize() {
        confirm_btn.setText(Ressources.getString(BlackjackKeys.BUTTON_PLAY));
        quit_btn.setText(Ressources.getString(BlackjackKeys.BUTTON_QUIT));
        bet_lbl.setText(Ressources.getString(BlackjackKeys.LABEL_BET));

        bet = new SimpleIntegerProperty();

        // Ajoute un listener au champ texte pour retirer tout ce qui n'est pas un nombre
        bet_field.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                bet_field.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }

    /**
     * Appeler lorsque le joueur clique sur le bouton : Confirmer
     */
    @FXML
    private void handleConfirm() {
        try {

            int value = Integer.parseInt(bet_field.getText());

            if ((value > 0) && (value <= currentPlayer.getWallet())) {
                currentPlayer.getCurrentHand().setBet(value);
                bet.setValue(value);
            }
        } catch (NumberFormatException err) {
            // Ne rien faire car c'est juste que le champ de la mise est vide
        }
    }

    /**
     * Appeler lorsque le joueur clique sur le bouton : Quitter
     */
    @FXML
    private void handleQuit() {
        main.primaryStage.close();

    }

    @Override
    public void init(Game game, GameView main) {
        this.game = game;
        this.main = main;
        this.currentPlayer = game.getHumanPlayer();
        this.wallet_lbl.setText(MessageFormat.format(Ressources.getString(BlackjackKeys.LABEL_INIT_WALLET),
                new Object[]{currentPlayer.getWallet()}));
    }
}
