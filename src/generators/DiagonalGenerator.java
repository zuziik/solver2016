package generators;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Zuzka on 9.1.2016.
 */
public class DiagonalGenerator extends SuperGenerator {

    public DiagonalGenerator(Generator wrapped){
        super(wrapped);
    }

    /** Funkcia prida do formul podmienku Diagonalneho sudoku: Kazda z hlavnych diagonal obsahuje cisla 1-9 presne raz*/
    private void generateDiagonals(){
        List<List<Integer>> region = new ArrayList<>();
        for (int i=0; i<9; i++){
            region.add(new ArrayList<>(Arrays.asList(i, i)));
        }
        super.generateRegion(region);

        region = new ArrayList<>();
        for (int i=0; i<9; i++){
            region.add(new ArrayList<>(Arrays.asList(i, 8-i)));
        }
        super.generateRegion(region);
    }

    /**
     * Funkcia vygeneruje CNF podla typu sudoku - generatora a prida ich do zoznamu formulas
     */
    @Override
    public void generateCNF() {
        super.generateCNF();
        generateDiagonals();
    }
}
