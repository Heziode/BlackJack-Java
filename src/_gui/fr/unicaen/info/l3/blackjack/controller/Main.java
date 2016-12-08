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

import fr.unicaen.info.l3.blackjack.ressources.Ressources;
import fr.unicaen.info.l3.blackjack.ressources.Utils;
import fr.unicaen.info.l3.blackjack.ressources.config.Configurator;
import fr.unicaen.info.l3.blackjack.ressources.config.CoreConfigurator;
import fr.unicaen.info.l3.blackjack.ressources.language.BlackjackKeys;
import fr.unicaen.info.l3.blackjack.view.InfoDialogController;
import fr.unicaen.info.l3.blackjack.view.MainMenuController;
import fr.unicaen.info.l3.blackjack.view.NewGameDialogController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;

/**
 * Partie principal de l'application
 * Va executer le launcher pour afficher le menu principal et pouvoir ainsi jouer un partie
 */
public final class Main extends Application {

    /**
     * Conteneur principal de l'IHM (la fenêtre)
     */
    public Stage primaryStage;

    /**
     * Disposition de la fenêtre
     */
    private BorderPane rootLayout;

    /**
     * Méthode appelée au lancement du programme
     * Elle configure les paramètres de l'application avant de lancer l'IHM
     *
     * @param args Liste d'arguments passer au programme
     */
    public static void main(String[] args) {
        config();
        launch(args);
    }

    /**
     * Configue l'application suivante ce qui lui est passé en paramètre JVM
     */
    private static void config() {

        // S'il existe un paramètre lang et que la locale (la langue) soit correcte
        if ((System.getProperty("lang") != null) && (Utils.localeIsValid(new Locale(System.getProperty("lang"))))) {
            Configurator conf = Configurator.getInstance();
            conf.setPropertie(CoreConfigurator.LANG, System.getProperty("lang"));
        }
    }

    /**
     * Initialise la coucher racine
     */
    private void initRootLayout() {
        try {
            // Charge la couche principale depuis un fichier fxml
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/fr/unicaen/info/l3/blackjack/view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            // Affiche la scène avec la mise en page de la racine (rootLayout)
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Affiche le menu principal
     */
    private void showMaimMenu() {

        try {
            // Charge le menu principal
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/fr/unicaen/info/l3/blackjack/view/MainMenu.fxml"));
            AnchorPane mainMenu = (AnchorPane) loader.load();

            // Affecte le menu principal au centre du BorderPane principal
            rootLayout.setCenter(mainMenu);

            // Donne un contrôleur pour le menu principal
            MainMenuController controller = loader.getController();
            controller.setMain(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Ouvre une boîte de dialogue pour paramétrer la partie
     */
    public void showNewGameDialog() {
        try {
            // Charge le fichier fxml d'initialisation de partie et créer une scène pour la boîte de dialogue
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/fr/unicaen/info/l3/blackjack/view/NewGameDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Créer la scène de dialogue.
            Stage dialogStage = new Stage();
            dialogStage.setTitle(Ressources.getString(BlackjackKeys.WINTITLE_INIT_GAME_WINDOW_NAME));

            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Affecte un contrôleur
            NewGameDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);

            // Affiche la boîte de dialogue et attend qu'une action de l'utilisateur la ferme
            dialogStage.showAndWait();

            // Si le joueur a cliqué sur le bouton de validation
            if (controller.isOkClicked()) {
                showBoard(controller.getNumberOfPlayers(), controller.getNumberOfDecks());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Affiche la boîte d'information
     */
    public void showInfoDialog() {
        try {
            // Charge le fichier fxml d'initialisation de partie et créer une scène pour la boite de dialogue
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/fr/unicaen/info/l3/blackjack/view/InfoDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Créer la scène de dialogue
            Stage dialogStage = new Stage();
            dialogStage.setTitle(Ressources.getString(BlackjackKeys.WINTITLE_INFO));

            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            dialogStage.setResizable(false);

            // Affecte un contrôleur
            InfoDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);

            // Affiche la boîte de dialogue
            dialogStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Affiche le plateau de jeu dans un autre thread (permet la création de plusieurs parties simultanées)
     *
     * @param numberOfPlayers Nombre de joueurs
     * @param numberOfDecks   Nombre de jeux de cartes
     */
    private void showBoard(int numberOfPlayers, int numberOfDecks) {
        Platform.runLater(new Runnable() {
            public void run() {
                try {
                    new GameView(numberOfPlayers, numberOfDecks).start(new Stage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    /**
     * Méthode appelée par l'initialisation de la fenêtre
     *
     * @param primaryStage Fenêtre à initialiser
     */
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle(Ressources.getString(BlackjackKeys.WINTITLE_MAIN_WINDOW_NAME));

        // Affecte un icon à l'application.
        this.primaryStage.getIcons().add(new Image(getClass().getResourceAsStream(Utils.APPLICATION_ICON_PATH)));

        initRootLayout();

        showMaimMenu();
    }
}
