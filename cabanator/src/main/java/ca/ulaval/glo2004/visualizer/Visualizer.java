/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package ca.ulaval.glo2004.visualizer;

import ca.ulaval.glo2004.cabanon.domaine.Controlleur;
import ca.ulaval.glo2004.cabanon.domaine.murs.AccessoireDTO;
import ca.ulaval.glo2004.cabanon.domaine.murs.EntremiseMurDTO;
import ca.ulaval.glo2004.cabanon.domaine.murs.Mur;
import ca.ulaval.glo2004.cabanon.domaine.plancher.LigneEntremiseDTO;
import ca.ulaval.glo2004.gui.MainWindow;
import ca.ulaval.glo2004.preferences.Preferences;
import static ca.ulaval.glo2004.visualizer.Hitzone.TypeAccessoire.Accessoire;
import static ca.ulaval.glo2004.visualizer.Hitzone.TypeAccessoire.EntremiseMur;
import static ca.ulaval.glo2004.visualizer.Hitzone.TypeAccessoire.LigneEntremise;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;
import resources.UnitConverter;

/**
 *
 * @author raphael
 */
public class Visualizer extends javax.swing.JPanel {

    final static public int VUE_MUR_NORD = 1;
    final static public int VUE_MUR_SUD = 2;
    final static public int VUE_MUR_EST = 3;
    final static public int VUE_MUR_OUEST = 4;
    final static public int VUE_PLANCHER = 5;
    final static public int VUE_TOIT_DESSUS = 6;
    final static public int VUE_TOIT_FACE = 7;
    final static public int VUE_TOIT_COTE = 8;
    static public int DRAG_MOVE = 1;
    static public int DRAG_ACCESSOIRE = 2;
    
    private int vueActuelle;
    private Controlleur controlleur = null;
    private int initialPadding = 150;
    private double zoom; 
    private Afficheur frame;
    private double ratioInchPerPixel;
    private boolean isDragging = false;
    private ArrayList<Integer> previousDragCoordinates = null;
    private final JPanel content;
    private final boolean showScale = false;
    private MainWindow parent;


    private int BaseWidth;
    private int BaseHeight;
    private long lastScrollTime = 0;
    private int lastScrollWidth;
    private int lastScrollHeight;
    private int lastX;
    private int lastY;
    private float pourcentageXInFrame;
    private float pourcentageYInFrame;

    private boolean showGrid = false;
    private boolean magneticGrid = false;
    private ArrayList<Integer> gridPosX = new ArrayList<>();
    private ArrayList<Integer> gridPosY = new ArrayList<>();
    
    
    /**
     * Creates new form Visualizer
     */
    public Visualizer() {
        initComponents();
        this.content = new JPanel();
        this.content.setLayout(null);
        this.content.setOpaque(false);
        visualizerScale.setVisible(this.showScale);
        VueMursSelector.setVisible(false);
        VueToitSelector.setVisible(false);
    }
    
    public void positionContent(){
        this.remove(this.content);
        Border border = this.getBorder();
        Insets insets = border.getBorderInsets(this);
        int width = this.getWidth() - insets.right - insets.left;
        int height = this.getHeight() - insets.bottom - insets.top;
        this.content.setSize(new Dimension(width, height));
        this.content.setBounds(insets.top, insets.left, width, height);
        this.add(this.content);
    }
    
    /*
    
    START - GETTERS / SETTERS
    
    */

    public int getVueActuelle() {
        return vueActuelle;
    }

    public void setVueActuelle(int vueActuelle) {
        if(this.controlleur == null){
            return;
        }
        int previousVue = this.vueActuelle;
        
        this.vueActuelle = vueActuelle;
        this.setZoom(1);

        double previousWidth = 0;
        double previousHeight = 0;
        if(this.frame != null){
            previousWidth = this.frame.getElementWidth();
            previousHeight = this.frame.getElementHeight();
            this.content.remove((Component) this.frame);
            this.content.repaint();
        }
        this.frame = null;
        String t = "";

        VueToitSelector.setVisible(false);
        VueMursSelector.setVisible(false);
        if(this.vueActuelle == VUE_PLANCHER){
            t = "Plancher";
            this.frame = new AfficheurPlancher(this);
        }else if(this.vueIsMur()){
            Mur.orientations orientation = null;
            VueMursSelector.setVisible(true);
            switch(this.vueActuelle){
                case VUE_MUR_NORD:
                    t = "Mur nord";
                    orientation = Mur.orientations.Nord;
                    break;
                case VUE_MUR_SUD:
                    t = "Mur sud";
                    orientation = Mur.orientations.Sud;
                    break;
                case VUE_MUR_EST:
                    t = "Mur est";
                    orientation = Mur.orientations.Est;
                    break;
                case VUE_MUR_OUEST:
                    t = "Mur ouest";
                    orientation = Mur.orientations.Ouest;
                    break;
            }
            this.frame = new AfficheurMur(this, orientation);
        }
        else if (this.vueActuelle == VUE_TOIT_DESSUS){
            VueToitSelector.setVisible(true);
            t = "Toit dessus";
            this.frame = new AfficheurToitDessus(this);
        }
        else if (this.vueActuelle == VUE_TOIT_FACE){
            VueToitSelector.setVisible(true);
            t = "Toit face";
            this.frame = new AfficheurToitFace(this);
        }
        else if (this.vueActuelle == VUE_TOIT_COTE){
            VueToitSelector.setVisible(true);
            t = "Toit côté";
            this.frame = new AfficheurToitCote(this);
        }
        else{
            t = "Default";
            this.frame = new AfficheurPlancher(this);
        }
        
        if (this.frame != null) {
            this.frame.setOpaque(false);
            this.centerFrame();
            //this.frame.setBorder(BorderFactory.createDashedBorder(Color.BLACK));
            //this.frame.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            this.content.add((Component) this.frame);
        }
    }


    public void centerFrame(){
        this.setZoom(1);
        double ElementWidthInInches = this.frame.getElementWidth();
        double ElementHeightInInches = this.frame.getElementHeight();

        int AfficheurWidthInPixels = this.getWidth();
        int AfficheurHeightInPixels = this.getHeight();

        int MaxFrameWidthInAfficheur = AfficheurWidthInPixels - this.getInitialPadding();
        int MaxFrameHeightInAfficheur = AfficheurHeightInPixels - this.getInitialPadding();

        // What would be the width of the frame if its height was set to the max height available ? 
        double WidthOfFrameIfHeightIsMaxInPixel = MaxFrameHeightInAfficheur * ElementWidthInInches / ElementHeightInInches;

        // What would be the height of the frame if its width was set to the max width available ? 
        double HeightOfFrameIfWidthIsMaxInPixel = MaxFrameWidthInAfficheur * ElementHeightInInches / ElementWidthInInches;

        /*
            Si la WidthOfFrameIfHeightIsMaxInPixel est plus petite que le MaxFrameWidthInAfficheur, 
            ça veut dire que la hauteur serait egal a MaxFrameHeightInAfficheur, donc on aurait le 
            meilleur ratio initial 

            Sinon, ça veut dire que le HeightOfFrameIfWidthIsMaxInPixel est plus petit que  le 
            MaxFrameHeightInAfficheur
        */
        double FrameWidth = 0;
        double FrameHeight = 0;
        if(WidthOfFrameIfHeightIsMaxInPixel <= MaxFrameWidthInAfficheur){
           FrameWidth = WidthOfFrameIfHeightIsMaxInPixel;
           FrameHeight = MaxFrameHeightInAfficheur;
        }else{
           FrameWidth = MaxFrameWidthInAfficheur;
           FrameHeight = HeightOfFrameIfWidthIsMaxInPixel;
        }
        int w = (int) Math.ceil(FrameWidth);
        int h = (int) Math.ceil(FrameHeight);
        Dimension size = new Dimension(w, h);
        this.frame.setSize(size);
        this.frame.setPreferredSize(size);

        int paddingLeft = (AfficheurWidthInPixels - w) / 2;
        int paddingTop = (AfficheurHeightInPixels - h) / 2;
        
        this.frame.setPositions(new double[]{paddingLeft, paddingTop});

        this.calculateRatio();
        this.BaseWidth = w;
        this.BaseHeight = h;
        //this.originalHalfFrame = h / 2;
        this.frame.setBounds(this.frame.getPositionX(), this.frame.getPositionY(), w, h);
        this.frame.repaint();
    }
    
    private void calculateRatio(){
        double ElementWidthInInches = this.frame.getElementWidth();
        int FrameWidthInPixel = this.frame.getWidth();     
        this.ratioInchPerPixel = ElementWidthInInches / FrameWidthInPixel;
    }

    public void zoomFrame(MouseWheelEvent evt){
        Rectangle bounds = this.frame.getBounds();
        long time = System.currentTimeMillis();
        if(time > this.lastScrollTime + 100){
            this.lastX = evt.getX();
            this.lastY = evt.getY();
            int lastScrollWidth = this.frame.getWidth();
            int lastScrollHeight = this.frame.getHeight();
            int PositionXInFrame = this.lastX - bounds.x;
            this.pourcentageXInFrame = (float) PositionXInFrame / lastScrollWidth;
            int PositionYInFrame = this.lastY - bounds.y;
            this.pourcentageYInFrame = (float) PositionYInFrame / lastScrollHeight;
        }
        this.lastScrollTime = time;
        double change = evt.getPreciseWheelRotation()/50;
        double newZoom = this.getZoom() - change;
        if(this.setZoom(newZoom)){
            int w = (int) Math.round(this.BaseWidth * newZoom);
            int h = (int) Math.round(this.BaseHeight * newZoom);
            int newBoundX =  this.lastX - Math.round(w * this.pourcentageXInFrame);
            int newBoundY =  this.lastY - Math.round(h * this.pourcentageYInFrame);

            this.frame.setPositions(new double[]{newBoundX, newBoundY});
            this.frame.setBounds(this.frame.getPositionX(), this.frame.getPositionY(), w, h);
            this.calculateRatio();
            this.frame.repaint();
            this.repaint();
        }
    }

    public void moveFrame(MouseEvent evt){
        if(!this.isDragging){
            this.isDragging = true;
            this.previousDragCoordinates = new ArrayList<Integer>();
            this.previousDragCoordinates.add( evt.getX());
            this.previousDragCoordinates.add(evt.getY());
        }else{
            int previousX = this.previousDragCoordinates.get(0);
            int previousY = this.previousDragCoordinates.get(1);
            int diffX = evt.getX() - previousX;
            int diffY = evt.getY() - previousY;
            if(this.selectedElement == null){
                Rectangle bounds = this.frame.getBounds();
                double[] positions = this.frame.getPositions();
                this.frame.setPositions(new double[]{positions[0] + diffX, positions[1] + diffY});
                this.frame.setBounds(this.frame.getPositionX(), this.frame.getPositionY(), bounds.width, bounds.height);
            }else{
                double X = this.frame.PixelsToInches(diffX);
                double Y = this.frame.PixelsToInches(diffY);
                switch(this.selectedElementType){
                    case Accessoire:
                        AccessoireDTO accessoire = this.controlleur.getAccessoireFromUUID(this.selectedElement);
                        this.controlleur.deplacerAccessoire(accessoire.orientation, accessoire.Id, UnitConverter.fractionator(accessoire.CoordAccessoireX + X), UnitConverter.fractionator(accessoire.CoordAccessoireY - Y), false);
                        break;
                    case LigneEntremise:
                        LigneEntremiseDTO ligne = this.controlleur.getLigneEntremises(this.selectedElement);
                        this.controlleur.deplacerLigneEntremise(this.selectedElement, UnitConverter.fractionator(ligne.CoordXLigneEntremise + X), false);
                        break;
                    case EntremiseMur:
                        Rectangle bounds = this.frame.getBounds();
                        EntremiseMurDTO entremise = this.controlleur.getEntremiseMur(this.selectedElement);
                        this.controlleur.deplacerEntremiseMur(entremise.orientation, this.selectedElement, UnitConverter.fractionator(this.frame.PixelsToInches(evt.getX() - bounds.x)), UnitConverter.fractionator(this.frame.PixelsToInches(this.getHeight() - evt.getY() - bounds.y)), false);
                        break;
                }
                this.frame.repaint();
                this.parent.updatePane(selectedElement);
            }
            this.previousDragCoordinates.set(0, evt.getX());
            this.previousDragCoordinates.set(1, evt.getY());
        }
        this.repaint();
    }
    
    public void stopMovingFrame(){
        this.isDragging = false;
        this.previousDragCoordinates = null;
        if(this.selectedElement != null){
            switch(this.selectedElementType){
                case Accessoire:
                    AccessoireDTO accessoire = this.controlleur.getAccessoireFromUUID(this.selectedElement);
                    this.controlleur.deplacerAccessoire(accessoire.orientation, selectedElement, UnitConverter.fractionator(accessoire.CoordAccessoireX), UnitConverter.fractionator(accessoire.CoordAccessoireY));
                    break;
                case LigneEntremise:
                    LigneEntremiseDTO ligne = this.controlleur.getLigneEntremises(selectedElement);
                    this.controlleur.deplacerLigneEntremise(selectedElement, UnitConverter.fractionator(ligne.CoordXLigneEntremise) );
                    break;
                case EntremiseMur:
                    EntremiseMurDTO entremise = this.controlleur.getEntremiseMur(selectedElement);
                    this.controlleur.deplacerEntremiseMur(entremise.orientation, entremise.Id, UnitConverter.fractionator(entremise.CoordEntremiseX), UnitConverter.fractionator(entremise.CoordEntremiseY));
                    break;
            }   
        }
        if(this.magneticGrid && this.showGrid && this.selectedElement != null){
            Rectangle bounds = this.frame.getBounds();
            double posX;
            double posY;
            int closestX = -1;
            int closestY = -1;
            switch(this.selectedElementType){
                case Accessoire:
                    AccessoireDTO accessoire = this.controlleur.getAccessoireFromUUID(this.selectedElement);
                    int CoordAccessoireX_px = this.frame.InchesToPixels(accessoire.CoordAccessoireX);
                    int CoordAccessoireY_px = this.frame.InchesToPixels(accessoire.CoordAccessoireY);
                    for(int x = 1; x <= this.gridPosX.size(); x++){
                        int Left_xRelatedToFrame = this.gridPosX.get(x - 1) - bounds.x;
                        int Right_xRelatedToFrame = this.gridPosX.get(x) - bounds.x;
                        if(Left_xRelatedToFrame < CoordAccessoireX_px && Right_xRelatedToFrame > CoordAccessoireX_px){
                            int diffLeft = CoordAccessoireX_px - Left_xRelatedToFrame;
                            int diffRight = Right_xRelatedToFrame - CoordAccessoireX_px;
                            if(diffLeft < diffRight){
                                closestX = Left_xRelatedToFrame;
                            }else{
                                closestX = Right_xRelatedToFrame;
                            }
                            break;
                        }
                    }

                    for(int x = 1; x < this.gridPosY.size(); x++){
                        int top_yRelatedToFrame = this.gridPosY.get(x - 1) - bounds.y;
                        int bottom_yRelatedToFrame = this.gridPosY.get(x) - bounds.y;
                        if(top_yRelatedToFrame < CoordAccessoireY_px && bottom_yRelatedToFrame > CoordAccessoireY_px){
                            int diffTop = CoordAccessoireY_px - top_yRelatedToFrame;
                            int diffRight = bottom_yRelatedToFrame - CoordAccessoireY_px;
                            if(diffTop < diffRight){
                                closestY = top_yRelatedToFrame;
                            }else{
                                closestY = bottom_yRelatedToFrame;
                            }
                            break;
                        }
                    }
                    //posY = accessoire.CoordAccessoireY;
                    if(closestX != -1){
                        posX = this.frame.PixelsToInches(closestX);
                    }else{
                        posX = accessoire.CoordAccessoireX;
                    }
                    if(closestY != -1){
                        posY = this.frame.PixelsToInches(closestY);
                    }else{
                        posY = accessoire.CoordAccessoireY;
                    }
                    this.controlleur.deplacerAccessoire(accessoire.orientation, accessoire.Id, UnitConverter.fractionator(posX), UnitConverter.fractionator(posY));
                    break;
                case LigneEntremise:
                    //LigneEntremiseDTO ligne = this.controlleur.getLigneEntremises(this.selectedElement);
                    //this.controlleur.deplacerLigneEntremise(this.selectedElement, UnitConverter.fractionator(ligne.CoordXLigneEntremise + X));
                    break;
                case EntremiseMur:
                    EntremiseMurDTO entremise = this.controlleur.getEntremiseMur(this.selectedElement);
                    CoordAccessoireY_px = this.frame.InchesToPixels(entremise.CoordEntremiseY);
                    for(int x = 1; x < this.gridPosY.size(); x++){
                        int top_yRelatedToFrame = this.gridPosY.get(x - 1) - bounds.y;
                        int bottom_yRelatedToFrame = this.gridPosY.get(x) - bounds.y;
                        if(top_yRelatedToFrame < CoordAccessoireY_px && bottom_yRelatedToFrame > CoordAccessoireY_px){
                            int diffTop = CoordAccessoireY_px - top_yRelatedToFrame;
                            int diffBottom = bottom_yRelatedToFrame - CoordAccessoireY_px;
                            if(diffTop < diffBottom){
                                closestY = top_yRelatedToFrame;
                            }else{
                                closestY = bottom_yRelatedToFrame;
                            }
                            break;
                        }
                    }
                    if(closestY != -1){
                        posY = this.frame.PixelsToInches(closestY);
                    }else{
                        posY = entremise.CoordEntremiseY;
                    }
                    this.controlleur.deplacerEntremiseMur(entremise.orientation, selectedElement, UnitConverter.fractionator(entremise.CoordEntremiseX), UnitConverter.fractionator(posY));
                    break;
            }
            this.frame.repaint();
            this.parent.updatePane(selectedElement);
        }
        this.controlleur.unselectAccessories();
        this.selectedElement = null;
        this.selectedElementType = null;
        this.frame.repaint();
    }
    
    public double getZoom() {
        return zoom;
    }

    public boolean setZoom(double zoom) {
        if(zoom < 0.05){
            return false;
        }
        this.zoom = zoom;
        SliderZoomShow.setText("Zoom : "+(Math.round(this.zoom * 10000)/100)+"%");
        this.updateScale();
        return true;
    }

    public int getInitialPadding() {
        return initialPadding;
    }

    public void setInitialPadding(int initialPadding) {
        this.initialPadding = initialPadding;
    }

    public Controlleur getControlleur() {
        return this.controlleur;
    }

    public void setControlleur(Controlleur controlleur) {
        this.controlleur = controlleur;
    }
    
    /*
    
    DONE - GETTERS / SETTERS
    
    */

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(this.showGrid){
            this.gridPosX.clear();
            this.gridPosY.clear();


            Rectangle bounds = this.frame.getBounds();
            g.setColor(Color.LIGHT_GRAY);
            int startX = bounds.x;
            int px = this.frame.InchesToPixels(Preferences.global().getGridSize());
            for(int x = startX; x > 0; x -= px){
                this.gridPosX.add(x);
                g.drawLine(x, 0, x, this.getHeight());
            }
            for(int x = startX; x < this.getWidth(); x += px){
                this.gridPosX.add(x);
                g.drawLine(x, 0, x, this.getHeight());
            }


            int startY = bounds.y;
            for(int y = startY; y > 0; y -= px){
                this.gridPosY.add(y);
                g.drawLine(0, y, this.getWidth(), y);
            }
            for(int y = startY; y < this.getWidth(); y += px){
                this.gridPosY.add(y);
                g.drawLine(0, y, this.getWidth(), y);
            }

        }
    }
    
    
    private void updateScale(){
        if(this.showScale) visualizerScale.repaint();
    }
    
    
    /*
    
    Functions utilitaires
    
    */
    
    
    final private boolean vueIsMur(){
        ArrayList<Integer> vuesMur = new ArrayList<Integer>(Arrays.asList(VUE_MUR_NORD, VUE_MUR_SUD, VUE_MUR_EST, VUE_MUR_OUEST));
        return vuesMur.contains(this.vueActuelle);
    }
  
    
    final public double getRatio(){
        return this.ratioInchPerPixel;
    }
    
    
    public void repaintVue(){
        if(this.frame != null && !this.isDragging){
            this.setVueActuelle(this.getVueActuelle());
            //this.content.repaint();
            //this.frame.repaint();
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        VueSelector = new javax.swing.JPanel();
        BouttonPlancher = new javax.swing.JButton();
        BouttonMur = new javax.swing.JButton();
        BouttonToit = new javax.swing.JButton();
        VueMursSelector = new javax.swing.JPanel();
        VueMurSud = new javax.swing.JButton();
        VueMurNord = new javax.swing.JButton();
        VueMurEst = new javax.swing.JButton();
        VueMurOuest = new javax.swing.JButton();
        VueToitSelector = new javax.swing.JPanel();
        VueToitDessus = new javax.swing.JButton();
        VueToitFace = new javax.swing.JButton();
        VueToitCote = new javax.swing.JButton();
        AfficheurToolbar = new javax.swing.JPanel();
        GrillageCheckbox = new javax.swing.JCheckBox();
        SliderZoomShow = new javax.swing.JLabel();
        centerButton = new javax.swing.JButton();
        GrillageMag = new javax.swing.JCheckBox();
        visualizerScale = new ca.ulaval.glo2004.visualizer.visualizerScale();

        setCursor(new java.awt.Cursor(java.awt.Cursor.MOVE_CURSOR));
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                formMousePressed(evt);
            }
        });

        VueSelector.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        VueSelector.setToolTipText("");
        VueSelector.setName("VueSelector"); // NOI18N

        BouttonPlancher.setText("Plancher");
        BouttonPlancher.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                BouttonPlancherMousePressed(evt);
            }
        });

        BouttonMur.setText("Mur");
        BouttonMur.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                BouttonMurMousePressed(evt);
            }
        });

        BouttonToit.setText("Toit");
        BouttonToit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                BouttonToitMousePressed(evt);
            }
        });

        VueMurSud.setText("Sud");
        VueMurSud.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BouttonVueMurSud(evt);
            }
        });

        VueMurNord.setText("Nord");
        VueMurNord.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                VueMurNordMousePressed(evt);
            }
        });
        VueMurNord.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BouttonVueMurNord(evt);
            }
        });

        VueMurEst.setText("Est");
        VueMurEst.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BouttonVueMurEst(evt);
            }
        });

        VueMurOuest.setText("Ouest");
        VueMurOuest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BouttonVueMurOuest(evt);
            }
        });

        javax.swing.GroupLayout VueMursSelectorLayout = new javax.swing.GroupLayout(VueMursSelector);
        VueMursSelector.setLayout(VueMursSelectorLayout);
        VueMursSelectorLayout.setHorizontalGroup(
            VueMursSelectorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(VueMursSelectorLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(VueMurNord)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(VueMurSud)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(VueMurEst)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(VueMurOuest)
                .addGap(32, 32, 32))
        );
        VueMursSelectorLayout.setVerticalGroup(
            VueMursSelectorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(VueMursSelectorLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(VueMursSelectorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(VueMurOuest)
                    .addComponent(VueMurEst)
                    .addComponent(VueMurSud)
                    .addComponent(VueMurNord)))
        );

        VueToitDessus.setText("Dessus");
        VueToitDessus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                VueToitDessusActionPerformed(evt);
            }
        });

        VueToitFace.setText("Face");
        VueToitFace.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                VueToitFaceActionPerformed(evt);
            }
        });

        VueToitCote.setText("Côté");
        VueToitCote.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                VueToitCoteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout VueToitSelectorLayout = new javax.swing.GroupLayout(VueToitSelector);
        VueToitSelector.setLayout(VueToitSelectorLayout);
        VueToitSelectorLayout.setHorizontalGroup(
            VueToitSelectorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(VueToitSelectorLayout.createSequentialGroup()
                .addComponent(VueToitDessus)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(VueToitCote)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(VueToitFace, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        VueToitSelectorLayout.setVerticalGroup(
            VueToitSelectorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, VueToitSelectorLayout.createSequentialGroup()
                .addGap(0, 6, Short.MAX_VALUE)
                .addGroup(VueToitSelectorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(VueToitDessus)
                    .addComponent(VueToitFace)
                    .addComponent(VueToitCote)))
        );

        javax.swing.GroupLayout VueSelectorLayout = new javax.swing.GroupLayout(VueSelector);
        VueSelector.setLayout(VueSelectorLayout);
        VueSelectorLayout.setHorizontalGroup(
            VueSelectorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(VueSelectorLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(BouttonPlancher)
                .addGap(18, 18, 18)
                .addComponent(BouttonMur)
                .addGap(18, 18, 18)
                .addComponent(BouttonToit)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(VueSelectorLayout.createSequentialGroup()
                .addComponent(VueMursSelector, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(VueToitSelector, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        VueSelectorLayout.setVerticalGroup(
            VueSelectorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(VueSelectorLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(VueSelectorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BouttonPlancher)
                    .addComponent(BouttonMur)
                    .addComponent(BouttonToit))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                .addGroup(VueSelectorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(VueMursSelector, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(VueToitSelector, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        AfficheurToolbar.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        AfficheurToolbar.setName("AfficheurToolbar"); // NOI18N

        GrillageCheckbox.setText("Grillage");
        GrillageCheckbox.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                GrillageCheckboxStateChanged(evt);
            }
        });
        GrillageCheckbox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GrillageCheckboxActionPerformed(evt);
            }
        });

        SliderZoomShow.setText("Zoom : 100%");

        centerButton.setText("Centrer");
        centerButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                centerButtonMouseClicked(evt);
            }
        });
        centerButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                centerButtonActionPerformed(evt);
            }
        });

        GrillageMag.setText("Magnetique");
        GrillageMag.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                GrillageMagStateChanged(evt);
            }
        });
        GrillageMag.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GrillageMagActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout AfficheurToolbarLayout = new javax.swing.GroupLayout(AfficheurToolbar);
        AfficheurToolbar.setLayout(AfficheurToolbarLayout);
        AfficheurToolbarLayout.setHorizontalGroup(
            AfficheurToolbarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AfficheurToolbarLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(AfficheurToolbarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(AfficheurToolbarLayout.createSequentialGroup()
                        .addComponent(centerButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(SliderZoomShow, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(AfficheurToolbarLayout.createSequentialGroup()
                        .addComponent(GrillageCheckbox, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(GrillageMag, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(24, Short.MAX_VALUE))
        );
        AfficheurToolbarLayout.setVerticalGroup(
            AfficheurToolbarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AfficheurToolbarLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(AfficheurToolbarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(centerButton)
                    .addComponent(SliderZoomShow))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(AfficheurToolbarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(GrillageCheckbox)
                    .addComponent(GrillageMag))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        visualizerScale.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout visualizerScaleLayout = new javax.swing.GroupLayout(visualizerScale);
        visualizerScale.setLayout(visualizerScaleLayout);
        visualizerScaleLayout.setHorizontalGroup(
            visualizerScaleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 129, Short.MAX_VALUE)
        );
        visualizerScaleLayout.setVerticalGroup(
            visualizerScaleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 34, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(VueSelector, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 83, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(visualizerScale, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(AfficheurToolbar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(392, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(VueSelector, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(visualizerScale, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(AfficheurToolbar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void GrillageCheckboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GrillageCheckboxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_GrillageCheckboxActionPerformed

    private void centerButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_centerButtonActionPerformed
        this.centerFrame();
    }//GEN-LAST:event_centerButtonActionPerformed

    private void centerButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_centerButtonMouseClicked
        this.centerFrame();
    }//GEN-LAST:event_centerButtonMouseClicked

    private void BouttonVueMurNord(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BouttonVueMurNord
        this.setVueActuelle(VUE_MUR_NORD);
        this.parent.setSidePanelTab(1);
        this.parent.setChoixMurTab(0);
        VueToitSelector.setVisible(false);
    }//GEN-LAST:event_BouttonVueMurNord

    private void BouttonVueMurEst(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BouttonVueMurEst
        this.setVueActuelle(VUE_MUR_EST);
        this.parent.setSidePanelTab(1);
        this.parent.setChoixMurTab(2);
        VueToitSelector.setVisible(false);
    }//GEN-LAST:event_BouttonVueMurEst

    private void BouttonVueMurOuest(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BouttonVueMurOuest
        this.setVueActuelle(VUE_MUR_OUEST);
        this.parent.setSidePanelTab(1);
        this.parent.setChoixMurTab(3);
        VueToitSelector.setVisible(false);
    }//GEN-LAST:event_BouttonVueMurOuest

    private void BouttonVueMurSud(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BouttonVueMurSud
        this.setVueActuelle(VUE_MUR_SUD);
        this.parent.setSidePanelTab(1);
        this.parent.setChoixMurTab(1);
        VueToitSelector.setVisible(false);
    }//GEN-LAST:event_BouttonVueMurSud

    private void BouttonPlancherMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BouttonPlancherMousePressed
        this.setVueActuelle(VUE_PLANCHER);
        this.parent.setSidePanelTab(0);
        VueMursSelector.setVisible(false);
        VueToitSelector.setVisible(false);
    }//GEN-LAST:event_BouttonPlancherMousePressed

    private void BouttonMurMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BouttonMurMousePressed
        VueMursSelector.setVisible(true);
        VueToitSelector.setVisible(false);
    }//GEN-LAST:event_BouttonMurMousePressed

    private void BouttonToitMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BouttonToitMousePressed
        this.parent.setSidePanelTab(2);
        VueMursSelector.setVisible(false);
        VueToitSelector.setVisible(true);
    }//GEN-LAST:event_BouttonToitMousePressed

    private void VueMurNordMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_VueMurNordMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_VueMurNordMousePressed

    private void formMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMousePressed
        this.selectedElement = null;
        this.selectedElementType = null;
        Rectangle bounds = this.frame.getBounds();
        Hitzone hitzone = this.frame.accessoireClicked(evt.getX() - bounds.x, evt.getY() - bounds.y);
        if(hitzone != null){
            this.selectAccessoryByHitZone(hitzone);
        }
    }//GEN-LAST:event_formMousePressed

    private void VueToitDessusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_VueToitDessusActionPerformed
        this.setVueActuelle(VUE_TOIT_DESSUS);
        this.parent.setSidePanelTab(2);
    }//GEN-LAST:event_VueToitDessusActionPerformed

    private void VueToitFaceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_VueToitFaceActionPerformed
        this.setVueActuelle(VUE_TOIT_FACE);
        this.parent.setSidePanelTab(2);
    }//GEN-LAST:event_VueToitFaceActionPerformed

    private void GrillageCheckboxStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_GrillageCheckboxStateChanged
        this.showGrid = GrillageCheckbox.isSelected();
        this.repaint();
    }//GEN-LAST:event_GrillageCheckboxStateChanged

    private void GrillageMagStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_GrillageMagStateChanged
        this.magneticGrid = GrillageMag.isSelected();
    }//GEN-LAST:event_GrillageMagStateChanged

    private void GrillageMagActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GrillageMagActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_GrillageMagActionPerformed

    private void VueToitCoteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_VueToitCoteActionPerformed
        this.setVueActuelle(VUE_TOIT_COTE);
        this.parent.setSidePanelTab(2);
    }//GEN-LAST:event_VueToitCoteActionPerformed

    private UUID selectedElement = null;
    private Hitzone.TypeAccessoire selectedElementType = null;

    private void selectAccessoryByHitZone(Hitzone hitzone){
        this.selectedElement = null;
        this.selectedElementType = hitzone.getType();
        boolean b;
        switch(this.selectedElementType){
            case Accessoire:
                AccessoireDTO accessoire = this.controlleur.getAccessoireFromUUID(hitzone.getReference());
                b = accessoire.isSelected;
                this.controlleur.unselectAccessories();
                if(!b){
                    selectedElement = accessoire.Id;
                    this.controlleur.selectAccessory(accessoire);
                }
                break;
            case LigneEntremise:
                LigneEntremiseDTO ligne = this.controlleur.getLigneEntremises(hitzone.getReference());
                b = ligne.selected;
                this.controlleur.unselectAccessories();
                if(!b){
                    selectedElement = ligne.Id;
                    this.controlleur.selectLigneEntremise(ligne);
                }
                break;
            case EntremiseMur:
                EntremiseMurDTO entremise = this.controlleur.getEntremiseMur(hitzone.getReference());
                b = entremise.selected;
                this.controlleur.unselectAccessories();
                if(!b){
                    selectedElement = entremise.Id;
                    this.controlleur.selectEntremiseMur(entremise);
                }
                break;
        }
        //System.out.println("------------------------");
        this.frame.repaint();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel AfficheurToolbar;
    private javax.swing.JButton BouttonMur;
    private javax.swing.JButton BouttonPlancher;
    private javax.swing.JButton BouttonToit;
    private javax.swing.JCheckBox GrillageCheckbox;
    private javax.swing.JCheckBox GrillageMag;
    private javax.swing.JLabel SliderZoomShow;
    private javax.swing.JButton VueMurEst;
    private javax.swing.JButton VueMurNord;
    private javax.swing.JButton VueMurOuest;
    private javax.swing.JButton VueMurSud;
    private javax.swing.JPanel VueMursSelector;
    private javax.swing.JPanel VueSelector;
    private javax.swing.JButton VueToitCote;
    private javax.swing.JButton VueToitDessus;
    private javax.swing.JButton VueToitFace;
    private javax.swing.JPanel VueToitSelector;
    private javax.swing.JButton centerButton;
    private ca.ulaval.glo2004.visualizer.visualizerScale visualizerScale;
    // End of variables declaration//GEN-END:variables

    public void setParent(MainWindow parent) {
        this.parent = parent;
    }
}
