package commands;

import graphics.InfoBox;
import javafx.scene.control.Label;
import sudoku.Sudoku;

import java.util.*;

/**
 * Created by Zuzka on 9.1.2016.
 */
public class ShowProgressCommand implements Command {

    Sudoku sudoku;
    InfoBox infoBox;
    List<List<Set<Integer>>> numbers;

    public ShowProgressCommand(Sudoku sudoku, InfoBox infoBox) {
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

    private void addNumber(String s) {
        int n = Integer.parseInt(s) - 1;
        Integer z = n % 9 + 1;
        n /= 9;
        int y = n % 9;
        n /= 9;
        int x = n;
        this.numbers.get(x).get(y).add(z);
    }

    private void updateOutput() {
        for ( int x = 0; x < 9; x++ ) {
            for ( int y = 0; y < 9; y++ ) {
                String text = "";
                for ( Integer n = 1; n <= 9; n++ ) {
                    if (numbers.get(x).get(y).contains(n)) {
                        text += n.toString();
                    }
                }
                sudoku.getOutputGrid().setText(x, y, text);
            }
        }
    }

    private void process(List<String> solutions) {
        for (String solution : solutions) {
            if (! solution.equals("SAT")) {
                int index = solution.indexOf(":");
                solution = solution.substring(index + 1);
                String[] numbers = solution.split(" ");
                System.out.println(solution);
                for (String s : numbers) {
                    if (!s.equals("")) {
                        addNumber(s);
                    }
                }
            }
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

        Date date = new Date();
        String text = date.toString().substring(11, 19) + '\n';

        int count = sudoku.getGenerator().countSolutions();
        if (count == 0) {
            text += "No Solutions Found\n";
        } else if (count < 0) {
            text += "Time Limit Expired\n";
        } else if (count >= 5000) {
            text += "Too Many Solutions\n";
        } else {
            List<String> solutions = sudoku.getGenerator().generateAllSolutions();
            process(solutions);
            text += "Progress Displayed\n";
        }
        this.infoBox.addInfo(text);
    }
}
