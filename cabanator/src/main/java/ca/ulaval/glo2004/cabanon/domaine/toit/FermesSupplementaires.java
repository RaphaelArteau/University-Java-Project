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
public class FermesSupplementaires extends Fermes{

    private ArrayList<EntremiseDebordement> listeEntremisesChevron1;
    private ArrayList<EntremiseDebordement> listeEntremisesChevron2;
    private ArrayList<Double> CoordEntremisesDebordChevron1;
    private ArrayList<Double> CoordEntremisesDebordChevron2;
    private double espacementEntremise;
    private double angle;

    public FermesSupplementaires (Toit parent, int orientationToit, ArrayList<Poincons> poincons, double coordFermesSupp){
       super (parent,orientationToit,poincons, coordFermesSupp);
       this.CoordEntremisesDebordChevron1 = CoordEntremiseChevrons1();
       this.CoordEntremisesDebordChevron2 = CoordEntremiseChevrons2();
       this.listeEntremisesChevron1 = genererEntremisesDebordChevron1();
       this.listeEntremisesChevron2 = genererEntremisesDebordChevron2();
       this.espacementEntremise = parent.getEspacementEntremises();

       parent.CalculLongueurChevrons();
       parent.CalculLongueurPoinconsPrincipal();
       parent.CalculLongueurPoinconSupplementaire();
    }
    
    public ArrayList<EntremiseDebordement> getEntremiseChevron1(){
        return this.listeEntremisesChevron1;
    }
    
     public ArrayList<EntremiseDebordement> getEntremiseChevron2(){
        return this.listeEntremisesChevron2;
    }
     
     public ArrayList<Double> getCoordEntremisesDebordChevron1(){
         return this.CoordEntremisesDebordChevron1;
     }
     public ArrayList<Double> getCoordEntremisesDebordChevron2(){
         return this.CoordEntremisesDebordChevron2;
     }
     public double getEspacementEntremise(){
         return this.espacementEntremise;
     }
    
    
    
    public double calculnombreEntremise(){
        double longueurChevron = this.parent.CalculLongueurChevrons();
        double nombreEntremiseParChevron = 0;
        if (this.espacementEntremise != 0) {
            nombreEntremiseParChevron = (longueurChevron / 2) / this.espacementEntremise;
        }
        nombreEntremiseParChevron = Math.floor(nombreEntremiseParChevron);
        //System.out.println(nombreEntremiseParChevron);
        return nombreEntremiseParChevron;
    }
    
   public double getTailleEntremise(){
        return this.parent.getLongueurDebord();
    }
    
   
 private ArrayList<Double> CoordEntremiseChevrons1(){
        ArrayList<Double> listeCoordEntremisesChevron1 = new ArrayList<>();        
        double angleRad = Math.toRadians(this.angle);
        double espacementEntremises = this.espacementEntremise;
        double PremierChevronX = 0;
        double PremierChevronY = 0;
        double nombreEntremiseParChevron = this.calculnombreEntremise();
        
        for(int i = 1; i<= nombreEntremiseParChevron; i++){
        double EntremisePremierChevronX = PremierChevronX + (i * espacementEntremises * Math.cos(angleRad));
        double EntremisePremierChevronY = PremierChevronY + (i * espacementEntremises * Math.sin(angleRad));
        listeCoordEntremisesChevron1.add(EntremisePremierChevronX);
        listeCoordEntremisesChevron1.add(EntremisePremierChevronY);  
        }               
        return listeCoordEntremisesChevron1;
 }
 
 private ArrayList<EntremiseDebordement> genererEntremisesDebordChevron1(){
     ArrayList<EntremiseDebordement> listeEntremisesChevron1 = new ArrayList<>();
     ArrayList<Double> coord = this.CoordEntremiseChevrons1();
     for (int i = 0; i < coord.size()- 1; i+= 2){
         double coordonneX = coord.get(i);
         double coordonneY = coord.get(i+1);
         EntremiseDebordement entremise = new EntremiseDebordement(this, this.parent.getLongueurDebord(),coordonneX,coordonneY);
         listeEntremisesChevron1.add(entremise);
     }
     return listeEntremisesChevron1;
 }
      
private ArrayList<Double> CoordEntremiseChevrons2(){
 
    ArrayList<Double> listeCoordEntremisesChevron2 = new ArrayList<>(); 
    double angleRad = Math.toRadians(this.angle);
        double chevronUnitaire = this.parent.CalculLongueurChevrons() / 2;
        double espacementEntremises = this.espacementEntremise;
        double hauteurToit = this.parent.getHauteurToit();
        double DeuxiemeChevronX = chevronUnitaire;
        double DeuxiemeChevronY = hauteurToit;
        double nombreEntremiseParChevron = this.calculnombreEntremise();
        
        for (int i = 1; i <= nombreEntremiseParChevron; i++){
            double EntremiseDeuxiemeChevronX = DeuxiemeChevronX + (i * espacementEntremises * Math.cos(angleRad));
            double EntremiseDeuxiemeChevronY = DeuxiemeChevronY + (i * espacementEntremises * Math.sin(angleRad));
            listeCoordEntremisesChevron2.add(EntremiseDeuxiemeChevronX);
            listeCoordEntremisesChevron2.add(EntremiseDeuxiemeChevronY);
         }
        return listeCoordEntremisesChevron2;
}

private ArrayList<EntremiseDebordement> genererEntremisesDebordChevron2(){
     ArrayList<EntremiseDebordement> listeEntremisesChevron2 = new ArrayList<>();
     ArrayList<Double> coord = this.CoordEntremiseChevrons2();
     for (int i = 0; i < coord.size()- 1; i+= 2){
         double coordonneX = coord.get(i);
         double coordonneY = coord.get(i+1);
         EntremiseDebordement entremise = new EntremiseDebordement(this, this.parent.getLongueurDebord(),coordonneX,coordonneY);
         listeEntremisesChevron2.add(entremise);
     }
     return listeEntremisesChevron2;
 }


}