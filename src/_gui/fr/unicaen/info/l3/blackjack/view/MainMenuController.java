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

import fr.unicaen.info.l3.blackjack.controller.Main;
import fr.unicaen.info.l3.blackjack.ressources.Ressources;
import fr.unicaen.info.l3.blackjack.ressources.language.BlackjackKeys;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

/**
 * Classe contrôleur du menu principal
 */
public final class MainMenuController {

    /**
     * Bouton qui va appeler l'initialisation de la partie
     */
    @FXML
    private Button play_btn;

    /**
     * Bouton qui affiche des informations sur l'application
     */
    @FXML
    private Button info_btn;

    /**
     * Bouton permettant de quitter l'application
     */
    @FXML
    private Button quit_btn;

    /**
     * Référence vers l'application principale (le conteneur)
     */
    private Main main;

    /**
     * Initialise le contrôleur de classe. Automatiquement appelé après que le fichier fxml soit chargé
     */
    @FXML
    private void initialize() {
        play_btn.setText(Ressources.getString(BlackjackKeys.BUTTON_PLAY));
        info_btn.setText(Ressources.getString(BlackjackKeys.BUTTON_INFO));
        quit_btn.setText(Ressources.getString(BlackjackKeys.BUTTON_QUIT));
    }

    /**
     * Est appelé par l'application principale pour donner une référence sur elle-même
     *
     * @param main référence vers le conteneur
     */
    public void setMain(Main main) {
        this.main = main;
    }

    /**
     * Appelé lorsque le joueur appuie sur le bouton Jouer
     */
    @FXML
    private void handlePlay() {
        main.showNewGameDialog();
    }

    /**
     * Appelé lorsque le joueur appuie sur le bouton Info
     */
    @FXML
    private void handleInfo() {
        main.showInfoDialog();
    }

    /**
     * Appelé lorsque le joueur appuie sur le bouton Quitter pour fermer l'application
     */
    @FXML
    private void handleQuit() {
        main.primaryStage.close();
    }
}
