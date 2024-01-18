package control;

import model.tabModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Classe Listener per i filtri per la tabella
 * @author Aresta Simone
 * @version 1.0
 */
public class comboBoxListener implements ActionListener {
    /** Filtro della tabella considerato */
    private JComboBox cb;
    /** TableModel della tabella */
    private tabModel tM;
    /** Bottone per la visualizzazione del grafico*/
    private JButton Bhist;

    /**
     * Metodo costruttore che inizializza gli attributi ai valori passati come parametri
     * @param cb Filtro considerato
     * @param tM TableModel della tabella
     * @param Bhist Bottone grafico
     */
    public comboBoxListener(JComboBox cb, tabModel tM, JButton Bhist){
        this.cb=cb;
        this.tM = tM;
        this.Bhist=Bhist;
    }

    /**
     * Aggiorna la JComboBox inserendo ogni volta la lista aggiornata dei nomi/materie disponibili
     * @param c JComboBox da aggiornare
     * @param tM TableModel di riferimento
     */
    public static void updateComboBox(JComboBox c, tabModel tM){
        c.removeAllItems();
        c.addItem("(nessun filtro)");
        int ch = (c.getActionCommand().equals("studente"))?0:1; //se m è uguale a s restituisci 0 altrimenti 1
        ArrayList<String> names = tM.getUniqueName(ch);

        for(int i=0;i<names.size();i++)
            c.addItem(names.get(i));
    }

    /**
     * Metodo per verificare se una stringa è presente in un JComboBox. Restituisce true se una Stringa è presente nel JComboBox c passato come parametro, false altrimenti
     * @param c JComboBox in cui controllare
     * @param searchString Stringa da confrontare
     * @return True se searchString è presente in c, false altrimenti
     */
    public static boolean isInComboBox(JComboBox c, String searchString){
        int size = c.getModel().getSize();
        for(int i=0;i<size;i++){
            String current = (String)c.getModel().getElementAt(i);
            if(current != null && current.equalsIgnoreCase(searchString))
                return true; //La stringa è presente
        }

        return false; //La stringa non è presente
    }

    /**
     * Invocato quando un filtro viene modificato
     *
     * @param e L'evento che sarà processato
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("comboBoxEdited")) return; //inserito perchè altrimenti creava errori di logica

        /*viene lanciato lo stesso codice indipendentemente da quale filtro viene modificato
        in name c'è la stringa del filtro chiamante, in other c'è la stringa dell'altro filtro
         controllo prima se name è nullo, altrimenti mi da errore. A questo punto mi chiedo se il filtro chiamante
        chiede nessun filtro, se si rimuovo i filtri, e se l'altro ha dei filtri, filtro solo tramite l'altro
        se invece il filtro chiamante è diverso di "(nessun filtro)", controllo se l'altro è uguale a nessun filtro,
        se si filtro solo tramite il filtro chiamante, se no filtro tramite entrambi
         */

        String name = (String) (((JComboBox)e.getSource()).getSelectedItem());
        String other = (String) cb.getSelectedItem();
        if(name!=null) {
            if (name.equals("(nessun filtro)")) {
                tM.removeFilter();
                Bhist.setVisible(false);
                if (!other.equals("(nessun filtro)")) {
                    if (e.getActionCommand().equals("studente")) tM.filterTableBySubject(other);
                    else if(e.getActionCommand().equals("materia")) tM.filterTableByName(other);

                    if(!tM.getEntrys().isEmpty() && isInComboBox(cb, other)) Bhist.setVisible(true);
                    else Bhist.setVisible(false);
                }
            }
            else if (other.equals("(nessun filtro)")) {
                if (e.getActionCommand().equals("studente")) tM.filterTableByName(name);
                else if(e.getActionCommand().equals("materia")) tM.filterTableBySubject(name);

                if(!tM.getEntrys().isEmpty() && isInComboBox(((JComboBox)e.getSource()), name)) Bhist.setVisible(true);
                else Bhist.setVisible(false);
            }
            else {
                if (e.getActionCommand().equals("studente")){
                    tM.filterTableByName_Subject(name, other);
                    Bhist.setVisible(false);
                }
                else{
                    tM.filterTableByName_Subject(other, name);
                    Bhist.setVisible(false);
                }
            }
        }

    }
}


