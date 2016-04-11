package commands;

import graphics.InfoBox;
import sudoku.Sudoku;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zuzka on 20.3.2016.
 */
public class GenerateAnySolution implements Command {

    private final Sudoku sudoku;
    private final InfoBox infoBox;
    private String text;
    private List<List<Integer>> solution;

    public GenerateAnySolution(Sudoku sudoku, InfoBox infoBox) {
        this.sudoku = sudoku;
        this.infoBox = infoBox;
        this.text = "";
    }

    public String getText() {
        return this.text;
    }

    public List<List<Integer>> getSolution() {
        return this.solution;
    }

    /** Do riesenia na poziciu x,y vlozi cislo z rozsahu 0-8 podla jedneho cisla premennej z vystupu SAT solvera */
    private void insertIntoSolution(String s) {
        int n = Integer.parseInt(s) - 1;
        Integer z = n % 9;
        n /= 9;
        int y = n % 9;
        n /= 9;
        int x = n;
        this.solution.get(x).set(y, z);
    }

    @Override
    public void execute() {
        Command command = new InputToSudokuCommand(sudoku);
        command.execute();

        String solution = sudoku.getGenerator().generateOneSolution();

        switch (solution) {
            case "TLE":
                text += "Time limit expired\n";
                break;
            case "UNSAT":
                text += "No solutions found\n";
                break;
            default:
                this.solution = new ArrayList<>();
                for (int i = 0; i < 9; i++) {
                    List<Integer> row = new ArrayList<>();
                    for (int j = 0; j < 9; j++ ) {
                        row.add(0);
                    }
                    this.solution.add(row);
                }

                int index = solution.indexOf(":");
                solution = solution.substring(index + 1);
                String[] numbers = solution.split(" ");
                for (String s : numbers) {
                    if (!s.equals("")) {
                        insertIntoSolution(s);
                    }
                }
                text += "Solution displayed\n";
                break;
        }
    }
}
