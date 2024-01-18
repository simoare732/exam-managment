package control;

import model.tabModel;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Classe Listener che gestisce la visualizzazione del popup se premo su una voce della tabella con esame composto
 * @author Aresta Simone
 * @version 1.0
 */
public class tableExamsCompListener extends MouseAdapter {
    /** Tabella dell'applicazione */
    private JTable tabella;
    /** Pannello principale dell'applicazione */
    private JPanel panel;

    /**
     * Metodo costruttore che inizializza gli attributi ai valori passati come parametri
      * @param tabella Tabella dell'applicazione
     * @param panel Pannello principale dell'applicazione
     */
    public tableExamsCompListener(JTable tabella, JPanel panel){
        this.tabella=tabella;
        this.panel=panel;
    }

    /**
     * Quando viene selezionato il voto di un esame che ha l'esame composto, visualizza i vari esami di cui è composto
     * @param e L'evento che sarà processato
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        int row = tabella.rowAtPoint(e.getPoint());
        int col = tabella.columnAtPoint(e.getPoint());
        tabModel tM = (tabModel) tabella.getModel();

        // Prima di visualizzare il popup controllo che effettivamente sia un esame composto, questo controllando se l'array di voti è vuoto o meno
        //inoltre controllo se la colonna premuta è quella dei voti
        if(tM.getEntrys().get(row).getArrVoti()!=null && col==3){
            StringBuilder messaggio = new StringBuilder("Voti delle prove intermedie:\n");

            int size = tM.getEntrys().get(row).getArrVoti().length;
            for(int i=0;i<size;i++){
                messaggio.append("Prova ").append(i + 1).append(": ").append(tM.getEntrys().get(row).getVoti(i));
                messaggio.append(", Peso: ").append(tM.getEntrys().get(row).getPesi(i)).append("%\n");
            }
            JOptionPane.showMessageDialog(panel, messaggio.toString(), "Risultati prove intermedie",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
