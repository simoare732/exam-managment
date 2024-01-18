package control;

import model.saveLoad;
import model.tabModel;
import model.saveLoad;

import javax.swing.*;
import java.awt.event.*;
import java.io.File;

/**
 * Classe Listener che intercetta l'operazione di chiusura del frame. Avviato se la tabella e' stata modiicata e non salvata.
 * ATTENZIONE: anche se la tabella e' salvata sul file temporaneo, comunque viene chiesto se salvare
 * @author Aresta Simone
 * @version 1.0
 */
public class closeListener extends WindowAdapter {
    /** TableModel della tabella */
    private tabModel tM;

    /**
     * Metodo costruttore a cui viene passato il TableModel della tabella
     * @param tM TableModel della tabella
     */
    public closeListener(tabModel tM){
        this.tM = tM;
    }

    /**
     * Metodo invocato quando il frame principale è in chiusura
     * @param e L'evento che sarà processato
     */
    @Override
    public void windowClosing(WindowEvent e) {
        if(tM.getIsDirty()){
            int result = JOptionPane.showConfirmDialog(null, "Vuoi salvare prima di uscire?", "Conferma", JOptionPane.YES_NO_OPTION);

            if(result == JOptionPane.YES_OPTION) {
                JFileChooser fileChooser = saveLoad.chooseFile("Salva su file", "dat");

                fileChooser.setSelectedFile(new File("tabella.dat"));

                int userSelection = fileChooser.showSaveDialog(null);

                if(userSelection==JFileChooser.APPROVE_OPTION){
                    File selectedFile = fileChooser.getSelectedFile();

                    if(selectedFile.exists()){
                        int ch = JOptionPane.showConfirmDialog(null,"Il file esiste già, vuoi sovrascriverlo?", "Conferma sovrascrittura", JOptionPane.YES_NO_OPTION);
                        if(ch != JOptionPane.YES_OPTION) return;
                    }
                    saveLoad.saveTableToFile(tM, selectedFile);
                }
                return;
            }
            else return;
        }


    }
}
