package sudoku;

import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by Zuzka on 7.3.2016.
 */
public class Rules {

    private static HashMap<Type, String> rules;
    private static String baseRule;
    private final static Rules rInstance = new Rules();

    private Rules() {
        rules = new HashMap<>();
        rules.put(Type.Classic, "No number can be repeated within an outlined 3x3 box.");
        rules.put(Type.Antiknight, "No two cells which can be connected by one move of a chess knight (2+1 cells in any direction) can contain same numbers.");
        rules.put(Type.Diagonal, "Numbers can not be repeated within two main diagonals.");
        rules.put(Type.DisjointGroups, "Numbers can not be repeated within the same relative positions within the main 3x3 boxes.");
        rules.put(Type.Even, "Cells marked with blue circles must contain even numbers.");
        rules.put(Type.ExtraRegion, "No number can be repeated within the marked region of the same color.");
        rules.put(Type.Irregular, "No number can be repeated within the irregular region marked with the same color.");
        rules.put(Type.NonConsecutive, "No two orthogonally adjacent cells may contain two consecutive numbers.");
        rules.put(Type.Odd, "Cells marked with blue circles must contain even numbers.");
        rules.put(Type.Untouchable, "No two cells containing the same numbers may share a corner.");
        baseRule = "Fill in the whole grid with numbers 1-9 so that no number is repeated within a row or a column.";
    }

    public static String getRules(Set<Type> types) {
        StringBuffer r = new StringBuffer(baseRule);
        for (Type t : types) {
            r.append(" "+rules.get(t));
        }
        return new String(r);
    }
}
