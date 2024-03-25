/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ca.ulaval.glo2004.cabanon.domaine;

import ca.ulaval.glo2004.cabanon.domaine.Cabanon;
import ca.ulaval.glo2004.cabanon.domaine.plancher.Plancher;

import java.util.UUID;

/**
 *
 * @author raphael
 */
public class CabanonDTO{
 
    public Plancher Plancher;
    public double Largeur;
    public double Profondeur;
    public double HauteurMurs;
    public UUID Uuid;
    public double Cout;
    
    public CabanonDTO(Cabanon cabanon){
        Plancher = cabanon.getPlancher();
        Largeur = cabanon.getLargeur();
        Profondeur = cabanon.getProfondeur();
        HauteurMurs = cabanon.getHauteurMurs();
        Uuid = cabanon.getUuid();
        Cout = cabanon.getCout();
    }
}