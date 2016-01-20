package generators;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by Zuzka on 9.1.2016.
 */
public class Generator {
    String outputFile = "files/formulas.txt";
    //formula obsahuje true alebo false pre kazdu z 729 premennych
    //formulas[i] == true prave vtedy, ked sudoku[i/81][(i % 81)/9] == i % 9
    //interne ide o sudoku s R0-8, C0-8, cislami 0-8, len pri vypise sa prida +1
    //pozor na parne, resp. neparne sudoku (parne obsahuje "cisla" 1,3,5,7)
    ArrayList<ArrayList<Integer>> formulas = new ArrayList<>();

    /** Nastavenia pred generovanim */
    private String inputFile = "files/formulas.txt";               // vstupny subor s CNF vo formate DIMACS
    private int timeLimit = 5;                  // casovy limit, ako dlho ma SAT solver bezat
    private char mode = '1'; //sposob generovania: 1 = lubovolne 1 riesenie, a = vsetky riesenia, c = pocet rieseni
    //TODO pouzivatel si moze sam nastavit svoj casovy limit (kolko je ochotny cakat na vystup generatora)
    private ArrayList<String> SAToutput;    // cely vystup generatora

    public Generator() {
    }

    /** Funkcia vygeneruje CNF podla typu sudoku - generatora a prida ich do zoznamu formulas */
    public void generateCNF() {
        this.formulas = new ArrayList<>();
    }

    /** Funkcia vrati poziciu policka v zozname 729 premennych (1-729) pre CNF
     * Pozor, pri indexacii do pola (napriklad possibles) by sme chceli cislo o 1 mensie*/
    int variableNo(int x, int y, int z){
        return 81*x + 9*y + z + 1;
    }

    /** Funkcia vrati zoznam formul pre aktualne sudoku */
    public ArrayList<ArrayList<Integer>> getCNFFormulas() {
        return this.formulas;
    }

    /** Funkcia vrati textovu reprezentaciu jednej formuly v normovanom tvare: pre kazdu z premennych vypise kladne
     * alebo zaporne cislo podla toho, ci sa premenna vo formule nachadza v pozitivnom alebo negovanom zmysle a na
     * koniec formuly zapise 0 */
    String printFormula(ArrayList<Integer> f){
        StringBuffer s = new StringBuffer();
        for ( Integer i : f ) {
            s.append(i);
            s.append(' ');
        }
        s.append("0");
        return new String(s);
    }

    /** Funkcia vypise vsetky formuly do suboru, ktory je vstupom pre SAT solver */
    public void printToFile() throws IOException {
        try{
            PrintWriter out = new PrintWriter(new File(outputFile));
            out.println("p cnf 729 " + formulas.size());
            for (ArrayList<Integer> list : formulas){
                out.println(printFormula(list));
            }
            out.close();
        }
        catch(IOException e){
            System.err.println("File not found");
        }
    }

    /** Funkcia vygeneruje CNF pre aktualne sudoku a vytvori subor, do ktoreho ich vypise */
    public void createFileWithCNF() throws IOException{
        this.generateCNF();
        this.printToFile();
    }

    private boolean generate() {
        try {
            /** Vytvorenie a spustenie procesu pre generovanie CNF pomocou SAT solvera relsat */
            Process p = Runtime.getRuntime().exec("cmd /C relsat.exe" + " -#" + mode + " -t" + timeLimit + " " + inputFile);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(p.getInputStream()));

            /** Vypis vystupu SAT solvera na vystup a ulozenie do premennej SAToutput*/
            String line = null;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
                if (line.charAt(0) != 'c'){
                    SAToutput.add(line);
                }
            }
            return SAToutput.get(SAToutput.size()-1).equals("TIME LIMIT EXPIRED");
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /** Funkcia vypise vysledok SAT solvera do suboru */
    public void printToFile(String outputFile){
        try{
            PrintWriter out = new PrintWriter(new FileOutputStream(new File(outputFile)));
            for (String line : SAToutput){
                out.println(line);
            }
            out.close();
        } catch (FileNotFoundException e) {
            System.err.println("Could not export CNF formulas: File not found!");
        }
    }

    public void generateOneSolution() {

    }

    public void generateAllSolutions() {

    }

    public int countSolutions() {
        this.SAToutput = new ArrayList<>();
        this.mode = 'c';
        try {
            createFileWithCNF();
        } catch (IOException e) {
            System.err.println("Error while writing into file");
        }
        boolean timeLimitExpired = generate();
        if (timeLimitExpired) {
            return -1;
        }
        else {
            System.out.println(SAToutput);
            if (SAToutput.get(0).equals("UNSAT")) {
                return 0;
            }
            String s = SAToutput.get(SAToutput.size()-2);
            int index = s.indexOf(":");
            return Integer.parseInt(s.substring(index+2));
        }
    }
}
