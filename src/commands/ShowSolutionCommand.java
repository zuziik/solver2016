package commands;

import graphics.InfoBox;
import sudoku.Sudoku;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Zuzka on 9.1.2016.
 */
public class ShowSolutionCommand implements Command {

    Sudoku sudoku;
    InfoBox infoBox;

    public ShowSolutionCommand(Sudoku sudoku, InfoBox infoBox) {
        this.sudoku = sudoku;
        this.infoBox = infoBox;
    }

    private void setNumber(String s) {
        int n = Integer.parseInt(s) - 1;
        Integer z = n % 9 + 1;
        n /= 9;
        int y = n % 9;
        n /= 9;
        int x = n;
        sudoku.getOutputGrid().setText(x, y, z.toString());
    }

    /**
     * Funkcia zabezpeci vykonanie prikazu, ktory je reprezentovany danou triedou
     */
    @Override
    public void execute() {
        Command command = new InputToSudokuCommand(sudoku);
        command.execute();

        Date date = new Date();
        String text = date.toString().substring(11,19)+'\n';

        String solution = sudoku.getGenerator().generateOneSolution();

        if (solution.equals("TLE")) {
            text += "Time Limit Expired\n";
        }
        else if (solution.equals("UNSAT")) {
            text += "No Solutions Found\n";
        }
        else {
            int index = solution.indexOf(":");
            solution = solution.substring(index + 1);
            String[] numbers = solution.split(" ");
            System.out.println(solution);
            for ( String s : numbers ) {
                if (!s.equals("")) {
                    setNumber(s);
                }
            }
            text += "Solution Displayed\n";
        }

        this.infoBox.addInfo(text);

    }
}
