package commands;

import graphics.InfoBox;
import sudoku.Sudoku;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zuzka on 20.3.2016.
 */
public class GenerateSudokuNew implements Command {
    private Sudoku sudoku;
    private InfoBox infoBox;
    private List<List<Integer>> zeros;
    private List<List<Integer>> solution;
    private List<List<Integer>> bestSolution;
    private int bestSolutionCount;
    private int repetitionsCount;    /** kolkokrat ma generator vyskusat nejake riesenie */

    public GenerateSudokuNew(Sudoku sudoku, InfoBox infoBox) {
        this.sudoku = sudoku;
        this.infoBox = infoBox;
        this.bestSolutionCount = 0;
        this.repetitionsCount = sudoku.getGenerator().getRepetitionsLimit();
    }

    private List<List<Integer>> clone(List<List<Integer>> list) {
        List<List<Integer>> cloned = new ArrayList<>();

        for (List<Integer> row : list) {
            List<Integer> cloned_row = new ArrayList<>();
            for (Integer x : row) {
                cloned_row.add(x);
            }
            cloned.add(cloned_row);
        }

        return cloned;
    }

    private void tryOneSolution() {
        /** Vygeneruje nejake riesenie. Ak nenajde ziadne, skonci, inak pokracuje */
        GenerateAnySolution command = new GenerateAnySolution(this.sudoku, null);
        command.execute();
        this.solution = command.getSolution();
        if (this.solution == null) {
            return;
        }

        /** Do sudoku zada len cisla zvolene pouzivatelom podla vygenerovaneho riesenia a zisti pocet rieseni */
        for (List<Integer> cell : zeros) {
            int x = cell.get(0);
            int y = cell.get(1);
            int num = this.solution.get(x).get(y);
            sudoku.setNumber(x, y, num);
        }

        /** Ak ma uloha menej rieseni ako sme doteraz nasli, aktualizuje sa najlepsie najdene riesenie */
        CountSolutionsCommand countCommand = new CountSolutionsCommand(sudoku, null);
        countCommand.execute();
        int solutions = countCommand.getCount();
        if ((this.bestSolutionCount == 0) || (solutions < this.bestSolutionCount)) {
            this.bestSolutionCount = solutions;
            this.bestSolution = clone(this.solution);
        }
    }

    private void generateSudoku() {
        for ( int i = 0; i < this.repetitionsCount; i++ ) {
            System.out.println("Trying solution #"+(i+1));
            tryOneSolution();
            if (this.bestSolutionCount == 1) {
                break;
            }
        }
        if (this.bestSolution == null) {
            this.infoBox.addInfo("Nothing found so far.\n");
            return;
        }
        for (List<Integer> cell : zeros) {
            int x = cell.get(0);
            int y = cell.get(1);
            int num = this.bestSolution.get(x).get(y);
            sudoku.setNumber(x, y, num);
        }
        this.infoBox.addInfo("Best found: "+bestSolutionCount+" solutions\n");
    }


    @Override
    public void execute() {
        Command command = new InputToSudokuCommand(sudoku);
        command.execute();

        this.zeros = sudoku.getZeros();

        if (this.zeros.size() == 0) {
            this.infoBox.addInfo("No cells to generate!\n");
            return;
        }

        CountSolutionsCommand countSolutionsCommand = new CountSolutionsCommand(this.sudoku, null);
        countSolutionsCommand.execute();
        int count = countSolutionsCommand.getCount();

        if (count == 0) {
            this.infoBox.addInfo("No solution!\n");
        }
        else {
            generateSudoku();
        }
    }
}
