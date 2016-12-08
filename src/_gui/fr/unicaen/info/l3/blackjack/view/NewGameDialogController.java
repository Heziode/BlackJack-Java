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

package fr.unicaen.info.l3.blackjack.view;

import fr.unicaen.info.l3.blackjack.controller.Game;
import fr.unicaen.info.l3.blackjack.ressources.Ressources;
import fr.unicaen.info.l3.blackjack.ressources.language.BlackjackKeys;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

import java.text.MessageFormat;

/**
 * Classe contrôleur de la boîte de dialogue permettant de configurer une partie
 */
public final class NewGameDialogController {

    /*
     * Labels et bouttons
     */

    /**
     * Groupe de bouton radio pour définir le nombre jeux de cartes
     */
    @FXML
    private ToggleGroup numberOfDecksGroup;
    /**
     * Label : nombre de joueurs
     */
    @FXML
    private Label numberOfPlayers_lbl;
    /**
     * Label : nombre de jeux de cartes
     */
    @FXML
    private Label numberOfDecks_lbl;
    /**
     * Bouton : Jouer
     */
    @FXML
    private Button play_btn;
    /**
     * Bouton : annuler
     */
    @FXML
    private Button cancle_btn;
    /**
     * Groupe de bouton radio pour définir le nombre de joueurs
     */
    @FXML
    private ToggleGroup numberOfPlayersGroup;

    /*
     * Autres propriétés
     */
    /**
     * Boîte dialogue pour les erreurs éventuelles (même si c'est cloisonné)
     */
    private Stage dialogStage;

    /**
     * Permets de savoir si l'utilisateur à valider pour créer la partie ou annuler
     */
    private boolean okClicked = false;

    /**
     * Nombre de jouer définit par l'utilisateur
     */
    private int numberOfPlayers;

    /**
     * Nombre de jeu de cartes défini par l'utilisateur
     */
    private int numberOfDecks;

    /**
     * @return Retourne le nombre les joueurs
     */
    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    /**
     * @return Retourne le nombre les jeux de cartes
     */
    public int getNumberOfDecks() {
        return numberOfDecks;
    }

    /*
     * Définitions des méthodes
     */

    /**
     * Retourne true si l'utilisateur valide le formulaire, false sinon
     *
     * @return Retourne true si l'utilisateur valide le formulaire, false sinon
     */
    public boolean isOkClicked() {
        return okClicked;
    }

    /**
     * Initialise le contrôleur de classe. Automatiquement appelé après que le fichier fxml soit chargé
     */
    @FXML
    private void initialize() {

        numberOfPlayers_lbl.setText(Ressources.getString(BlackjackKeys.LABEL_NUMBER_OF_PLAYERS));
        numberOfDecks_lbl.setText(Ressources.getString(BlackjackKeys.LABEL_NUMBER_OF_DECKS));

        play_btn.setText(Ressources.getString(BlackjackKeys.BUTTON_PLAY));
        cancle_btn.setText(Ressources.getString(BlackjackKeys.BUTTON_CANCEL));
    }

    /**
     * Fixe l'état de ce dialogue
     *
     * @param dialogStage Référence vers conteneur de la boîte de dialogue
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    /**
     * Appelé lorsque l'utilisateur clique sur Jouer
     */
    @FXML
    private void handleOk() {

        String errorMessage = "";

        // Vérifie le nombre de joueurs
        String numberOfPlayersStr = ((RadioButton) numberOfPlayersGroup.getSelectedToggle()).getText();
        try {
            int numberOfPlayers = Integer.parseInt(numberOfPlayersStr);

            if ((numberOfPlayers < Game.MIN_PLAYERS) || (numberOfPlayers > Game.MAX_PLAYERS)) {
                errorMessage += MessageFormat.format(Ressources.getString(BlackjackKeys.ERROR_INVALID_NUMBER),
                        new Object[]{numberOfPlayers}) + "\n";
            }
            else {
                this.numberOfPlayers = numberOfPlayers;
            }
        } catch (NumberFormatException err) {
            errorMessage += MessageFormat
                    .format(Ressources.getString(BlackjackKeys.ERROR_NOT_NUMBER), new Object[]{numberOfPlayers}) + "\n";
        }

        // Vérifie le nombre de jeux de cartes
        String numberOfDecksStr = ((RadioButton) numberOfDecksGroup.getSelectedToggle()).getText();
        try {
            int numberOfDecks = Integer.parseInt(numberOfDecksStr);

            if ((numberOfDecks < Game.MIN_DECKS) || (numberOfDecks > Game.MAX_DECKS)) {
                errorMessage += MessageFormat
                        .format(Ressources.getString(BlackjackKeys.ERROR_INVALID_NUMBER), new Object[]{numberOfDecks}) +
                        "\n";
            }
            else {
                this.numberOfDecks = numberOfDecks;
            }
        } catch (NumberFormatException err) {
            errorMessage += MessageFormat
                    .format(Ressources.getString(BlackjackKeys.ERROR_NOT_NUMBER), new Object[]{numberOfDecks}) + "\n";
        }


        if (errorMessage.length() != 0) {
            // Affiche un message d'erreur
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle(Ressources.getString(BlackjackKeys.WINERRORTITLE_INVALID_FIELD_WINDOW_NAME));
            alert.setHeaderText(Ressources.getString(BlackjackKeys.HEADERTITLE_INVALID_FIELD));
            alert.setContentText(errorMessage);

            alert.showAndWait();
        }
        else {
            this.okClicked = true;
            dialogStage.close();
        }
    }

    /**
     * Appelé lorsque l'utilisateur clique sur Annuler
     */
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

}
