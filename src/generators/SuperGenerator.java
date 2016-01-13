package generators;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zuzka on 11.1.2016.
 */
public abstract class SuperGenerator extends Generator {

    Generator wrapped;

    public SuperGenerator(Generator wrapped) {
        this.wrapped = wrapped;
    }

    /** Funkcia vygeneruje CNF zabalenej varianty a prida svoje podmienky */
    public void generateCNF(){
        wrapped.generateCNF();
        this.formulas.addAll(wrapped.getCNFFormulas());
    }

    /** Funkcia pre kazde dve policka zo zadaneho regionu zabezpeci, ze budu obsahovat rozne cisla.
     * Funkcia predpoklada, ze zadany region bude obsahovat kazde policko LEN RAZ.*/
    void generateRegion(List<List<Integer>> cells){
        //pre vsetky x1, x2, y1, y2, z, (x1,y1)<>(x2,y2): ^Px1y1z v ^Px2y2z
        for (List<Integer> cell1 : cells){
            for (List<Integer> cell2 : cells){
                if (cell1 != cell2){
                    for (int z = 0; z < 9; z++){
                        ArrayList<Integer> list = new ArrayList<>();
                        list.add(-variableNo(cell1.get(0),cell1.get(1),z));
                        list.add(-variableNo(cell2.get(0),cell2.get(1),z));
                        formulas.add(list);
                    }
                }
            }
        }
    }

}
