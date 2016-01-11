package generators;

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

}
