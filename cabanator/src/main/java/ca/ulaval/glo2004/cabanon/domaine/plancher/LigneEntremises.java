package ca.ulaval.glo2004.cabanon.domaine.plancher;

import java.util.UUID;

public class LigneEntremises {
    private UUID uuid;
    private double coordXLigneEntremise;
    private boolean conflit;
    private boolean selected;
    
    public LigneEntremises(double coordX){
        this.uuid = UUID.randomUUID();
        this.coordXLigneEntremise = coordX;
        this.conflit = false;
    }
    
    public UUID getUUID(){
        return this.uuid;
    }
    
    public double getCoordXLigneEntremise(){
        return this.coordXLigneEntremise;
    }
    
    public void setCoordXLigneEntremise(double coordX){
        if(coordX>= 4){
            this.coordXLigneEntremise = coordX;
        }
    }
    
    public boolean getConflit(){
        return this.conflit;
    }
    
    public void setConflit(boolean conflit){
        this.conflit = conflit;
    }
    public void setIdLigneEntremise(UUID id){
        this.uuid = id;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    
}
