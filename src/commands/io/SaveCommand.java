package commands.io;

import commands.Command;
import graphics.InfoBox;
import sudoku.Sudoku;
import sudoku.Type;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

/**
 * Trieda reprezentuje prikaz, ktory zabezpeci ulozenie sudoku do suboru, ktory je pren
 * predvoleny (alebo do predvoleneho suboru, ak sudoku ziaden nastaveny nema)
 */
public class SaveCommand implements Command {

    private final Sudoku sudoku;
    private final InfoBox infoBox;

    /**
     * @param sudoku aktualne sudoku
     * @param infoBox tabula, na ktoru sa vypisuju hlasky o cinnosti
     */
    public SaveCommand(Sudoku sudoku, InfoBox infoBox) {
        this.sudoku = sudoku;
        this.infoBox = infoBox;
    }

    /** Funkcia vrati retazec reprezentujuci ciselny obsah mriezky  */
    private String sudokuToFile() {
        StringBuffer s = new StringBuffer();
        for (int i=0; i<9; i++){
            for (int j=0; j<9; j++){
                String text = sudoku.getInputGrid().getText(i,j);
                if (text.equals("")){
                    s.append(".");
                }
                else{
                    s.append(text);
                }
            }
            s.append("\n");
        }
        return new String(s);
    }

    /** Funkcia vrati retazec, ktory reprezentuje typy ukladaneho sudoku spolu so zoznamami
     * policok, ktore reprezentuju niektore z nich (napriklad zoznam parnych policok) */
    private String typesToString() {
        StringBuffer t = new StringBuffer();
        Set<Type> types = sudoku.getTypes();
        for ( Type type : types ) {
            t.append(type);
            t.append("\n");
            if ( type.equals(Type.Even) ) {
                t.append(sudoku.getEvens());
                t.append("\n");
            }
            else if ( type.equals(Type.Odd) ) {
                t.append(sudoku.getOdds());
                t.append("\n");
            }
            else if ( type.equals(Type.Irregular) ) {
                t.append(sudoku.getIrregulars());
                t.append("\n");
            }
            else if ( type.equals(Type.Fortress) ) {
                t.append(sudoku.getFortress());
                t.append("\n");
            }
            else if ( type.equals(Type.ExtraRegion) ) {
                t.append(sudoku.getExtras());
                t.append("\n");
            }
            else if ( type.equals(Type.Consecutive) ) {
                t.append(sudoku.getDots());
                t.append("\n");
            }
        }
        return new String(t);
    }

    /**
     * Funkcia zabezpeci vykonanie prikazu, ktory je reprezentovany danou triedou
     */
    @Override
    public void execute() {
        sudoku.getInputGrid().updateGrid();
        try{
            PrintWriter out = new PrintWriter(sudoku.getFile());
            String text = sudokuToFile()+typesToString();
            out.print(text);
            out.close();
            this.infoBox.addInfo("Sudoku saved");
        }
        catch(IOException e){
            System.err.println("File not found");
        }

    }
}
