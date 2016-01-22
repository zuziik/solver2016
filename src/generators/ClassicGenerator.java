package generators;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Funkcia reprezentuje generator pre Klasicke sudoku
 */
public class ClassicGenerator extends SuperGenerator {

    public ClassicGenerator(Generator wrapped){
        super(wrapped);
    }

    /** Funkcia zabezpeci, ze cisla v stvorcoch 3x3 sa nebudu opakovat*/
    private void generateClassic(){
        // pre vsetky x1, x2, y1, y2, z, (x1,y1)<>(x2,y2), x1/3 == x2/3, y1/3 == y2/3: ^Px1y1z v ^Px2y2z
        for ( int i = 0; i < 3; i++ ) {
            for ( int j = 0; j < 3; j++ ) {
                List<List<Integer>> region = new ArrayList<>();
                for ( int x = 0; x < 3; x++ ) {
                    for ( int y = 0; y < 3; y++ ) {
                        region.add(new ArrayList<>(Arrays.asList(3 * i + x, 3 * j + y)));
                    }
                }
                super.generateRegion(region);
            }
        }
    }

    /**
     * Funkcia vygeneruje CNF podla typu sudoku - generatora a prida ich do zoznamu formulas
     */
    @Override
    public void generateCNF() {
        super.generateCNF();
        System.out.println(super.formulas.size());
        System.out.println("Idem generovat klasiku");
        System.out.println(super.formulas.size());
        generateClassic();
    }
}
