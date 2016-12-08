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

import fr.unicaen.info.l3.blackjack.ressources.Ressources;
import fr.unicaen.info.l3.blackjack.ressources.language.BlackjackKeys;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * Contrôleur de la vue : EndGameDialog
 */
public final class EndGameDialogController {

    /**
     * Message affiché par la boîte de dialogue informant le joueur qu'il a perdu
     */
    @FXML
    private Label lose_msg_lbl;

    /**
     * Bouton permettant de quitter la partie actuelle
     */
    @FXML
    private Button quit_btn;

    /**
     * Boîte dialogue
     */
    private Stage dialogStage;

    /**
     * Initialise le contrôleur de classe. Automatiquement appelé après que le fichier fxml soit chargé
     */
    @FXML
    private void initialize() {
        lose_msg_lbl.setText(Ressources.getString(BlackjackKeys.LABEL_LOSE_MESSAGE));
        quit_btn.setText(Ressources.getString(BlackjackKeys.BUTTON_QUIT));
    }

    /**
     * Méthode appelée lorsque le joueur clique sur : quitte
     */
    @FXML
    public void handleQuit() {
        dialogStage.close();
    }

    /**
     * Mutateur dialogStage
     *
     * @param dialogStage Nouvelle valeur de dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
}
