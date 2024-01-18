package control;

import model.entry;
import model.insegnamento;
import model.studente;
import model.tabModel;
import view.mainPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

/**
 * Classe Listener per i pulsanti aggiungi, modifica, rimuovi
 * @author Aresta Simone
 * @version 1.0
 */

public class butListener implements ActionListener {
    /** Pulsante considerato */
    private JButton but;
    /** Tabella dell'apllicazione */
    private JTable tabella;
    /** Pannello principale dell'applicazione */
    private mainPanel panel;
    /** TableModel della tabella */
    private tabModel tM;
    /** Filtro per lo studente */
    private JComboBox stud;
    /** Filtro per la mteria */
    private JComboBox ins;

    /**
     * Metodo costruttore con parametri la tabella che andremo a modificare e il bottone premuto tra modifica, aggiungi e rimuovi
     * @param tabella tabella con gli esami
     * @param but bottone che viene premuto tra aggiungi, modifica e rimuovi
     * @param panel pannello contenente la tabella
     * @param stud filtro per il nome dello studente
     * @param ins filtro per il nome della materia
     */
    public butListener(JTable tabella, JButton but, mainPanel panel, JComboBox stud, JComboBox ins){
        this.tabella = tabella;
        this.but = but;
        this.panel=panel;
        tM = (tabModel) tabella.getModel();
        this.stud=stud;
        this.ins=ins;
    }

    /**
     * Invocato quando un bottone viene premuto.
     * Essendoci 3 bottoni è essenziale distinguere quali dei 3 è stato premuto.
     *
     * @param e l'evento processato
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        String nameButton = e.getActionCommand();
        entry ent = null;
        switch(nameButton){
            case "Aggiungi":
                ent = addMethod(-1);
                if(ent == null) return;
                tM.aggiungiRiga(ent);
                break;
            case "Modifica":
                ent = modifyMethod();
                if(ent == null) return;
                tM.sostituisciRiga(tabella.getSelectedRow(), ent);
                break;
            case "Rimuovi":
                boolean eliminato = delMethod();
                if(!eliminato) return;
                break;
        }

        tM.setIsDirty(true); //è stato modificato quindi ora è "sporco"
        comboBoxListener.updateComboBox(stud, tM);
        comboBoxListener.updateComboBox(ins, tM);
    }

    /**
     * Metodo privato che implementa i vari popup quando premi il tasto aggiungi
     * Ogni popup è implementato con un JOptionPane.showInputDialog(panel, "Testo"));
     * @param row Se bisogna modificare una riga, row prende la riga da modificare, -1 altrimenti
     * @return Ritorna la riga da aggiungere in tabella
     */
    private entry addMethod(int row){
        String [] types = {"Esame Semplice", "Esame Composto"};
        entry ent = new entry();

        //I 3 popup con nome studente, cognome, nome dell'insegnamento
        //se row è diverso da -1 mostra il popup con il nome precedentemente immesso
        if(row != -1)
            ent.setNome(JOptionPane.showInputDialog(panel, "Nome studente:", tM.getEntrys().get(row).getNome()));
        else ent.setNome(JOptionPane.showInputDialog(panel, "Nome studente:"));
        if(ent.getNome()==null) return null;

        if(row != -1) ent.setCognome(JOptionPane.showInputDialog(panel, "Cognome studente:", tM.getEntrys().get(row).getCognome()));
        else ent.setCognome(JOptionPane.showInputDialog(panel, "Cognome studente:"));
        if(ent.getCognome()==null) return null;

        if(row != -1) ent.setNomeMateria(JOptionPane.showInputDialog(panel, "Nome dell'insegnamento:", tM.getEntrys().get(row).getNomeMateria()));
        else ent.setNomeMateria(JOptionPane.showInputDialog(panel, "Nome dell'insegnamento:"));
        if(ent.getNomeMateria()==null) return null;

        int choice = JOptionPane.showOptionDialog(panel, "Seleziona il tipo di esame", "Scelta Tipo Esame",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, types, types[0]);
        if(choice==JOptionPane.CLOSED_OPTION) return null;

        boolean controllo = false;
        if(choice==0){ //Se l'opzione è esame semplice
            while(!controllo){ //Rimane nel ciclo while finchè il voto immesso non è valido
                try{
                    String tmp;
                    if(row!=-1) tmp = JOptionPane.showInputDialog(panel, "Voto finale (18-30):", tM.getEntrys().get(row).getVoto());
                    else tmp = JOptionPane.showInputDialog(panel, "Voto finale (18-30):");
                    if(tmp==null) return null; //Ho messo una stringa di mezzo per dare la possibilità di premere cancel
                    ent.setVoto(Integer.parseInt(tmp));
                    if(ent.getVoto()<18 || ent.getVoto()>30) throw new NumberFormatException();
                    controllo = true;
                }catch(NumberFormatException exc){
                    JOptionPane.showMessageDialog(panel, "Inserire un valore numerico valido.",
                            "Errore di input", JOptionPane.ERROR_MESSAGE);
                }
            }

        }
        else{ //Esame composto
            int numTests=0;
            while(numTests==0){ //Viene visualizzato il popup del numero di prove intermedie finchè non c'è un numero valido
                try{
                    String tmp;
                    //Se la riga è valida e se l'esame precedentemente immesso era composto, visualizza il vecchio numero di prove
                    if(row != -1 && tM.getEntrys().get(row).getArrVoti()!=null) tmp = JOptionPane.showInputDialog(panel, "Numero di prove intermedie:", tM.getEntrys().get(row).getArrVoti().length);
                    else tmp = JOptionPane.showInputDialog(panel, "Numero di prove intermedie:");
                    if(tmp==null) return null; //Ho messo una stringa di mezzo per dare la possibilità di premere cancel
                    numTests = Integer.parseInt(tmp);
                }catch(NumberFormatException exc){
                    JOptionPane.showMessageDialog(panel, "Inserire un valore numerico valido.",
                            "Errore di input", JOptionPane.ERROR_MESSAGE);
                    numTests=0;
                }
            }
            ent.esameCompostoVoti(numTests);
            String tmp;
            int vf = 0;

            //In questo ciclo for vengono visualizzati numTests volte le finestre per inserire il voto della la prova intermedia e il proprio peso
            //ATTENZIONE: la somma delle percentuali deve essere 100 (o al massimo 99)
            controllo=false;
            while(!controllo){
                for(int i=0;i<numTests;i++){
                    try{
                        tmp = JOptionPane.showInputDialog(panel, "Inserisci voto per la prova intermedia " + (i + 1) + ":");
                        if(tmp==null) return null; //Ho messo una stringa di mezzo per dare la possibilità di premere cancel
                        if(Integer.parseInt(tmp)<0 || Integer.parseInt(tmp)>30) throw new NumberFormatException();
                        ent.setVoti(Integer.parseInt(tmp), i);

                        tmp = JOptionPane.showInputDialog(panel, "Inserisci peso (%) per la prova intermedia  " + (i + 1) + ":");
                        if(tmp==null) return null; //Ho messo una stringa di mezzo per dare la possibilità di premere cancel
                        ent.setPesi(Integer.parseInt(tmp), i);

                    }catch(NumberFormatException exc){
                        JOptionPane.showMessageDialog(panel, "Inserire un valore numerico valido.",
                                "Errore di input", JOptionPane.ERROR_MESSAGE);
                        i--;
                    }
                }
                vf = (int) ent.mediaPesata(ent.getArrVoti(), ent.getArrPesi());
                if(Arrays.stream(ent.getArrPesi()).sum() != 100 && Arrays.stream(ent.getArrPesi()).sum() != 99)
                    JOptionPane.showMessageDialog(panel, "ATTENZIONE: la somma percentuale deve essere 100", "Errore di input", JOptionPane.ERROR_MESSAGE);
                else if (vf < 18 || vf > 30)
                    JOptionPane.showMessageDialog(panel, "ATTENZIONE: la media dei voti deve essere nel range (18-30)", "Errore di input", JOptionPane.ERROR_MESSAGE);
                else controllo = true;
            }
            ent.setVoto(vf);
            ent.setLode(false);

        }

        //Per decidere se si vuole la lode o meno. Richiesto solo se il voto è 30
        if(ent.getVoto()==30){
            String [] opzs = {"OK"};
            JRadioButton opz1 = new JRadioButton("Si");
            JRadioButton opz2 = new JRadioButton("No");
            ButtonGroup grp = new ButtonGroup(); grp.add(opz1); grp.add(opz2);
            JPanel radioPanel = new JPanel(); radioPanel.add(opz1); radioPanel.add(opz2);
            int ch = JOptionPane.showOptionDialog(panel, radioPanel, "Lode: ", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, opzs, opzs[0]);
            if(ch==JOptionPane.CLOSED_OPTION) return null;
            ent.setLode(opz1.isSelected());
        }

        //Gestisco il popup che chiede i CFU
        controllo=false;
        while(!controllo){
            try{
                String tmp;
                if(row!=-1) tmp = JOptionPane.showInputDialog(panel, "Numero crediti:", tM.getEntrys().get(row).getCfu());
                else tmp = JOptionPane.showInputDialog(panel, "Numero crediti:");
                if(tmp==null) return null;
                ent.setCfu(Integer.parseInt(tmp));
                if(ent.getCfu()<0) throw new NumberFormatException();
                controllo = true;
            }catch(NumberFormatException exc){
                JOptionPane.showMessageDialog(panel, "Inserire un valore numerico valido.",
                        "Errore di input", JOptionPane.ERROR_MESSAGE);
            }
        }

        //Ritorno la riga per poter essere inserita in tabella
        return ent;

    }

    /**
     * Metodo che modifica la riga della tabella selezionata.
     * ATTENZIONE: i voti intermedi non vengono di nuovo visualizzati durante la modifica, quindi vanno rimmessi.
     * @return Ritorna la riga modificata
     */
    private entry modifyMethod(){
        int selectedRow = tabella.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(panel, "Seleziona una riga per modificare l'esame.",
                    "Errore", JOptionPane.ERROR_MESSAGE);
            return null;
        }
        entry ent = addMethod(selectedRow);
        return ent;

    }

    /**
     * Metodo che elimina la riga della tabella selezionata
     * @return Ritorna un valore booleano che è true se la riga è stata cancellata, false altrimenti
     */
    private boolean delMethod(){
        int selectedRow = tabella.getSelectedRow();
        int ch;
        //Mostra il popup di selezionare una riga
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(panel, "Seleziona una riga per eliminare l'esame.",
                    "Errore", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        //Mostra il popup se l'utente è sicuro di cancellare la voce selezionata
        else ch = JOptionPane.showConfirmDialog(panel, "Sicuro di voler cancellare la voce selezionata?", "ATTENZIONE", JOptionPane.OK_CANCEL_OPTION);
        if (ch == JOptionPane.CANCEL_OPTION || ch == JOptionPane.CLOSED_OPTION)
            return false;
        tM.eliminaRiga(selectedRow);
        return true;
    }


}
