# Blackjack Java

Ce programme est un jeu de blackjack développé dans le cadre d'un projet universitaire.

Les règles utilisées dans ce projet concernant le blackjack sont celles disponnible sur [wikipedia](https://fr.wikipedia.org/wiki/Blackjack_(jeu)).

>   Le programme requiert Java 8 et utilise JavaFX
>
>   Ce programme nécessite également la library [Card Game Library](https://github.com/Heziode-dev/Card-Game-Library)



## Design Pattern

Pour la réalisation de ce projet, l'utilisation du patron **MVC (Model Vue Contrôleur)** s'est révélé indispensable.

Nous avons remarqué que le jeu suivait toujours le même schéma qui est :

-   une phase de distribution
-   une phase de décision pour chaque joueurs ainsi que le dealer qui lui joue en dernier
-   enfin, pour chaque joueurs, on détermine s'il a gagné ou perdu ses gains suivant ses cartes et celles du dealer

Pour réaliser cela nous avons choisi d'utiliser le patron **état (*state*)**.



Un joueur se trouve face à plusieurs choix pour jouer son tour :

-   passer son tour
-   demander une carte
-   doubler sa mise
-   diviser sa main
-   demander une assurance

Le joueur doit pouvoir passer d'une strategie de jeu à une autre si elle respecte les règles du jeu. C'est pourquoi nous avons mis en place le patron **strategie (*strategy*)**.



Dans le blackjack, il faut créer des joueurs en fonction de certaines règles :

-   création d'un joueur suivant son type (dealer ou joueur humain / robot)
-   changement des règles de création (argent de départ)

Ainsi le patron **usine (*factory*)** nous permet de créer dynamiquement différents joueurs en fonction des règles énoncées ci-dessus.

Le programme utilise un système de configurations (vue ci-dessous). La classe Configurator permet d'interagir avec un fichier de configuration. Pour optimiser la lecture du disque, nous avons utilisé un patron **singleton (*singleton*)** empêchant ainsi plusieurs instanciations de la classe.

## Spécificités



### Découpage en module

Dans une optique de modularité, nous avons découpé le projet en module. Ainsi blackjack est composé de deux modules :

-   coreAutomate : qui contient le cœur de l'application
-   gui : qui contient l'interface graphique

Le module coreAutomate est totalement indépendant de gui. On pourrais ainsi très bien créer une nouvelle interface graphique sans utiliser le module gui.

### Langues et configuration

Pour faciliter la maintenance ainsi que de permettre l'internationnalisation du code, nous avons choisi d'utiliser un système de classes contenant des clefs qui sont définies dans des fichiers de configurations.

La classe **BlackjackKeys** définit un ensemble de constante ayant pour valeur des clefs de configurations.

Ainsi lors de l'initialisation du programme, la langue est chargé depuis un fichier de configurations correspondant à une langue définie.



De plus pour permettre la configuration du programme, un fichier de configuration par défaut est généré lors du lancement du programme (nommé `config.properties`) s'il n'existe pas à l'endroit où ce trouve le jar exécutable de blackjack et qui contient la langue à utiliser dans le programme. Le format de la langue doit correspondre à la norme [ISO_639-2](https://fr.wikipedia.org/wiki/Liste_des_codes_ISO_639-2).



Si la langue n'est pas connue, elle est remplacée par la langue par défaut définit dans le programme qui est le français. Le système permet également d'introduire de nouvelle langues sans avoir à éditer le code. Il suffit pour cela d'introduire le fichier sous la forme `lang_<code ISO de la lange>.properties` dans le répertoire lang ce trouvant dans `ressources/language` du module gui. Le programme est disponible en français (fre) et en anglais (eng).

### Paramètre JVM

Il est également possible de définir la langue choisie à l'aide d'un paramètre JVM. Le format est le suivant :

`-Dlang=<code ISO de la langue>`



## Licence

(The MIT License)

Copyright (c) 2016 - Dauprat Quentin, Duval Florian, Girault Jeoffrey & Michel Fabien

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions: 

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.