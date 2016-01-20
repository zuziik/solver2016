package generators;

import java.util.ArrayList;

/**
 * Created by Zuzka on 9.1.2016.
 */
public class UntouchableGenerator extends SuperGenerator {

    public UntouchableGenerator(Generator wrapped){
        super(wrapped);
    }

    private void generateUT() {
        for ( int x = 0; x < 9; x++ ) {
            for ( int y = 0; y < 9; y++ ) {
                if ( x + 1 <= 8 && y + 1 <= 8 ) {
                    for ( int z = 0; z < 9; z++ ) {
                        ArrayList<Integer> list = new ArrayList<>();
                        list.add(-variableNo(x,y,z));
                        list.add(-variableNo(x+1,y+1,z));
                        formulas.add(list);
                    }
                }
                if ( x + 2 >= 0 && y - 1 >= 0 ) {
                    for ( int z = 0; z < 9; z++ ) {
                        ArrayList<Integer> list = new ArrayList<>();
                        list.add(-variableNo(x,y,z));
                        list.add(-variableNo(x+1,y-1,z));
                        formulas.add(list);
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
        generateUT();
    }
}
