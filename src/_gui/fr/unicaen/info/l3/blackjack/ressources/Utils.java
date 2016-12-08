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

package fr.unicaen.info.l3.blackjack.ressources;

import java.util.Locale;
import java.util.MissingResourceException;

/**
 * Classe contenant différentes méthodes pouvant être utilisé dans plusieurs classes du programme
 */
public final class Utils {

    /**
     * Référence vers l'image d'icon du programme
     */
    public static final String APPLICATION_ICON_PATH = "/fr/unicaen/info/l3/blackjack/ressources/images/favicon.png";

    /**
     * Constructeur privée pour empêcher l'instanciation
     */
    private Utils() {
    }

    /**
     * Vérifie si la locale passer en paramètre est valide
     *
     * @param locale Locale à vérifier
     * @return Retourne true si la locale est correcte, false sinon
     */
    public static boolean localeIsValid(Locale locale) {
        try {
            return locale.getISO3Language() != null && locale.getISO3Country() != null;
        } catch (MissingResourceException e) {
            return false;
        }
    }

} 
