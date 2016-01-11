package generators;

/**
 * Created by Zuzka on 9.1.2016.
 */
public class ClassicGenerator extends SuperGenerator {

    public ClassicGenerator(Generator wrapped){
        super(wrapped);
    }

    private void generateClassic() {
        //TODO
    }

    /**
     * Funkcia vygeneruje CNF podla typu sudoku - generatora a prida ich do zoznamu formulas
     */
    @Override
    public void generateCNF() {
        super.generateCNF();
        generateClassic();
    }
}
