package commands;

import graphics.InfoBox;
import sudoku.Sudoku;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zuzka on 15.3.2016.
 */
public class GenerateCommand implements Command {

    private Sudoku sudoku;
    private InfoBox infoBox;
    private List<List<Integer>> zeros;

    public GenerateCommand(Sudoku sudoku, InfoBox infoBox) {
        this.sudoku = sudoku;
        this.infoBox = infoBox;
        this.zeros = sudoku.getZeros();
    }

    private void generateSudoku() {
        List<Integer> numbers = new ArrayList<>(zeros.size());
        for (int i = 0; i < zeros.size(); i++) {
            numbers.add(0);
        }

        boolean found = false;
        while (true) {
            int i = 0;
            for (List<Integer> cell : zeros) {
                int x = cell.get(0);
                int y = cell.get(1);
                int num = numbers.get(i);
                sudoku.setNumber(x, y, num);
                i++;
            }

            CountSolutionsCommand command = new CountSolutionsCommand(sudoku, null);
            command.execute();

            int solutions = command.getCount();
            if (solutions == 1) {
                found = true;
                System.out.println("Juchuu "+numbers);
                this.infoBox.addInfo("Sudoku generated :)\n");
                break;
            }

            boolean changed = false;
            for (int j = 0; j < numbers.size(); j++) {
                int n = numbers.get(j);
                if (n+1 <= 8) {
                    numbers.set(j,n+1);
                    changed = true;
                    break;
                }
            }
            System.out.println(numbers);
            if (!changed) {
                this.infoBox.addInfo("No solutions found!\n");
                break;
            }

        }

        if (found) {
            //TODO: Vpisat cisla do sudoku
        }
        else {
            for (List<Integer> cell : zeros) {
                int x = cell.get(0);
                int y = cell.get(1);
                sudoku.setZero(x, y);
            }
        }
    }

    /**
     * Funkcia zabezpeci vykonanie prikazu, ktory je reprezentovany danou triedou
     */
    @Override
    public void execute() {
        Command command = new InputToSudokuCommand(sudoku);
        command.execute();

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
        else if (count == 1) {
            this.infoBox.addInfo("Already has 1 solution\n");
        }
        else {
            generateSudoku();
        }
    }
}
