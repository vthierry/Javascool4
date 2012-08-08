/*********************************************************************************
* Philippe.Vienne@sophia.inria.fr, Copyright (C) 2011.  All rights reserved.    *
* Guillaume.Matheron@sophia.inria.fr, Copyright (C) 2011.  All rights reserved. *
* Thierry.Vieville@sophia.inria.fr, Copyright (C) 2009.  All rights reserved.   *
*********************************************************************************/

package org.javascool.macros;

import org.javascool.gui.Desktop;
import org.javascool.widgets.Console;

/** Cette factory contient des fonctions générales rendues visibles à l'utilisateur de proglets.
 * <p>Elle permet de définir des fonctions statiques qui seront utilisées pour faire des programmes élèves.</p>
 * <p>Elle permet aussi avoir quelques fonctions de base lors de la création de nouvelles proglets.</p>
 *
 * @see <a href="Stdout.java.html">code source</a>
 * @serial exclude
 */
public class Stdout {
  // @factory
  private Stdout() {}

  /** Affiche dans la console une chaîne de caractères ou la représentation textuelle d'un objet sur la console.
   * - Cette fonction ne change pas le focus de javascool.
   * @param string La chaine ou l'objet à afficher sous sa représentation textuelle.
   * @see #println(String)
   */
  public static void echo(String string) {
    Console.getInstance().print(string + "\n");
  }
  /**
   * @see #echo(String)
   */
  public static void echo(int string) {
    echo("" + string);
  }
  /**
   * @see #echo(String)
   */
  public static void echo(char string) {
    echo("" + string);
  }
  /**
   * @see #echo(String)
   */
  public static void echo(double string) {
    echo("" + string);
  }
  /**
   * @see #echo(String)
   */
  public static void echo(boolean string) {
    echo("" + string);
  }
  /**
   * @see #echo(String)
   */
  public static void echo(Object string) {
    echo("" + string);
  }
  /**
   * @see #echo(String)
   */
  public static void echo() {
    echo("");
  }
  /** Affiche dans la console une chaîne de caractères ou la représentation textuelle d'un objet sur la console.
   * - Cette fonction ramène le focus de javascool sur la console.
   * @param string La chaine ou l'objet à afficher sous sa représentation textuelle.
   * @see #echo(String)
   */
  public static void println(String string) {
    Desktop.getInstance().focusOnConsolePanel();
    System.err.println("printing : \"" + string + "\"");
    Console.getInstance().print(string + "\n");
  }
  /**
   * @see #echo(String)
   */
  public static void println(int i) {
    println("" + i);
  }
  /**
   * @see #echo(String)
   */
  public static void println(char i) {
    println("" + i);
  }
  /**
   * @see #echo(String)
   */
  public static void println(double d) {
    println("" + d);
  }
  /**
   * @see #echo(String)
   */
  public static void println(boolean b) {
    println("" + b);
  }
  /**
   * @see #echo(String)
   */
  public static void println(Object o) {
    println("" + o);
  }
  /**
   * @see #echo(String)
   */
  public static void println() {
    println("");
  }
  /** Affiche dans la console une chaîne de caractères ou la représentation textuelle d'un objet sur la console sans retour à la ligne.
   * @param string La chaine ou l'objet à afficher sous sa représentation textuelle.
   */
  public static void print(String string) {
    System.out.print(string);
    System.out.flush();
  }
  /**
   * @see #print(String)
   */
  public static void print(int i) {
    print("" + i);
  }
  /**
   * @see #print(String)
   */
  public static void print(char i) {
    print("" + i);
  }
  /**
   * @see #print(String)
   */
  public static void print(double d) {
    print("" + d);
  }
  /**
   * @see #print(String)
   */
  public static void print(boolean b) {
    print("" + b);
  }
  /**
   * @see #print(String)
   */
  public static void print(Object o) {
    print("" + o);
  }
  /** Efface tout ce qui est écrit dans la console. */
  public static void clear() {
    Console.getInstance().clear();
  }
  /** Sauve ce qui est présentement écrit dans la console dans un fichier.
   * @param location La localisation (chemin du fichier ou localisation internet) où sauver le texte.
   */
  public static void saveConsoleOutput(String location) {
    Console.getInstance().saveConsoleOutput(location);
  }
}
