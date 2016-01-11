package generators;

/**
 * Created by Zuzka on 9.1.2016.
 */
public class DiagonalGenerator extends SuperGenerator {

    public DiagonalGenerator(Generator wrapped){
        super(wrapped);
    }

    private void generateDiagonals() {
        //TODO
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
