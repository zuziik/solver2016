package generators;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Zuzka on 5.4.2016.
 */
public class ConsecutiveGenerator extends SuperGenerator {

    private final List<List<Integer>> dots;

    public ConsecutiveGenerator(Generator wrapped, List<List<Integer>> dots) {
        super(wrapped);
        this.dots = dots;
    }

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

    @Override
    public void generateCNF() {
        super.generateCNF();
        generateConsecutive();
        System.out.println("GENERUJEM SUSLEDNE");
    }
}
