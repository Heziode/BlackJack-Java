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

import java.text.MessageFormat;

/**
 * Vue de la boîte d'information
 */
public final class InfoDialogController {

    /*
     * Labels et boutons
     */

    /**
     * Affiche des infos concernant l'application
     */
    @FXML
    Label info_lbl;

    /**
     * Affiche la version de l'application
     */
    @FXML
    Label version_lbl;

    /**
     * Affiche les auteurs de l'application
     */
    @FXML
    Label authors_lbl;

    /**
     * Bouton permettant de fermer la boîte de dialogue
     */
    @FXML
    Button ok_btn;

    /*
     * Autres propriétés
     */

    /**
     * Boîte dialogue pour les erreurs éventuelles (même si c'est cloisonné)
     */
    private Stage dialogStage;

    /**
     * Initialise le contrôleur de classe. Automatiquement appelé après que le fichier fxml soit chargé
     */
    @FXML
    private void initialize() {

        info_lbl.setText(Ressources.getString(BlackjackKeys.LABEL_INFO_ABOUT_APP));
        version_lbl.setText(MessageFormat
                .format(Ressources.getString(BlackjackKeys.LABEL_INFO_VERSION), System.getProperty("BJVersion")));
        authors_lbl.setText(Ressources.getString(BlackjackKeys.LABEL_INFO_AUTHORS));

        ok_btn.setText(Ressources.getString(BlackjackKeys.BUTTON_OK));
    }

    /**
     * Fixe l'état de cette boîte de dialoge
     *
     * @param dialogStage Référence vers conteneur de la boîte de dialogue
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    /**
     * Appelé lorsque l'utilisateur clique sur ok
     */
    @FXML
    private void handleOk() {
        dialogStage.close();
    }
}
