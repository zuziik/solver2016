package generators;

import java.util.ArrayList;
import java.util.List;

/**
 * Trieda reprezentuje generator pre Neparne sudoku
 */
public class OddGenerator extends SuperGenerator {

    private final List<List<Integer>> cells;

    /**
     * @param wrapped odkaz na vnoreny generator
     * @param cells zoznam policok, ktore maju obsahovat neparne cisla
     */
    public OddGenerator(Generator wrapped, List<List<Integer>> cells) {
        super(wrapped);
        this.cells = cells;
    }

    /** Funkcia vygeneruje CNF pre neparne sudoku: pre kazde policko zo zoznamu cells zabezpeci, ze moze obsahovat
     * len neparne cisla */
    private void generateOdd(){
        for (List<Integer> cell : cells){
            ArrayList<Integer> list = new ArrayList<>();
            for (int i = 0; i < 9; i+=2 ) {
                list.add(variableNo(cell.get(0),cell.get(1),i));
            }
            formulas.add(list);
        }
    }

    /**
     * Funkcia vygeneruje CNF podla typu sudoku - generatora a prida ich do zoznamu formulas
     */
    @Override
    public void generateCNF() {
        super.generateCNF();
        generateOdd();
    }
}
