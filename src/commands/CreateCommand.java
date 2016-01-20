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

        if (types.contains(Type.Classic)) {
            generator = new ClassicGenerator(generator);
        }
        if (types.contains(Type.Diagonal)) {
            generator = new DiagonalGenerator(generator);
        }
        if (types.contains(Type.Untouchable)) {
            generator = new UntouchableGenerator(generator);
        }
        if (types.contains(Type.NonConsecutive)) {
            generator = new NonconsecutiveGenerator(generator);
        }
        if (types.contains(Type.DisjointGroups)) {
            generator = new DisjointGroupsGenerator(generator);
        }
        if (types.contains(Type.Antiknight)) {
            generator = new AntiknightGenerator(generator);
        }

        if (types.contains(Type.Even) && evens.size() > 0) {
            generator = new EvenGenerator(generator, evens);
            sudoku.setEvens(evens);
        }

        if (types.contains(Type.Odd) && odds.size() > 0) {
            generator = new OddGenerator(generator, odds);
            sudoku.setOdds(odds);
        }

        if (types.contains(Type.Irregular) && irregulars.size() > 0) {
            generator = new RegionGenerator(generator, irregulars);
            sudoku.setIrregulars(irregulars);
        }

        if (types.contains(Type.ExtraRegion) && extras.size() > 0) {
            generator = new RegionGenerator(generator, extras);
            sudoku.setExtras(extras);
        }

        return generator;
    }

    private InputGrid createInputGrid() {
        InputGrid inputGrid = new InputGrid();

        if (types.contains(Type.Classic)) {
            inputGrid.getBorderLayer().showBorders();
        } else {
            inputGrid.getBorderLayer().hideBorders();
        }

        if (types.contains(Type.Diagonal)) {
            inputGrid.getDiagonalLayer().showDiagonals();
        }

        if (types.contains(Type.Even)) {
            ParityLayer parityLayer = inputGrid.getParityLayer();
            for ( List<Integer> cell : evens ) {
                parityLayer.color(cell.get(0),cell.get(1),'E');
            }
        }

        if (types.contains(Type.Odd)) {
            ParityLayer parityLayer = inputGrid.getParityLayer();
            for ( List<Integer> cell : odds ) {
                parityLayer.color(cell.get(0),cell.get(1),'O');
            }
        }

        if (types.contains(Type.Irregular)) {
            IrregularLayer irregularLayer = inputGrid.getIrregularLayer();
            int c = 1;
            for (List<List<Integer>> region : irregulars) {
                for (List<Integer> cell : region) {
                    irregularLayer.color(cell.get(0),cell.get(1),c);
                }
                c++;
            }
        }

        if (types.contains(Type.ExtraRegion)) {
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
