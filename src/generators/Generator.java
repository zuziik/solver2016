package generators;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Trieda reprezentuje GENERATOR na pocitanie rieseni sudoku
 */
public class Generator {
    private final String outputFile;
    //formula obsahuje true alebo false pre kazdu z 729 premennych
    //formulas[i] == true prave vtedy, ked sudoku[i/81][(i % 81)/9] == i % 9
    //interne ide o sudoku s R0-8, C0-8, cislami 0-8, len pri vypise sa prida +1
    //pozor na parne, resp. neparne sudoku (parne obsahuje "cisla" 1,3,5,7)
    ArrayList<ArrayList<Integer>> formulas = new ArrayList<>();

    /** Nastavenia pred generovanim */
    private final String inputFile;            // vstupny subor s CNF vo formate DIMACS
    private double timeLimit;                  // casovy limit, ako dlho ma SAT solver bezat
    private int repetitionsLimit;              // kolko rieseni moze generator vyskusat pri generovani
    private String mode; //sposob generovania: 1 = lubovolne 1 riesenie, a = vsetky riesenia, c = pocet rieseni
    private int seed;
    private Random random;
    private ArrayList<String> SAToutput;    // cely vystup generatora

    Generator() {
        this.inputFile = "formulas.txt";
        //this.inputFile = "files/formulas.txt";
        this.outputFile = this.inputFile;
        this.timeLimit = 5;
        this.repetitionsLimit = 50;
        this.random = new Random();
        this.seed = 1;
    }

    /** Funkcia vrati referenciu na vstupny subor, do ktoreho sa ukladaju CNF formuly
     * @return vrati referenciu na vstupny subor, do ktoreho sa ukladaju CNF formuly
     */
    public String getInputFile() {
        return this.inputFile;
    }

    /** Funkcia vrati casovy limit generatora
     * @return vrati casovy limit generatora
     */
    public double getTimeLimit() {
        return this.timeLimit;
    }

    /** Funkcia nastavi casovy limit generatora
     * @param timeLimit novy casovy limit generatora
     * */
    public void setTimeLimit(double timeLimit) {
        this.timeLimit = timeLimit;
    }

    /**
     * Funkcia nastavi limit poctu opakovani generatora pri generovani noveho sudoku
     * @param repetitionsLimit novy limit poctu opakovani generatora
     */
    public void setRepetitionsLimit(int repetitionsLimit) {
        this.repetitionsLimit = repetitionsLimit;
    }

    /**
     * Funkcia vrati aktualny limit poctu opakovani generatora
     * @return aktualny limit poctu opakovani generatora
     */
    public int getRepetitionsLimit() {
        return this.repetitionsLimit;
    }

    /** Funkcia vygeneruje CNF podla typu sudoku - generatora a prida ich do zoznamu formulas */
    void generateCNF() {
        this.formulas = new ArrayList<>();
    }

    /** Funkcia vrati poziciu policka v zozname 729 premennych (1-729) pre CNF.
     * Pozor, pri indexacii do pola (napriklad possibles) by sme chceli cislo o 1 mensie
     * @param x cislo riadku
     * @param y cislo stlpca
     * @param z cislo v policku
     * @return vrati cislo premennej pre zadane parametre
     */
    int variableNo(int x, int y, int z){
        return 81*x + 9*y + z + 1;
    }

    /** Funkcia vrati zoznam formul pre aktualne sudoku */
    ArrayList<ArrayList<Integer>> getCNFFormulas() {
        return this.formulas;
    }

    /** Funkcia vrati textovu reprezentaciu jednej formuly v normovanom tvare: pre kazdu z premennych vypise kladne
     * alebo zaporne cislo podla toho, ci sa premenna vo formule nachadza v pozitivnom alebo negovanom zmysle a na
     * koniec formuly zapise 0 */
    private String printFormula(ArrayList<Integer> f){
        StringBuffer s = new StringBuffer();
        for ( Integer i : f ) {
            s.append(i);
            s.append(' ');
        }
        s.append("0");
        return new String(s);
    }

    /** Funkcia vypise vsetky formuly do suboru, ktory je vstupom pre SAT solver */
    private void printToFile() {
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

    /** Funkcia vygeneruje CNF pre aktualne sudoku a vytvori subor, do ktoreho ich vypise
     * @throws IOException
     */
    public void createFileWithCNF() throws IOException{
        this.generateCNF();
        this.printToFile();
    }

    private void copyRelsat(String fileName) {
        //gets program.exe from inside the JAR file as an input stream
        InputStream is = null;
        try {
            //is = getClass().getResource("/relsat.exe").openStream();
            is = getClass().getResource("/" + fileName).openStream();

            //sets the output stream to a system folder
            OutputStream os = new FileOutputStream(fileName);

            byte[] b = new byte[2048];
            int length;

            while ((length = is.read(b)) != -1) {
                os.write(b, 0, length);
            }

            is.close();
            os.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean generate() {
        try {
            /** Vytvorenie a spustenie procesu pre generovanie CNF pomocou SAT solvera relsat */

            String OS = System.getProperty("os.name").toLowerCase();
            System.out.println(OS);
            String relsat = "";
            Process p = null;
            if (OS.contains("windows")) {
                relsat = "relsat.exe";
                this.copyRelsat(relsat);
                p = Runtime.getRuntime().exec("cmd /C " + relsat + " -#" + mode + " -s" + seed + " -t" + timeLimit + " " + inputFile);
            } else if (OS.contains("linux")) {
                relsat = "relsat";
                this.copyRelsat(relsat);
                Process chmod = Runtime.getRuntime().exec("cmd chmod u+x relsat");
                p = Runtime.getRuntime().exec(relsat + " -#" + mode + " -s" + seed + " -t" + timeLimit + " " + inputFile);
            }

            //Process p = Runtime.getRuntime().exec("cmd /C relsat.exe" + " -#" + mode + " -s" + seed + " -t" + timeLimit + " " + inputFile);
            //Process p = Runtime.getRuntime().exec("cmd /C " + relsat + " -#" + mode + " -s" + seed + " -t" + timeLimit + " " + inputFile);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(p.getInputStream()));

            /** Vypis vystupu SAT solvera na vystup a ulozenie do premennej SAToutput*/
            PrintWriter os = new PrintWriter("output.txt");

            String line;
            while ((line = in.readLine()) != null) {
                //System.out.println(line);
                os.write(line);
                if (line.charAt(0) != 'c'){
                    SAToutput.add(line);
                }
            }

            os.close();
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
            SAToutput.forEach(out::println);
            out.close();
        } catch (FileNotFoundException e) {
            System.err.println("Could not export CNF formulas: File not found!");
        }
    }

    /** Funkcia vrati retazec reprezentujuci lubovolne riesenie prislusneho sudoku alebo UNSAT, ak ziadne riesenie
     * neexistuje, alebo TLE, ak casovy limit generatora vyprsal.
     * @return vrati vysledok generovania: riesenie, TLE alebo UNSAT
     */
    public String generateOneSolution() {
        this.SAToutput = new ArrayList<>();
        this.mode = "1";
        this.seed = Math.abs(this.random.nextInt());
        try {
            createFileWithCNF();
        } catch (IOException e) {
            System.err.println("Error while writing into file");
        }
        boolean timeLimitExpired = generate();
        if (timeLimitExpired) {
            return "TLE";
        }
        if (SAToutput.get(0).equals("UNSAT")) {
            return "UNSAT";
        }
        return SAToutput.get(0);
    }

    /** Funkcia vrati zoznam vsetkych rieseni sudoku. Tato funkcia je volana len v pripade, ze pocet rieseni je 1-5000
     * @return vrati zoznam vsetkych rieseni sudoku
     */
    public List<String> generateAllSolutions() {
        this.SAToutput = new ArrayList<>();
        this.mode = "5000";
        try {
            createFileWithCNF();
        } catch (IOException e) {
            System.err.println("Error while writing into file");
        }
        boolean timeLimitExpired = generate();
        if (timeLimitExpired) {
            return null;
        }
        return SAToutput;
    }

    /** Funkcia vrati pocet rieseni prislusneho sudoku alebo -1, ak pocas vypoctu vyprsal casovy limit generatora
     * @return vrati pocet rieseni sudoku alebo -1 pri neuspechu
     */
    public int countSolutions() {
        this.SAToutput = new ArrayList<>();
        this.mode = "c";
        try {
            createFileWithCNF();
        } catch (IOException e) {
            System.err.println("Error while writing into file");
        }
        boolean timeLimitExpired = generate();
        if (timeLimitExpired) {
            return -1;
        }
        //System.out.println(SAToutput);
        if (SAToutput.get(0).equals("UNSAT")) {
            return 0;
        }
        String s = SAToutput.get(SAToutput.size()-2);
        int index = s.indexOf(":");
        return Integer.parseInt(s.substring(index+2));
    }

}
