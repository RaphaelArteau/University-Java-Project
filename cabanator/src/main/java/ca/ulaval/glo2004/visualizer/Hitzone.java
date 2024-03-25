/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ca.ulaval.glo2004.visualizer;

import java.util.UUID;

/**
 *
 * @author raphael
 */
public class Hitzone {

    private int x1;
    private int x2;
    private int y1;
    private int y2;
    private UUID reference;
    private TypeAccessoire type;
    public static enum TypeAccessoire{
        LigneEntremise, Accessoire, EntremiseMur
    }

    public Hitzone(int x1, int x2, int y1, int y2, UUID reference, TypeAccessoire type){
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
        this.reference = reference;
        this.type = type;
    }

    public boolean isClicked(int clickedX, int clickedY){
        if(this.x1 < clickedX && clickedX < this.x2 && this.y1 < clickedY && clickedY < this.y2){
           return true;
        }else{
           return false;
        }
    }

    public TypeAccessoire getType(){
        return this.type;
    }


    public UUID getReference() {
        return reference;
    }


}
