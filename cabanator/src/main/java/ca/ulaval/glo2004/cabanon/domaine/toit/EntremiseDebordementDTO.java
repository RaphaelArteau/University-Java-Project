/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ca.ulaval.glo2004.cabanon.domaine.toit;

/**
 *
 * @author Utilisateur
 */
public class EntremiseDebordementDTO {
     public FermesSupplementaires parent;
    public double longueur;
    public double coordX;
    public double coordY;
    
    public EntremiseDebordementDTO (EntremiseDebordement entremiseDebord){
        parent = entremiseDebord.getParent();
        longueur = entremiseDebord.getLongueur();
        coordX = entremiseDebord.getCoordX();
        coordY = entremiseDebord.getCoordY();
    }
}
