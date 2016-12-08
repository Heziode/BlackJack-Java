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

package fr.unicaen.info.l3.blackjack.ressources.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Properties;
import java.util.jar.JarInputStream;
import java.util.jar.Manifest;

/**
 * Classe permettant d'interagir avec le fichier de configuration
 */
public final class Configurator {

    /**
     * Instance de cet objet en singleton (pour éviter de multiples lectures du même fichier)
     */
    private static Configurator instance = null;

    /**
     * Emplacement du fichier de configuration
     */
    private String fileLocation;

    /**
     * Ensemble des propriétés utilisaient dans le programme
     */
    private Properties bundle;

    /**
     * Constructeur privé appelé lors du premier appel de cette classe
     *
     * @param fileLocation Emplacement du fichier de configuration
     */
    private Configurator(String fileLocation) {
        this.fileLocation = fileLocation;

        bundle = new Properties();
        FileInputStream file = null;
        try {
            file = new FileInputStream(fileLocation);
            bundle.load(file);
            file.close();
        } catch (IOException err) {
            generateConfig();

            try {
                file = new FileInputStream(fileLocation);
                bundle.load(file);
                file.close();
            } catch (IOException err2) {
                err2.printStackTrace();
            }
        }
    }

    /**
     * Singleton : retourne une instance unique de la classe.
     * Le synchronized permet d'éviter le problème de multiples définitions dans
     * un environnement multi-thread
     *
     * @return Retourne une instance de {@link Configurator}
     */
    public static synchronized Configurator getInstance() {
        if (instance == null) {
            File f = new File(System.getProperty("java.class.path"));
            File dir = f.getAbsoluteFile().getParentFile();
            String path = dir.toString();

            instance = new Configurator(path + "/config.properties");

            JarInputStream jarStream = null;
            try {
                jarStream = new JarInputStream(new FileInputStream(
                        Configurator.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()));
                Manifest mf = jarStream.getManifest();

                System.setProperty("BJVersion", mf.getMainAttributes().getValue("Specification-Version"));
            } catch (IOException e) {
                e.printStackTrace();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }


        }
        return instance;
    }

    /**
     * Permets de récupérer une propriété
     *
     * @param key Clé dont on souhaite connaitre sa valeur associée
     * @return Retourne la valeur associé à la clé
     */
    public String getPropertie(String key) {
        return bundle.getProperty(key);
    }

    /**
     * Permets de (re)définir un couple clé-valeur
     *
     * @param key   Clé à (re)définir
     * @param value Valeur à (re)définir
     */
    public void setPropertie(String key, String value) {
        bundle.setProperty(key, value);
        save("Blackjack Configuration");
    }

    /**
     * Sauvegarde les modifications dans le fichier de configuration
     *
     * @param comment Commentaire introduit dans de fichier
     */
    public void save(String comment) {
        try {
            FileOutputStream file = new FileOutputStream(fileLocation);
            bundle.store(file, comment);
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Génère une configuration par défaut
     */
    private void generateConfig() {
        bundle.setProperty(CoreConfigurator.LANG, CoreConfiguratorDefaultValue.LANG_VALUE);
        save("Blackjack Configuration");
    }
} 
