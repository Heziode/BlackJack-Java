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

package fr.unicaen.info.l3.blackjack.ressources.language;

/**
 * Classe contenant les différentes clés relatif à l'affichage permettant ainsi une externalisation du texte
 * affichier à l'utilisateur et éventuellement un support multi-langue
 */
public final class BlackjackKeys {

    // Titre de fenêtre
    public static final String WINTITLE_MAIN_WINDOW_NAME = "mainWindowName_winTitle";
    public static final String WINTITLE_INIT_GAME_WINDOW_NAME = "initGameWindowName_winTitle";
    public static final String WINERRORTITLE_INVALID_FIELD_WINDOW_NAME = "invalidFieldWindowName_winErrTitle";
    public static final String WINTITLE_END_OF_GAME = "endOfGame_winTitle";
    public static final String WINTITLE_INFO = "info_winTitle";

    // Header (modal / dialoge)
    public static final String HEADERTITLE_INVALID_FIELD = "invalidField_headerTitle";

    // Erreur dans les boites de dialoge
    public static final String ERROR_INVALID_NUMBER = "invalidNumber_err";
    public static final String ERROR_NOT_NUMBER = "notNumber_err";

    // Bouton
    public static final String BUTTON_PLAY = "play_btn";
    public static final String BUTTON_INFO = "info_btn";
    public static final String BUTTON_QUIT = "quit_btn";
    public static final String BUTTON_CANCEL = "cancel_btn";
    public static final String BUTTON_NEXT = "next_btn";
    public static final String BUTTON_PASS = "pass_btn";
    public static final String BUTTON_ASK = "ask_btn";
    public static final String BUTTON_DOUBLE = "double_btn";
    public static final String BUTTON_INSURANCE = "insurance_btn";
    public static final String BUTTON_DIVIDE = "divide_btn";
    public static final String BUTTON_OK = "ok_btn";

    // Label
    public static final String LABEL_NUMBER_OF_PLAYERS = "numberOfPlayers_lbl";
    public static final String LABEL_NUMBER_OF_DECKS = "numberOfDecks_lbl";
    public static final String LABEL_SCORE = "score_lbl";
    public static final String LABEL_BET = "bet_lbl";
    public static final String LABEL_BET_WITH_VALUE = "betWithValue_lbl";
    public static final String LABEL_INIT_WALLET = "wallet_init_lbl";
    public static final String LABEL_WIN = "win_lbl";
    public static final String LABEL_TIE = "tie_lbl";
    public static final String LABEL_LOSE = "lose_lbl";
    public static final String LABEL_LOSE_MESSAGE = "lose_msg_lbl";
    public static final String LABEL_INFO_ABOUT_APP = "infoAboutApp_lbl";
    public static final String LABEL_INFO_VERSION = "infoVersion_lbl";
    public static final String LABEL_INFO_AUTHORS = "infoAuthors_lbl";

    /**
     * Constructeur privée pour empêcher l'instanciation
     */
    private BlackjackKeys() {
    }
}
