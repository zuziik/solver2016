package generators;

import main.Sudoku;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * Created by Zuzka on 9.1.2016.
 */
public class RowColGenerator extends Generator {

    private Sudoku sudoku;

    public RowColGenerator(Sudoku sudoku) {
        this.sudoku = sudoku;
    }

    /**
     * Funkcia vygeneruje CNF podla typu sudoku - generatora a prida ich do zoznamu formulas
     */
    @Override
    public void generateCNF() {
        this.min1Number();
        this.max1Number();
        this.noRowRepetition();
        this.noColRepetition();
        this.generateGivens();
    }

    /** Funkcia prida formuly pre kazde policko podla vpisiek, ktore obsahuje */
    private void generateGivens(){
        List<List<Set<Integer>>> options = this.sudoku.getOptions();
        for ( int x = 0; x < 9; x++ ){
            for ( int y = 0; y < 9; y++ ) {
                Set<Integer> set = options.get(x).get(y);
                ArrayList<Integer> list = new ArrayList<>();
                for ( Integer k : set ) {
                    list.add(variableNo(x, y, k));
                }
                if (list.size() > 0) formulas.add(list);
            }
        }
    }

    /** Funkcia zabezpeci, ze cisla v riadkoch sa nebudu opakovat*/
    private void noRowRepetition(){
        // pre vsetky x, y1, y2, z, y1<>y2: ^Pxy1z v ^Pxy2z
        for ( int x = 0; x < 9; x++ ) {
            for ( int z = 0; z < 9; z++ ) {
                for ( int y1 = 0; y1 < 9; y1++ ) {
                    for ( int y2 = y1+1; y2 < 9; y2++ ) {
                        ArrayList<Integer> list = new ArrayList<>();
                        list.add(-variableNo(x, y1, z));
                        list.add(-variableNo(x, y2, z));
                        formulas.add(list);
                    }
                }

            }
        }
    }

    /** Funkcia zabezpeci, ze cisla v stlpcoch sa nebudu opakovat*/
    private void noColRepetition(){
        // pre vsetky x1, x2, y, z, x1<>x2: ^Px1yz v ^Px2yz
        for ( int x1 = 0; x1 < 9; x1++ ) {
            for ( int x2 = x1+1; x2 < 9; x2++ ) {
                for ( int y = 0; y < 9; y++ ) {
                    for ( int z = 0; z < 9; z++ ) {
                        ArrayList<Integer> list = new ArrayList<>();
                        list.add(-variableNo(x1, y, z));
                        list.add(-variableNo(x2, y, z));
                        formulas.add(list);
                    }
                }

            }
        }
    }

    /** Funkcia zabezpeci, ze kazde policko bude obsahovat nejake cislo 0-8 */
    private void min1Number(){
        // pre vsetky x, y: Pxy1 v Pxy2 v ... v Pxy9
        for ( int x = 0; x < 9; x++ ) {
            for ( int y = 0; y < 9; y++ ) {
                ArrayList<Integer> list = new ArrayList<>();
                for ( int z = 0; z < 9; z++ ) {
                    list.add(variableNo(x, y, z));
                }
                formulas.add(list);
            }
        }
    }

    /** Funkcia zabezpeci, ze kazde policko bude obsahovat maximalne jedno cislo */
    private void max1Number(){
        // pre vsetky x, y, z1, z2, z1<>z2: ^Pxzy1 v ^Pxzy2
        for ( int x = 0; x < 9; x++ ) {
            for ( int y = 0; y < 9; y++ ) {
                for ( int z1 = 0; z1 < 9; z1++ ) {
                    for ( int z2 = z1+1; z2 < 9; z2++ ) {
                        ArrayList<Integer> list = new ArrayList<>();
                        list.add(-variableNo(x, y, z1));
                        list.add(-variableNo(x, y, z2));
                        formulas.add(list);
                    }
                }

            }
        }
    }
}
