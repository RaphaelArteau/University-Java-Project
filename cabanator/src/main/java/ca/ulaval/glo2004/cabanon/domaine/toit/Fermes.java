/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ca.ulaval.glo2004.cabanon.domaine.toit;
import ca.ulaval.glo2004.preferences.Preferences;

import java.util.UUID;
import java.util.ArrayList;

/**
 *
 * @author Charles
 */
public class Fermes {
    
    private UUID id;
    private int orientationToit;
    private ArrayList<Poincons> Poincons;
    private double Entrait;
    private double coordFermeX;
    public Toit parent;
    public double angle;
    
    public static int Nord = 1;
    public static int Est = 2; 
    
    public Fermes (Toit parent, int orientationToit, ArrayList<Poincons> poincons, double coordFerme){
        this.parent = parent;
        this.id = UUID.randomUUID();
        this.orientationToit = parent.getOrientationToit();
        this.angle = parent.getAngleToit();
        this.Entrait = parent.getParent().getLargeur();
        this.Poincons = poincons;
        this.coordFermeX = coordFerme;
    }
    
    public UUID getUUID(){
        return this.id;
    } 
    
    public double getCoordFermesX(){
        return this.coordFermeX;
    }
    
    public void setCoordFermesX(double coord){
        this.coordFermeX = coord;
    }
    
}
