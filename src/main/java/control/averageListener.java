package control;

import model.entry;
import model.insegnamento;
import model.tabModel;
import view.mainPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Classe Listener per il tasto media
 * @author Aresta Simone
 * @version 1.0
 */
public class averageListener implements ActionListener {
    /** TableModel della tabella */
    private tabModel tM;
    /** Pannello principale dell'applicazione */
    private mainPanel panel;

    /**
     * Metodo costruttore che inizializza il TableModel e il pannello
     * @param panel pannello principale dell'applicazione
     */
    public averageListener(mainPanel panel){
        this.panel=panel;
        tM = this.panel.getTabModel();
    }

    /**
     * Invocato quando il bottone media viene premuto
     *
     * @param e l'evento che sarà processato
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        ArrayList <entry> entrys = tM.getEntrys();
        if(entrys.isEmpty()){
            JOptionPane.showMessageDialog(panel, "Non ci sono voci in tabella","Errore di output", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int voti[] = new int[entrys.size()];
        int pesi[] = new int[entrys.size()];
        for(int i=0;i<entrys.size();i++){
            voti[i] = entrys.get(i).getVoto();
            pesi[i] = entrys.get(i).getCfu();
        }
        double avgWeighetd = entry.mediaPesata(voti,pesi);
        //Mostra il pannello con la media. La media ha al massimo 2 cifre dopo la virgola
        JOptionPane.showMessageDialog(panel, "La media pesata dei voti è: "+String.format("%.1f", avgWeighetd));
    }
}
