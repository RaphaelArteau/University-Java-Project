/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ca.ulaval.glo2004.cabanon.domaine.plancher;

import ca.ulaval.glo2004.cabanon.domaine.Cabanon;
import java.util.ArrayList;
import java.util.UUID;

/**
 *
 * @author gabriel
 */
public class PlancherDTO {
    public double Profondeur;
    public double Largeur;
    public double DernierBlocLargeur;
    public double DernierBlocProfondeur;
    public double EspacementSolives;
    public int[] DimensionGridBlocPlancher;
    
    public ArrayList<Double> CoordYSolives;
    public ArrayList<LigneEntremises> LignesEntremises;
    
    public Cabanon Parent;
    public UUID Uuid;
    
    public PlancherDTO(Plancher plancher) {
        Profondeur = plancher.getProfondeur();
        Largeur = plancher.getLargeur();
        DernierBlocLargeur = plancher.getDernierBlocLargeur();
        DernierBlocProfondeur = plancher.getDernierBlocProfondeur();
        DimensionGridBlocPlancher = plancher.getDimensionGridBlocPlancher();
        CoordYSolives = new ArrayList<> (plancher.getCoordYSolives());
        EspacementSolives = plancher.getEspacementSolives();
        LignesEntremises = new ArrayList<>(plancher.getLignesEntremises());
        Parent = plancher.getParent();
        Uuid = plancher.getUuid();
    }
    
}
