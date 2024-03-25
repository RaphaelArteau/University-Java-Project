/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package ca.ulaval.glo2004.gui;

import ca.ulaval.glo2004.cabanon.domaine.CabanonDTO;
import ca.ulaval.glo2004.cabanon.domaine.Controlleur;
import ca.ulaval.glo2004.cabanon.domaine.murs.Accessoire;
import ca.ulaval.glo2004.cabanon.domaine.murs.AccessoireDTO;
import ca.ulaval.glo2004.cabanon.domaine.murs.EntremiseMurDTO;
import ca.ulaval.glo2004.cabanon.domaine.murs.Mur;
import static ca.ulaval.glo2004.cabanon.domaine.murs.Mur.orientations.Est;
import static ca.ulaval.glo2004.cabanon.domaine.murs.Mur.orientations.Nord;
import static ca.ulaval.glo2004.cabanon.domaine.murs.Mur.orientations.Ouest;
import static ca.ulaval.glo2004.cabanon.domaine.murs.Mur.orientations.Sud;
import ca.ulaval.glo2004.cabanon.domaine.murs.MurDTO;
import ca.ulaval.glo2004.cabanon.domaine.plancher.LigneEntremiseDTO;
import ca.ulaval.glo2004.cabanon.domaine.plancher.PlancherDTO;
import java.awt.Component;
import java.awt.Dimension;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import javax.swing.JFrame;
import javax.swing.JPanel;
import ca.ulaval.glo2004.visualizer.Visualizer;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import resources.Sauvegarde;
import resources.UnitConverter;
import static resources.UnitConverter.toMoneyString;


/**
 *
 * @author raphael
 */
public class MainWindow extends javax.swing.JFrame {

    /**
     * Creates new form MainWindow
     */
    private CabanonDTO cabanon;
    private PlancherDTO plancher;
    private Map<Integer, MurDTO> murs = new HashMap<>(); 
    private Controlleur controlleur;
    private String originalFile = null;
    public Map<UUID, AddedAccessoryPane> panes = new HashMap<>();
    
    private enum AddedAccessoryList{
        Entremise,
        Fenetre,
        Porte
    }
    
    
    public MainWindow(String window_title, File creationValues) {
        this.controlleur = new Controlleur(creationValues, this);
        setTitle(window_title);
        initComponents();
        if(creationValues != null){
            this.originalFile = creationValues.getAbsolutePath();
        }else{
            this.Enregistrer.setVisible(false);
        }
        Visualizer.positionContent();
        Visualizer.setControlleur(controlleur);
        Visualizer.setParent(this);
        Visualizer.setVueActuelle(Visualizer.VUE_PLANCHER);
        Largeur.setText(this.controlleur.getLargeurCabanonString());
        Profondeur.setText(this.controlleur.getProfondeurCabanonString());
        HauteurTotale.setText(UnitConverter.fractionator((int)this.controlleur.getHauteurMursCabanon()+(int)this.controlleur.getHauteurToit()));
        HauteurMurs.setText( this.controlleur.getHauteurMursString());
        ModifHauteurMurs.setText(this.controlleur.getHauteurMursString());
        //HauteurMurInput.addComponentListener(l);
        largeurPlancher.setText(this.controlleur.getLargeurCabanonString());
        profondeurPlancher.setText(this.controlleur.getProfondeurCabanonString());
        DistanceSolive.setText(this.controlleur.getEspacementSolivesString());
        AngleToit.setText(UnitConverter.fractionator(this.controlleur.getToit().AngleToit));
        //TextFieldEntremiseFerme.setText(UnitConverter.fractionator(this.controlleur.getEspacementEntremiseDebord()));
        PorteAFaux.setText(UnitConverter.fractionator(this.controlleur.getToit().PorteAFaux));
        DistanceFermes.setText(UnitConverter.fractionator(this.controlleur.getToit().EspacementFermes));
        DistanceEntremise.setText(UnitConverter.fractionator(this.controlleur.getToit().EspacementEntremisesDebor));
        Debordement.setText(UnitConverter.fractionator(this.controlleur.getToit().LongueurDebordement));
        if(this.controlleur.getLignesEntremises().size() > 0){
            for(LigneEntremiseDTO ligne : this.controlleur.getLignesEntremises()){
                this.addLigneEntremisePane(ligne.Id);
            }
        }
        //longeurPlancher.setValue(cabanon.getProfondeur());
        //hauteurMurs.setValue(cabanon.getHauteurMurs());
        ArrayList<Mur.orientations> orientations = new ArrayList<>(Arrays.asList(Mur.orientations.Nord, Mur.orientations.Sud, Mur.orientations.Est, Mur.orientations.Ouest));
        for(Mur.orientations orientation : Mur.orientations.values()){
            String espacement = this.controlleur.getEspacementMontantsMurString(orientation);
            ArrayList<AccessoireDTO> accessoires = this.controlleur.getAccessoiresMur(orientation);
            ArrayList<EntremiseMurDTO> entremises = this.controlleur.getEntremisesMur(orientation);
            JPanel container = null;
            switch(orientation){
                case Nord:
                    NordDistanceMontant.setText(espacement);
                    break;
                case Sud:
                    SudDistanceMontant.setText(espacement);
                    break;
                case Est:
                    EstDistanceMontant.setText(espacement);
                    break;
                case Ouest:
                    OuestDistanceMontant.setText(espacement);
                    break;
            }
            for(AccessoireDTO accessoire : accessoires){
                AddedAccessoryList type = null;
                switch(accessoire.TypeAccessoire){
                    case 1:
                        type = AddedAccessoryList.Fenetre;
                        break;
                    case 2:
                        type = AddedAccessoryList.Porte;
                        break;
                }
                this.addAccessoryPane(type, accessoire.Id, orientation);
            }
            for(EntremiseMurDTO entremise : entremises){
                if(entremise.AccessoireParent == null) this.addEntremisePane(entremise.Id, orientation);
            }
        miseAJourCout();
        }
    }

    public void updatePane(UUID id){
        AddedAccessoryPane pane = this.panes.get(id);
        if(pane.isVisible()) pane.updateValues();
    }

    public void setPaneVisibility(UUID id, boolean visibility){
        this.panes.get(id).setVisible(visibility);
    }

    public void setAllPaneVisibility(boolean visibility){
        for(UUID id : this.panes.keySet()){
            this.setPaneVisibility(id, visibility);
        }
    }

    private void addLigneEntremise(){
        PlancherDTO plancher = this.controlleur.getPlancher();
        UUID id = this.controlleur.ajouterLigneEntremises(this.controlleur.getCabanon().Largeur/2);
        this.addLigneEntremisePane(id);
        miseAJourCout();
    }
    
    public final void miseAJourCout(){
        this.controlleur.miseAJourCout();
        miseAJourCoutAffiche();
    }
    
    public final void miseAJourCoutAffiche(){
        champPrixMainWindow.setText(toMoneyString(this.controlleur.getCout()));
    }

    private void addLigneEntremisePane(UUID id){

        PlancherDTO plancher = this.controlleur.getPlancher();
        LigneEntremisePane pane = new LigneEntremisePane(id, plancher.LignesEntremises.size(), this);
        panes.put(id, pane);

        LigneEntremiseContainer.add(pane);
        LigneEntremiseContainer.setSize(LigneEntremiseContainer.getPreferredSize());
        LigneEntremiseContainer.revalidate();
        LigneEntremiseContainer.repaint();

        int height = 0;
        for(Component component : panneauPlancherScrollContainer.getComponents()){
            height += component.getSize().height;
        }
        height += 200;
        panneauPlancherScrollContainer.setPreferredSize(new Dimension(panneauPlancherScrollContainer.getSize().width, height));
        panneauPlancherScrollContainer.revalidate();
        panneauPlancherScrollContainer.repaint();
        
    }

    private void addEntremise(){
        int selectedWall = ChoixMur.getSelectedIndex();

        ArrayList<Mur.orientations> orientations = new ArrayList<Mur.orientations>();
        orientations.add(Mur.orientations.Nord);
        orientations.add(Mur.orientations.Sud);
        orientations.add(Mur.orientations.Est);
        orientations.add(Mur.orientations.Ouest);
        Mur.orientations orientation = orientations.get(selectedWall);

        UUID id = this.controlleur.ajouterEntremiseMur(orientation);
        
        miseAJourCout();

        this.addEntremisePane(id, orientation);
    }

   private void addEntremisePane(UUID id, Mur.orientations orientation){
       JPanel container = null;

        switch(orientation){
            case Nord:
                container = this.NordScrollContainer;
                break;
            case Sud:
                container = this.SudWallScrollContainer;
                break;
            case Est:
                container = this.EstScrollContainer;
                break;
            case Ouest:
                container = this.OuestScrollContainer;
                break;
        }
        AddedEntremisePane pane = new AddedEntremisePane(id, orientation,this);
        panes.put(id, pane);
        pane.setId(id);
        pane.setSize(container.getWidth(), pane.getPreferredSize().height);
        container.add(pane);
        container.setSize(container.getPreferredSize());
        container.revalidate();
        container.repaint();
   }

   private void addAccessory(AddedAccessoryList type){
        int selectedWall = ChoixMur.getSelectedIndex();

        ArrayList<Mur.orientations> orientations = new ArrayList<Mur.orientations>();
        orientations.add(Mur.orientations.Nord);
        orientations.add(Mur.orientations.Sud);
        orientations.add(Mur.orientations.Est);
        orientations.add(Mur.orientations.Ouest);
        Mur.orientations orientation = orientations.get(selectedWall);

        int typeInt = 0;
        UUID id = null;
        switch(type){
            case Fenetre:
                typeInt = Accessoire.typeFenetre;
                id = this.controlleur.ajouterAccessoire(orientation, typeInt);
                break;
            case Porte:
                typeInt = Accessoire.typePorte;
                id = this.controlleur.ajouterAccessoire(orientation, typeInt);
                break;
        }
        if(id != null){
            this.addAccessoryPane(type, id, orientation);
        }
        
        miseAJourCout();
   }
   
   private void addAccessoryPane(AddedAccessoryList type, UUID id, Mur.orientations orientation){
       JPanel container = null;
        switch(orientation){
            case Nord:
                container = this.NordScrollContainer;
                break;
            case Sud:
                container = this.SudWallScrollContainer;
                break;
            case Est:
                container = this.EstScrollContainer;
                break;
            case Ouest:
                container = this.OuestScrollContainer;
                break;
        }
        if(container != null){
            AddedAccessoryPane pane = null;
            switch(type){
                case Fenetre:
                    pane = new AddedWindowPane(id, orientation, this);
                    break;
                case Porte:
                    pane = new AddedDoorPane(id, orientation, this);
                    break;
            }
            panes.put(id, pane);
            if(pane != null){
                pane.setId(id);
                pane.setSize(container.getWidth(), pane.getPreferredSize().height);
                container.add(pane);
                container.setSize(container.getPreferredSize());
                container.revalidate();
                container.repaint();
            }
        }
        miseAJourCout();
   }

    public void setSidePanelTab(int index){
        this.Side_Panel.setSelectedIndex(index);
    }

    public void setChoixMurTab(int index){
        this.ChoixMur.setSelectedIndex(index);
    }

    public void refreshVisualizer(){
        this.Visualizer.repaintVue();
    }
    
    public Controlleur getControlleur(){
        return this.controlleur;
    }
    
    public Visualizer getVisualizer() {
    return this.Visualizer;
}


    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jSeparator7 = new javax.swing.JSeparator();
        Control_bar = new javax.swing.JToolBar();
        newButton = new javax.swing.JButton();
        Enregistrer = new javax.swing.JButton();
        EnregistrerSous = new javax.swing.JButton();
        Ouvrir = new javax.swing.JButton();
        jSeparator5 = new javax.swing.JToolBar.Separator();
        UndoButton = new javax.swing.JButton();
        RedoButton = new javax.swing.JButton();
        jSeparator6 = new javax.swing.JToolBar.Separator();
        filler6 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));
        filler5 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));
        filler3 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));
        filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));
        jSeparator11 = new javax.swing.JToolBar.Separator();
        jButton18 = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        champPrixMainWindow = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        filler4 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 32767));
        jSeparator10 = new javax.swing.JToolBar.Separator();
        ReglagesButton = new javax.swing.JButton();
        Side_Panel = new javax.swing.JTabbedPane();
        Plancher = new javax.swing.JPanel();
        panneauPlancherOptions = new javax.swing.JScrollPane();
        panneauPlancherScrollContainer = new javax.swing.JPanel();
        PlancherHeader = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        largeurPlancher = new javax.swing.JFormattedTextField();
        profondeurPlancher = new javax.swing.JFormattedTextField();
        EditPlancherSize = new javax.swing.JButton();
        DistanceSoliveContainer = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        DistanceSolive = new javax.swing.JFormattedTextField();
        EditDistanceSolive = new javax.swing.JButton();
        LigneEntremiseContainer = new javax.swing.JPanel();
        AddLigneEntremisePlancher = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        AddLigneEntremiseButton = new javax.swing.JButton();
        Murs = new javax.swing.JPanel();
        MurHeader = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        ModifHauteurMurs = new javax.swing.JTextField();
        OKButtonHauteurMurs = new javax.swing.JButton();
        ChoixMur = new javax.swing.JTabbedPane();
        TabMurNord = new javax.swing.JPanel();
        WallContainerHeader4 = new javax.swing.JPanel();
        jLabel24 = new javax.swing.JLabel();
        jSeparator28 = new javax.swing.JSeparator();
        jLabel25 = new javax.swing.JLabel();
        NordDistanceMontantButton = new javax.swing.JButton();
        NordDistanceMontant = new javax.swing.JTextField();
        WallScroll5 = new javax.swing.JScrollPane();
        NordScrollContainer = new javax.swing.JPanel();
        TabMurSud = new javax.swing.JPanel();
        WallContainerHeader1 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jSeparator25 = new javax.swing.JSeparator();
        jLabel19 = new javax.swing.JLabel();
        jButton6 = new javax.swing.JButton();
        SudDistanceMontant = new javax.swing.JTextField();
        WallScroll1 = new javax.swing.JScrollPane();
        SudWallScrollContainer = new javax.swing.JPanel();
        TabMurEst = new javax.swing.JPanel();
        WallContainerHeader2 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        jSeparator26 = new javax.swing.JSeparator();
        jLabel21 = new javax.swing.JLabel();
        jButton7 = new javax.swing.JButton();
        EstDistanceMontant = new javax.swing.JTextField();
        WallScroll2 = new javax.swing.JScrollPane();
        EstScrollContainer = new javax.swing.JPanel();
        TabMurOuest = new javax.swing.JPanel();
        WallContainerHeader3 = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        jSeparator27 = new javax.swing.JSeparator();
        jLabel23 = new javax.swing.JLabel();
        jButton8 = new javax.swing.JButton();
        OuestDistanceMontant = new javax.swing.JTextField();
        WallScroll3 = new javax.swing.JScrollPane();
        OuestScrollContainer = new javax.swing.JPanel();
        MurAccessoiresButtons = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        AddEntremiseButton = new javax.swing.JButton();
        AddFenetreButton = new javax.swing.JButton();
        AddPorteButton = new javax.swing.JButton();
        Toit_ScrollArea = new javax.swing.JScrollPane();
        Toit = new javax.swing.JPanel();
        LabelAngle = new javax.swing.JLabel();
        AngleToit = new javax.swing.JTextField();
        ValiderAngleToit = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        LabelPorteàFaux = new javax.swing.JLabel();
        PorteAFaux = new javax.swing.JTextField();
        ButtonPorteAFaux = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JSeparator();
        LabelFermes = new javax.swing.JLabel();
        DistanceFermes = new javax.swing.JTextField();
        ButtonDistanceFermes = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JSeparator();
        LabelOrientation = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        LabelDebordement = new javax.swing.JLabel();
        Debordement = new javax.swing.JFormattedTextField();
        ButtonDebordementAvant = new javax.swing.JButton();
        DistanceEntremise = new javax.swing.JTextField();
        DistanceEntremiseValidation = new javax.swing.JButton();
        jSeparator8 = new javax.swing.JSeparator();
        LabelO = new javax.swing.JLabel();
        OrientationComboBox = new javax.swing.JComboBox<>();
        Visualizer = new ca.ulaval.glo2004.visualizer.Visualizer();
        PaneauDimensions = new javax.swing.JPanel();
        HauteurTotale = new javax.swing.JLabel();
        HauteurMurs = new javax.swing.JLabel();
        Profondeur = new javax.swing.JLabel();
        Largeur = new javax.swing.JLabel();
        ImageDimensions = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        quitterButton = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenu3 = new javax.swing.JMenu();
        jMenu4 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(1000, 600));
        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                formMouseDragged(evt);
            }
        });
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                formComponentResized(evt);
            }
        });

        Control_bar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        Control_bar.setFloatable(true);
        Control_bar.setRollover(true);

        newButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/new.png"))); // NOI18N
        newButton.setText("Nouveau");
        newButton.setFocusable(false);
        newButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        newButton.setMaximumSize(new java.awt.Dimension(70, 50));
        newButton.setName(""); // NOI18N
        newButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        newButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newButtonActionPerformed(evt);
            }
        });
        Control_bar.add(newButton);

        Enregistrer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/save.png"))); // NOI18N
        Enregistrer.setText("Enregistrer");
        Enregistrer.setFocusable(false);
        Enregistrer.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Enregistrer.setMaximumSize(new java.awt.Dimension(85, 50));
        Enregistrer.setName(""); // NOI18N
        Enregistrer.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        Enregistrer.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                EnregistrerMousePressed(evt);
            }
        });
        Control_bar.add(Enregistrer);

        EnregistrerSous.setIcon(new javax.swing.ImageIcon(getClass().getResource("/save.png"))); // NOI18N
        EnregistrerSous.setText("Enregistrer Sous");
        EnregistrerSous.setFocusable(false);
        EnregistrerSous.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        EnregistrerSous.setMaximumSize(new java.awt.Dimension(85, 50));
        EnregistrerSous.setName(""); // NOI18N
        EnregistrerSous.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        EnregistrerSous.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                EnregistrerSousMousePressed(evt);
            }
        });
        Control_bar.add(EnregistrerSous);

        Ouvrir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/open.png"))); // NOI18N
        Ouvrir.setText("Ouvrir");
        Ouvrir.setFocusable(false);
        Ouvrir.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Ouvrir.setMaximumSize(new java.awt.Dimension(70, 50));
        Ouvrir.setName(""); // NOI18N
        Ouvrir.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        Ouvrir.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                OuvrirMousePressed(evt);
            }
        });
        Control_bar.add(Ouvrir);
        Control_bar.add(jSeparator5);

        UndoButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/undo.png"))); // NOI18N
        UndoButton.setFocusable(false);
        UndoButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        UndoButton.setLabel("Défaire");
        UndoButton.setMaximumSize(new java.awt.Dimension(55, 50));
        UndoButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        UndoButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                UndoButtonMousePressed(evt);
            }
        });
        Control_bar.add(UndoButton);

        RedoButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/redo.png"))); // NOI18N
        RedoButton.setFocusable(false);
        RedoButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        RedoButton.setInheritsPopupMenu(true);
        RedoButton.setLabel("Refaire");
        RedoButton.setMaximumSize(new java.awt.Dimension(55, 50));
        RedoButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        RedoButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RedoButtonActionPerformed(evt);
            }
        });
        Control_bar.add(RedoButton);
        Control_bar.add(jSeparator6);
        Control_bar.add(filler6);
        Control_bar.add(filler5);
        Control_bar.add(filler3);
        Control_bar.add(filler2);
        Control_bar.add(filler1);
        Control_bar.add(jSeparator11);

        jButton18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/list.png"))); // NOI18N
        jButton18.setFocusable(false);
        jButton18.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton18.setLabel("Liste Pièces");
        jButton18.setMaximumSize(new java.awt.Dimension(85, 50));
        jButton18.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton18ActionPerformed(evt);
            }
        });
        Control_bar.add(jButton18);

        jLabel10.setText("   Est. Coûts :");
        jLabel10.setToolTipText("");
        Control_bar.add(jLabel10);
        jLabel10.getAccessibleContext().setAccessibleName("Ést. Coûts:");

        champPrixMainWindow.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        champPrixMainWindow.setText("0.00");
        champPrixMainWindow.setToolTipText("");
        champPrixMainWindow.setMaximumSize(new java.awt.Dimension(100, 25));
        champPrixMainWindow.setMinimumSize(new java.awt.Dimension(100, 25));
        champPrixMainWindow.setName(""); // NOI18N
        champPrixMainWindow.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                champPrixMainWindowActionPerformed(evt);
            }
        });
        Control_bar.add(champPrixMainWindow);

        jLabel1.setText("$");
        Control_bar.add(jLabel1);
        Control_bar.add(filler4);
        Control_bar.add(jSeparator10);

        ReglagesButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/settings.png"))); // NOI18N
        ReglagesButton.setFocusable(false);
        ReglagesButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ReglagesButton.setLabel("Réglages");
        ReglagesButton.setMaximumSize(new java.awt.Dimension(65, 50));
        ReglagesButton.setName(""); // NOI18N
        ReglagesButton.setPreferredSize(new java.awt.Dimension(65, 50));
        ReglagesButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        ReglagesButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ReglagesButtonActionPerformed(evt);
            }
        });
        Control_bar.add(ReglagesButton);

        Side_Panel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        Side_Panel.setMinimumSize(new java.awt.Dimension(322, 109));
        Side_Panel.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                Side_PanelStateChanged(evt);
            }
        });

        panneauPlancherOptions.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        panneauPlancherOptions.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        panneauPlancherScrollContainer.setForeground(new java.awt.Color(100, 100, 100));
        panneauPlancherScrollContainer.setMinimumSize(new java.awt.Dimension(0, 0));
        panneauPlancherScrollContainer.setName(""); // NOI18N
        panneauPlancherScrollContainer.setPreferredSize(new java.awt.Dimension(300, 250));
        panneauPlancherScrollContainer.setLayout(new java.awt.GridBagLayout());

        PlancherHeader.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        PlancherHeader.setLayout(null);

        jLabel3.setText("Dimensions");
        PlancherHeader.add(jLabel3);
        jLabel3.setBounds(10, 0, 120, 16);

        jLabel4.setText("Largeur :");
        PlancherHeader.add(jLabel4);
        jLabel4.setBounds(10, 20, 90, 16);

        jLabel5.setText("Profondeur :");
        PlancherHeader.add(jLabel5);
        jLabel5.setBounds(120, 20, 80, 16);

        largeurPlancher.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        largeurPlancher.setText("0.0");
        largeurPlancher.setToolTipText("");
        largeurPlancher.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                largeurPlancherActionPerformed(evt);
            }
        });
        PlancherHeader.add(largeurPlancher);
        largeurPlancher.setBounds(10, 40, 92, 22);

        profondeurPlancher.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        profondeurPlancher.setText("0.00");
        profondeurPlancher.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                profondeurPlancherActionPerformed(evt);
            }
        });
        PlancherHeader.add(profondeurPlancher);
        profondeurPlancher.setBounds(120, 40, 91, 22);

        EditPlancherSize.setText("OK");
        EditPlancherSize.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                EditPlancherSizeMousePressed(evt);
            }
        });
        PlancherHeader.add(EditPlancherSize);
        EditPlancherSize.setBounds(220, 40, 51, 23);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 279;
        gridBagConstraints.ipady = 99;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(1, 0, 0, 20);
        panneauPlancherScrollContainer.add(PlancherHeader, gridBagConstraints);

        DistanceSoliveContainer.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        DistanceSoliveContainer.setLayout(null);

        jLabel7.setText("Distance entre les solives :");
        DistanceSoliveContainer.add(jLabel7);
        jLabel7.setBounds(6, 6, 260, 31);

        DistanceSolive.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        DistanceSolive.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DistanceSoliveActionPerformed(evt);
            }
        });
        DistanceSoliveContainer.add(DistanceSolive);
        DistanceSolive.setBounds(121, 43, 90, 22);

        EditDistanceSolive.setText("OK");
        EditDistanceSolive.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                EditDistanceSoliveMousePressed(evt);
            }
        });
        DistanceSoliveContainer.add(EditDistanceSolive);
        EditDistanceSolive.setBounds(223, 43, 51, 23);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 279;
        gridBagConstraints.ipady = 79;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(100, 0, 0, 20);
        panneauPlancherScrollContainer.add(DistanceSoliveContainer, gridBagConstraints);

        LigneEntremiseContainer.setLayout(new javax.swing.BoxLayout(LigneEntremiseContainer, javax.swing.BoxLayout.Y_AXIS));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        panneauPlancherScrollContainer.add(LigneEntremiseContainer, gridBagConstraints);

        panneauPlancherOptions.setViewportView(panneauPlancherScrollContainer);

        jLabel8.setText("Ajouter une ligne d'entremises");

        AddLigneEntremiseButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/new.png"))); // NOI18N
        AddLigneEntremiseButton.setToolTipText("");
        AddLigneEntremiseButton.setMaximumSize(new java.awt.Dimension(100, 100));
        AddLigneEntremiseButton.setMinimumSize(new java.awt.Dimension(50, 50));
        AddLigneEntremiseButton.setName(""); // NOI18N
        AddLigneEntremiseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddLigneEntremiseButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout AddLigneEntremisePlancherLayout = new javax.swing.GroupLayout(AddLigneEntremisePlancher);
        AddLigneEntremisePlancher.setLayout(AddLigneEntremisePlancherLayout);
        AddLigneEntremisePlancherLayout.setHorizontalGroup(
            AddLigneEntremisePlancherLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AddLigneEntremisePlancherLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(AddLigneEntremiseButton, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8)
                .addContainerGap(52, Short.MAX_VALUE))
        );
        AddLigneEntremisePlancherLayout.setVerticalGroup(
            AddLigneEntremisePlancherLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AddLigneEntremisePlancherLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(AddLigneEntremisePlancherLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(AddLigneEntremiseButton, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout PlancherLayout = new javax.swing.GroupLayout(Plancher);
        Plancher.setLayout(PlancherLayout);
        PlancherLayout.setHorizontalGroup(
            PlancherLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panneauPlancherOptions, javax.swing.GroupLayout.PREFERRED_SIZE, 302, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(PlancherLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(AddLigneEntremisePlancher, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        PlancherLayout.setVerticalGroup(
            PlancherLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PlancherLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panneauPlancherOptions, javax.swing.GroupLayout.PREFERRED_SIZE, 405, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(AddLigneEntremisePlancher, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        Side_Panel.addTab("Plancher", Plancher);

        MurHeader.setPreferredSize(new java.awt.Dimension(302, 92));

        jLabel14.setText("Hauteur des murs :");
        jLabel14.setToolTipText("");
        jLabel14.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        ModifHauteurMurs.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        ModifHauteurMurs.setMaximumSize(new java.awt.Dimension(64, 22));
        ModifHauteurMurs.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                ModifHauteurMursFocusLost(evt);
            }
        });
        ModifHauteurMurs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ModifHauteurMursActionPerformed(evt);
            }
        });
        ModifHauteurMurs.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                ModifHauteurMursKeyTyped(evt);
            }
        });

        OKButtonHauteurMurs.setText("Ok");
        OKButtonHauteurMurs.setToolTipText("");
        OKButtonHauteurMurs.setActionCommand("OK");
        OKButtonHauteurMurs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OKButtonHauteurMursActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout MurHeaderLayout = new javax.swing.GroupLayout(MurHeader);
        MurHeader.setLayout(MurHeaderLayout);
        MurHeaderLayout.setHorizontalGroup(
            MurHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MurHeaderLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ModifHauteurMurs, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(OKButtonHauteurMurs)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        MurHeaderLayout.setVerticalGroup(
            MurHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MurHeaderLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(MurHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(MurHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(ModifHauteurMurs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(OKButtonHauteurMurs))
                    .addComponent(jLabel14))
                .addContainerGap(12, Short.MAX_VALUE))
        );

        ChoixMur.setOpaque(true);
        ChoixMur.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                ChoixMurStateChanged(evt);
            }
        });

        WallContainerHeader4.setLayout(null);

        jLabel24.setText("Distance entre montants :");
        jLabel24.setAutoscrolls(true);
        WallContainerHeader4.add(jLabel24);
        jLabel24.setBounds(10, 10, 160, 16);
        WallContainerHeader4.add(jSeparator28);
        jSeparator28.setBounds(0, 40, 300, 17);

        jLabel25.setText("Accessoires:");
        jLabel25.setToolTipText("");
        WallContainerHeader4.add(jLabel25);
        jLabel25.setBounds(10, 60, 140, 16);

        NordDistanceMontantButton.setText("Ok");
        NordDistanceMontantButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                DistanceMontantButtonMousePressed(evt);
            }
        });
        NordDistanceMontantButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NordDistanceMontantButtonActionPerformed(evt);
            }
        });
        WallContainerHeader4.add(NordDistanceMontantButton);
        NordDistanceMontantButton.setBounds(240, 10, 50, 23);

        NordDistanceMontant.setName("DistanceMontant"); // NOI18N
        WallContainerHeader4.add(NordDistanceMontant);
        NordDistanceMontant.setBounds(170, 10, 60, 22);

        WallScroll5.setBorder(null);
        WallScroll5.setName("WallScroll"); // NOI18N

        NordScrollContainer.setName("ScrollContainer"); // NOI18N
        NordScrollContainer.setLayout(new javax.swing.BoxLayout(NordScrollContainer, javax.swing.BoxLayout.Y_AXIS));
        WallScroll5.setViewportView(NordScrollContainer);

        javax.swing.GroupLayout TabMurNordLayout = new javax.swing.GroupLayout(TabMurNord);
        TabMurNord.setLayout(TabMurNordLayout);
        TabMurNordLayout.setHorizontalGroup(
            TabMurNordLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(WallContainerHeader4, javax.swing.GroupLayout.DEFAULT_SIZE, 302, Short.MAX_VALUE)
            .addComponent(WallScroll5)
        );
        TabMurNordLayout.setVerticalGroup(
            TabMurNordLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TabMurNordLayout.createSequentialGroup()
                .addComponent(WallContainerHeader4, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(WallScroll5, javax.swing.GroupLayout.DEFAULT_SIZE, 232, Short.MAX_VALUE))
        );

        ChoixMur.addTab("Nord", TabMurNord);

        WallContainerHeader1.setLayout(null);

        jLabel18.setText("Distance entre montants :");
        jLabel18.setAutoscrolls(true);
        WallContainerHeader1.add(jLabel18);
        jLabel18.setBounds(10, 10, 160, 16);
        WallContainerHeader1.add(jSeparator25);
        jSeparator25.setBounds(0, 40, 300, 17);

        jLabel19.setText("Accessoires:");
        jLabel19.setToolTipText("");
        WallContainerHeader1.add(jLabel19);
        jLabel19.setBounds(10, 60, 110, 16);

        jButton6.setText("Ok");
        jButton6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                DistanceMontantButtonMousePressed(evt);
            }
        });
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        WallContainerHeader1.add(jButton6);
        jButton6.setBounds(240, 10, 50, 23);

        SudDistanceMontant.setName("DistanceMontant"); // NOI18N
        WallContainerHeader1.add(SudDistanceMontant);
        SudDistanceMontant.setBounds(170, 10, 60, 22);

        WallScroll1.setBorder(null);
        WallScroll1.setName("WallScroll"); // NOI18N

        SudWallScrollContainer.setName("ScrollContainer"); // NOI18N
        SudWallScrollContainer.setLayout(new javax.swing.BoxLayout(SudWallScrollContainer, javax.swing.BoxLayout.Y_AXIS));
        WallScroll1.setViewportView(SudWallScrollContainer);

        javax.swing.GroupLayout TabMurSudLayout = new javax.swing.GroupLayout(TabMurSud);
        TabMurSud.setLayout(TabMurSudLayout);
        TabMurSudLayout.setHorizontalGroup(
            TabMurSudLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(WallContainerHeader1, javax.swing.GroupLayout.DEFAULT_SIZE, 302, Short.MAX_VALUE)
            .addComponent(WallScroll1)
        );
        TabMurSudLayout.setVerticalGroup(
            TabMurSudLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TabMurSudLayout.createSequentialGroup()
                .addComponent(WallContainerHeader1, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(WallScroll1, javax.swing.GroupLayout.DEFAULT_SIZE, 232, Short.MAX_VALUE))
        );

        ChoixMur.addTab("Sud", TabMurSud);

        WallContainerHeader2.setLayout(null);

        jLabel20.setText("Distance entre montants :");
        jLabel20.setAutoscrolls(true);
        WallContainerHeader2.add(jLabel20);
        jLabel20.setBounds(10, 10, 160, 16);
        WallContainerHeader2.add(jSeparator26);
        jSeparator26.setBounds(0, 40, 300, 17);

        jLabel21.setText("Accessoires:");
        jLabel21.setToolTipText("");
        WallContainerHeader2.add(jLabel21);
        jLabel21.setBounds(10, 60, 110, 16);

        jButton7.setText("Ok");
        jButton7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                DistanceMontantButtonMousePressed(evt);
            }
        });
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        WallContainerHeader2.add(jButton7);
        jButton7.setBounds(240, 10, 50, 23);

        EstDistanceMontant.setName("DistanceMontant"); // NOI18N
        WallContainerHeader2.add(EstDistanceMontant);
        EstDistanceMontant.setBounds(170, 10, 60, 22);

        WallScroll2.setBorder(null);
        WallScroll2.setName("WallScroll"); // NOI18N

        EstScrollContainer.setName("ScrollContainer"); // NOI18N
        EstScrollContainer.setLayout(new javax.swing.BoxLayout(EstScrollContainer, javax.swing.BoxLayout.Y_AXIS));
        WallScroll2.setViewportView(EstScrollContainer);

        javax.swing.GroupLayout TabMurEstLayout = new javax.swing.GroupLayout(TabMurEst);
        TabMurEst.setLayout(TabMurEstLayout);
        TabMurEstLayout.setHorizontalGroup(
            TabMurEstLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(WallContainerHeader2, javax.swing.GroupLayout.DEFAULT_SIZE, 302, Short.MAX_VALUE)
            .addComponent(WallScroll2)
        );
        TabMurEstLayout.setVerticalGroup(
            TabMurEstLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TabMurEstLayout.createSequentialGroup()
                .addComponent(WallContainerHeader2, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(WallScroll2, javax.swing.GroupLayout.DEFAULT_SIZE, 232, Short.MAX_VALUE))
        );

        ChoixMur.addTab("Est", TabMurEst);

        WallContainerHeader3.setLayout(null);

        jLabel22.setText("Distance entre montants :");
        jLabel22.setAutoscrolls(true);
        WallContainerHeader3.add(jLabel22);
        jLabel22.setBounds(10, 10, 160, 16);
        WallContainerHeader3.add(jSeparator27);
        jSeparator27.setBounds(0, 40, 300, 17);

        jLabel23.setText("Accessoires:");
        jLabel23.setToolTipText("");
        WallContainerHeader3.add(jLabel23);
        jLabel23.setBounds(10, 60, 130, 16);

        jButton8.setText("Ok");
        jButton8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                DistanceMontantButtonMousePressed(evt);
            }
        });
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });
        WallContainerHeader3.add(jButton8);
        jButton8.setBounds(240, 10, 50, 23);

        OuestDistanceMontant.setName("DistanceMontant"); // NOI18N
        WallContainerHeader3.add(OuestDistanceMontant);
        OuestDistanceMontant.setBounds(170, 10, 60, 22);

        WallScroll3.setBorder(null);
        WallScroll3.setName("WallScroll"); // NOI18N

        OuestScrollContainer.setName("ScrollContainer"); // NOI18N
        OuestScrollContainer.setLayout(new javax.swing.BoxLayout(OuestScrollContainer, javax.swing.BoxLayout.Y_AXIS));
        WallScroll3.setViewportView(OuestScrollContainer);

        javax.swing.GroupLayout TabMurOuestLayout = new javax.swing.GroupLayout(TabMurOuest);
        TabMurOuest.setLayout(TabMurOuestLayout);
        TabMurOuestLayout.setHorizontalGroup(
            TabMurOuestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(WallContainerHeader3, javax.swing.GroupLayout.DEFAULT_SIZE, 302, Short.MAX_VALUE)
            .addComponent(WallScroll3)
        );
        TabMurOuestLayout.setVerticalGroup(
            TabMurOuestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TabMurOuestLayout.createSequentialGroup()
                .addComponent(WallContainerHeader3, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(WallScroll3, javax.swing.GroupLayout.DEFAULT_SIZE, 232, Short.MAX_VALUE))
        );

        ChoixMur.addTab("Ouest", TabMurOuest);

        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText("Ajouter :");
        jLabel17.setToolTipText("");

        AddEntremiseButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/new.png"))); // NOI18N
        AddEntremiseButton.setText("Entremise");
        AddEntremiseButton.setToolTipText("");
        AddEntremiseButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        AddEntremiseButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        AddEntremiseButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                AddEntremiseButtonMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                AddEntremiseButtonMousePressed(evt);
            }
        });
        AddEntremiseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddEntremiseButtonActionPerformed(evt);
            }
        });

        AddFenetreButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/new.png"))); // NOI18N
        AddFenetreButton.setText("Fenêtre");
        AddFenetreButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        AddFenetreButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        AddFenetreButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                AddFenetreButtonMouseClicked(evt);
            }
        });
        AddFenetreButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddFenetreButtonActionPerformed(evt);
            }
        });

        AddPorteButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/new.png"))); // NOI18N
        AddPorteButton.setText("Porte");
        AddPorteButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        AddPorteButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        AddPorteButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                AddPorteButtonMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                AddPorteButtonMousePressed(evt);
            }
        });
        AddPorteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddPorteButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout MurAccessoiresButtonsLayout = new javax.swing.GroupLayout(MurAccessoiresButtons);
        MurAccessoiresButtons.setLayout(MurAccessoiresButtonsLayout);
        MurAccessoiresButtonsLayout.setHorizontalGroup(
            MurAccessoiresButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MurAccessoiresButtonsLayout.createSequentialGroup()
                .addGroup(MurAccessoiresButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(MurAccessoiresButtonsLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(AddEntremiseButton, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(AddFenetreButton, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(AddPorteButton, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(24, 24, 24))
                    .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        MurAccessoiresButtonsLayout.setVerticalGroup(
            MurAccessoiresButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MurAccessoiresButtonsLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(MurAccessoiresButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(MurAccessoiresButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(AddFenetreButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(AddPorteButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, MurAccessoiresButtonsLayout.createSequentialGroup()
                        .addComponent(AddEntremiseButton, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );

        AddFenetreButton.getAccessibleContext().setAccessibleDescription("");
        AddPorteButton.getAccessibleContext().setAccessibleDescription("");

        javax.swing.GroupLayout MursLayout = new javax.swing.GroupLayout(Murs);
        Murs.setLayout(MursLayout);
        MursLayout.setHorizontalGroup(
            MursLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(ChoixMur)
            .addComponent(MurHeader, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(MursLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(MurAccessoiresButtons, javax.swing.GroupLayout.PREFERRED_SIZE, 279, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        MursLayout.setVerticalGroup(
            MursLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MursLayout.createSequentialGroup()
                .addComponent(MurHeader, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ChoixMur, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(MurAccessoiresButtons, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        Side_Panel.addTab("Murs", Murs);

        Toit_ScrollArea.setHorizontalScrollBar(null);

        Toit.setBackground(new java.awt.Color(255, 255, 255));

        LabelAngle.setText("Angle du toit:");

        AngleToit.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        AngleToit.setText("100");
        AngleToit.setToolTipText("");
        AngleToit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AngleToitActionPerformed(evt);
            }
        });

        ValiderAngleToit.setText("OK");
        ValiderAngleToit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ValiderAngleToitActionPerformed(evt);
            }
        });

        LabelPorteàFaux.setText("Taille du porte-à-faux:");

        PorteAFaux.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        PorteAFaux.setText("15");
        PorteAFaux.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PorteAFauxActionPerformed(evt);
            }
        });

        ButtonPorteAFaux.setText("OK");
        ButtonPorteAFaux.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonPorteAFauxActionPerformed(evt);
            }
        });

        LabelFermes.setText("Distance entre les fermes:");

        DistanceFermes.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        DistanceFermes.setText("24");
        DistanceFermes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DistanceFermesActionPerformed(evt);
            }
        });

        ButtonDistanceFermes.setText("OK");
        ButtonDistanceFermes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonDistanceFermesActionPerformed(evt);
            }
        });

        LabelOrientation.setText("Distance entremises debordement:");

        LabelDebordement.setText("Débordement:");

        Debordement.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        Debordement.setText("40");

        ButtonDebordementAvant.setText("OK");
        ButtonDebordementAvant.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonDebordementAvantActionPerformed(evt);
            }
        });

        DistanceEntremise.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        DistanceEntremise.setText("15");

        DistanceEntremiseValidation.setText("OK");
        DistanceEntremiseValidation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DistanceEntremiseValidationActionPerformed(evt);
            }
        });

        LabelO.setText("Orientation:");

        OrientationComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nord/Sud", "Est/Ouest" }));
        OrientationComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OrientationComboBoxActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout ToitLayout = new javax.swing.GroupLayout(Toit);
        Toit.setLayout(ToitLayout);
        ToitLayout.setHorizontalGroup(
            ToitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1)
            .addComponent(jSeparator2)
            .addComponent(jSeparator4)
            .addComponent(jSeparator8)
            .addGroup(ToitLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ToitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ToitLayout.createSequentialGroup()
                        .addComponent(DistanceEntremise, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(ToitLayout.createSequentialGroup()
                        .addComponent(PorteAFaux, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(ButtonPorteAFaux)
                        .addGap(37, 37, 37))
                    .addGroup(ToitLayout.createSequentialGroup()
                        .addComponent(DistanceFermes, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(ButtonDistanceFermes)
                        .addGap(38, 38, 38))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ToitLayout.createSequentialGroup()
                        .addGroup(ToitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(ToitLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(DistanceEntremiseValidation))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, ToitLayout.createSequentialGroup()
                                .addComponent(AngleToit, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(ValiderAngleToit))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, ToitLayout.createSequentialGroup()
                                .addGroup(ToitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(LabelAngle, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(LabelOrientation, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(LabelPorteàFaux, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(LabelFermes, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(LabelDebordement, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(40, 40, 40))
                    .addGroup(ToitLayout.createSequentialGroup()
                        .addComponent(Debordement, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(ButtonDebordementAvant)
                        .addGap(39, 39, 39))))
            .addGroup(ToitLayout.createSequentialGroup()
                .addGroup(ToitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(ToitLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(ToitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(LabelO)
                            .addComponent(OrientationComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        ToitLayout.setVerticalGroup(
            ToitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ToitLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(LabelAngle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ToitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AngleToit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ValiderAngleToit))
                .addGap(18, 18, 18)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(LabelPorteàFaux)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(ToitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(PorteAFaux, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ButtonPorteAFaux))
                .addGap(12, 12, 12)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 3, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(LabelFermes)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ToitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(DistanceFermes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ButtonDistanceFermes))
                .addGap(16, 16, 16)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 3, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(LabelOrientation)
                .addGap(12, 12, 12)
                .addGroup(ToitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(DistanceEntremise, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(DistanceEntremiseValidation))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(LabelDebordement)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(ToitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Debordement, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ButtonDebordementAvant))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator8, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(LabelO)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(OrientationComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(80, Short.MAX_VALUE))
        );

        Toit_ScrollArea.setViewportView(Toit);

        Side_Panel.addTab("Toit", Toit_ScrollArea);

        Visualizer.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        Visualizer.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                VisualizerMouseDragged(evt);
            }
        });
        Visualizer.addMouseWheelListener(new java.awt.event.MouseWheelListener() {
            public void mouseWheelMoved(java.awt.event.MouseWheelEvent evt) {
                VisualizerMouseWheelMoved(evt);
            }
        });
        Visualizer.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                VisualizerMouseReleased(evt);
            }
        });
        Visualizer.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                VisualizerComponentResized(evt);
            }
        });

        HauteurTotale.setFont(new java.awt.Font("Arial Black", 0, 14)); // NOI18N
        HauteurTotale.setForeground(new java.awt.Color(255, 153, 0));
        HauteurTotale.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        HauteurTotale.setText("350");

        HauteurMurs.setFont(new java.awt.Font("Arial Black", 0, 14)); // NOI18N
        HauteurMurs.setForeground(new java.awt.Color(255, 153, 51));
        HauteurMurs.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        HauteurMurs.setText("284");

        Profondeur.setFont(new java.awt.Font("Arial Black", 0, 14)); // NOI18N
        Profondeur.setForeground(new java.awt.Color(255, 153, 51));
        Profondeur.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        Profondeur.setText("120");

        Largeur.setFont(new java.awt.Font("Arial Black", 0, 14)); // NOI18N
        Largeur.setForeground(new java.awt.Color(255, 153, 51));
        Largeur.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        Largeur.setText("272");

        ImageDimensions.setIcon(new javax.swing.ImageIcon(getClass().getResource("/DimensionsCabanonToitNord.png"))); // NOI18N

        javax.swing.GroupLayout PaneauDimensionsLayout = new javax.swing.GroupLayout(PaneauDimensions);
        PaneauDimensions.setLayout(PaneauDimensionsLayout);
        PaneauDimensionsLayout.setHorizontalGroup(
            PaneauDimensionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PaneauDimensionsLayout.createSequentialGroup()
                .addGap(200, 200, 200)
                .addComponent(Largeur, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(PaneauDimensionsLayout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(Profondeur, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(PaneauDimensionsLayout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(HauteurTotale, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(PaneauDimensionsLayout.createSequentialGroup()
                .addGap(185, 185, 185)
                .addComponent(HauteurMurs, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(ImageDimensions)
        );
        PaneauDimensionsLayout.setVerticalGroup(
            PaneauDimensionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PaneauDimensionsLayout.createSequentialGroup()
                .addGap(200, 200, 200)
                .addComponent(Largeur))
            .addGroup(PaneauDimensionsLayout.createSequentialGroup()
                .addGap(200, 200, 200)
                .addComponent(Profondeur))
            .addGroup(PaneauDimensionsLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(HauteurTotale))
            .addGroup(PaneauDimensionsLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(HauteurMurs))
            .addComponent(ImageDimensions)
        );

        jMenu1.setText("Fichier");

        quitterButton.setText("Quitter");
        quitterButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                quitterButtonActionPerformed(evt);
            }
        });
        jMenu1.add(quitterButton);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Éditer");
        jMenuBar1.add(jMenu2);

        jMenu3.setText("Affichage");
        jMenuBar1.add(jMenu3);

        jMenu4.setText("Aide");
        jMenuBar1.add(jMenu4);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(Side_Panel, javax.swing.GroupLayout.PREFERRED_SIZE, 304, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(PaneauDimensions, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Visualizer, javax.swing.GroupLayout.DEFAULT_SIZE, 914, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(Control_bar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(Control_bar, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(Visualizer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(PaneauDimensions, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Side_Panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        Side_Panel.getAccessibleContext().setAccessibleName("ChoixZone");
        Side_Panel.getAccessibleContext().setAccessibleDescription("");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void newButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newButtonActionPerformed

        dispose();
        MainWindow main = new MainWindow("Cabanator", null);
        main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        main.pack();
        main.setVisible(true);

    }//GEN-LAST:event_newButtonActionPerformed

    private void AngleToitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AngleToitActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_AngleToitActionPerformed

    private void largeurPlancherActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_largeurPlancherActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_largeurPlancherActionPerformed

    private void ReglagesButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ReglagesButtonActionPerformed
        new PreferencePane(this.controlleur, this).setVisible(true);
    }//GEN-LAST:event_ReglagesButtonActionPerformed

    private void champPrixMainWindowActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_champPrixMainWindowActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_champPrixMainWindowActionPerformed

    private void PorteAFauxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PorteAFauxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PorteAFauxActionPerformed

    private void formComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentResized
        Visualizer.positionContent();
    }//GEN-LAST:event_formComponentResized

    private void formMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseDragged
        // TODO add your handling code here:
    }//GEN-LAST:event_formMouseDragged

    private void VisualizerMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_VisualizerMouseDragged
        Visualizer.moveFrame(evt);
    }//GEN-LAST:event_VisualizerMouseDragged

    private void VisualizerMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_VisualizerMouseReleased
        Visualizer.stopMovingFrame();
    }//GEN-LAST:event_VisualizerMouseReleased

    private void VisualizerMouseWheelMoved(java.awt.event.MouseWheelEvent evt) {//GEN-FIRST:event_VisualizerMouseWheelMoved
        Visualizer.zoomFrame(evt);
    }//GEN-LAST:event_VisualizerMouseWheelMoved

    private void quitterButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_quitterButtonActionPerformed
        System.exit(0);
    }//GEN-LAST:event_quitterButtonActionPerformed

    private void AddEntremiseButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AddEntremiseButtonMouseClicked
        
    }//GEN-LAST:event_AddEntremiseButtonMouseClicked

    private void AddEntremiseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddEntremiseButtonActionPerformed

    }//GEN-LAST:event_AddEntremiseButtonActionPerformed

    private void AddFenetreButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AddFenetreButtonMouseClicked

    }//GEN-LAST:event_AddFenetreButtonMouseClicked

    private void AddPorteButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AddPorteButtonMouseClicked
    
    }//GEN-LAST:event_AddPorteButtonMouseClicked

    private void ModifHauteurMursFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_ModifHauteurMursFocusLost

        //Mettre a jour la hauteur des mur via le controlleur 
       /* String saisieString = this.ModifHauteurMurs.getText();
        double saisieDouble = Double.parseDouble(saisieString);
        this.controlleur.setHauteurMurs(saisieDouble);
        this.refreshVisualizer();*/
    }//GEN-LAST:event_ModifHauteurMursFocusLost

    private void ModifHauteurMursKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ModifHauteurMursKeyTyped

    }//GEN-LAST:event_ModifHauteurMursKeyTyped

    private void ModifHauteurMursActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ModifHauteurMursActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ModifHauteurMursActionPerformed

    private void OKButtonHauteurMursActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OKButtonHauteurMursActionPerformed
        this.controlleur.setHauteurMurs(this.ModifHauteurMurs.getText());
        this.HauteurMurs.setText( this.controlleur.getHauteurMursString());
        this.refreshVisualizer();
        miseAJourCout();
    }//GEN-LAST:event_OKButtonHauteurMursActionPerformed

    private void AddFenetreButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddFenetreButtonActionPerformed
        this.addAccessory(AddedAccessoryList.Fenetre);
        this.refreshVisualizer();
        miseAJourCout();
    }//GEN-LAST:event_AddFenetreButtonActionPerformed

    private void AddPorteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddPorteButtonActionPerformed
      
    }//GEN-LAST:event_AddPorteButtonActionPerformed

    private void profondeurPlancherActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_profondeurPlancherActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_profondeurPlancherActionPerformed

    private void EditPlancherSizeMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_EditPlancherSizeMousePressed
        //double largeur = UnitConverter.defractionator(this.largeurPlancher.getText());
        //double profondeur = UnitConverter.defractionator(this.longeurPlancher.getText());
        this.controlleur.setLargeurCabanon(this.largeurPlancher.getText());
        this.controlleur.setProfondeurCabanon(this.profondeurPlancher.getText());
        this.Profondeur.setText(this.profondeurPlancher.getText());
        this.Largeur.setText(this.largeurPlancher.getText());
        this.HauteurTotale.setText(UnitConverter.fractionator((int)this.controlleur.getHauteurMursCabanon()+(int)this.controlleur.getHauteurToit()));
        this.refreshVisualizer();
        miseAJourCout();
    }//GEN-LAST:event_EditPlancherSizeMousePressed

    
    private void AddPorteButtonMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AddPorteButtonMousePressed
        this.addAccessory(AddedAccessoryList.Porte);
        this.refreshVisualizer();
        miseAJourCout();
    }//GEN-LAST:event_AddPorteButtonMousePressed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton8ActionPerformed

    private void Side_PanelStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_Side_PanelStateChanged
        int index = this.Side_Panel.getSelectedIndex();
        switch(index){
            case 0:
                this.Visualizer.setVueActuelle(Visualizer.VUE_PLANCHER);
                break;
            case 1:
                int wallIndex = this.ChoixMur.getSelectedIndex();
                switch(wallIndex){
                    case 0:
                        this.Visualizer.setVueActuelle(Visualizer.VUE_MUR_NORD);
                        break;
                    case 1:
                        this.Visualizer.setVueActuelle(Visualizer.VUE_MUR_SUD);
                        break;
                    case 2:
                        this.Visualizer.setVueActuelle(Visualizer.VUE_MUR_EST);
                        break;
                    case 3:
                        this.Visualizer.setVueActuelle(Visualizer.VUE_MUR_OUEST);
                        break;
                }
                break;
            case 2:
                this.Visualizer.setVueActuelle(Visualizer.VUE_TOIT_FACE);
                break;
        }
    }//GEN-LAST:event_Side_PanelStateChanged

    private void ChoixMurStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_ChoixMurStateChanged
        int wallIndex = this.ChoixMur.getSelectedIndex();
        switch(wallIndex){
            case 0:
                this.Visualizer.setVueActuelle(Visualizer.VUE_MUR_NORD);
                break;
            case 1:
                this.Visualizer.setVueActuelle(Visualizer.VUE_MUR_SUD);
                break;
            case 2:
                this.Visualizer.setVueActuelle(Visualizer.VUE_MUR_EST);
                break;
            case 3:
                this.Visualizer.setVueActuelle(Visualizer.VUE_MUR_OUEST);
                break;
        }
    }//GEN-LAST:event_ChoixMurStateChanged

    private void EditDistanceSoliveMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_EditDistanceSoliveMousePressed
        this.controlleur.setEspacementSolivesPlancher(this.DistanceSolive.getText());
        this.refreshVisualizer();
        miseAJourCout();
    }//GEN-LAST:event_EditDistanceSoliveMousePressed

    private void NordDistanceMontantButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NordDistanceMontantButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_NordDistanceMontantButtonActionPerformed

    private void DistanceMontantButtonMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DistanceMontantButtonMousePressed
        int selectedWall = ChoixMur.getSelectedIndex();
        Mur.orientations orientation = Mur.orientations.values()[selectedWall];

        String valeurStr = null;
        switch(orientation){
            case Nord:
                valeurStr = this.NordDistanceMontant.getText();
                break;
            case Sud:
                valeurStr = this.SudDistanceMontant.getText();
                break;
            case Est:
                valeurStr = this.EstDistanceMontant.getText();
                break;
            case Ouest:
                valeurStr = this.OuestDistanceMontant.getText();
                break;
        }

        if(valeurStr != null){
            this.controlleur.setEspacementMontantsMur(orientation, valeurStr);
            this.refreshVisualizer();
        }
        miseAJourCout();
    }//GEN-LAST:event_DistanceMontantButtonMousePressed

    private void AddLigneEntremiseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddLigneEntremiseButtonActionPerformed
        this.addLigneEntremise();
        this.refreshVisualizer();
        miseAJourCout();
    }//GEN-LAST:event_AddLigneEntremiseButtonActionPerformed

    private void DistanceSoliveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DistanceSoliveActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_DistanceSoliveActionPerformed

    private void EnregistrerSousMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_EnregistrerSousMousePressed
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Sauvegarde");
        int userSelection = fileChooser.showSaveDialog(this);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            String fileName = fileToSave.getAbsolutePath();
            if(!fileName.endsWith(".cabanator")){
                fileName = fileName+".cabanator";
            }
            this.originalFile = fileName;
            this.Enregistrer.setVisible(true);
            Sauvegarde save = new Sauvegarde(this.controlleur);
            try {
                FileOutputStream fileOut = new FileOutputStream(fileName);
                ObjectOutputStream out = new ObjectOutputStream(fileOut);
                out.writeObject(save);
                out.close();
            }catch(Exception i){
                i.printStackTrace();
            }
        }
    }//GEN-LAST:event_EnregistrerSousMousePressed

    private void EnregistrerMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_EnregistrerMousePressed
        Sauvegarde save = new Sauvegarde(this.controlleur);
            try {
                FileOutputStream fileOut = new FileOutputStream(this.originalFile);
                ObjectOutputStream out = new ObjectOutputStream(fileOut);
                out.writeObject(save);
                out.close();
                JOptionPane.showMessageDialog(this, "Sauvegarde complétée.");
            }catch(Exception i){
                i.printStackTrace();
            }
    }//GEN-LAST:event_EnregistrerMousePressed

    private void OuvrirMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_OuvrirMousePressed
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Ouvrir");
        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            String fileName = file.getAbsolutePath();
            if(fileName.endsWith(".cabanator")){
                dispose();
                MainWindow main = new MainWindow("Cabanator", file);
                main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                main.pack();
                main.setVisible(true);
            }else{
                System.out.println("Not finishing in .cabanator");
            }
        }
        miseAJourCout();
    }//GEN-LAST:event_OuvrirMousePressed

    private void RedoButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RedoButtonActionPerformed
        this.controlleur.redo();
        this.largeurPlancher.setText(this.controlleur.getLargeurCabanonString());
        this.profondeurPlancher.setText(this.controlleur.getProfondeurCabanonString());
        this.Largeur.setText(this.controlleur.getLargeurCabanonString());
        this.Profondeur.setText(this.controlleur.getProfondeurCabanonString());
        this.DistanceSolive.setText(this.controlleur.getEspacementSolivesString());
        this.HauteurMurs.setText(this.controlleur.getHauteurMursString());
        this.ModifHauteurMurs.setText(this.controlleur.getHauteurMursString());
        this.NordDistanceMontant.setText(this.controlleur.getEspacementMontantsMurString(Nord));
        this.SudDistanceMontant.setText(this.controlleur.getEspacementMontantsMurString(Sud));
        this.EstDistanceMontant.setText(this.controlleur.getEspacementMontantsMurString(Est));
        this.OuestDistanceMontant.setText(this.controlleur.getEspacementMontantsMurString(Ouest));
        for(AddedAccessoryPane pane : this.panes.values()){
        this.updatePane(pane.getID());
        }
        AngleToit.setText(UnitConverter.fractionator(this.controlleur.getToit().AngleToit));
        //TextFieldEntremiseFerme.setText(UnitConverter.fractionator(this.controlleur.getEspacementEntremiseDebord()));
        PorteAFaux.setText(UnitConverter.fractionator(this.controlleur.getToit().PorteAFaux));
        DistanceFermes.setText(UnitConverter.fractionator(this.controlleur.getToit().EspacementFermes));
        DistanceEntremise.setText(UnitConverter.fractionator(this.controlleur.getToit().EspacementEntremisesDebor));
        Debordement.setText(UnitConverter.fractionator(this.controlleur.getToit().LongueurDebordement));
        this.refreshVisualizer();
        miseAJourCout();
    }//GEN-LAST:event_RedoButtonActionPerformed

    private void jButton18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton18ActionPerformed
        new CostPane(this.controlleur, this).setVisible(true);
    }//GEN-LAST:event_jButton18ActionPerformed

    private void UndoButtonMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_UndoButtonMousePressed
        this.controlleur.undo();
        this.largeurPlancher.setText(this.controlleur.getLargeurCabanonString());
        this.profondeurPlancher.setText(this.controlleur.getProfondeurCabanonString());
        this.Largeur.setText(this.controlleur.getLargeurCabanonString());
        this.Profondeur.setText(this.controlleur.getProfondeurCabanonString());
        this.DistanceSolive.setText(this.controlleur.getEspacementSolivesString());
        this.HauteurMurs.setText(this.controlleur.getHauteurMursString());
        this.ModifHauteurMurs.setText(this.controlleur.getHauteurMursString());
        this.NordDistanceMontant.setText(this.controlleur.getEspacementMontantsMurString(Nord));
        this.SudDistanceMontant.setText(this.controlleur.getEspacementMontantsMurString(Sud));
        this.EstDistanceMontant.setText(this.controlleur.getEspacementMontantsMurString(Est));
        this.OuestDistanceMontant.setText(this.controlleur.getEspacementMontantsMurString(Ouest));
        for(AddedAccessoryPane pane : this.panes.values()){
        this.updatePane(pane.getID());
        }
        AngleToit.setText(UnitConverter.fractionator(this.controlleur.getToit().AngleToit));
        //TextFieldEntremiseFerme.setText(UnitConverter.fractionator(this.controlleur.getEspacementEntremiseDebord()));
        PorteAFaux.setText(UnitConverter.fractionator(this.controlleur.getToit().PorteAFaux));
        DistanceFermes.setText(UnitConverter.fractionator(this.controlleur.getToit().EspacementFermes));
        DistanceEntremise.setText(UnitConverter.fractionator(this.controlleur.getToit().EspacementEntremisesDebor));
        Debordement.setText(UnitConverter.fractionator(this.controlleur.getToit().LongueurDebordement));
        this.refreshVisualizer();
        miseAJourCout();
    }//GEN-LAST:event_UndoButtonMousePressed

    private void AddEntremiseButtonMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AddEntremiseButtonMousePressed
        this.addEntremise();
        this.refreshVisualizer();
        miseAJourCout();
    }//GEN-LAST:event_AddEntremiseButtonMousePressed

    private void ValiderAngleToitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ValiderAngleToitActionPerformed
        this.controlleur.setAngleToit(this.AngleToit.getText());
        this.refreshVisualizer();
        miseAJourCout();
    }//GEN-LAST:event_ValiderAngleToitActionPerformed

    private void ButtonPorteAFauxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonPorteAFauxActionPerformed
        this.controlleur.setPorteAFaux(this.PorteAFaux.getText());
        this.refreshVisualizer();
        miseAJourCout();
    }//GEN-LAST:event_ButtonPorteAFauxActionPerformed

    private void ButtonDistanceFermesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonDistanceFermesActionPerformed

        this.controlleur.setEspacementFermes(this.DistanceFermes.getText());
        this.refreshVisualizer();
        miseAJourCout();

    }//GEN-LAST:event_ButtonDistanceFermesActionPerformed

    private void ButtonDebordementAvantActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonDebordementAvantActionPerformed
       
        this.controlleur.setLongueurDebordement(this.Debordement.getText());
        this.refreshVisualizer();
        miseAJourCout();

    }//GEN-LAST:event_ButtonDebordementAvantActionPerformed

    private void DistanceFermesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DistanceFermesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_DistanceFermesActionPerformed

    private void DistanceEntremiseValidationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DistanceEntremiseValidationActionPerformed
        this.controlleur.setEspacementEntDebord(this.DistanceEntremise.getText());
        this.refreshVisualizer();
        miseAJourCout();
    }//GEN-LAST:event_DistanceEntremiseValidationActionPerformed

    private void VisualizerComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_VisualizerComponentResized
        this.Visualizer.centerFrame();
    }//GEN-LAST:event_VisualizerComponentResized

    private void OrientationComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OrientationComboBoxActionPerformed
     String s = (String)OrientationComboBox.getSelectedItem();
          switch(s){
              case"Nord/Sud":
                  this.controlleur.setOrientationToit(1);
                  this.refreshVisualizer();
                  miseAJourCout();
                  this.HauteurTotale.setText((int)(this.controlleur.getToit().HauteurToit + this.controlleur.getHauteurMursCabanon())+"");
                  //this.ImageDimensions.setIcon(this.getIconImage(DimensionsCabanonToitNord.png));
                  break;
              case"Est/Ouest":
                  this.controlleur.setOrientationToit(2);
                  this.refreshVisualizer();
                  miseAJourCout();
                  this.HauteurTotale.setText((int)(this.controlleur.getToit().HauteurToit + this.controlleur.getHauteurMursCabanon())+"");
                  break;
                  
          }
    }//GEN-LAST:event_OrientationComboBoxActionPerformed

    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AddEntremiseButton;
    private javax.swing.JButton AddFenetreButton;
    private javax.swing.JButton AddLigneEntremiseButton;
    private javax.swing.JPanel AddLigneEntremisePlancher;
    private javax.swing.JButton AddPorteButton;
    private javax.swing.JTextField AngleToit;
    private javax.swing.JButton ButtonDebordementAvant;
    private javax.swing.JButton ButtonDistanceFermes;
    private javax.swing.JButton ButtonPorteAFaux;
    private javax.swing.JTabbedPane ChoixMur;
    private javax.swing.JToolBar Control_bar;
    private javax.swing.JFormattedTextField Debordement;
    private javax.swing.JTextField DistanceEntremise;
    private javax.swing.JButton DistanceEntremiseValidation;
    private javax.swing.JTextField DistanceFermes;
    private javax.swing.JFormattedTextField DistanceSolive;
    private javax.swing.JPanel DistanceSoliveContainer;
    private javax.swing.JButton EditDistanceSolive;
    private javax.swing.JButton EditPlancherSize;
    private javax.swing.JButton Enregistrer;
    private javax.swing.JButton EnregistrerSous;
    private javax.swing.JTextField EstDistanceMontant;
    private javax.swing.JPanel EstScrollContainer;
    private javax.swing.JLabel HauteurMurs;
    private javax.swing.JLabel HauteurTotale;
    private javax.swing.JLabel ImageDimensions;
    private javax.swing.JLabel LabelAngle;
    private javax.swing.JLabel LabelDebordement;
    private javax.swing.JLabel LabelFermes;
    private javax.swing.JLabel LabelO;
    private javax.swing.JLabel LabelOrientation;
    private javax.swing.JLabel LabelPorteàFaux;
    private javax.swing.JLabel Largeur;
    private javax.swing.JPanel LigneEntremiseContainer;
    private javax.swing.JTextField ModifHauteurMurs;
    private javax.swing.JPanel MurAccessoiresButtons;
    private javax.swing.JPanel MurHeader;
    private javax.swing.JPanel Murs;
    private javax.swing.JTextField NordDistanceMontant;
    private javax.swing.JButton NordDistanceMontantButton;
    private javax.swing.JPanel NordScrollContainer;
    private javax.swing.JButton OKButtonHauteurMurs;
    private javax.swing.JComboBox<String> OrientationComboBox;
    private javax.swing.JTextField OuestDistanceMontant;
    private javax.swing.JPanel OuestScrollContainer;
    private javax.swing.JButton Ouvrir;
    private javax.swing.JPanel PaneauDimensions;
    private javax.swing.JPanel Plancher;
    private javax.swing.JPanel PlancherHeader;
    private javax.swing.JTextField PorteAFaux;
    private javax.swing.JLabel Profondeur;
    private javax.swing.JButton RedoButton;
    private javax.swing.JButton ReglagesButton;
    private javax.swing.JTabbedPane Side_Panel;
    private javax.swing.JTextField SudDistanceMontant;
    private javax.swing.JPanel SudWallScrollContainer;
    private javax.swing.JPanel TabMurEst;
    private javax.swing.JPanel TabMurNord;
    private javax.swing.JPanel TabMurOuest;
    private javax.swing.JPanel TabMurSud;
    private javax.swing.JPanel Toit;
    private javax.swing.JScrollPane Toit_ScrollArea;
    private javax.swing.JButton UndoButton;
    private javax.swing.JButton ValiderAngleToit;
    private ca.ulaval.glo2004.visualizer.Visualizer Visualizer;
    private javax.swing.JPanel WallContainerHeader1;
    private javax.swing.JPanel WallContainerHeader2;
    private javax.swing.JPanel WallContainerHeader3;
    private javax.swing.JPanel WallContainerHeader4;
    private javax.swing.JScrollPane WallScroll1;
    private javax.swing.JScrollPane WallScroll2;
    private javax.swing.JScrollPane WallScroll3;
    private javax.swing.JScrollPane WallScroll5;
    protected javax.swing.JTextField champPrixMainWindow;
    private javax.swing.Box.Filler filler1;
    private javax.swing.Box.Filler filler2;
    private javax.swing.Box.Filler filler3;
    private javax.swing.Box.Filler filler4;
    private javax.swing.Box.Filler filler5;
    private javax.swing.Box.Filler filler6;
    private javax.swing.JButton jButton18;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator10;
    private javax.swing.JToolBar.Separator jSeparator11;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator25;
    private javax.swing.JSeparator jSeparator26;
    private javax.swing.JSeparator jSeparator27;
    private javax.swing.JSeparator jSeparator28;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JToolBar.Separator jSeparator5;
    private javax.swing.JToolBar.Separator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JFormattedTextField largeurPlancher;
    private javax.swing.JButton newButton;
    private javax.swing.JScrollPane panneauPlancherOptions;
    private javax.swing.JPanel panneauPlancherScrollContainer;
    private javax.swing.JFormattedTextField profondeurPlancher;
    private javax.swing.JMenuItem quitterButton;
    // End of variables declaration//GEN-END:variables
}
