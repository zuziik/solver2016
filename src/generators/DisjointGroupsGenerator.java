package generators;

/**
 * Created by Zuzka on 9.1.2016.
 */
public class DisjointGroupsGenerator extends SuperGenerator {

    public DisjointGroupsGenerator(Generator wrapped){
        super(wrapped);
    }

    private void generateDG() {
        //TODO
    }

    /**
     * Funkcia vygeneruje CNF podla typu sudoku - generatora a prida ich do zoznamu formulas
     */
    @Override
    public void generateCNF() {
        super.generateCNF();
        generateDG();
    }
}
