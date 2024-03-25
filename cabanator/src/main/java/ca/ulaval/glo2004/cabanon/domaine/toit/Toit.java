/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ca.ulaval.glo2004.cabanon.domaine.toit;


import ca.ulaval.glo2004.cabanon.domaine.Cabanon;
import ca.ulaval.glo2004.preferences.Preferences;
import java.util.ArrayList;

/**
 *
 * @author Charles
 */
public class Toit {
    
    private Cabanon parent;
    private int orientationToit;
    private double angleToit;
    private double porte_a_faux;
    private double porte_a_faux_base;
    private double porte_a_faux_hauteur;



    private double HauteurToit;
    private double longueurDebordement;
    private double espacementFermes;
    private double espacementEntremisesDebor;
    private int nombreEntremisesDebord;
    private double EntraitFermes;
    private double chevrons;
    private double poinconsPrincipal;
    private double poinconsSupp;
    private double nombreFerme;
    private ArrayList<Poincons> PoinconsFermes;
    private ArrayList<Fermes> ListeFermes;
    private ArrayList<Double> CoordPoinconsY;
    private ArrayList<Double> CoordPoinconsX;
    private ArrayList<Double> CoordEntremisesDebor;
    private ArrayList<Double> CoordFermes;
    private ArrayList<Double> CoordFermesOrdo;
    private ArrayList<Double> coordEntremisesOrdo;
    
    public static int Nord = 1;
    public static int Est = 2;
    private double porte_a_faux_hypothenuse;
  
    public Toit (Cabanon parent){
        this.parent = parent;
        //pas sur pour l'orientation
        this.orientationToit = Nord;
        this.porte_a_faux = 15;
        this.angleToit = 90;
        this.chevrons = this.CalculLongueurChevrons() / 2;
      //  System.out.println(chevrons);
        this.longueurDebordement = 20;
        this.espacementFermes = 24;
        this.nombreFerme = this.CalculNombreFermes();
        this.espacementEntremisesDebor = 15;
        this.nombreEntremisesDebord = (int) this.calculnombreEntremise();
        this.HauteurToit = this.getHauteurToit();
        this.poinconsPrincipal = this.HauteurToit - 2;
        this.poinconsSupp = this.CalculLongueurPoinconSupplementaire();
        CalculNombreFermes();        
        this.ListeFermes = this.CreerListeFermes();
        this.PoinconsFermes = this.ListePoincons();
        this.CoordEntremisesDebor = new ArrayList<>();
        this.CoordFermes = this.GenererFermes();
        this.CoordPoinconsY = this.GenererPoinconsY();
        this.CoordPoinconsX = this.GenererPoinconsX();
        this.EntraitFermes = this.parent.getLargeur();
        this.calculatePorteAFauxMeasures();
        this.CoordFermesOrdo = this.ordonnerCoordFermes();
        /*for(double coord:this.CoordFermesOrdo){
            System.out.println(coord);
        }*/
        this.coordEntremisesOrdo = this.coordEntremisesOrdo();
        /*for(double coord:this.coordEntremisesOrdo){
            System.out.println(coord);
        }*/
    }
    
   public final void setOrientationToit(int orientation){
       this.orientationToit = orientation;
       this.HauteurToit = this.getHauteurToit();
       this.calculerHauteurTotale();
   }
   
    public final void setAngle(double angle){
        if(angle==0 || angle<0 || angle>176){
            angle=176;
        }
        this.angleToit = angle;
        this.calculerHauteurTotale();
        this.CalculLongueurChevrons();
        this.calculatePorteAFauxMeasures();
    }
    
    public final void setPorteAfaux(double taille){
        this.porte_a_faux = taille;
        //System.out.println(taille);
        calculatePorteAFauxMeasures();
    }

    private final void calculatePorteAFauxMeasures(){
        double base = this.porte_a_faux;
        this.porte_a_faux_hypothenuse = (base / Math.cos(Math.toRadians(180 - (this.angleToit / 2) - 90)));
        if(this.orientationToit==1){
        this.porte_a_faux_hauteur = (this.porte_a_faux + this.parent.getLargeur() / 2) / (Math.tan(Math.toRadians((this.angleToit / 2))));}
        else{this.porte_a_faux_hauteur = (this.porte_a_faux + this.parent.getProfondeur() / 2) / (Math.tan(Math.toRadians((this.angleToit / 2))));}
    }
    
    public final void setLongueurDebor(double longueur){
        this.longueurDebordement = longueur;
    }
    
    public final void setEspacementFermes(double espacement){
        this.espacementFermes = espacement;
        this.CalculNombreFermes();
    }
    
    public final void setEspacementEntremisesDebor(double espacement){
        this.espacementEntremisesDebor = espacement;
    }
    
    public final Cabanon getParent(){
       return this.parent;
   }
      
    public final int getOrientationToit(){
        return this.orientationToit;
    }
    
    public final double getAngleToit(){
        return this.angleToit;
    }
    
    public final double getPorteAfaux(){
        return this.porte_a_faux;
    }
    
    public final double getEspacementFermes(){
        return this.espacementFermes;
    }
    
    public final double getEspacementEntremises(){
        return this.espacementEntremisesDebor;
    }
    
    public final double getLongueurDebord(){
        return this.longueurDebordement;
    }
    
    public final double getHauteurToit(){
        if(this.orientationToit == 1){
            this.HauteurToit = this.parent.getLargeur()/ (2 *(Math.tan(Math.toRadians(this.angleToit/2))));
        }
        else{
            this.HauteurToit = this.parent.getProfondeur()/ (2 *(Math.tan(Math.toRadians(this.angleToit/2))));
        }
        return this.HauteurToit;
    }
    
    public double getEntraitFerme(){
        return this.EntraitFermes;
    }
    
    public ArrayList<Fermes> getFermes(){
        return this.ListeFermes;
    }
    
    public ArrayList<Double> getCoordEntremisesDebor(){
        return this.CoordEntremisesDebor;
    }
    
    public ArrayList<Double> getCoordFermes(){
        return this.CoordFermes;
    }
    
    public ArrayList<Double> getCoordPoinconsX(){
        return this.CoordPoinconsX;
    }
    
     public ArrayList<Double> getCoordPoinconsY(){
        return this.CoordPoinconsY;
    }
    
    public ArrayList<Poincons> getPoincons(){
       ArrayList<Poincons> poincons = this.ListePoincons();
       poincons = this.PoinconsFermes; 
       return this.PoinconsFermes;
    }
    
    public double getChevrons(){
        this.chevrons = this.CalculLongueurChevrons() / 2;
        return this.chevrons;
    }
    
    public double getPoinconsPrincipal(){
        return this.poinconsPrincipal;
    }
    
    public double getPoinconsSupp(){
        return this.poinconsSupp;
    }
    
    public int getNombreEntremises(){
        return this.nombreEntremisesDebord;
    }
    
    public double getNombreFermes(){
        return this.nombreFerme + 4;
    }
    public ArrayList<Double> getCoordFermesOrdo(){
        this.CoordFermesOrdo = this.ordonnerCoordFermes();
        return this.ordonnerCoordFermes();
    }
    public ArrayList<Double> getCoordEntremisesOrdo(){
        this.coordEntremisesOrdo = this.coordEntremisesOrdo();
        return this.coordEntremisesOrdo();
    }
    
    public void changerOrientation(){
        if (this.orientationToit == Nord){
        this.setOrientationToit(Est);
        }
        else{
            this.setOrientationToit(Nord);
        }
    }
    
    public double CalculLongueurChevrons(){
        return 2 * (this.HauteurToit / Math.sin(Math.toRadians(180 - (90 + (this.angleToit / 2)))));
    }
    
     public double CalculLongueurPoinconsPrincipal(){
        double hauteurFerme = (this.EntraitFermes / 2) * Math.tan(Math.toRadians(this.angleToit));
        double taillePoincons = Math.sqrt((this.EntraitFermes/2)*(this.EntraitFermes/2) + hauteurFerme * hauteurFerme);  
        taillePoincons = poinconsPrincipal;
        return taillePoincons;
    }
    
    public double CalculLongueurPoinconSupplementaire(){
        double quartEntrait = this.EntraitFermes / 4;
        double angleBasDeFerme = (180 - this.angleToit) / 2;
        double hauteurFerme = quartEntrait * Math.tan(Math.toRadians(angleBasDeFerme));
        double longueurPoinconsSupp = Math.sqrt(quartEntrait * quartEntrait + hauteurFerme * hauteurFerme);
        return longueurPoinconsSupp;
    }
    
    public ArrayList<Poincons> ListePoincons(){
       ArrayList<Poincons> ListeDePoincons = new ArrayList<>();
       double poinconCentral = this.CalculLongueurPoinconsPrincipal();
       double poinconGauche = this.CalculLongueurPoinconSupplementaire();
       double poinconDroite = this.CalculLongueurPoinconSupplementaire();
       if ((this.CalculLongueurChevrons() / 2) > Preferences.getLongueurMax24()){
           Poincons poinconsLeft = new Poincons(poinconGauche, this.EntraitFermes * 0.25, this.HauteurToit * 2);
           Poincons poinconsCentre = new Poincons(poinconCentral, this.EntraitFermes * 0.5, this.HauteurToit);
           Poincons poinconsRight = new Poincons(poinconDroite, this.EntraitFermes * 0.75, this.HauteurToit * 2);
           ListeDePoincons.add(poinconsLeft);
           ListeDePoincons.add(poinconsCentre);
           ListeDePoincons.add(poinconsRight);
       }
       else{
           Poincons poinconsCentre = new Poincons(poinconCentral, this.EntraitFermes * 0.5, this.CalculLongueurPoinconsPrincipal());
           ListeDePoincons.add(poinconsCentre);
       }
       this.PoinconsFermes = ListeDePoincons;
       
       return PoinconsFermes;       
    }
    
    public int calculnombreEntremise(){
        double longueurChevron = this.CalculLongueurChevrons();
        int nombreEntremiseParChevron = 0;
        if (this.espacementEntremisesDebor != 0) {
            nombreEntremiseParChevron = (int) Math.floor(longueurChevron / (2 * this.espacementEntremisesDebor));
        }
        return nombreEntremiseParChevron;
    }
    
      private ArrayList<Double> GenererPoinconsX(){
        ArrayList<Poincons> CoordPoincons = this.PoinconsFermes;
        ArrayList<Double> CoordPoinconsX = new ArrayList<>();   
        for (Poincons poincons : CoordPoincons){            
            CoordPoinconsX.add(poincons.getCoordPoinconsX());
        }
        return CoordPoinconsX;
    }
        private ArrayList<Double> GenererPoinconsY(){
        ArrayList<Poincons> CoordPoincons = this.PoinconsFermes;
        ArrayList<Double> CoordPoinconsY = new ArrayList<>();
        
        for (Poincons poincons : CoordPoincons){            
            CoordPoinconsY.add(poincons.getCoordPoinconsY());
        }
        return CoordPoinconsY;
    }
    private double CalculNombreFermes(){
        double distance = this.parent.getPlancher().getProfondeur();
        //calcul de la distance disponible considerant 
        //les deux fermes des extremités du toit
        double distanceDisponible = distance - (2 * this.getEspacementFermes());
        double nombreDeFermes = distanceDisponible / this.getEspacementFermes();
        //arrondi vers le bas pour le nombre de ferme pile
        nombreDeFermes = Math.floor(nombreDeFermes);
        this.nombreFerme = nombreDeFermes;
        return nombreDeFermes;
    }
    
    private ArrayList<Fermes> CreerListeFermes(){
        double nombreFermes = this.CalculNombreFermes();
        ArrayList<Fermes> listeFermes = new ArrayList<>();
        // creation ferme debut
        Fermes fermeFin = new Fermes(this,this.getOrientationToit(),
                this.PoinconsFermes, this.parent.getProfondeur());
        double coordfermeDebut = fermeFin.getCoordFermesX() - (this.CalculNombreFermes() + 1) * this.getEspacementFermes();
        Fermes fermeDebut = new Fermes(this, this.getOrientationToit(), 
                this.PoinconsFermes,coordfermeDebut);
        listeFermes.add(fermeDebut);
        // creation fermes entre les deux extremitées
        double coord = this.getEspacementFermes() + fermeDebut.getCoordFermesX();
        for (int i=0; i < nombreFermes; i++){
           Fermes fermes = new Fermes(this, this.getOrientationToit(),this.PoinconsFermes, coord);
           listeFermes.add(fermes);
           coord = coord + this.getEspacementFermes();
        }                   
        // creation ferme fin
        // on ajoute 1 a calculNombreFermes puisque celle des extremitees sont pas consideres
        listeFermes.add(fermeFin);
        
        //creation fermes supplementaire
        FermesSupplementaires fermeSupp1 = new FermesSupplementaires(this,this.getOrientationToit(),
              this.PoinconsFermes, coordfermeDebut - this.longueurDebordement);
       listeFermes.add(fermeSupp1);
       FermesSupplementaires fermeSupp2 = new FermesSupplementaires(this,this.getOrientationToit(),
              this.PoinconsFermes, fermeFin.getCoordFermesX() + this.longueurDebordement);
       listeFermes.add(fermeSupp2);
            
        return listeFermes;
    }
    
    private ArrayList<Double> GenererFermes(){
        ArrayList<Fermes> fermes = this.CreerListeFermes();
        ArrayList<Double> CoordfermesX = new ArrayList<>();
        for (Fermes ferme : fermes){            
            CoordfermesX.add(ferme.getCoordFermesX());
        }
        return CoordfermesX;
    }
    
    private void calculerHauteurTotale(){
        if(this.orientationToit == 1){
            this.HauteurToit = (this.parent.getLargeur() / 2) / (Math.tan(Math.toRadians(this.angleToit/2)));
        }
        else{
            this.HauteurToit = (this.parent.getProfondeur() / 2) / (Math.tan(Math.toRadians(this.angleToit/2)));
        }
    }
    
    
    public void MiseAJourToit(){
        CalculNombreFermes();
        CreerListeFermes();
        GenererFermes();
        calculerHauteurTotale();
        CalculLongueurChevrons();
        calculatePorteAFauxMeasures();
    }
    
    public ArrayList<double[]> calculListePieceToit(){
        ArrayList<double[]> piecesToit = new ArrayList<>();
        double longueurMax24 = Preferences.getLongueurMax24();
        
        //Incrémentation pour les pièces de fermes (trois scénarios : 1, 2, n pièces pour chaque chevrons)
        //Calcul du nombre de pièces necessaires pour les chevrons
        double longueurChevronsEtPAF = (this.chevrons) + (this.porte_a_faux / Math.cos(Math.toRadians(180 - (this.angleToit / 2) - 90)));
        int nbPiecesChevrons = (int) Math.ceil(longueurChevronsEtPAF / longueurMax24);
        
        if (nbPiecesChevrons == 1){
            double[] piecesChevrons1 = {24, longueurChevronsEtPAF, ((180 - this.angleToit) / 2), ((180 - this.angleToit) / 2), (this.nombreFerme + 4)};
            
            double[] piecesChevrons2 = {24, longueurChevronsEtPAF, ((180 + this.angleToit) / 2), ((180 + this.angleToit) / 2), (this.nombreFerme + 4)};
            
            piecesToit.add(piecesChevrons1);
            piecesToit.add(piecesChevrons2);
        }
        
        else if (nbPiecesChevrons == 2) {
            double[] piecesChevrons1pleinesDebut = {24, longueurMax24, ((180 - this.angleToit) / 2), 90, (this.nombreFerme + 4)};
            double[] piecesChevrons1partiellesFin = {24, (longueurChevronsEtPAF % longueurMax24), 90, ((180 - this.angleToit) / 2), (this.nombreFerme + 4)};
            
            double[] piecesChevrons2partiellesFin = {24, (longueurChevronsEtPAF % longueurMax24), ((180 + this.angleToit) / 2), 90, (this.nombreFerme + 4)};
            double[] piecesChevrons2pleinesDebut = {24, longueurMax24, 90, ((180 + this.angleToit) / 2), (this.nombreFerme + 4)};
        
            piecesToit.add(piecesChevrons1partiellesFin);
            piecesToit.add(piecesChevrons2partiellesFin);
            piecesToit.add(piecesChevrons1pleinesDebut);
            piecesToit.add(piecesChevrons2pleinesDebut);
        }
        
        else if (nbPiecesChevrons > 2) {
            double[] piecesChevrons1pleinesDebut = {24, longueurMax24, ((180 - this.angleToit) / 2), 90, (this.nombreFerme + 4)};
            double[] piecesChevrons1partiellesFin = {24, (longueurChevronsEtPAF % longueurMax24), 90, ((180 - this.angleToit) / 2), (this.nombreFerme + 4)};
            
            double[] piecesChevrons2partiellesFin = {24, (longueurChevronsEtPAF % longueurMax24), ((180 + this.angleToit) / 2), 90, (this.nombreFerme + 4)};
            double[] piecesChevrons2pleinesDebut = {24, longueurMax24, 90, ((180 + this.angleToit) / 2), (this.nombreFerme + 4)};
            double[] piecesChevronspleines = {24, longueurMax24, 90, 90, ((this.nombreFerme + 4) * (nbPiecesChevrons - 2) * 2)};

            piecesToit.add(piecesChevrons1partiellesFin);
            piecesToit.add(piecesChevrons2partiellesFin);
            piecesToit.add(piecesChevrons1pleinesDebut);
            piecesToit.add(piecesChevrons2pleinesDebut);
            piecesToit.add(piecesChevronspleines);
        }
        
        //Pour chaque fin de segment de chevron, on ajoute le poincon qui le supporte
        for (int i = 1; i <= nbPiecesChevrons; i++ ){
            if (i == nbPiecesChevrons){
                piecesToit.addAll(CalculPiecesPoinconsOpposes(this.HauteurToit));
            }
            else{
                //System.out.println("hauteur demandée");
                //System.out.println((i * longueurMax24 * Math.cos(Math.toRadians(this.angleToit / 2))));
                piecesToit.addAll(CalculPiecesPoinconsOpposes(i * longueurMax24 * Math.cos(Math.toRadians(this.angleToit / 2))));
            }
        }
        
        //Ajout des Entraits (bases de fermes)
        int nbreEntraits = (int) Math.floor(EntraitFermes / longueurMax24);
        if (nbreEntraits == 1){
            double[] entraitFermes = {24, EntraitFermes, 90, 90, this.nombreFerme + 4};
            piecesToit.add(entraitFermes);
        }
        else {
            double[] entraitFermesPartiels = {24, (EntraitFermes % longueurMax24), 90, 90, this.nombreFerme + 4};
            double[] entraitFermesPleins = {24, longueurMax24, 90, 90, ((nbreEntraits - 1)*(this.nombreFerme + 4))};
            piecesToit.add(entraitFermesPartiels);
            piecesToit.add(entraitFermesPleins);
        }
        
        //Ajout des pieces pour les entremises de toit
        int nbreEntremises = this.calculnombreEntremise() * 4;
        double[] entremisesToit = {24, longueurDebordement, 90, 90, nbreEntremises};
        piecesToit.add(entremisesToit);
        
        return piecesToit;
    }
    
    private ArrayList<double[]> CalculPiecesPoinconsOpposes(double hauteur){
        ArrayList<double[]> piecesPoincons = new ArrayList<>();
        double longueurMax24 = Preferences.getLongueurMax24();
        int nbPiecesPoincons = (int) Math.ceil((hauteur - 2)/ longueurMax24);  
        if (hauteur == this.HauteurToit){
            if (nbPiecesPoincons == 1){
                double[] piecesPoincons1Centre = {24, (hauteur - 2), 90, 90, (this.nombreFerme + 4)};
                piecesPoincons.add(piecesPoincons1Centre);
                return piecesPoincons;
            }
            else{
                double[] piecesPoinconsNCentrePartiel = {24, ((hauteur - 2) % longueurMax24), 90, 90, (this.nombreFerme + 4)};
                double[] piecesPoinconsNCentrePleins = {24, longueurMax24, 90, 90, ((nbPiecesPoincons - 1) * (this.nombreFerme + 4))};
                piecesPoincons.add(piecesPoinconsNCentrePartiel);
                piecesPoincons.add(piecesPoinconsNCentrePleins);
                return piecesPoincons;
            }
        }
        else{
            if (nbPiecesPoincons == 1){
                double[] piecesPoincons11 = {24, (hauteur - 2), (180 - (this.angleToit / 2)), 90, (this.nombreFerme + 4)};
                double[] piecesPoincons12 = {24, (hauteur - 2), 90, (this.angleToit / 2), (this.nombreFerme + 4)};

                piecesPoincons.add(piecesPoincons11);
                piecesPoincons.add(piecesPoincons12);
                return piecesPoincons;
            }
            else{
                double[] piecesPoinconsN1 = {24, ((hauteur - 2) % longueurMax24), (180 - (this.angleToit / 2)), 90, (this.nombreFerme + 4)};
                double[] piecesPoinconsN2 = {24, ((hauteur - 2) % longueurMax24), 90, (this.angleToit / 2), (this.nombreFerme + 4)};
                double[] piecesPoinconsNPleins = {24, longueurMax24, 90, 90, (2 * (nbPiecesPoincons - 1) * (this.nombreFerme + 4))};
                piecesPoincons.add(piecesPoinconsN1);
                piecesPoincons.add(piecesPoinconsN2);
                piecesPoincons.add(piecesPoinconsNPleins);
                return piecesPoincons;
            }
        }
    }
    private ArrayList<Double> ordonnerCoordFermes(){
        double longueurToit;
        if(this.orientationToit == 1){
            longueurToit = this.parent.getProfondeur();
        }
        else{
            longueurToit = this.parent.getLargeur();
        }
        ArrayList<Double> coordFermesOrdo = new ArrayList();
        double coord = 0;
        coordFermesOrdo.add(coord+1);
        coord+=this.longueurDebordement;
        int nombreFermes = 1;
        for(int i =0; i<nombreFermes;i++){
            coordFermesOrdo.add(coord+1);
            if(coord + this.espacementFermes < longueurToit + this.longueurDebordement-3){
                coord+=this.espacementFermes;
                nombreFermes++;
            }
            else{
                coord = longueurToit +this.longueurDebordement-1;
                coordFermesOrdo.add(coord);
            }
        }
        coord+=this.longueurDebordement;
        coordFermesOrdo.add(coord);
        return coordFermesOrdo;
    }
    
    private ArrayList<Double> coordEntremisesOrdo(){
        double longueurToit;
        if(this.orientationToit == 1){
            longueurToit = this.parent.getLargeur();
        }
        else{
            longueurToit = this.parent.getProfondeur();
        }
        int nombreEntremises = 1;
        double longueurChevron = (longueurToit + this.porte_a_faux)/Math.sin(Math.toRadians(angleToit/2));
        double largeurEntremise = 2*(Math.sin(Math.toRadians(angleToit/2)))+ 4*(Math.sin(Math.toRadians(90-(angleToit/2))));
        double espacement =this.espacementEntremisesDebor * (Math.sin(Math.toRadians(angleToit/2)));
        double coordX = this.espacementEntremisesDebor * (Math.sin(Math.toRadians(angleToit/2)));
        ArrayList<Double> listeCoordEntremises = new ArrayList();
        for(int i=0; i< nombreEntremises;i++){
        listeCoordEntremises.add(coordX);
        if(coordX+espacement+largeurEntremise > (longueurToit/2)+this.porte_a_faux){
            coordX = longueurToit/2 - largeurEntremise;
        }
        else{
            coordX += espacement;
            nombreEntremises++;
        }
        }
        int taille = listeCoordEntremises.size();
        for(int y = taille -1; y>-1;y--){
        listeCoordEntremises.add(longueurToit/2 +this.porte_a_faux +(longueurToit/2 +this.porte_a_faux - listeCoordEntremises.get(y))-4);
        }
        return listeCoordEntremises;
        }

    public double getPorteAFauxHypo() {
        this.calculatePorteAFauxMeasures();
        return porte_a_faux_hypothenuse;
    }

    public double getPorteAFauxHauteur() {
        this.calculatePorteAFauxMeasures();
        return porte_a_faux_hauteur;
    }

}




