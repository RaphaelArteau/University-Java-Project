/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ca.ulaval.glo2004.cabanon.domaine.toit;

import ca.ulaval.glo2004.preferences.Preferences;

/**
 *
 * @author Utilisateur
 */
public class EntremiseDebordement {
    private FermesSupplementaires parent;
    private double longueur = Preferences.getLongueurMax24();
    private double coordX;
    private double coordY;
    
    public EntremiseDebordement(FermesSupplementaires parent, double taille, double X, double Y){
        this.longueur = taille;
        this.parent = parent;
        this.coordX = X;
        this.coordY = Y;
    }
    
    public double getLongueur(){
        return this.longueur;
    }
    
    public double getCoordX(){
        return this.coordX;
    }
    
    public double getCoordY(){
        return this.coordY;
    }
    
    public FermesSupplementaires getParent(){
        return this.parent;
    }
    public void setLongueur(double nouvelleLongueur){
        this.longueur = this.parent.getTailleEntremise();
    }
}
