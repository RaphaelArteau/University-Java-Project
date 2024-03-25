/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ca.ulaval.glo2004.cabanon.domaine;

import ca.ulaval.glo2004.cabanon.domaine.murs.Accessoire;
import ca.ulaval.glo2004.cabanon.domaine.murs.AccessoireDTO;
import ca.ulaval.glo2004.cabanon.domaine.murs.EntremiseMur;
import ca.ulaval.glo2004.cabanon.domaine.murs.EntremiseMurDTO;
import ca.ulaval.glo2004.cabanon.domaine.toit.Toit;
import ca.ulaval.glo2004.cabanon.domaine.plancher.Plancher;
import ca.ulaval.glo2004.cabanon.domaine.murs.Mur;
import ca.ulaval.glo2004.cabanon.domaine.plancher.LigneEntremiseDTO;
import ca.ulaval.glo2004.preferences.Preferences;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 *
 * @author raphael
 */
public class Cabanon {
    
    private double largeur;
    private double profondeur;
    private double hauteurMurs;
    private UUID uuid;
    private double cout;
    
    private Map<Mur.orientations, Mur> murs = new HashMap<>(); 
    private Plancher plancher;
    private Toit toit;
    
    public Cabanon(File creationValues){
        
        valeursCabanon valeursDeCreation = new valeursCabanon(creationValues);
        
        this.setLargeur(valeursDeCreation.largeur);
        this.setProfondeur(valeursDeCreation.profondeur);
        this.setHauteurMurs(valeursDeCreation.hauteurMurs);
        this.uuid = UUID.randomUUID();
        
        // On crée le plancher
        this.plancher = new Plancher(this);
        this.plancher.setEspacementSolives(valeursDeCreation.distanceSolivePlancher);
        if(valeursDeCreation.LignesEntremises != null){
            for(LigneEntremiseDTO ligne : valeursDeCreation.LignesEntremises){
                this.plancher.ajouterLigneEntremises(ligne.CoordXLigneEntremise);
                //UUID id = this.plancher.ajouterLigneEntremises(ligne.CoordXLigneEntremise);
                //this.plancher.getLigneEntremise(id).setCoordXLigneEntremise(ligne.CoordXLigneEntremise);
            }
        }
        
        // On crée les 4 murs
        ArrayList<Mur.orientations> orientations = new ArrayList<>(Arrays.asList(Mur.orientations.Nord, Mur.orientations.Sud, Mur.orientations.Est, Mur.orientations.Ouest));
        for(Mur.orientations orientation : Mur.orientations.values()){
            Mur tmpMur = new Mur(this, orientation);
            
            Map<String, Double> info = valeursDeCreation.valeursMurs.get(orientation);
            ArrayList<AccessoireDTO> accessoires = valeursDeCreation.AccessoiresMurs.get(orientation);
            ArrayList<EntremiseMurDTO> entremises = valeursDeCreation.EntremisesMurs.get(orientation);

            if(info != null){
                if(info.containsKey("espacementMontant")) tmpMur.setEspacementMontants(info.get("espacementMontant"));
            }
            if(accessoires != null){
                for(AccessoireDTO accessoireDTO : accessoires){
                    Accessoire accessoire = tmpMur.ajouterAccessoire(accessoireDTO.TypeAccessoire);
                    //accessoire.setCoordXPourcent(accessoireDTO.PourAccessoireX);
                    //accessoire.setCoordYPourcent(accessoireDTO.PourAccessoireY);
                    accessoire.setcoordAccessoireX(accessoireDTO.CoordAccessoireX);
                    accessoire.setcoordAccessoireY(accessoireDTO.CoordAccessoireY);
                    accessoire.setEspacementMontants(accessoireDTO.EspacementMontants);
                    accessoire.setHauteurContour(accessoireDTO.HauteurContour);
                    accessoire.setLargeurContour(accessoireDTO.LargeurContour);
                    accessoire.setTailleLinteau(accessoireDTO.TailleLinteau);
                }
            }
            if(entremises != null){
                for(EntremiseMurDTO entremiseDTO : entremises){
                    EntremiseMur entremise = tmpMur.ajouterEntremiseMur();
                    entremise.setCoordEntremiseX(entremiseDTO.CoordEntremiseX);
                    entremise.setCoordEntremiseY(entremiseDTO.CoordEntremiseY);
                }
            }
            this.murs.put(orientation, tmpMur);
        }
        
        // On crée le toit
        this.toit = new Toit(this);
        if(valeursDeCreation.valeursToit){
            this.toit.setAngle(valeursDeCreation.AngleToit.intValue());
            this.toit.setEspacementEntremisesDebor(valeursDeCreation.EspacementEntremisesDebor);
            this.toit.setEspacementFermes(valeursDeCreation.EspacementFermes);
            this.toit.setPorteAfaux(valeursDeCreation.PorteAFaux);
            this.toit.setOrientationToit(valeursDeCreation.OrientationToit.intValue());
        }
        
    }
    
    public ArrayList<double[]> calculListePieces(){
        ArrayList<double[]> listePieces = new ArrayList<>();
        listePieces.addAll(this.plancher.calculPiecesPlancher());
        listePieces.addAll(this.toit.calculListePieceToit());
        for(Mur.orientations orientation : Mur.orientations.values()){
            listePieces.addAll(murs.get(orientation).calculListePiecesMur());
        }

        calculerCout(listePieces);
        return listePieces;
    }
    

    
    public void calculerCout(ArrayList<double[]> listePieces){
        double sommationCout = 0;
        for (int i = 0; i < listePieces.size(); i++){
            if ((int)listePieces.get(i)[0] == 24.0){
                sommationCout += ((listePieces.get(i)[1] * listePieces.get(i)[4] * Preferences.getPrixPied24()) / 12);
            }
            if ((int)listePieces.get(i)[0] == 26){
                sommationCout += ((listePieces.get(i)[1] * listePieces.get(i)[4] * Preferences.getPrixPied26()) / 12);
            }
            if ((int)listePieces.get(i)[0] == 28){
                sommationCout += ((listePieces.get(i)[1] * listePieces.get(i)[4] * Preferences.getPrixPied28()) / 12);
            }
            if ((int)listePieces.get(i)[0] == 210){
                sommationCout += ((listePieces.get(i)[1] * listePieces.get(i)[4] * Preferences.getPrixPied210()) / 12);
            }
        }
        this.cout = sommationCout;
    }
    
    public void miseAJourComposantes(){
        this.plancher.miseAJourPlancher();
    }
    
    public Plancher getPlancher() {
        return this.plancher;
    }
    
    public Map<Mur.orientations, Mur> getMurs() {
        return murs;
    }

    public Mur getMur(Mur.orientations orientation) {
        return murs.get(orientation);
    }
    
    public double getCout(){
        return cout;
    }

    public void setLargeur(double largeur) {
        /*if(largeur % Preferences.global().getLongueurMax24() < 4){
            largeur = Preferences.global().getLongueurMax24();
        }*/
        this.largeur = largeur;
        //this.plancher.setLargeur(largeur);
    }

    public void setProfondeur(double profondeur) {
        /*if((profondeur -8) % Preferences.global().getLongueurMax24() < 4){
            profondeur += Preferences.global().getLongueurMax24()+8;
        }*/
        this.profondeur = profondeur;
        //this.plancher.setProfondeur(profondeur);
    }

    public void setHauteurMurs(double hauteurMurs) {
        this.hauteurMurs = hauteurMurs;
    }
    
    public Toit getToit(){
        return toit;
    }
    
    public void setToit(Toit toit){
        this.toit = toit;
    }

    public double getLargeur() {
        return largeur;
        
    }
 
    public double getProfondeur() {
        return profondeur;
    }

    public double getHauteurMurs() {
        return hauteurMurs;
    }
      
    public UUID getUuid(){
        return this.uuid;
    }

}
