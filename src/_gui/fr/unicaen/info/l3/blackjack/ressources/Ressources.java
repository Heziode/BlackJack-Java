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

import fr.unicaen.info.l3.blackjack.ressources.config.Configurator;
import fr.unicaen.info.l3.blackjack.ressources.config.CoreConfigurator;
import fr.unicaen.info.l3.blackjack.ressources.language.ResourceBundleControl;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Contient les différents textes à afficher suivant la langue courante définis dans le fichier de conf de l'application
 */
public final class Ressources {

    /**
     * Fichier de configuration de l'application (pour la langue)
     */
    private static Configurator conf = Configurator.getInstance();

    /**
     * Ensemble des textes affichaient dans le programme
     */
    private static ResourceBundle bundle = null;

    /**
     * Constructeur privée pour empêcher l'instanciation
     */
    private Ressources() {
    }

    /**
     * Récupère l'ensemble ressources (texte) correspondant à la langue définit dans le fichier de configuration
     *
     * @see Configurator
     */
    private static synchronized ResourceBundle getRessource() {
        if (bundle == null) {
            try {
                bundle = ResourceBundle.getBundle("fr.unicaen.info.l3.blackjack.ressources.language.lang.lang",
                        new Locale(conf.getPropertie(CoreConfigurator.LANG)), new ResourceBundleControl());
            } catch (Exception err) {
                System.err.println(
                        conf.getPropertie(CoreConfigurator.LANG) + " : language is not supported. Changed by French.");
                conf.setPropertie(CoreConfigurator.LANG, "fre");
                bundle = ResourceBundle.getBundle("fr.unicaen.info.l3.blackjack.ressources.language.lang.lang",
                        new Locale(conf.getPropertie(CoreConfigurator.LANG)), new ResourceBundleControl());
            }

        }
        return bundle;
    }

    /**
     * Récupère une chaîne de caractères dans la ressource
     *
     * @param key Clé associée à la valeur que l'on souhaite obtenir
     * @return Retourne la valeur associé à la clé
     */
    public static String getString(String key) {
        return Ressources.getRessource().getString(key);
    }
} 
