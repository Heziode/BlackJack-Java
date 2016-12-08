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
import fr.unicaen.info.l3.blackjack.ressources.language.BlackjackKeys;
import fr.unicaen.info.l3.blackjack.view.BoardController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Affichage de la fenêtre de jeu
 */
public final class GameView extends Application {

    /**
     * Conteneur principal de l'IHM (la fenêtre)
     */
    public Stage primaryStage;

    /**
     * Disposition de la fenêtre
     */
    private BorderPane rootLayout;

    /**
     * Référence vers le contrôleur de vue du plateau
     */
    private BoardController boardController;

    /**
     * Nombre de joueurs dans la partie
     */
    private int numberOfPlayers;

    /**
     * Nombre de jeux de cartes dans la partie
     */
    private int numberOfDecks;

    /**
     * Constructeur logique
     *
     * @param numberOfPlayers Nombre de joueurs
     * @param numberOfDecks   Nombre de jeux de cartes
     */
    public GameView(int numberOfPlayers, int numberOfDecks) {
        this.numberOfPlayers = numberOfPlayers;
        this.numberOfDecks = numberOfDecks;
    }

    /**
     * Initialise la couche raçine
     */
    private void initRootLayout() {
        try {
            // Charge la couche principal depuis un fichier fxml
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/fr/unicaen/info/l3/blackjack/view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            // Affiche la scène avec la mise en page de la raçine (rootLayout)
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Affiche le plateu de jeu
     *
     * @param numberOfPlayers Nombre de joueurs
     * @param numberOfDecks   Nombre de jeux de cartes
     */
    private void showBoard(int numberOfPlayers, int numberOfDecks) {

        try {
            // Charge le menu principal.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/fr/unicaen/info/l3/blackjack/view/Board.fxml"));
            BorderPane board = loader.load();

            // Affecte le menu principal au centre du BorderPane principal
            rootLayout.setCenter(board);

            // Donne un contrôleur pour le menu principal
            BoardController controller = loader.getController();
            controller.init(this, numberOfPlayers, numberOfDecks);
            boardController = controller;

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void start(Stage primaryStage) throws InterruptedException {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle(Ressources.getString(BlackjackKeys.WINTITLE_MAIN_WINDOW_NAME));

        // Affecte un icon à l'application
        this.primaryStage.getIcons().add(new Image(getClass().getResourceAsStream(Utils.APPLICATION_ICON_PATH)));

        initRootLayout();

        showBoard(numberOfPlayers, numberOfDecks);

        Platform.runLater(new Thread() {
            public void run() {
                try {
                    join();
                    boardController.launchGame();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
