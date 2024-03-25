/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ca.ulaval.glo2004.visualizer;

import ca.ulaval.glo2004.cabanon.domaine.Controlleur;
import java.util.ArrayList;
import javax.swing.JPanel;

/**
 *
 * @author raphael
 */
public abstract class Afficheur extends JPanel {
        
    private double positionX = 0;
    private double positionY = 0;
    protected Visualizer parent;
    protected Controlleur controlleur;
    protected ArrayList<Hitzone> hitzones = new ArrayList();
    
    protected Afficheur(Visualizer parent){
        this.parent = parent;
        this.controlleur = parent.getControlleur();
    }
    
    public double[] getPositions(){
        return new double[]{this.positionX, this.positionY};
    }
    
    public int getPositionX(){
        return (int) Math.round(this.positionX);
    }
    
    public int getPositionY(){
        return (int) Math.round(this.positionY);
    }
    
    public void setPositions(double[] positions){
        this.positionX = positions[0];
        this.positionY = positions[1];
    }
    
    final public int InchesToPixels(double inches){
        return (int) (inches / this.parent.getRatio());
    }
    
    final public double PixelsToInches(int pixels){
        return (pixels * this.parent.getRatio());
    }

    public Hitzone accessoireClicked(int x, int y){
        for(Hitzone hitzone : this.hitzones){
            if(hitzone.isClicked(x, y)){
                return hitzone;
            }
        }
        return null;
    }
    


    abstract public double getElementWidth();
    abstract public double getElementHeight();
 
    
    
    
}
