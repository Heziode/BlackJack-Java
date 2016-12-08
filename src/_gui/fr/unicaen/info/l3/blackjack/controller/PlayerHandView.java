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

import cardgamebase.controller.SimpleHandView;
import cardgamebase.model.card.SimpleDeck;
import fr.unicaen.info.l3.blackjack.model.BJHand;
import fr.unicaen.info.l3.blackjack.model.GameRulesBlackJack;
import fr.unicaen.info.l3.blackjack.model.actor.Player;
import fr.unicaen.info.l3.blackjack.ressources.Ressources;
import fr.unicaen.info.l3.blackjack.ressources.language.BlackjackKeys;
import javafx.collections.ListChangeListener;
import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.text.MessageFormat;
import java.util.ArrayList;

/**
 * Classe gérant l'affichage des mains d'un joueur
 */
public final class PlayerHandView {

    /**
     * Joueur à observer
     */
    private Player player;

    /**
     * Contiendra l'ensemble des mains d'un joueur avec pour chacune d'elles le score, la mise et les cartes
     */
    private GridPane gridPane;

    /**
     * Contient l'ensemble des labels de status (gagné, etc.),donc au plus 2 pour le blackjack car 1 pour chaque main
     */
    private ArrayList<Label> statusList;

    /**
     * Constructeur
     *
     * @param player   Joueur dont on souhaite afficher les mains
     * @param gridPane Référence vers un gridpane initialiser pour l'affichage
     */
    public PlayerHandView(Player player, GridPane gridPane) {
        this.player = player;
        this.gridPane = gridPane;
        statusList = new ArrayList<Label>();

        player.getHandsList().addListener(new ListChangeListener<BJHand>() {
            @Override
            public void onChanged(Change<? extends BJHand> c) {
                initializeHandView();
            }
        });

        player.getCurrentHandProperty().addListener((observable, oldValue, newValue) -> {
            initializeHandView();
        });

        for (int i = 0; i < this.player.getHandsList().size(); i++) {
            this.player.getHandsList().get(i).getResult().addListener(observable -> {
                displayResults();
            });
        }

        initializeHandView();
    }

    /**
     * Initialise la vue d'un joueur (l'ensemble de ses mains)
     */
    private void initializeHandView() {
        this.gridPane.getChildren().removeAll(this.gridPane.getChildren());
        this.gridPane.setVgap(4); // lignes
        this.gridPane.setHgap(10); // colonnes

        for (int i = 0; i < this.player.getHandsList().size(); i++) {

            statusList.add(i, new Label());
            statusList.get(i).getStyleClass().removeAll();
            statusList.get(i).getStyleClass().add("text-status-init");
            gridPane.add(statusList.get(i), i, 0);

            AnchorPane anchorPane = new AnchorPane();
            if (player.isPlayable() && player.getCurrentHand().equals(player.getHandsList().get(i))) {
                anchorPane.getStyleClass().add("current-handplayer-frame");
            }

            // Création de la vue des cartes du joueur
            new SimpleHandView<Player, BJHand, GameRulesBlackJack, SimpleDeck>(player, i, anchorPane);
            gridPane.add(anchorPane, i, 1);

            Label score = new Label(MessageFormat.format(Ressources.getString(BlackjackKeys.LABEL_SCORE),
                    new Object[]{this.player.getHandsList().get(i).getHandValue()}));
            score.getStyleClass().removeAll();
            score.getStyleClass().add("text-info");
            gridPane.add(score, i, 2);
            GridPane.setHalignment(score, HPos.CENTER);

            Label bet = new Label(MessageFormat.format(Ressources.getString(BlackjackKeys.LABEL_BET_WITH_VALUE),
                    new Object[]{this.player.getHandsList().get(i).getBet().getValue()}));
            bet.getStyleClass().add("text-info");
            gridPane.add(bet, i, 3);
            GridPane.setHalignment(bet, HPos.CENTER);


            /*
             * Écouteurs
             */

            this.player.getHandsList().get(i).getScore().addListener((observable, oldValue, newValue) -> {
                // Le score à changer
                score.setText(
                        MessageFormat.format(Ressources.getString(BlackjackKeys.LABEL_SCORE), new Object[]{newValue}));
            });

            this.player.getHandsList().get(i).getBet().addListener((observable, oldValue, newValue) -> {
                // La mise à changer
                bet.setText(MessageFormat
                        .format(Ressources.getString(BlackjackKeys.LABEL_BET_WITH_VALUE), new Object[]{newValue}));
            });
        }
    }

    /**
     * Affiche le résultat de chaque main
     */
    private void displayResults() {
        for (int i = 0; i < this.player.getHandsList().size(); i++) {
            if (this.player.getHandsList().get(i).getResult().getValue() != null) {
                displayResult(i);
            }
        }
    }

    /**
     * Affiche le résultat de la main dont l'indice est passé en paramètre
     *
     * @param index Indice de la main
     */
    private void displayResult(int index) {
        statusList.get(index).getStyleClass().remove("text-status-init");
        statusList.get(index).getStyleClass().add("text-status");

        switch (player.getHandsList().get(index).getResult().getValue()) {
            case WIN:
                statusList.get(index).setText(Ressources.getString(BlackjackKeys.LABEL_WIN));
                statusList.get(index).getStyleClass().add("text-status-win");
                break;
            case TIE:
                statusList.get(index).setText(Ressources.getString(BlackjackKeys.LABEL_TIE));
                statusList.get(index).getStyleClass().add("text-status-tie");
                break;
            case LOSE:
                statusList.get(index).setText(Ressources.getString(BlackjackKeys.LABEL_LOSE));
                statusList.get(index).getStyleClass().add("text-status-lose");
                break;
            default:
                statusList.get(index).setText("");
                statusList.get(index).getStyleClass().add("text-status-default");
        }
        GridPane.setHalignment(statusList.get(index), HPos.CENTER);
    }
}
