package commands.generate;

import commands.Command;
import commands.basic.InputToSudokuCommand;
import graphics.InfoBox;
import sudoku.Sudoku;

import java.util.ArrayList;
import java.util.List;

/**
 * Trieda reprezentuje prikaz, ktory vygeneruje sudoku s co najmensim poctom rieseni.
 * Generator doplna cisla do policok oznacenych uzivatelom ako 0 a kontroluje pocet rieseni. Toto sa vykona maximalne
 * tolko krat, kolko je povolene v konfiguracii a nakoniec vypise najlepsiu moznost (t.j. s najmensim poctom rieseni)
 */
public class GenerateSudoku implements Command {
    private Sudoku sudoku;
    private InfoBox infoBox;
    private List<List<Integer>> zeros;
    private List<List<Integer>> solution;
    private List<List<Integer>> bestSolution;
    private int bestSolutionCount;
    private int repetitionsCount;    /** kolkokrat ma generator vyskusat nejake riesenie */

    /**
     * @param sudoku aktualne sudoku
     * @param infoBox tabula, na ktoru sa vypisuju hlasky o cinnosti
     */
    public GenerateSudoku(Sudoku sudoku, InfoBox infoBox) {
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

    /**
     * Funkcia vygeneruje nahodne jedno riesenie aktualneho sudoku, vyskusa zadat len ziadane cisla a spocita riesenia.
     */
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

    /**
     * Funkcia vygeneruje sudoku s co najmensim poctom rieseni.
     * Skontroluje, ci mriezka uz nema ziadne alebo jedine riesenie, ci su oznacene nejake cisla na generovanie.
     * Ak je vsetko v poriadku, zacina generovat tolkokrat, kolko ma nastavene v konfiguracii.
     */
    private void generateSudoku() {
        for ( int i = 0; i < this.repetitionsCount; i++ ) {
            System.out.println("Trying solution #"+(i+1));
            tryOneSolution();
            if (this.bestSolutionCount == 1) {
                break;
            }
        }
        if (this.bestSolution == null) {
            this.infoBox.addInfo("Nothing found so far.");
            return;
        }
        for (List<Integer> cell : zeros) {
            int x = cell.get(0);
            int y = cell.get(1);
            int num = this.bestSolution.get(x).get(y);
            sudoku.setNumber(x, y, num);
        }
        if (bestSolutionCount == -1) {
            this.infoBox.addInfo("Too many solutions");
        }
        else {
            this.infoBox.addInfo("Best found: "+bestSolutionCount+" solutions");
        }
    }

    /**
     * Funkcia zabezpeci vykonanie prikazu, ktory je reprezentovany danou triedou
     */
    @Override
    public void execute() {
        Command command = new InputToSudokuCommand(sudoku);
        command.execute();

        this.zeros = sudoku.getZeros();

        if (this.zeros.size() == 0) {
            this.infoBox.addInfo("No cells to generate!");
            return;
        }

        CountSolutionsCommand countSolutionsCommand = new CountSolutionsCommand(this.sudoku, null);
        countSolutionsCommand.execute();
        int count = countSolutionsCommand.getCount();

        if (count == 0) {
            this.infoBox.addInfo("No solution!");
        }
        else {
            generateSudoku();
        }
    }
}
