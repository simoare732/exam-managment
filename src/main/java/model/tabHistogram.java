package model;

import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.style.Styler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Classe che rappresenta il grafico che si andrà a visualizzare
 * @author Aresta Simone
 * @version 1.0
 */
public class tabHistogram {
    /** Grafico da visualizzare*/
    private CategoryChart chart;

    /**
     * Metodo costruttore che inizializza il grafico che si andrà a vedere.
     * @param tM TableModel della tabella
     * @param valX Assume vero se il grafico da visualizzare è di una specifica materia, falso se è di uno specifico studente
     * @param name Il nome della materia/studente di cui si sta vedendo il grafico
     */
    public tabHistogram(tabModel tM, boolean valX, String name){

        Map<String, Integer> data = new HashMap<>();
        ArrayList <entry> entrys = tM.getEntrys();

        if(valX) {
            chart = new CategoryChartBuilder().width(800).height(600).title("Istogramma").xAxisTitle("Studenti").yAxisTitle("Voti").build();
            for(int i=0;i<entrys.size();i++){
                data.put(entrys.get(i).getNome()+" "+entrys.get(i).getCognome(), entrys.get(i).getVoto());
            }
        }
        else{
            chart = new CategoryChartBuilder().width(800).height(600).title("Istogramma").xAxisTitle("Materie").yAxisTitle("Voti").build();
            for(int i=0;i<entrys.size();i++){
                data.put(entrys.get(i).getNomeMateria(), entrys.get(i).getVoto());
            }
        }
        chart.getStyler().setYAxisMin(0.0); // Imposta il valore minimo sull'asse Y
        chart.getStyler().setYAxisMax(30.0); // Imposta il valore massimo sull'asse Y
        chart.getStyler().setLegendPosition(Styler.LegendPosition.OutsideS); //Imposta la posizione della legenda fuori dal grafico
        chart.getStyler().setLegendSeriesLineLength(name.length()+5); //Imposta la lunghezza della linea della legenda. +5 perchè mi sembra la lunghezza giusta

        List<String> xAx= new ArrayList<String>(data.keySet());
        List<Integer> yAx= new ArrayList<Integer>(data.values());

        chart.addSeries(name, xAx, yAx, null);
    }

    /**
     * Metodo che restituisce il grafico
     * @return grafico creato
     */
    public CategoryChart getChart(){
        return chart;
    }
}
