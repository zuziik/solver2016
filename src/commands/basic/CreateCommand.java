package commands.basic;

import commands.Command;
import generators.*;
import graphics.grids.InputGrid;
import graphics.grids.OutputGrid;
import graphics.grids.layers.*;
import graphics.stages.MainStage;
import javafx.stage.Stage;
import sudoku.Sudoku;
import sudoku.Type;

import java.io.File;
import java.util.List;
import java.util.Set;

/**
 * Trieda reprezentuje prikaz, ktory na zaklade zadanych parametrov vytvori nove sudoku
 */
public class CreateCommand implements Command {

    private final List<List<List<Integer>>> irregulars;
    private final List<List<List<Integer>>> extras;
    private final List<List<Integer>> fortress;
    private final List<List<Integer>> evens;
    private final List<List<Integer>> odds;
    private final List<List<Integer>> dots;
    private final Set<Type> types;
    private final Stage stage;
    private final List<List<Integer>> numbers;
    private final File file;

    /**
     * @param numbers mnozina cisel vpisanych do sudoku
     * @param stage aktualne okno, ktore sa ma zmenit na hlavne
     * @param types mnozina typov vytvaraneho sudoku
     * @param irregulars mnozina policok, ktore tvoria nepravidelne regiony
     * @param extras mnozina policok, ktore tvoria extra regiony
     * @param fortress mnozina policok, ktore su oznacene ako pevnost
     * @param evens mnozina policok, ktore su oznacene ako parne
     * @param odds mnozina policok, ktore su oznacene ako neparne
     * @param dots mnozina policok, medzi ktorymi je susledna bodka
     * @param file odkaz na subor, z ktoreho sa sudoku cita
     */
    public CreateCommand(List<List<Integer>> numbers, Stage stage, Set<Type> types, List<List<List<Integer>>> irregulars,
                         List<List<List<Integer>>> extras, List<List<Integer>> fortress, List<List<Integer>> evens,
                         List<List<Integer>> odds, List<List<Integer>> dots, File file) {
        this.stage = stage;
        this.irregulars = irregulars;
        this.extras = extras;
        this.fortress = fortress;
        this.evens = evens;
        this.odds = odds;
        this.dots = dots;
        this.types = types;
        this.numbers = numbers;
        this.file = file;
    }

    /** Funkcia vytvori generator k sudoku na zaklade zvolenych typov */
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

        if (types.contains(Type.Fortress) && fortress.size() > 0) {
            generator = new FortressGenerator(generator, fortress);
            sudoku.setFortress(fortress);
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

        sudoku.setGeneratorOriginal(generator);

        if (types.contains(Type.Consecutive)) {
            generator = new ConsecutiveGenerator(generator, dots);
            sudoku.setDots(dots);
        }

        sudoku.setGenerator(generator);

        return generator;
    }

    /** Funkcia vytvori graficku reprezentaciu sudoku na zaklade zvolenych typov (farebne
     * oznaci regiony a parne/neparne policka, okraje mriezky a diagonaly */
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

        if (types.contains(Type.Fortress)) {
            FortressLayer fortressLayer = inputGrid.getFortressLayer();
            for (List<Integer> cell : fortress) {
                fortressLayer.color(cell.get(0), cell.get(1));
            }
        }

        inputGrid.getTextFieldLayer().setInputHandlers();
        return inputGrid;
    }

    private void setDots(InputGrid inputGrid) {
        if (dots == null) return;
        ConsecutiveLayer consecutiveLayer = inputGrid.getConsecutiveLayer();
        for (List<Integer> dot : dots) {
            int x1 = dot.get(0);
            int y1 = dot.get(1);
            int x2 = dot.get(2);
            int y2 = dot.get(3);
            consecutiveLayer.showDot(x1, y1, x2, y2);
        }
    }

    /** Funkcia zabezpeci vykonanie prikazu, ktory je reprezentovany danou triedou*/
    @Override
    public void execute() {
        Sudoku sudoku = new Sudoku();

        InputGrid inputGrid = createInputGrid();
        setDots(inputGrid);
        OutputGrid outputGrid = new OutputGrid(inputGrid);
        createGenerator(sudoku);

        sudoku.setInputGrid(inputGrid);
        sudoku.setOutputGrid(outputGrid);
        sudoku.setFile(file);
        sudoku.setTypes(types);
        if (numbers != null) sudoku.setNumbers(numbers);

        MainStage mainStage = new MainStage(sudoku);
        mainStage.start();
        this.stage.close();
    }
}
