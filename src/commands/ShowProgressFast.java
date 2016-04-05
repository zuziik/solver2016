package commands;

import graphics.InfoBox;
import sudoku.Sudoku;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by Zuzka on 18.3.2016.
 */
public class ShowProgressFast implements Command {

    private final Sudoku sudoku;
    private final InfoBox infoBox;
    private final List<List<Set<Integer>>> numbers;

    public ShowProgressFast(Sudoku sudoku, InfoBox infoBox) {
        this.sudoku = sudoku;
        this.infoBox = infoBox;
        this.numbers = new ArrayList<>(9);
        for ( int i = 0; i < 9; i++ ) {
            this.numbers.add(new ArrayList<>(9));
        }
        for ( int i = 0; i < 9; i++ ) {
            for ( int j = 0; j < 9; j++ ) {
                this.numbers.get(i).add(new TreeSet<>());
            }
        }
    }

    /** Funkcia prevedie cislo premennej pre SAT solver na trojicu riadok, stlpec, cislo
     * a toto cislo prida do zoznamu moznosti pre dany riadok a stlpec */
    private void addNumber(String s) {
        int n = Integer.parseInt(s) - 1;
        Integer z = n % 9 + 1;
        n /= 9;
        int y = n % 9;
        n /= 9;
        int x = n;
        this.numbers.get(x).get(y).add(z);
    }

    /** Funkcia vypise zoznam moznosti pre kazde policko do vystupnej mriezky */
    private void updateOutput() {
        for ( int x = 0; x < 9; x++ ) {
            for ( int y = 0; y < 9; y++ ) {
                String text = "";
                Set set = numbers.get(x).get(y);
                if (set.size() == 1) {
                    for ( Integer n = 1; n <= 9; n++ ) {
                        if (set.contains(n)) {
                            text += n.toString();
                        }
                    }
                }
                else {
                    for ( Integer n = 1; n <= 9; n++ ) {
                        if (set.contains(n)) {
                            text += n.toString();
                        }
                        else {
                            text += " ";
                        }
                        if ( n % 3 == 0) {
                            text += "\n";
                        }
                        else {
                            text += " ";
                        }
                    }
                }
                sudoku.getOutputGrid().setText(x, y, text);
            }
        }
    }

    /** Funkcia spracuje vsetky mozne riesenia konkretneho sudoku na zaklade vystupu
     * SAT solvera */
    private void process() {
        try {
            /** Vytvorenie a spustenie procesu pre generovanie CNF pomocou SAT solvera relsat */
            Process p = Runtime.getRuntime().exec("cmd /C relsat.exe" + " -#5000" + " -t" +
                    sudoku.getGenerator().getTimeLimit() + " " + sudoku.getGenerator().getInputFile());
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(p.getInputStream()));

            /** Vypis vystupu SAT solvera na vystup a ulozenie do premennej SAToutput*/
            String line;
            while ((line = in.readLine()) != null) {
                if ((line.charAt(0) != 'c') && (!line.equals("SAT"))){
                    int index = line.indexOf(":");
                    line = line.substring(index + 1);
                    String[] numbers = line.split(" ");
                    for (String s : numbers) {
                        if (!s.equals("")) {
                            addNumber(s);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        updateOutput();
    }

    /**
     * Funkcia zabezpeci vykonanie prikazu, ktory je reprezentovany danou triedou
     */
    @Override
    public void execute() {
        Command command = new InputToSudokuCommand(sudoku);
        command.execute();

        String text = "";

        int count = sudoku.getGenerator().countSolutions();
        if (count == 0) {
            text += "No Solutions Found";
        } else if (count < 0) {
            text += "Time Limit Expired";
        } else if (count >= 5000) {
            text += "Too Many Solutions";
        } else {
            try {
                sudoku.getGenerator().createFileWithCNF();
            } catch (IOException e1) {
                System.err.println("Error while writing into file!");
            }
            process();
            text += "Progress Displayed";
        }
        this.infoBox.addInfo(text);
    }
}
