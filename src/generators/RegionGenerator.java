package generators;

import java.util.List;

/**
 * Trieda reprezentuje generator pre Extra regionalne sudoku
 */
public class RegionGenerator extends SuperGenerator {

    //zoznam regionov (teda zoznamov policok - dvojic suradnic)
    private final List<List<List<Integer>>> regions;

    /**
     * @param wrapped odkaz na vnoreny generator
     * @param regions zoznam regionov (skupin policok, v ktorych sa cisla nemaju opakovat)
     */
    public RegionGenerator(Generator wrapped, List<List<List<Integer>>> regions) {
        super(wrapped);
        this.regions = regions;
    }

    /** Funkcia vygeneruje CNF pre extra regionalne: pre kazdy region zo zoznamu zabezpeci, ze cisla v nom sa nebudu
     * opakovat */
    private void generateRegions() {
        for ( List<List<Integer>> region : regions ) {
            super.generateRegion(region);
        }
    }

    /**
     * Funkcia vygeneruje CNF podla typu sudoku - generatora a prida ich do zoznamu formulas
     */
    @Override
    public void generateCNF() {
        super.generateCNF();
        generateRegions();
    }
}
