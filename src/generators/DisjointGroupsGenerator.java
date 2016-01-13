package generators;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Zuzka on 9.1.2016.
 */
public class DisjointGroupsGenerator extends SuperGenerator {

    public DisjointGroupsGenerator(Generator wrapped){
        super(wrapped);
    }

    /** Funkcia vygeneruje formuly pre disjoint region na zadanej relativnej pozicii R i, C j*/
    private void generateDisjointRegion(int i, int j) {
        List<List<Integer>> region = new ArrayList<>();
        for ( int x = 0; x < 3; x++ ) {
            for ( int y = 0; y < 3; y++ ) {
                region.add(new ArrayList<>(Arrays.asList(3 * x + i, 3 * y + j)));
            }
        }
        super.generateRegion(region);
    }

    /** Funkcia vygeneruje CNF pre podmienku Disjoint Groups sudoku: Cisla na rovnakych relativnych poziciach v ramci
     * stvorcov 3x3 sa neopakuju */
    private void generateDG() {
        for ( int i = 0; i < 3; i++ ) {
            for ( int j = 0; j < 3; j++ ) {
                generateDisjointRegion(i,j);
            }
        }
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
