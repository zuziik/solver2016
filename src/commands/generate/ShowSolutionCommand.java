package commands.generate;

import commands.Command;
import commands.basic.InputToSudokuCommand;
import graphics.InfoBox;
import sudoku.Sudoku;

/**
 * Trieda reprezentuje prikaz, ktory zabezpeci zobrazenie lubovolneho riesenia aktualneho
 * sudoku, ak nejake existuje
 */
public class ShowSolutionCommand implements Command {

    private final Sudoku sudoku;
    private final InfoBox infoBox;

    /**
     * @param sudoku aktualne sudoku
     * @param infoBox tabula, na ktoru sa vypisuju hlasky o cinnosti
     */
    public ShowSolutionCommand(Sudoku sudoku, InfoBox infoBox) {
        this.sudoku = sudoku;
        this.infoBox = infoBox;
    }

    /** Funkcia prevedie cislo premennej pre SAT solver na trojicu riadok, stlpec, cislo
     * a toto cislo vypise na konkretnu poziciu do vystupnej mriezky */
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
     * Funkcia zabezpeci vykonanie prikazu, ktory je reprezentovany danou triedou. Vypise
     * lubovolne riesenie sudoku do vystupnej mriezky a vypise o tom spravu do infoBoxu
     */
    @Override
    public void execute() {
        Command command = new InputToSudokuCommand(sudoku);
        command.execute();

        String text = "";

        String solution = sudoku.getGenerator().generateOneSolution();

        switch (solution) {
            case "TLE":
                text += "Time limit expired";
                break;
            case "UNSAT":
                text += "No solutions found";
                break;
            default:
                int index = solution.indexOf(":");
                solution = solution.substring(index + 1);
                String[] numbers = solution.split(" ");
                for (String s : numbers) {
                    if (!s.equals("")) {
                        setNumber(s);
                    }
                }
                text += "Solution displayed";
                break;
        }

        this.infoBox.addInfo(text);

    }
}
