package commands.generate;

import commands.Command;
import commands.basic.InputToSudokuCommand;
import generators.ConsecutiveGenerator;
import generators.Generator;
import graphics.InfoBox;
import graphics.grids.layers.ConsecutiveLayer;
import graphics.stages.MainStage;
import sudoku.Sudoku;
import sudoku.Type;

import java.util.List;
import java.util.Set;

/**
 * Trieda reprezentuje prikaz, ktory do existujuceho sudoku vykresli vsetky susledne bodkz (oznaci vsetky dvojice
 * susediacich cisel, ktore sa lisia presne o 1)
 */
public class CreateConsecutiveCommand implements Command {

    private Sudoku sudoku;
    private InfoBox infoBox;
    private List<List<Set<Integer>>> numbers;
    private MainStage root;

    /**
     * @param sudoku aktualne sudoku
     * @param infoBox tabula, na ktoru sa vypisuju hlasky o cinnosti
     * @param root odkaz na hlavne okno
     */
    public CreateConsecutiveCommand(Sudoku sudoku, InfoBox infoBox, MainStage root) {
        this.sudoku = sudoku;
        this.infoBox = infoBox;
        this.numbers = sudoku.getOptions();
        this.root = root;
    }

    /**
     * Funkcia skontroluje, ci je aktualna mriezka celkom vyplnena.
     * @return vrati true, ak su vyplnene vsetky policka aktualnej mriezky
     */
    public boolean checkFullGrid() {
        for (List<Set<Integer>> row : numbers) {
            for (Set<Integer> cell : row) {
                if (cell.size() != 1) {
                    this.infoBox.addInfo("The grid must be filled!");
                    return false;
                }
            }
        }
        return true;
    }

    private Integer getNum(int x, int y) {
        if ((x < 0) || (y < 0)) {
            return null;
        }
        Set<Integer> set = this.numbers.get(x).get(y);
        int z = 0;
        for (Integer i : set) z = i;
        return z;
    }

    /**
     * Funkcia zabezpeci vykonanie prikazu, ktory je reprezentovany danou triedou
     */
    @Override
    public void execute() {
        Command command = new InputToSudokuCommand(sudoku);
        command.execute();

        if (checkFullGrid()) {
            ConsecutiveLayer consecutiveLayer = this.sudoku.getInputGrid().getConsecutiveLayer();
            consecutiveLayer.hideAllDots();

            for ( int x = 0; x < 9; x++ ) {
                for ( int y = 0; y < 9; y++ ) {
                    Integer z = getNum(x, y);
                    Integer up = getNum(x-1, y);
                    Integer left = getNum(x, y-1);
                    if ((left != null) && (Math.abs(left - z) == 1)) {
                        consecutiveLayer.showDot(x, y-1, x, y);
                    }
                    if ((up != null) && (Math.abs(up - z) == 1)) {
                        consecutiveLayer.showDot(x-1, y, x, y);
                    }
                }
            }

            this.infoBox.addInfo("Consecutive displayed");
            this.sudoku.getTypes().add(Type.Consecutive);
            this.root.updateTypes();

            Generator generator = this.sudoku.getGenerator();
            this.sudoku.setGeneratorOriginal(generator);
            generator = new ConsecutiveGenerator(generator, consecutiveLayer.getDots());
            this.sudoku.setGenerator(generator);

            this.sudoku.getOutputGrid().setConsecutiveLayer(sudoku.getInputGrid().getConsecutiveLayer().clone());
        }
    }
}
