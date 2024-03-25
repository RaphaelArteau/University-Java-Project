/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ca.ulaval.glo2004.visualizer;

import ca.ulaval.glo2004.cabanon.domaine.plancher.LigneEntremises;
import ca.ulaval.glo2004.cabanon.domaine.plancher.PlancherDTO;
import ca.ulaval.glo2004.preferences.Preferences;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

/**
 *
 * @author raphael
 */
public class AfficheurPlancher extends Afficheur{
    
    private PlancherDTO plancher;
    private boolean preciseLigneEntremiseDragging = false;
    
    public AfficheurPlancher(Visualizer parent) {
        super(parent);
        this.plancher = this.controlleur.getPlancher();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        this.hitzones.removeAll(this.hitzones);
        PlancherDTO plancher = controlleur.getPlancher();

        int m2pouces = InchesToPixels(2);
        
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        
        /*
        g2.setColor(Color.BLACK);
        g2.drawRect(0, 0, TwoInchesInPixel, this.getHeight());
        g2.drawRect(this.getWidth() - TwoInchesInPixel, 0, TwoInchesInPixel, this.getHeight());
        g2.drawRect(0, 0, this.getWidth(), TwoInchesInPixel);
        g2.drawRect(0, this.getHeight() - TwoInchesInPixel, this.getWidth(), TwoInchesInPixel);
        */
        
        int blocsY = this.plancher.DimensionGridBlocPlancher[1];
        int blocsX = this.plancher.DimensionGridBlocPlancher[0];
        
        int longueurMax26EnPixels = InchesToPixels(Preferences.global().getLongueurMax26());
        int originY= InchesToPixels(this.controlleur.getCabanon().Profondeur) - longueurMax26EnPixels;
        ArrayList<Double> coordsSolives = new ArrayList();
        for(int i= 0; i< this.plancher.CoordYSolives.size(); i++){
            coordsSolives.add(this.controlleur.getProfondeurCabanon()- this.plancher.CoordYSolives.get(i));
        }


        ArrayList<LigneEntremises> lignes = this.plancher.LignesEntremises;
        for(LigneEntremises ligne : lignes){
            int x = InchesToPixels(ligne.getCoordXLigneEntremise());
            if(x > 3){
                /*
                double originYEntremise = m2pouces;
                int x2 = x - m2pouces;
                for(int i = coordsSolives.size()-1; i >= 0; i--){
                    Double solive = coordsSolives.get(i);
                    double HeightEntremise = InchesToPixels(solive) - originYEntremise;
                    x2 = x2 < x ? x2 + (m2pouces * 2) : x2 - (m2pouces * 2);
                    g.drawRect(x2, InchesToPixels(originYEntremise), m2pouces, (int) HeightEntremise);
                    originYEntremise = originYEntremise + HeightEntremise + m2pouces;
                }
                */
                double originYEntremise = m2pouces;
                int x2 = x - m2pouces;
                ArrayList<Integer> coordsSolivesPouces = new ArrayList();
                coordsSolivesPouces.add(this.getHeight());
                for(Double d : coordsSolives){
                    coordsSolivesPouces.add(InchesToPixels(d));
                }
                coordsSolivesPouces.add(0);
                for(int i = 0; i < coordsSolivesPouces.size() - 1; i++){
                    int height = coordsSolivesPouces.get(i) - coordsSolivesPouces.get(i+1) - m2pouces;
                    if(i == 0){
                        height = height - m2pouces;
                    }
                    x2 = x2 < x ? x2 + (m2pouces /** 2*/) : x2 - (m2pouces /** 2*/);
                    //g.setColor(Color.ORANGE);
                    //g.fillRect(x2, coordsSolivesPouces.get(i+1) + m2pouces, m2pouces, height);
                    //g.setColor(Color.BLACK);

                    if(this.preciseLigneEntremiseDragging){
                        /*
                        On cree une hitzone pour la fenetre et offrir le drag and drop
                        */
                        int x1 = x2;
                        int xSize = m2pouces;
                        int x_2 = x1 + xSize;
                        int y1 = coordsSolivesPouces.get(i+1) + m2pouces;
                        int ySize = height;
                        int y2 = x1 + ySize;

                        this.hitzones.add(new Hitzone(x1, x_2, y1, y2, ligne.getUUID(), Hitzone.TypeAccessoire.LigneEntremise));

                        Color cb = g.getColor();
                        g.setColor(Color.BLUE);
                        g.fillRect(x1, y1, xSize, ySize);
                        g.setColor(cb);

                        /*
                        Fin de la creation de la hitzone, on peut de-commenter en haut pour la visualiser
                        */
                    }
                    
                    Color c = g.getColor();
                    if(ligne.isSelected()){
                        g.setColor(Color.ORANGE);
                    }else{
                        g.setColor(Color.WHITE);
                    }
                    g.fillRect(x2, coordsSolivesPouces.get(i+1) + m2pouces, m2pouces, height);
                    g.setColor(c);
                    g.drawRect(x2, coordsSolivesPouces.get(i+1) + m2pouces, m2pouces, height);
                    //g.setColor(Color.WHITE);
                }

                if(!this.preciseLigneEntremiseDragging){
                    /*
                    On cree une hitzone pour la fenetre et offrir le drag and drop
                    */
                    int x1 = x - m2pouces;
                    int xSize = m2pouces * 3;
                    int x_2 = x1 + xSize;
                    int y1 = m2pouces;
                    int ySize = this.getHeight() - (m2pouces * 2);
                    int y2 = x1 + ySize;

                    this.hitzones.add(new Hitzone(x1, x_2, y1, y2, ligne.getUUID(), Hitzone.TypeAccessoire.LigneEntremise));

                    //Color cb = g.getColor();
                    //g.setColor(Color.BLUE);
                    //g.fillRect(x1, y1, xSize, ySize);
                    //g.setColor(cb);

                    /*
                    Fin de la creation de la hitzone, on peut de-commenter en haut pour la visualiser
                    */
                }


            }
        }


        for(int ligne = 0; ligne < blocsY; ligne++){
            int originX = 0;
            int height;
            if(originY < 0){
                height = InchesToPixels(this.controlleur.getCabanon().Profondeur) - (longueurMax26EnPixels * (blocsY - 1));
                originY = 0;
            }else{
                height = longueurMax26EnPixels;
            }
            for(int bloc = 0; bloc < blocsX; bloc++){
                int width = Math.min(longueurMax26EnPixels, InchesToPixels(this.controlleur.getCabanon().Largeur) - originX);
                //Outer border
                g2.setColor(Color.WHITE);
                g2.fillRect(originX,originY, width, m2pouces);
                g2.fillRect(originX, originY + height - m2pouces, width, m2pouces);
                g2.fillRect(originX,originY + m2pouces, m2pouces, (int)(height - (m2pouces * 2)));
                g2.fillRect(originX + width - m2pouces,originY + m2pouces, m2pouces, (int)(height - (m2pouces * 2)));
                g2.setColor(Color.BLACK);
                g2.drawRect(originX,originY, width, m2pouces);
                g2.drawRect(originX, originY + height - m2pouces, width, m2pouces);
                g2.drawRect(originX,originY + m2pouces, m2pouces, (int)(height - (m2pouces * 2)));
                g2.drawRect(originX + width - m2pouces,originY + m2pouces, m2pouces, (int)(height - (m2pouces * 2)));
                
                //Inner border
                //g2.drawRect(originX + m2pouces,originY + m2pouces, width - (m2pouces * 2), height - (m2pouces * 2));

                double distanceSolive = InchesToPixels(this.plancher.EspacementSolives);

                
                int c = 0;
                for(Double coord : coordsSolives){
                    c = InchesToPixels(coord.doubleValue());
                    g2.setColor(Color.WHITE);
                    g2.fillRect(originX + m2pouces, c, width- (m2pouces * 2), m2pouces);
                    g2.setColor(Color.BLACK);
                    g2.drawRect(originX + m2pouces, c, width- (m2pouces * 2), m2pouces);
                }

                
                /*
                double originXEntremise = originX + m2pouces;
                double YEntremise = originY + (height - (m2pouces * 2)) / 2;
                boolean up = true;
                YEntremise = YEntremise + m2pouces;
                while(originXEntremise < originX + width - m2pouces){
                    int widthEntremise;
                    if( originXEntremise + distanceSolive > originX + width - m2pouces){
                        widthEntremise = (int) ((originX + width - m2pouces) - originXEntremise);
                    }else{
                        widthEntremise = (int) distanceSolive;
                    }
                    g2.drawRect((int) originXEntremise, (int) YEntremise, widthEntremise, m2pouces);
                    originXEntremise = originXEntremise + distanceSolive + m2pouces;
                    if(up){
                        YEntremise = YEntremise - (m2pouces * 2);
                    }else{
                        YEntremise = YEntremise + (m2pouces * 2);
                    }
                    up = !up;
                }
                */
                originX = originX + longueurMax26EnPixels;
                
            }
            originY = originY - longueurMax26EnPixels;
        }
        
        double zoom = this.parent.getZoom();
        //g2.scale(zoom, zoom);
        
        g2.dispose();
    }

    @Override
    public double getElementWidth() {
        return this.plancher.Largeur+1;
    }

    
    @Override
    public double getElementHeight() {
        return this.plancher.Profondeur+1;
    }
    
}
