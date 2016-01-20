package commands;

import generators.*;
import graphics.grids.InputGrid;
import graphics.grids.OutputGrid;
import graphics.grids.layers.IrregularLayer;
import graphics.grids.layers.ParityLayer;
import graphics.grids.layers.RegionLayer;
import graphics.stages.MainStage;
import javafx.stage.Stage;
import sudoku.Sudoku;
import sudoku.Type;

import java.util.List;
import java.util.Set;

/**
 * Created by Zuzka on 17.1.2016.
 */
public class CreateCommand implements Command {

    List<List<List<Integer>>> irregulars;
    List<List<List<Integer>>> extras;
    List<List<Integer>> evens;
    List<List<Integer>> odds;
    Set<Type> types;
    Stage stage;
    List<List<Integer>> numbers;

    public CreateCommand(List<List<Integer>> numbers, Stage stage, Set<Type> types, List<List<List<Integer>>> irregulars,
                         List<List<List<Integer>>> extras, List<List<Integer>> evens, List<List<Integer>> odds) {
        this.stage = stage;
        this.irregulars = irregulars;
        this.extras = extras;
        this.evens = evens;
        this.odds = odds;
        this.types = types;
        this.numbers = numbers;
    }

    private Generator createGenerator(Sudoku sudoku) {
        Generator generator = new RowColGenerator(sudoku);

        if (types.contains(Type.CLASSIC)) {
            generator = new ClassicGenerator(generator);
        }
        if (types.contains(Type.DIAGONAL)) {
            generator = new DiagonalGenerator(generator);
        }
        if (types.contains(Type.UNTOUCHABLE)) {
            generator = new UntouchableGenerator(generator);
        }
        if (types.contains(Type.NONCONSECUTIVE)) {
            generator = new NonconsecutiveGenerator(generator);
        }
        if (types.contains(Type.DISJOINT_GROUPS)) {
            generator = new DisjointGroupsGenerator(generator);
        }
        if (types.contains(Type.ANTIKNIGHT)) {
            generator = new AntiknightGenerator(generator);
        }

        if (types.contains(Type.EVEN) && evens.size() > 0) {
            generator = new EvenGenerator(generator, evens);
            sudoku.setEvens(evens);
        }

        if (types.contains(Type.ODD) && odds.size() > 0) {
            generator = new OddGenerator(generator, odds);
            sudoku.setOdds(odds);
        }

        if (types.contains(Type.IRREGULAR) && irregulars.size() > 0) {
            generator = new RegionGenerator(generator, irregulars);
            sudoku.setIrregulars(irregulars);
        }

        if (types.contains(Type.EXTRA_REGION) && extras.size() > 0) {
            generator = new RegionGenerator(generator, extras);
            sudoku.setExtras(extras);
        }

        return generator;
    }

    private InputGrid createInputGrid() {
        InputGrid inputGrid = new InputGrid();

        if (types.contains(Type.CLASSIC)) {
            inputGrid.getBorderLayer().showBorders();
        } else {
            inputGrid.getBorderLayer().hideBorders();
        }

        if (types.contains(Type.DIAGONAL)) {
            inputGrid.getDiagonalLayer().showDiagonals();
        }

        if (types.contains(Type.EVEN)) {
            ParityLayer parityLayer = inputGrid.getParityLayer();
            for ( List<Integer> cell : evens ) {
                parityLayer.color(cell.get(0),cell.get(1),'E');
            }
        }

        if (types.contains(Type.ODD)) {
            ParityLayer parityLayer = inputGrid.getParityLayer();
            for ( List<Integer> cell : odds ) {
                parityLayer.color(cell.get(0),cell.get(1),'O');
            }
        }

        if (types.contains(Type.IRREGULAR)) {
            IrregularLayer irregularLayer = inputGrid.getIrregularLayer();
            int c = 1;
            for (List<List<Integer>> region : irregulars) {
                for (List<Integer> cell : region) {
                    irregularLayer.color(cell.get(0),cell.get(1),c);
                }
                c++;
            }
        }

        if (types.contains(Type.EXTRA_REGION)) {
            RegionLayer regionLayer = inputGrid.getRegionLayer();
            char c = 'A';
            for (List<List<Integer>> region : extras) {
                for (List<Integer> cell : region) {
                    regionLayer.color(cell.get(0),cell.get(1),c);
                }
                c++;
            }
        }

        inputGrid.getTextFieldLayer().setInputHandlers();
        return inputGrid;
    }

    /** Funkcia zabezpeci vykonanie prikazu, ktory je reprezentovany danou triedou*/
    @Override
    public void execute() {
        Sudoku sudoku = new Sudoku();

        InputGrid inputGrid = createInputGrid();
        OutputGrid outputGrid = new OutputGrid(inputGrid);
        Generator generator = createGenerator(sudoku);

        sudoku.setInputGrid(inputGrid);
        sudoku.setOutputGrid(outputGrid);
        sudoku.setGenerator(generator);
        sudoku.setTypes(types);
        if (numbers != null) sudoku.setNumbers(numbers);

        MainStage mainStage = new MainStage(sudoku);
        mainStage.start();
        this.stage.close();
    }
}
