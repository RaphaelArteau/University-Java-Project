/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ca.ulaval.glo2004.preferences;

/**
 *
 * @author raphael
 */

public class Preferences {

    private static Preferences preferences = null;
    private static double longueurMax24 = 192;
    private static double longueurMax26 = 192;
    private static double longueurMax28 = 192;
    private static double longueurMax210 = 192;
    private static double prixPied24 = 4;
    private static double prixPied26 = 4.25;
    private static double prixPied28 = 4.5;
    private static double prixPied210 = 4.75;
    private static double gridSize = 12;
    
    

    public static enum uses {
        Inches,
        Feet
    };
    private static uses unitUsed;


    private Preferences(){
        unitUsed = uses.Inches;
    }

    public static synchronized Preferences global(){
       if (preferences == null)
           preferences = new Preferences();
       return preferences;
    }

    public uses getUnitUsed() {
         return unitUsed;
     }

    public void setUnitUsed(uses nouvelUnitUsed) {
         unitUsed = nouvelUnitUsed;
     }
     
    static public double getLongueurMax24() {
        return longueurMax24;
    }

    static public void setLongueurMax24(double longueurMax) {
        longueurMax24 = longueurMax;
    }
    
    static public double getLongueurMax26() {
        return longueurMax26;
    }

    static public void setLongueurMax26(double longueurMax) {
        longueurMax26 = longueurMax;
    }
 
    static public double getLongueurMax28() {
        return longueurMax28;
    }

    static public void setLongueurMax28(double longueurMax) {
        longueurMax28 = longueurMax;
    }
    
    static public double getLongueurMax210() {
        return longueurMax210;
    }

    static public void setLongueurMax210(double longueurMax) {
        longueurMax210 = longueurMax;
    }
    
    static public double getPrixPied24() {
        return prixPied24;
    }
    
    static public void setPrixPied24(double nouveauPrixPied24) {
        prixPied24 = nouveauPrixPied24;
    }
    
    static public double getPrixPied26() {
        return prixPied26;
    }
    
    static public void setPrixPied26(double nouveauPrixPied24) {
        prixPied26 = nouveauPrixPied24;
    }
     
    static public double getPrixPied28() {
        return prixPied28;
    }
    
    static public void setPrixPied28(double nouveauPrixPied28) {
        prixPied28 = nouveauPrixPied28;
    }
    
    static public double getPrixPied210() {
        return prixPied210;
    }
    
    static public void setPrixPied210(double nouveauPrixPied210) {
        prixPied210 = nouveauPrixPied210;
    }

    public static double getGridSize() {
        return gridSize;
    }

    public static void setGridSize(double gridSize) {
        Preferences.gridSize = gridSize;
    }
 
 
 
}