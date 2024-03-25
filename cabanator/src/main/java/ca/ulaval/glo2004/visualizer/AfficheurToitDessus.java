/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ca.ulaval.glo2004.visualizer;


import java.awt.Color;
import java.awt.Graphics;
import ca.ulaval.glo2004.cabanon.domaine.toit.ToitDTO;




/**
 *
 * @author Utilisateur
 */

public class AfficheurToitDessus extends Afficheur {
    private ToitDTO toit;
    
    public AfficheurToitDessus(Visualizer parent) {
        super(parent);
        this.toit = this.controlleur.getToit();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int _2inches_px = InchesToPixels(2);
       
        
        //On dessine les fermes
        if(this.toit.OrientationToit ==1){
        for(Double coord: this.controlleur.getCoordFermesOrdo()){
            //System.out.println(coord);
            g.setColor(Color.WHITE);
            g.fillRect(0,InchesToPixels(coord-1), InchesToPixels(this.controlleur.getToit().PorteAFaux+this.controlleur.getCabanon().Largeur/2), _2inches_px);
            g.fillRect(InchesToPixels(this.controlleur.getToit().PorteAFaux + this.controlleur.getCabanon().Largeur/2),InchesToPixels(coord -1), InchesToPixels(this.controlleur.getToit().PorteAFaux+this.controlleur.getCabanon().Largeur/2), _2inches_px);
            g.setColor(Color.BLACK);
            g.drawRect(0,InchesToPixels(coord-1), InchesToPixels(this.controlleur.getToit().PorteAFaux+this.controlleur.getCabanon().Largeur/2), _2inches_px);
            g.drawRect(InchesToPixels(this.controlleur.getToit().PorteAFaux + this.controlleur.getCabanon().Largeur/2),InchesToPixels(coord -1), InchesToPixels(this.controlleur.getToit().PorteAFaux+this.controlleur.getCabanon().Largeur/2), _2inches_px);
                        if((this.controlleur.getToit().PorteAFaux+this.controlleur.getCabanon().Largeur/2)/Math.sin(Math.toRadians(this.controlleur.getToit().AngleToit/2)) > Double.parseDouble(this.controlleur.getLongueurMax24())){
                g.drawLine(InchesToPixels((this.controlleur.getToit().PorteAFaux+this.controlleur.getCabanon().Largeur/2)/2), InchesToPixels(coord-1),
                        InchesToPixels((this.controlleur.getToit().PorteAFaux+this.controlleur.getCabanon().Largeur/2)/2), InchesToPixels(coord-1)+_2inches_px);
                g.drawLine(InchesToPixels((this.controlleur.getToit().PorteAFaux+this.controlleur.getCabanon().Largeur/2)/2)*3, InchesToPixels(coord-1),
                        InchesToPixels((this.controlleur.getToit().PorteAFaux+this.controlleur.getCabanon().Largeur/2)/2)*3, InchesToPixels(coord-1)+_2inches_px);
            }
        }
        //On dessine les Entremises de débordement en perspective
        int tranche2X4 = InchesToPixels(2*(Math.sin(Math.toRadians(this.controlleur.getToit().AngleToit/2))));
        int largeur2X4 = InchesToPixels(4*(Math.sin(Math.toRadians(90-(this.controlleur.getToit().AngleToit/2)))));
        for(double coord: this.controlleur.getCoordEntremisesOrdo()){
            if(coord < this.controlleur.getToit().PorteAFaux+this.controlleur.getCabanon().Largeur/2){
            g.setColor(Color.WHITE);
            g.fillRect(InchesToPixels(coord),_2inches_px ,tranche2X4, InchesToPixels(this.controlleur.getToit().LongueurDebordement-2));
            g.fillRect(InchesToPixels(this.controlleur.getToit().PorteAFaux+this.controlleur.getLargeurCabanon()/2 -1), _2inches_px, _2inches_px, InchesToPixels(this.controlleur.getToit().LongueurDebordement-2));
            g.setColor(Color.LIGHT_GRAY);
            g.fillRect(InchesToPixels(coord)+tranche2X4, _2inches_px,largeur2X4,InchesToPixels(this.controlleur.getToit().LongueurDebordement-2));
            g.setColor(Color.BLACK);
            g.drawRect(InchesToPixels(coord),_2inches_px ,tranche2X4, InchesToPixels(this.controlleur.getToit().LongueurDebordement-2));
            g.drawRect(InchesToPixels(coord)+tranche2X4, _2inches_px,largeur2X4,InchesToPixels(this.controlleur.getToit().LongueurDebordement-2));
            g.drawRect(InchesToPixels(this.controlleur.getToit().PorteAFaux+this.controlleur.getLargeurCabanon()/2 -1), _2inches_px, _2inches_px, InchesToPixels(this.controlleur.getToit().LongueurDebordement-2));
            int coordNY = InchesToPixels(this.controlleur.getCabanon().Profondeur+this.controlleur.getToit().LongueurDebordement);
            g.setColor(Color.WHITE);
            g.fillRect(InchesToPixels(coord),coordNY ,tranche2X4, InchesToPixels(this.controlleur.getToit().LongueurDebordement-2));
            g.fillRect(InchesToPixels(this.controlleur.getToit().PorteAFaux+this.controlleur.getLargeurCabanon()/2 -1), coordNY, _2inches_px, InchesToPixels(this.controlleur.getToit().LongueurDebordement-2));
            g.setColor(Color.LIGHT_GRAY);
            g.fillRect(InchesToPixels(coord)+tranche2X4, coordNY,largeur2X4,InchesToPixels(this.controlleur.getToit().LongueurDebordement-2));
            g.setColor(Color.BLACK);
            g.drawRect(InchesToPixels(coord),coordNY ,tranche2X4, InchesToPixels(this.controlleur.getToit().LongueurDebordement-2));
            g.drawRect(InchesToPixels(coord)+tranche2X4, coordNY,largeur2X4,InchesToPixels(this.controlleur.getToit().LongueurDebordement-2));
            g.drawRect(InchesToPixels(this.controlleur.getToit().PorteAFaux+this.controlleur.getLargeurCabanon()/2 -1), coordNY, _2inches_px, InchesToPixels(this.controlleur.getToit().LongueurDebordement-2));
            }
            else{
            g.setColor(Color.LIGHT_GRAY);
            g.fillRect(InchesToPixels(coord),_2inches_px ,largeur2X4, InchesToPixels(this.controlleur.getToit().LongueurDebordement-2));
            g.setColor(Color.WHITE);
            g.fillRect(InchesToPixels(coord)+largeur2X4, _2inches_px,tranche2X4,InchesToPixels(this.controlleur.getToit().LongueurDebordement-2));
            g.setColor(Color.BLACK);
            g.drawRect(InchesToPixels(coord),_2inches_px ,largeur2X4, InchesToPixels(this.controlleur.getToit().LongueurDebordement-2));
            g.drawRect(InchesToPixels(coord)+largeur2X4, _2inches_px,tranche2X4,InchesToPixels(this.controlleur.getToit().LongueurDebordement-2));
            int coordNY = InchesToPixels(this.controlleur.getCabanon().Profondeur + this.controlleur.getToit().LongueurDebordement);
            g.setColor(Color.LIGHT_GRAY);
            g.fillRect(InchesToPixels(coord),coordNY ,largeur2X4, InchesToPixels(this.controlleur.getToit().LongueurDebordement-2));
            g.setColor(Color.WHITE);
            g.fillRect(InchesToPixels(coord)+largeur2X4, coordNY,tranche2X4,InchesToPixels(this.controlleur.getToit().LongueurDebordement-2));
            g.setColor(Color.BLACK);
            g.drawRect(InchesToPixels(coord),coordNY ,largeur2X4, InchesToPixels(this.controlleur.getToit().LongueurDebordement-2));
            g.drawRect(InchesToPixels(coord)+largeur2X4, coordNY,tranche2X4,InchesToPixels(this.controlleur.getToit().LongueurDebordement-2));   
            }
        }
        }
        //cas orientation Est Ouest
        else{
                  for(Double coord: this.controlleur.getCoordFermesOrdo()){
            //System.out.println(coord);
            g.setColor(Color.WHITE);
            g.fillRect(InchesToPixels(coord-1),0, _2inches_px,InchesToPixels(this.controlleur.getToit().PorteAFaux+this.controlleur.getCabanon().Profondeur/2));
            g.fillRect(InchesToPixels(coord -1),InchesToPixels(this.controlleur.getToit().PorteAFaux + this.controlleur.getCabanon().Profondeur/2), _2inches_px, InchesToPixels(this.controlleur.getToit().PorteAFaux+this.controlleur.getCabanon().Profondeur/2));
            g.setColor(Color.BLACK);
            g.drawRect(InchesToPixels(coord-1),0, _2inches_px,InchesToPixels(this.controlleur.getToit().PorteAFaux+this.controlleur.getCabanon().Profondeur/2));
            g.drawRect(InchesToPixels(coord -1),InchesToPixels(this.controlleur.getToit().PorteAFaux + this.controlleur.getCabanon().Profondeur/2), _2inches_px, InchesToPixels(this.controlleur.getToit().PorteAFaux+this.controlleur.getCabanon().Profondeur/2));
                        if((this.controlleur.getToit().PorteAFaux+this.controlleur.getCabanon().Profondeur/2)/Math.sin(Math.toRadians(this.controlleur.getToit().AngleToit/2)) > Double.parseDouble(this.controlleur.getLongueurMax24())){
                g.drawLine(InchesToPixels((this.controlleur.getToit().PorteAFaux+this.controlleur.getCabanon().Profondeur/2)/2), InchesToPixels(coord-1),
                        InchesToPixels((this.controlleur.getToit().PorteAFaux+this.controlleur.getCabanon().Profondeur/2)/2), InchesToPixels(coord-1)+_2inches_px);
                g.drawLine(InchesToPixels((this.controlleur.getToit().PorteAFaux+this.controlleur.getCabanon().Profondeur/2)/2)*3, InchesToPixels(coord-1),
                        InchesToPixels((this.controlleur.getToit().PorteAFaux+this.controlleur.getCabanon().Profondeur/2)/2)*3, InchesToPixels(coord-1)+_2inches_px);
            }
        }
        //On dessine les Entremises de débordement en perspective
        int tranche2X4 = InchesToPixels(2*(Math.sin(Math.toRadians(this.controlleur.getToit().AngleToit/2))));
        int largeur2X4 = InchesToPixels(4*(Math.sin(Math.toRadians(90-(this.controlleur.getToit().AngleToit/2)))));
        for(double coord: this.controlleur.getCoordEntremisesOrdo()){
            if(coord < this.controlleur.getToit().PorteAFaux+this.controlleur.getCabanon().Profondeur/2){
            g.setColor(Color.WHITE);
            g.fillRect(_2inches_px,InchesToPixels(coord) , InchesToPixels(this.controlleur.getToit().LongueurDebordement-2), tranche2X4);
            g.fillRect(_2inches_px, InchesToPixels(this.controlleur.getToit().PorteAFaux+this.controlleur.getProfondeurCabanon()/2 -1), InchesToPixels(this.controlleur.getToit().LongueurDebordement-2), _2inches_px);
            g.setColor(Color.LIGHT_GRAY);
            g.fillRect(_2inches_px, InchesToPixels(coord)+tranche2X4,InchesToPixels(this.controlleur.getToit().LongueurDebordement-2), largeur2X4);
            g.setColor(Color.BLACK);
            g.drawRect(_2inches_px,InchesToPixels(coord) , InchesToPixels(this.controlleur.getToit().LongueurDebordement-2), tranche2X4);
            g.drawRect(_2inches_px,InchesToPixels(coord)+tranche2X4,InchesToPixels(this.controlleur.getToit().LongueurDebordement-2),largeur2X4);
            g.drawRect(_2inches_px, InchesToPixels(this.controlleur.getToit().PorteAFaux+this.controlleur.getProfondeurCabanon()/2 -1), InchesToPixels(this.controlleur.getToit().LongueurDebordement-2), _2inches_px);
            int coordNY = InchesToPixels(this.controlleur.getCabanon().Largeur+this.controlleur.getToit().LongueurDebordement);
            g.setColor(Color.WHITE);
            g.fillRect(coordNY,InchesToPixels(coord) , InchesToPixels(this.controlleur.getToit().LongueurDebordement-2),tranche2X4);
            g.fillRect(coordNY,InchesToPixels(this.controlleur.getToit().PorteAFaux+this.controlleur.getProfondeurCabanon()/2 -1), InchesToPixels(this.controlleur.getToit().LongueurDebordement-2), _2inches_px);
            g.setColor(Color.LIGHT_GRAY);
            g.fillRect(coordNY,InchesToPixels(coord)+tranche2X4,InchesToPixels(this.controlleur.getToit().LongueurDebordement-2),largeur2X4);
            g.setColor(Color.BLACK);
            g.drawRect(coordNY,InchesToPixels(coord) , InchesToPixels(this.controlleur.getToit().LongueurDebordement-2),tranche2X4);
            g.drawRect(coordNY,InchesToPixels(this.controlleur.getToit().PorteAFaux+this.controlleur.getProfondeurCabanon()/2 -1), InchesToPixels(this.controlleur.getToit().LongueurDebordement-2), _2inches_px);
            g.drawRect(coordNY,InchesToPixels(coord)+tranche2X4,InchesToPixels(this.controlleur.getToit().LongueurDebordement-2),largeur2X4);
            }
            else{
            g.setColor(Color.LIGHT_GRAY);
            g.fillRect(_2inches_px, InchesToPixels(coord), InchesToPixels(this.controlleur.getToit().LongueurDebordement-2),largeur2X4);
            g.setColor(Color.WHITE);
            g.fillRect(_2inches_px,InchesToPixels(coord)+largeur2X4,InchesToPixels(this.controlleur.getToit().LongueurDebordement-2), tranche2X4);
            g.setColor(Color.BLACK);
            g.drawRect(_2inches_px, InchesToPixels(coord), InchesToPixels(this.controlleur.getToit().LongueurDebordement-2),largeur2X4);
            g.drawRect(_2inches_px,InchesToPixels(coord)+largeur2X4,InchesToPixels(this.controlleur.getToit().LongueurDebordement-2), tranche2X4);
            int coordNY = InchesToPixels(this.controlleur.getCabanon().Largeur + this.controlleur.getToit().LongueurDebordement);
            g.setColor(Color.LIGHT_GRAY);
            g.fillRect(coordNY,InchesToPixels(coord), InchesToPixels(this.controlleur.getToit().LongueurDebordement-2), largeur2X4);
            g.setColor(Color.WHITE);
            g.fillRect(coordNY,InchesToPixels(coord)+largeur2X4,InchesToPixels(this.controlleur.getToit().LongueurDebordement-2),tranche2X4);
            g.setColor(Color.BLACK);
            g.drawRect(coordNY,InchesToPixels(coord), InchesToPixels(this.controlleur.getToit().LongueurDebordement-2), largeur2X4);
            g.drawRect(coordNY,InchesToPixels(coord)+largeur2X4,InchesToPixels(this.controlleur.getToit().LongueurDebordement-2),tranche2X4);   
            }  
        }
        }
        // creation ferme debut
       /*int coordFermeDebut = InchesToPixels(150);
       int debutX1 = coordFermeDebut;
       int debutX2 = coordFermeDebut + InchesToPixels(5);
       int debutX3 = debutX2;
       int debutX4 = debutX1;
       int [] fermeDebutX = {debutX1,debutX2,debutX3,debutX4};
       
       int debutY1 = 0;
       int debutY2 = debutY1;
       int debutY3 = largeurFermes;
       int debutY4 = debutY3;
       int [] fermeDebutY = {debutY1,debutY2,debutY3,debutY4};
       g.drawPolygon(fermeDebutX, fermeDebutY, 4);
       g.drawRect(debutX1, debutY4, debutX2- debutX1, (int)porteAfaux);
       g.drawRect(debutX1, debutY1, debutX2- debutX1, (int)porteAfaux);
      
       //ferme fin
       int coordFermeFin = coordFermeDebut + InchesToPixels(this.controlleur.getProfondeurCabanon());
       int finX1 = coordFermeFin;
       int finX2 = coordFermeFin + InchesToPixels(5);
       int finX3 = finX2;
       int finX4 = finX1;
       int [] fermeFinX = {finX1,finX2,finX3,finX4};
       
       int finY1 = 0;
       int finY2 = debutY1;
       int finY3 = largeurFermes;
       int finY4 = debutY3;
       int [] fermeFinY = {finY1,finY2,finY3,finY4};
       g.drawPolygon(fermeFinX, fermeFinY, 4);
       g.drawRect(finX1, finY4, finX2- finX1, (int)porteAfaux);
       g.drawRect(finX1, finY1, finX2- finX1, (int)porteAfaux);
        //ferme supp 1
        int coordFermeSupp1 = coordFermeDebut - InchesToPixels(this.controlleur.getLongueurDebordement())- InchesToPixels(30);
        int X1 = (int) coordFermeSupp1;
        int X2 = (int) coordFermeSupp1 + InchesToPixels(5);
        int X3 = X2;
        int X4 = X1;
        int [] FermesSuppX = {X1,X2,X3,X4};
        
        int Y1 = 0;
        int Y2 = Y1;
        int Y3 = largeurFermes;
        int Y4 = Y3;
        int [] FermesSuppY = {Y1,Y2,Y3,Y4};
        g.drawPolygon(FermesSuppX, FermesSuppY, 4);
        g.drawRect(X1, Y4, X2- X1, (int)porteAfaux);
        g.drawRect(X1, Y1, X2- X1, (int)porteAfaux);
        
 
        
     //   creation ferme millieux (entre fermes supp)
        int distance = coordFermeFin - coordFermeDebut - (2* InchesToPixels(this.controlleur.getEspacementFermes())-_2inches_px);
        double nombreFermes = distance  / InchesToPixels(this.controlleur.getEspacementFermes());
        int coordFerme = coordFermeDebut;
        
        for (int i = 0; i < nombreFermes; i++) {
        coordFerme += InchesToPixels(this.controlleur.getEspacementFermes());
        int x1 = (int) coordFerme + InchesToPixels(5);
        int x2 = (int) coordFerme + InchesToPixels(10);
        int x3 = x2;
        int x4 = x1;
        int [] FermesX = {x1,x2,x3,x4};
        
        int y1 = 0;
        int y2 = y1;
        int y3 = largeurFermes;
        int y4 = y3;
        int [] FermesY = {y1,y2,y3,y4};
        g.drawPolygon(FermesX, FermesY, 4);
        
        //porte-a-faux
        g.drawRect(x1, y4, x2- x1, (int)porteAfaux);
        g.drawRect(x1, y1, x2- x1, (int)porteAfaux);
        
        //chevrons separation
        g.drawLine(x1, y3 / 2, x2, y3 / 2);  
        
       }

      
       //creation ferme supp 2 
       int coordFermeSupp2 = coordFermeFin + InchesToPixels(35) + InchesToPixels(this.controlleur.getLongueurDebordement());
       int X5 = coordFermeSupp2;
       int X6 = X5 + InchesToPixels(5);
       int X7 = X6;
       int X8 = X5;
       int [] FermeSupp2X = {X5,X6,X7,X8};
       
       int Y5 = 0;
       int Y6 = Y5;
       int Y7 = largeurFermes;
       int Y8 = Y7;
       int [] FermeSupp2Y = {Y5,Y6,Y7,Y8};
       g.drawPolygon(FermeSupp2X, FermeSupp2Y, 4);
       g.drawRect(X5, Y8, X6- X5, (int)porteAfaux);
       g.drawRect(X5, Y5, X6- X5, (int)porteAfaux);
        
       
       // faire l'espacement des entremises
        //Entremise ferme debut
        for (int j = 0; j < nombreEntremise; j++) {
        int Xentremise = (int)coordFermeSupp1;
        int Yentremise = Y1 + InchesToPixels(15) + ((int)espacementEntremises*j);
        int LongueurEntremises = InchesToPixels((int)this.controlleur.getLongueurDebordement());
        g.drawRect(Xentremise, Yentremise, LongueurEntremises + InchesToPixels(35),_2inches_px);
       }
        //Entremise ferme fin
       for (int j = 0; j < nombreEntremise; j++){
       int Xentremise = (int)coordFermeFin + InchesToPixels(5);
       int Yentremise = Y1 + InchesToPixels(15) + ((int)espacementEntremises*j);
       int LongueurEntremises = InchesToPixels((int)this.controlleur.getLongueurDebordement());
       g.drawRect(Xentremise, Yentremise, LongueurEntremises + InchesToPixels(35),_2inches_px);   
        }
        //g.drawPolygon();*/

        
        }

    @Override
    public double getElementWidth() {
        //if(this.toit.OrientationToit ==1){
        return toit.parent.getLargeur() + 3*toit.PorteAFaux+2;
        /*}
        else{
            return toit.parent.getProfondeur() + 2*toit.PorteAFaux+2;
        }*/
    }
    
    @Override
    public double getElementHeight() {
        //if(this.toit.OrientationToit ==1){
        return toit.parent.getProfondeur() + 2*toit.LongueurDebordement+2;
        /*}
        else{
            return toit.parent.getLargeur() + 2*toit.PorteAFaux+2;
        }*/
    }

}
