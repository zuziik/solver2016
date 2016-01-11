package generators;

/**
 * Created by Zuzka on 9.1.2016.
 */
public class AntiknightGenerator extends SuperGenerator {

    public AntiknightGenerator(Generator wrapped){
        super(wrapped);
    }

    private void generateAK() {
        //TODO
    }

    /**
     * Funkcia vygeneruje CNF podla typu sudoku - generatora a prida ich do zoznamu formulas
     */
    @Override
    public void generateCNF() {
        super.generateCNF();
        generateAK();
    }

}
