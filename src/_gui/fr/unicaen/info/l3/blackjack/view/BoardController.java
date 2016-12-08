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

import cardgamebase.controller.SimpleDeckView;
import fr.unicaen.info.l3.blackjack.controller.DealerHandView;
import fr.unicaen.info.l3.blackjack.controller.Game;
import fr.unicaen.info.l3.blackjack.controller.GameView;
import fr.unicaen.info.l3.blackjack.controller.PlayerHandView;
import fr.unicaen.info.l3.blackjack.model.actor.Player;
import fr.unicaen.info.l3.blackjack.ressources.Ressources;
import fr.unicaen.info.l3.blackjack.ressources.language.BlackjackKeys;
import fr.unicaen.info.l3.blackjack.stategame.State;
import fr.unicaen.info.l3.blackjack.view.Actions.Action;
import fr.unicaen.info.l3.blackjack.view.Actions.ActionEndController;
import fr.unicaen.info.l3.blackjack.view.Actions.ActionInitBetController;
import fr.unicaen.info.l3.blackjack.view.Actions.ActionPlayController;
import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Classe gérant l'affichage d'un plateau
 */
public final class BoardController {

    /**
     * Emplacement du fichier fxml décrivant la vue : initialisation de la mise
     */
    private static final String ACTION_INIT_BET = "/fr/unicaen/info/l3/blackjack/view/Actions/ActionInitBet.fxml";

    /**
     * Emplacement du fichier fxml décrivant la vue : action joueuse
     */
    private static final String ACTION_PLAY = "/fr/unicaen/info/l3/blackjack/view/Actions/ActionPlay.fxml";

    /**
     * Emplacement du fichier fxml décrivant la vue : fin d'action
     */
    private static final String ACTION_END = "/fr/unicaen/info/l3/blackjack/view/Actions/ActionEnd.fxml";

    /**
     * Dictionnaire contenant comme clé le joueur et comme valeurs l'affichage de la main du joueur
     */
    private Map<Player, PlayerHandView> playerHandViews;

    /**
     * Classe de gestion d'une partie
     */
    private Game game;

    /**
     * Référence sur la fenêtre principale
     */
    private GameView main;

    /**
     * Contrôleur de vue qui change suivant l'état du jeu
     */
    private Action actionController;

    /**
     * Listener qui déclenche le lancement de l'action d'un état lors du changement d'état de gamme
     */
    private ChangeListener<State> gameListener;

    /**
     * Listener qui déclenche l'affichage de la fin de partie
     */
    private InvalidationListener endGameListener;

    /**
     * Plateau de jeu
     */
    @FXML
    private AnchorPane board;

    /**
     * Conteneur du plateau de jeu
     */
    @FXML
    private BorderPane container;

    /**
     * Initialise le contrôleur de classe. Automatiquement appelé après que le fichier fxml soit chargé
     */
    @FXML
    private void initialize() {
        gameListener = (observable, oldValue, newValue) -> {
            if (!game.endOfParty.getValue()) {
                game.running();
            }
        };

        endGameListener = observable -> {
            // Si la partie est terminée
            if (game.endOfParty.getValue()) {
                // Si le joueur humain a toujours de l'argent dans son portemonnaie
                if (game.getHumanPlayer().getWallet() > 0) {
                    showAction(ACTION_END, ActionEndController.class);
                }
                else {
                    // Sinon il a perdu, on lui affiche un message
                    showEndOfGame();
                }
            }
            else {
                // Sinon c'est que la partie vient d'être (ré)initialiser
                board.getChildren().clear();
                initializeGame();
            }
        };

    }

    /**
     * Appeler après l'initialise pour paramétrer les paramètres de jeu
     *
     * @param main            Référence vers le conteneur de jeu
     * @param numberOfPlayers Nombre de joueurs
     * @param numberOfDecks   Nombre de jeu de cartes
     */
    public void init(GameView main, int numberOfPlayers, int numberOfDecks) {
        game = new Game(numberOfPlayers, numberOfDecks);
        this.main = main;

        initializeGame();
    }

    /**
     * Initialise l'état de jeu : demande de la mise
     */
    private void initializeGame() {
        showAction(ACTION_INIT_BET, ActionInitBetController.class);
        ((ActionInitBetController) actionController)
                .setCurrentPlayer(game.getHumanPlayer()); // Ajoute la référence vers le joueur humain
        ((ActionInitBetController) actionController).betProperty().addListener((observable, oldValue, newValue) -> {
            initGame(newValue.intValue());
        }); // Listener pour détecter lorsque le joueur a valider sa mise
    }

    /**
     * Initialise le jeu : placement des cartes, etc.
     *
     * @param humanBet Mise du joueur humain
     */
    private synchronized void initGame(int humanBet) {
        playerHandViews = new HashMap<Player, PlayerHandView>();

        int size = 0;

        // Coordonnées dans la fenêtre pour l'affichage des mains (pas très intéressantes)
        int[][] coords = new int[6][2];
        coords[0][0] = 130;
        coords[0][1] = 150;

        coords[1][0] = 307;
        coords[1][1] = 340;

        coords[2][0] = 567;
        coords[2][1] = 420;

        coords[3][0] = 827;
        coords[3][1] = 340;

        coords[4][0] = 1003;
        coords[4][1] = 150;

        coords[5][0] = 567;
        coords[5][1] = 180;

        for (Player player : game.getPlayerList()) {
            actorPositioning(player, coords[size][0], coords[size][1]);
            size++;
        }

        // Vue du Dealer
        GridPane gridPane = new GridPane();
        new DealerHandView(game.getDealer(), gridPane, game);
        gridPane.setLayoutX(coords[coords.length - 1][0]);
        gridPane.setLayoutY(coords[coords.length - 1][1]);
        board.getChildren().add(gridPane);

        // Affichage du deck
        AnchorPane anchorPane = new AnchorPane();
        new SimpleDeckView(anchorPane, false, game.getDealer().getDeck());
        anchorPane.setLayoutX(450);
        anchorPane.setLayoutY(200);
        board.getChildren().add(anchorPane);

        showAction(ACTION_PLAY, ActionPlayController.class);
        ((ActionPlayController) actionController).iniHumanBet(humanBet);

        game.initializeBotBet();
        game.running();
        ((ActionPlayController) actionController).changeStatesButtons();

        // Exécute l'état lorsqu'il change
        game.getState().removeListener(gameListener);
        game.getState().addListener(gameListener);

        game.endOfParty.removeListener(endGameListener);
        game.endOfParty.addListener(endGameListener);
    }

    /**
     * Affiche les actions que peut faire l'utilisateur suivant un état de jeu
     */
    private void showAction(String FXMLRessource, Class<? extends Action> t) {
        try {
            // Charge le menu principal
            FXMLLoader loader = new FXMLLoader(getClass().getResource(FXMLRessource));
            GridPane board = loader.load();

            // Affecte l'ensemble d'action au bas du BorderPane
            this.container.setBottom(board);

            // Donne un contrôleur pour le menu de jeu
            this.actionController = t.cast(loader.getController());
            this.actionController.init(game, main);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Affiche la boîte de dialogue de fin de jeu lorsque le joueur n'a plus d'argent pour une nouvelle partie
     */
    private void showEndOfGame() {
        try {
            // Charge le fichier fxml d'initialisation de partie et créer une scène pour la boîte de dialogue
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(
                    BoardController.class.getResource("/fr/unicaen/info/l3/blackjack/view/EndGameDialog.fxml"));
            Pane page = loader.load();

            // Créer la scène de dialogue
            Stage dialogStage = new Stage();
            dialogStage.setTitle(Ressources.getString(BlackjackKeys.WINTITLE_END_OF_GAME));

            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(main.primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Affecte un contrôleur
            EndGameDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);

            // Affiche la boîte de dialogue et attend qu'une action de l'utilisateur la ferme
            dialogStage.showAndWait();
            main.primaryStage.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Place les différents acteurs sur le plateau de jeu
     *
     * @param player Joueur à placer
     * @param x      Coordonée x
     * @param y      Coordonée y
     */
    private void actorPositioning(Player player, int x, int y) {
        GridPane gridPane = new GridPane();
        playerHandViews.put(player, new PlayerHandView(player, gridPane));
        gridPane.setLayoutX(x);
        gridPane.setLayoutY(y);
        board.getChildren().add(gridPane);
    }

    /**
     * Lance le jeu
     */
    public void launchGame() {
        game.running();
    }
}
