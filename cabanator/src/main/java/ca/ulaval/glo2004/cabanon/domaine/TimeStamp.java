/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ca.ulaval.glo2004.cabanon.domaine;

import ca.ulaval.glo2004.cabanon.domaine.murs.AccessoireDTO;
import ca.ulaval.glo2004.cabanon.domaine.murs.EntremiseMurDTO;
import ca.ulaval.glo2004.cabanon.domaine.plancher.LigneEntremiseDTO;
import java.util.ArrayList;
import java.util.UUID;

/**
 *
 * @author Maxence
 */
public class TimeStamp {
    public UUID id;
    public double largeur;
    public double profondeur;
    public double hauteurMurs;
    public int orientationToit;
    public double angleToit;
    public double EspacementSolivesPlancher;
    public double espacementFermes;
    public double porteAFaux;
    public double Debordement;
    public double EspacementEntDebord;
    public double EspacementMurNord;
    public double EspacementMurSud;
    public double EspacementMurEst;
    public double EspacementMurOuest;
    public ArrayList<LigneEntremiseDTO> listeLignesEntremises;
    public ArrayList<AccessoireDTO> listeAccNord;
    public ArrayList<AccessoireDTO> listeAccSud;
    public ArrayList<AccessoireDTO> listeAccEst;
    public ArrayList<AccessoireDTO> listeAccOuest;
    public ArrayList<EntremiseMurDTO> listeEntNord;
    public ArrayList<EntremiseMurDTO> listeEntSud;
    public ArrayList<EntremiseMurDTO> listeEntEst;
    public ArrayList<EntremiseMurDTO> listeEntOuest;
    
    public TimeStamp(){
    this.id = UUID.randomUUID();};
    
}
