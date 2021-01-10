import java.math.BigInteger;

public class Const implements Derivation {
    private BigInteger index;

    Const(BigInteger index) {
        this.index = index;
    }

    public BigInteger getIndex() {
        return index;
    }

    public void setIndex(BigInteger index) {
        this.index = index;
    }

    public void multIndex(BigInteger mult) {
        this.index = index.multiply(mult);
    }

    public void addIndex(BigInteger add) {
        this.index = index.add(add);
    }

    @Override
    public Exp Derivate(Exp lexp, Exp rexp) {
        return new Exp(null, null, new Const(BigInteger.valueOf(0)));
    }

    @Override
    public String toString(Exp lexp, Exp rexp) {
        return index.toString();
    }

    @Override
    public String toNestString(Exp lexp, Exp rexp, Exp nexp) {
        return index.toString();
    }
}
