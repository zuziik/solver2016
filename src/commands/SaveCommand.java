package commands;

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

    public SaveCommand(Sudoku sudoku) {
        this.sudoku = sudoku;
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
        String t = "";
        Set<Type> types = sudoku.getTypes();
        for ( Type type : types ) {
            t += type+"\n";
            if ( type.equals(Type.Even) ) {
                t += sudoku.getEvens() + "\n";
            }
            else if ( type.equals(Type.Odd) ) {
                t += sudoku.getOdds() + "\n";
            }
            else if ( type.equals(Type.Irregular) ) {
                t += sudoku.getIrregulars() + "\n";
            }
            else if ( type.equals(Type.Fortress) ) {
                t += sudoku.getFortress() + "\n";
            }
            else if ( type.equals(Type.ExtraRegion) ) {
                t += sudoku.getExtras() + "\n";
            }
        }
        return t;
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
        }
        catch(IOException e){
            System.err.println("File not found");
        }

    }
}
