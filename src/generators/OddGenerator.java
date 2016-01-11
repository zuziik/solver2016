package generators;

import java.util.ArrayList;

/**
 * Created by Zuzka on 9.1.2016.
 */
public class OddGenerator extends SuperGenerator {

    ArrayList<ArrayList<Integer>> cells;

    public OddGenerator(Generator wrapped, ArrayList<ArrayList<Integer>> cells) {
        super(wrapped);
        this.cells = cells;
    }

    private void generateOdd() {
        //TODO
    }

    /**
     * Funkcia vygeneruje CNF podla typu sudoku - generatora a prida ich do zoznamu formulas
     */
    @Override
    public void generateCNF() {
        super.generateCNF();
        generateOdd();
    }
}
