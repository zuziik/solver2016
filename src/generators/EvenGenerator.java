package generators;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zuzka on 9.1.2016.
 */
public class EvenGenerator extends SuperGenerator {

    List<List<Integer>> cells;

    public EvenGenerator(Generator wrapped, List<List<Integer>> cells) {
        super(wrapped);
        this.cells = cells;
    }

    private void generateEven() {
        //TODO
    }

    /**
     * Funkcia vygeneruje CNF podla typu sudoku - generatora a prida ich do zoznamu formulas
     */
    @Override
    public void generateCNF() {
        super.generateCNF();
        generateEven();
    }
}
