/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ca.ulaval.glo2004.visualizer;

import java.awt.Graphics;
import ca.ulaval.glo2004.cabanon.domaine.toit.ToitDTO;
import java.awt.Color;
/**
 *
 * @author Maxence
 */
public class AfficheurToitCote extends Afficheur {
    private ToitDTO toit;
    
    public AfficheurToitCote(Visualizer parent) {
        super(parent);
        this.toit = this.controlleur.getToit();

    }
        @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int _2inches_px = InchesToPixels(2);
        double longueur;
        double largeur;
        double hauteur;
                if(toit.OrientationToit ==1){
            longueur = this.controlleur.getProfondeurCabanon();
            largeur = this.controlleur.getLargeurCabanon();
            hauteur = this.controlleur.getLargeurCabanon()/2 + toit.PorteAFaux/ Math.tan(Math.toRadians(toit.AngleToit/2));
        }
        else{
            longueur = this.controlleur.getLargeurCabanon();
            largeur = this.controlleur.getProfondeurCabanon();
            hauteur = this.controlleur.getProfondeurCabanon()/2 + toit.PorteAFaux/ Math.tan(Math.toRadians(toit.AngleToit/2));
        }
                //On dessine les fermes
        for(Double coord : this.controlleur.getCoordFermesOrdo()){
            g.setColor(Color.WHITE);
            g.fillRect(InchesToPixels(coord), 0,_2inches_px, InchesToPixels(hauteur));
            g.fillRect(InchesToPixels(3), 0, InchesToPixels(toit.CoordFermesOrdo.get(1)-3),2*_2inches_px);
            g.fillRect(InchesToPixels(toit.CoordFermesOrdo.get(toit.CoordFermesOrdo.size()-2)+2), 0, 
                    InchesToPixels(toit.CoordFermesOrdo.get(toit.CoordFermesOrdo.size()-1)-toit.CoordFermesOrdo.get(toit.CoordFermesOrdo.size()-2)-2),2*_2inches_px);
            g.setColor(Color.BLACK);
            g.drawRect(InchesToPixels(coord), 0,_2inches_px, InchesToPixels(hauteur));
            g.drawRect(InchesToPixels(3), 0, InchesToPixels(toit.CoordFermesOrdo.get(1)-3),2*_2inches_px);
            g.drawRect(InchesToPixels(toit.CoordFermesOrdo.get(toit.CoordFermesOrdo.size()-2)+2), 0, 
                    InchesToPixels(toit.CoordFermesOrdo.get(toit.CoordFermesOrdo.size()-1)-toit.CoordFermesOrdo.get(toit.CoordFermesOrdo.size()-2)-2),2*_2inches_px);
        }
        // on dessine les entremises
        int tranche2X4 = InchesToPixels(2*(Math.sin(Math.toRadians(90-this.controlleur.getToit().AngleToit/2))));
        int largeur2X4 = InchesToPixels(4*(Math.sin(Math.toRadians((this.controlleur.getToit().AngleToit/2)))));
        for(double coord: this.controlleur.getCoordEntremisesOrdo()){
        if(coord > this.controlleur.getToit().PorteAFaux+largeur/2){
            int coordY = InchesToPixels((coord-(largeur/2+toit.PorteAFaux))/Math.tan(Math.toRadians(toit.AngleToit/2)));
            g.setColor(Color.WHITE);
            g.fillRect(InchesToPixels(3),coordY, InchesToPixels(toit.CoordFermesOrdo.get(1)-3), tranche2X4);
            g.setColor(Color.LIGHT_GRAY);
            g.fillRect(InchesToPixels(3),coordY+tranche2X4, InchesToPixels(toit.CoordFermesOrdo.get(1)-3), largeur2X4);
            g.setColor(Color.BLACK);
            g.drawRect(InchesToPixels(3),coordY, InchesToPixels(toit.CoordFermesOrdo.get(1)-3), tranche2X4);
            g.drawRect(InchesToPixels(3),coordY+tranche2X4, InchesToPixels(toit.CoordFermesOrdo.get(1)-3), largeur2X4);
            int coordX = InchesToPixels(toit.CoordFermesOrdo.get(toit.CoordFermesOrdo.size()-2)+2);
            int longEnte = InchesToPixels(toit.CoordFermesOrdo.get(toit.CoordFermesOrdo.size()-1)-toit.CoordFermesOrdo.get(toit.CoordFermesOrdo.size()-2)-2);
            g.setColor(Color.WHITE);
            g.fillRect(coordX,coordY, longEnte, tranche2X4);
            g.setColor(Color.LIGHT_GRAY);
            g.fillRect(coordX,coordY+tranche2X4, longEnte, largeur2X4);
            g.setColor(Color.BLACK);
            g.drawRect(coordX,coordY, longEnte, tranche2X4);
            g.drawRect(coordX,coordY+tranche2X4, longEnte, largeur2X4);
            
        }
        }
    }
        @Override
    public double getElementWidth() {
        if(toit.OrientationToit == 1){
        return this.controlleur.getProfondeurCabanon() + 2*toit.LongueurDebordement+2;}
        else{return this.controlleur.getLargeurCabanon() + 2*toit.LongueurDebordement+2;}
    }

    @Override
    public double getElementHeight() {
        return toit.PorteAFauxHauteur +8;
    }
}
