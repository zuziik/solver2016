package generators;

/**
 * Created by Zuzka on 9.1.2016.
 */
public class NonconsecutiveGenerator extends SuperGenerator {

    public NonconsecutiveGenerator(Generator wrapped){
        super(wrapped);
    }

    private void generateNC() {
        //TODO
    }

    /**
     * Funkcia vygeneruje CNF podla typu sudoku - generatora a prida ich do zoznamu formulas
     */
    @Override
    public void generateCNF() {
        super.generateCNF();
        generateNC();
    }
}
