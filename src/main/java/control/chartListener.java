package control;

import model.tabHistogram;
import model.tabModel;
import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.SwingWrapper;
import view.mainPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Classe Listener per il tasto grafico
 * @author Aresta Simone
 * @version 1.0
 */

public class chartListener implements ActionListener {
    /** Filtro studente della tabella*/
    private JComboBox stud;
    /** Filtro insegnamento della tabella*/
    private JComboBox ins;
    /** TableModel della tabella*/
    private tabModel tM;

    /**
     * Metodo costruttore che inizializza i filtri studente e insegnamento e il TableModel della tabella
     * @param stud Filtro studente
     * @param ins Filtro insegnante
     * @param tM TableModel della tabella
     */
    public chartListener(JComboBox stud, JComboBox ins, tabModel tM){
        this.stud=stud;
        this.ins=ins;
        this.tM=tM;
    }

    /**
     * Invocato quando il pulsante grafico viene premuto.
     *
     * @param e L'evento che sarà processato
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        // Istogramma
        tabHistogram hist;

        if(stud.getSelectedItem().equals("(nessun filtro)")){
            hist = new tabHistogram(tM, true, tM.getEntrys().get(0).getNomeMateria());
        }
        else hist = new tabHistogram(tM, false, tM.getEntrys().get(0).getNome()+" "+tM.getEntrys().get(0).getCognome());

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                // SwingWrapper, avvolgiamo un categorychart in uno swingWrapper, per semplificare la visualizzazione di
                // un grafico a barre in una applicazione Java Swing
                SwingWrapper<CategoryChart> wrapper = new SwingWrapper<>(hist.getChart());
                // Questo crea un JFrame apposito per visualizzare l'istogramma
                JFrame chartPanel = wrapper.displayChart();
                chartPanel.setVisible(true);
                chartPanel.setSize(600,500);
                chartPanel.setMinimumSize(new Dimension(300,300));
                chartPanel.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            }
        });
        t.start();



    }
}
