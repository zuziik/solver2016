package generators;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Trieda reprezentuje generator pre Fortress sudoku
 */
public class FortressGenerator extends SuperGenerator {

    private final List<List<Integer>> cells;

    /**
     * @param wrapped odkaz na vnoreny generator
     * @param cells zoznam policok, ktore su oznacene ako sucast pevnosti
     */
    public FortressGenerator(Generator wrapped, List<List<Integer>> cells) {
        super(wrapped);
        this.cells = cells;
    }

    /** Funkcia prida do formul podmienku Fortress sudoku: ak hocikde v mriezke ortogonalne susedi policko oznacene ako
     * pevnost (sede) a policko mimo pevnosti (biele), potom cislo v sedom policku musi byt vacsie */
    private void generateFortress() {
        for ( List<Integer> cell : cells ) {
            int x = cell.get(0);
            int y = cell.get(1);

            // VLAVO
            List<Integer> compTo = new ArrayList<>(Arrays.asList(x-1,y));
            if ((x-1 >= 0) && !cells.contains(compTo)) {
                //vylucime take dvojice susediacich policok, kedy je v sedom policku mensie cislo
                for ( int i = 0; i < 9; i++ ) {
                    for ( int j = i + 1; j < 9; j++ ) {
                        ArrayList<Integer> list = new ArrayList<>();
                        list.add(-variableNo(x,y,i));
                        list.add(-variableNo(x-1,y,j));
                        formulas.add(list);
                    }
                }
            }

            // VPRAVO
            compTo = new ArrayList<>(Arrays.asList(x+1,y));
            if ((x + 1 <= 8) && !cells.contains(compTo)) {
                //vylucime take dvojice susediacich policok, kedy je v sedom policku mensie cislo
                for ( int i = 0; i < 9; i++ ) {
                    for ( int j = i + 1; j < 9; j++ ) {
                        ArrayList<Integer> list = new ArrayList<>();
                        list.add(-variableNo(x,y,i));
                        list.add(-variableNo(x+1,y,j));
                        formulas.add(list);
                    }
                }
            }

            // HORE
            compTo = new ArrayList<>(Arrays.asList(x,y-1));
            if ((y - 1 >= 0) && !cells.contains(compTo)) {
                //vylucime take dvojice susediacich policok, kedy je v sedom policku mensie cislo
                for ( int i = 0; i < 9; i++ ) {
                    for ( int j = i + 1; j < 9; j++ ) {
                        ArrayList<Integer> list = new ArrayList<>();
                        list.add(-variableNo(x,y,i));
                        list.add(-variableNo(x,y-1,j));
                        formulas.add(list);
                    }
                }
            }

            // DOLE
            compTo = new ArrayList<>(Arrays.asList(x,y+1));
            if ((y + 1 <= 8) && !cells.contains(compTo)) {
                //vylucime take dvojice susediacich policok, kedy je v sedom policku mensie cislo
                for ( int i = 0; i < 9; i++ ) {
                    for ( int j = i + 1; j < 9; j++ ) {
                        ArrayList<Integer> list = new ArrayList<>();
                        list.add(-variableNo(x,y,i));
                        list.add(-variableNo(x,y+1,j));
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
        generateFortress();
    }
}
