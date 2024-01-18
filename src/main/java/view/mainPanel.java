package view;

import control.*;
import model.*;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

/**
 * Classe che rappresenta la il pannello principale dell'applicazione
 * @author Aresta Simone
 * @version 1.0
 */
public class mainPanel extends JPanel{
    /** Tabella visualizzata */
    private JTable tabella;
    /** TableModel della tabella */
    private tabModel tM;
    /** 3 bottoni per aggiungere, modificare, rimuove voci */
    private JButton Badd, Bmodify, Bremove;
    /** Filtro sugli studenti per decidere quali studenti visualizzare */
    private JComboBox <String> filterStud;
    /** Filtro sugli insegnamenti per decidere quali insegnamenti visualizzare */
    private JComboBox <String> filterIns;
    /** Bottone per la visualizzazione dell'istogramma*/
    private JButton Bhist;

    /**
     * Metodo costruttore che crea il pannello principale dell'applicazione
     */
    public mainPanel(){
        super();
        setLayout(new BorderLayout());
        //Gestione esami
        ArrayList<entry> entrys = new ArrayList<>(); //Inizializzo l'array dinamico che conterrà le voci della tabella

        tM = new tabModel(entrys);
        tabella = new JTable(tM);
        tabella.getTableHeader().setReorderingAllowed(false); //Disabilita il trascinamento dellle colonne

        JScrollPane tab = new JScrollPane(tabella);
        tabella.addMouseListener(new tableExamsCompListener(tabella, this));

        filterStud = new JComboBox();
        filterStud.setEditable(true);
        filterStud.setActionCommand("studente");
        //Lo aggiorno subito così da inserirci subito l'opzione (nessun filtro) ed evitare errori
        comboBoxListener.updateComboBox(filterStud, tM);


        filterIns = new JComboBox();
        filterIns.setEditable(true);
        filterIns.setActionCommand("materia");
        //Lo aggiorno subito così da inserirci subito l'opzione (nessun filtro) ed evitare errori
        comboBoxListener.updateComboBox(filterIns, tM);


        Bhist = new JButton("Grafico");

        filterStud.addActionListener(new comboBoxListener(filterIns, tM, Bhist));
        filterIns.addActionListener(new comboBoxListener(filterStud, tM, Bhist));

        JPanel buttonPanel = new JPanel(); //Pannello dove inserire i bottoni

        Badd = new JButton("Aggiungi");
        Badd.addActionListener(new butListener(tabella, Badd, this, filterStud, filterIns));
        Bmodify = new JButton("Modifica");
        Bmodify.addActionListener(new butListener(tabella, Bmodify, this, filterStud, filterIns));
        Bremove = new JButton("Rimuovi");
        Bremove.addActionListener(new butListener(tabella, Bremove, this, filterStud, filterIns));
        buttonPanel.add(Badd);
        buttonPanel.add(Bmodify);
        buttonPanel.add(Bremove);

        add(tab, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        JPanel filterPanel = new JPanel();
        JPanel northArea = new JPanel(new BorderLayout());
        JLabel l1 = new JLabel("Filtra per:     Studenti ");
        JLabel l2 = new JLabel("Materia");
        filterPanel.add(l1);
        filterPanel.add(filterStud);
        filterPanel.add(l2);
        filterPanel.add(filterIns);

        JButton average = new JButton("Media");
        average.addActionListener(new averageListener(this));
        filterPanel.add(average);

        northArea.add(filterPanel, BorderLayout.CENTER);
        add(northArea, BorderLayout.NORTH);

        saveTmp thrd = new saveTmp(tM);
        thrd.start();

        //Salvataggio e caricamento esami

        JMenuItem saveOnFile = new JMenuItem("Salva su file");
        JMenuItem load = new JMenuItem("Carica");
        saveOnFile.addActionListener(new menuListener(tM));
        load.addActionListener(new menuListener(tM, filterStud, filterIns));

        JMenu menu1 = new JMenu("File");
        menu1.add(saveOnFile);
        menu1.add(load);

        JMenuBar menuBar = new JMenuBar();
        menuBar.add(menu1);

        northArea.add(menuBar, BorderLayout.NORTH);
        menuBar.setBackground(Color.getHSBColor(0,0,60));

        //Visualizzazione dati statistici

        Bhist.setVisible(false);
        buttonPanel.add(Bhist);
        Bhist.addActionListener(new chartListener(filterStud, filterIns, tM));

        //Stampa tabella
        JMenuItem printTab = new JMenuItem("Stampa...");
        printTab.addActionListener(new printListener(tabella));
        menu1.add(printTab);

    }

    /**
     * Metodo che restituisce il TableModel della tabella
     * @return il TableModel della tabella
     */
    public tabModel getTabModel(){
        return tM;
    }


}
