package commands.generate;

import commands.Command;
import graphics.InfoBox;
import graphics.stages.MainStage;
import sudoku.Sudoku;
import sudoku.Type;

/**
 * Trieda reprezentuje prikaz, ktory zabezpeci vymazanie suslednych bodiek sudoku
 */
public class DeleteConsecutiveCommand implements Command {

    private Sudoku sudoku;
    private InfoBox infoBox;
    private MainStage root;

    /**
     * @param sudoku aktualne sudoku
     * @param infoBox tabula, na ktoru sa vypisuju hlasky o cinnosti
     * @param root odkaz na hlavne okno
     */
    public DeleteConsecutiveCommand(Sudoku sudoku, InfoBox infoBox, MainStage root) {
        this.sudoku = sudoku;
        this.infoBox = infoBox;
        this.root = root;
    }

    /**
     * Funkcia zabezpeci vykonanie prikazu, ktory je reprezentovany danou triedou
     */
    @Override
    public void execute() {
        if (!sudoku.getTypes().contains(Type.Consecutive)) {
            infoBox.addInfo("Nothing to remove!");
            return;
        }
        sudoku.getTypes().remove(Type.Consecutive);
        root.updateTypes();
        sudoku.getInputGrid().getConsecutiveLayer().hideAllDots();
        sudoku.getOutputGrid().removeConsecutiveLayer();
        infoBox.addInfo("Consecutive removed");
        sudoku.setGenerator(sudoku.getGeneratorOriginal());
    }
}
