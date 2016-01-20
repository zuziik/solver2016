package commands;

import sudoku.Sudoku;
import sudoku.Type;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

/**
 * Created by Zuzka on 17.1.2016.
 */
public class SaveCommand implements Command {
    Sudoku sudoku;

    public SaveCommand(Sudoku sudoku) {
        this.sudoku = sudoku;
    }

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

    private String typesToString() {
        String t = "";
        Set<Type> types = sudoku.getTypes();
        for ( Type type : types ) {
            t += type+"\n";
            if ( type.equals(Type.EVEN) ) {
                t += sudoku.getEvens() + "\n";
            }
            else if ( type.equals(Type.ODD) ) {
                t += sudoku.getOdds() + "\n";
            }
            else if ( type.equals(Type.IRREGULAR) ) {
                t += sudoku.getIrregulars() + "\n";
            }
            else if ( type.equals(Type.EXTRA_REGION) ) {
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
