/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ca.ulaval.glo2004.cabanon.domaine.toit;

import java.util.ArrayList;

/**
 *
 * @author Utilisateur
 */
public class FermesSupplementairesDTO {
    public ArrayList<EntremiseDebordement> listeEntremisesChevron1;
    public ArrayList<EntremiseDebordement> listeEntremisesChevron2;
    public ArrayList<Double> CoordEntremisesDebordChevron1;
    public ArrayList<Double> CoordEntremisesDebordChevron2;
    public double espacementEntremise;
    
    public FermesSupplementairesDTO(FermesSupplementaires fermeSupp){
        listeEntremisesChevron1 = fermeSupp.getEntremiseChevron1();
        listeEntremisesChevron2 = fermeSupp.getEntremiseChevron2();
        CoordEntremisesDebordChevron1 = fermeSupp.getCoordEntremisesDebordChevron1();
        CoordEntremisesDebordChevron2 = fermeSupp.getCoordEntremisesDebordChevron2();
    }
}
