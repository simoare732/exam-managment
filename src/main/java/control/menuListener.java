package control;

import model.saveLoad;
import model.tabModel;
import model.entry;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

/**
 * Classe Listener per il menu dell'applicazione
 * @author Aresta Simone
 * @version 1.0
 */
public class menuListener implements ActionListener {
    /** TableModel della tabella */
    private tabModel tM;
    /** Tabella dell'applcazione */
    private JTable tab;
    /** Filtri per studente e della materia */
    private JComboBox stud, ins;

    /**
     * Metodo costruttore che inizializza gli attributi ai valori passati come  parametri (utile per il tasto carica)
     * @param tM TableModel della tabella
     * @param stud Filtro studente dell'applicazione
     * @param ins Filtro della materia dell'applicazione
     */
    public menuListener(tabModel tM, JComboBox stud, JComboBox ins){
        this.tM = tM;
        this.stud = stud;
        this.ins = ins;
    }

    /**
     * Metodo costruttore che inizializza solo TableModel al valore passato come parametro  (utile per il tasto salva)
     * @param tM TableModel della tabella
     */
    public menuListener(tabModel tM){
        this.tM=tM;
    }


    /**
     * Invoca questo metodo quando viene premuto salva file o carica da file.
     *
     * @param e L'evento che sarà processato
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("Carica")){
            /*
            * Scelgo il file da cui caricare e se non chiudo prima la finestra allora:
            * salvo in un ArrayList tmp l'arrayList salvato precedetemente su file
            * poi imposto nella tabella sia l'arrayList delle voci visualizzate che l'arrayList con tutte le voci
            */
            JFileChooser fileChooser = saveLoad.chooseFile("Carica tabella", "dat");

            int userSelection = fileChooser.showSaveDialog(null);
            File selectedFile = fileChooser.getSelectedFile();

            if(userSelection==JFileChooser.APPROVE_OPTION){
                ArrayList <entry> tmp;
                tmp = saveLoad.loadFromFile(selectedFile);
                //System.out.println(tmp.getEntrys().get(0).getNome());
                tM.setEntrys(tmp);
                tM.setEntrysAll(tmp);
                tM.fireTableDataChanged();

                comboBoxListener.updateComboBox(stud, tM);
                comboBoxListener.updateComboBox(ins, tM);

            }
        }
        else{
            JFileChooser fileChooser = saveLoad.chooseFile("Salva tabella", "dat");

            // Suggerisci un esempio di nome del file
            fileChooser.setSelectedFile(new File("tabella.dat"));

            int userSelection = fileChooser.showSaveDialog(null);

            if(userSelection==JFileChooser.APPROVE_OPTION){
                File selectedFile = fileChooser.getSelectedFile();

                if(selectedFile.exists()){
                    int ch = JOptionPane.showConfirmDialog(null,"Il file esiste già, vuoi sovrascriverlo?", "Conferma sovrascrittura", JOptionPane.YES_NO_OPTION);
                    if(ch != JOptionPane.YES_OPTION) return;
                }
                saveLoad.saveTableToFile(tM, selectedFile);
                tM.setIsDirty(false); //è stato salvato quindi non è più "sporco"
            }
        }

    }
}
