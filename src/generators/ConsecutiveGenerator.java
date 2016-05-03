package generators;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Trieda reprezentuje generator pre Susledne sudoku
 */
public class ConsecutiveGenerator extends SuperGenerator {

    private final List<List<Integer>> dots;

    /**
     * @param wrapped odkaz na vnoreny generator
     * @param dots mnozina dvojic policok, medzi ktorymi ma byt susledna bodka
     */
    public ConsecutiveGenerator(Generator wrapped, List<List<Integer>> dots) {
        super(wrapped);
        this.dots = dots;
    }

    /** Funkcia prida do formul podmienku Susledneho sudoku: ak je medzi dvomi polickami vyznacena bodka, cisla v nich
     * sa musia lisit presne o 1. Vsetky mozne bodky su uz vyznacene, t.j. inde tento pripad nenastane. */
    private void generateConsecutive() {
        for ( int x = 0; x < 9; x++ ) {
            for ( int y = 0; y < 9; y++ ) {
                // VLAVO
                if ( x - 1 >= 0 ) {
                    // S BODKOU
                    if (dots.contains(new ArrayList<>(Arrays.asList(x-1, y, x, y)))) {
                        for (int z1 = 0; z1 < 9; z1++) {
                            for (int z2 = 0; z2 < 9; z2++) {
                                if (Math.abs(z1 - z2) > 1) {
                                    ArrayList<Integer> list = new ArrayList<>();
                                    list.add(-variableNo(x, y, z1));
                                    list.add(-variableNo(x - 1, y, z2));
                                    formulas.add(list);
                                }
                            }
                        }
                    }
                    // BEZ BODKY
                    else {
                        for ( int z1 = 0; z1 < 9; z1++ ) {
                            if ( z1 - 1 >= 0 ) {
                                ArrayList<Integer> list = new ArrayList<>();
                                list.add(-variableNo(x,y,z1));
                                list.add(-variableNo(x-1,y,z1-1));
                                formulas.add(list);
                            }
                            if ( z1 + 1 < 9 ) {
                                ArrayList<Integer> list = new ArrayList<>();
                                list.add(-variableNo(x,y,z1));
                                list.add(-variableNo(x-1,y,z1+1));
                                formulas.add(list);
                            }
                        }
                    }
                }
                // HORE
                if ( y - 1 >= 0 ) {
                    // S BODKOU
                    if (dots.contains(new ArrayList<>(Arrays.asList(x, y-1, x, y)))) {
                        for (int z1 = 0; z1 < 9; z1++) {
                            for (int z2 = 0; z2 < 9; z2++) {
                                if (Math.abs(z1 - z2) > 1) {
                                    ArrayList<Integer> list = new ArrayList<>();
                                    list.add(-variableNo(x, y, z1));
                                    list.add(-variableNo(x, y - 1, z2));
                                    formulas.add(list);
                                }
                            }
                        }
                    }
                    // BEZ BODKY
                    else {
                        for ( int z1 = 0; z1 < 9; z1++ ) {
                            if ( z1 - 1 >= 0 ) {
                                ArrayList<Integer> list = new ArrayList<>();
                                list.add(-variableNo(x,y,z1));
                                list.add(-variableNo(x,y-1,z1-1));
                                formulas.add(list);
                            }
                            if ( z1 + 1 < 9 ) {
                                ArrayList<Integer> list = new ArrayList<>();
                                list.add(-variableNo(x,y,z1));
                                list.add(-variableNo(x,y-1,z1+1));
                                formulas.add(list);
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Funkcia vygeneruje CNF podla typu sudoku - generatora a prida ich do zoznamu formulas
     */
    @Override
    public void generateCNF() {
        super.generateCNF();
        generateConsecutive();
    }
}
