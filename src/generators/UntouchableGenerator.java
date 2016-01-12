package generators;

/**
 * Created by Zuzka on 9.1.2016.
 */
public class UntouchableGenerator extends SuperGenerator {

    public UntouchableGenerator(Generator wrapped){
        super(wrapped);
    }

    private void generateUT() {
        //TODO
    }

    /**
     * Funkcia vygeneruje CNF podla typu sudoku - generatora a prida ich do zoznamu formulas
     */
    @Override
    public void generateCNF() {
        super.generateCNF();
        generateUT();
    }
}
