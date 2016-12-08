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
import fr.unicaen.info.l3.blackjack.model.actor.Dealer;
import fr.unicaen.info.l3.blackjack.ressources.Ressources;
import fr.unicaen.info.l3.blackjack.ressources.language.BlackjackKeys;
import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.text.MessageFormat;

/**
 * Représentation de la main d'un dealer
 */
public final class DealerHandView {

    /**
     * Joueur à observer
     */
    private Dealer dealer;

    /**
     * Contiendra l'ensemble des mains d'un joueur avec pour chacune d'elles le score, la mise et les cartes
     */
    private GridPane gridPane;

    /**
     * Constructeur logique
     *
     * @param player   Joueur dont on souhaite afficher les mains
     * @param gridPane Référence vers un gridpane initialiser pour l'affichage
     * @param game     Référence vers le jeu
     */
    public DealerHandView(Dealer player, GridPane gridPane, Game game) {
        this.dealer = player;
        this.gridPane = gridPane;

        game.endOfParty.addListener(observable -> {
            dealer.refreshAllCards();
            initializeHandView();
        });

        initializeHandView();
    }

    /**
     * Initialise la vue d'un joueur (l'ensemble de ses mains)
     */
    private void initializeHandView() {
        gridPane.getChildren().removeAll(gridPane.getChildren());
        this.gridPane.setVgap(2); // lignes
        this.gridPane.setHgap(this.dealer.getHandsList().size()); // colonnes

        for (int i = 0; i < this.gridPane.getHgap(); i++) {

            AnchorPane anchorPane = new AnchorPane();

            // Création de la vue des cartes du dealer
            new SimpleHandView<Dealer, BJHand, GameRulesBlackJack, SimpleDeck>(dealer, i, anchorPane);
            gridPane.add(anchorPane, i, 0);

            Label score = new Label(MessageFormat.format(Ressources.getString(BlackjackKeys.LABEL_SCORE),
                    new Object[]{this.dealer.getHandsList().get(i).getHandValue()}));
            score.getStyleClass().removeAll();
            score.getStyleClass().add("text-info");
            gridPane.add(score, i, 1);
            GridPane.setHalignment(score, HPos.CENTER);
            score.setText(MessageFormat.format(Ressources.getString(BlackjackKeys.LABEL_SCORE),
                    new Object[]{this.dealer.getHandsList().get(i).getScore().getValue()}));

            // Écouteurs
            this.dealer.getHandsList().get(i).getScore().addListener((observable, oldValue, newValue) -> {
                // Le score à changer
                score.setText("");
                initializeHandView();

            });

        }
    }
}
