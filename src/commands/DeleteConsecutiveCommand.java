package commands;

import graphics.InfoBox;
import graphics.stages.MainStage;
import sudoku.Sudoku;
import sudoku.Type;

/**
 * Created by Zuzka on 5.4.2016.
 */
public class DeleteConsecutiveCommand implements Command {

    private Sudoku sudoku;
    private InfoBox infoBox;
    private MainStage root;

    public DeleteConsecutiveCommand(Sudoku sudoku, InfoBox infoBox, MainStage root) {
        this.sudoku = sudoku;
        this.infoBox = infoBox;
        this.root = root;
    }

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
