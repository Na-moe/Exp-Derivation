import java.math.BigInteger;

public class Power implements Derivation {
    private BigInteger index;

    Power(BigInteger index) {
        this.index = index;
    }

    public BigInteger getIndex() {
        return index;
    }

    public void setIndex(BigInteger index) {
        this.index = index;
    }

    public void addIndex(BigInteger add) {
        this.index = index.add(add);
    }

    @Override
    public Exp Derivate(Exp lexp, Exp rexp) {
        Exp exp1 = new Exp(null, null, new Const(index));
        Exp exp2 = new Exp(null, null, new Power(index.add(BigInteger.valueOf(-1))));
        return new Exp(exp1, exp2, new Mult());
    }

    @Override
    public String toString(Exp lexp, Exp rexp) {
        if (index.compareTo(BigInteger.valueOf(0)) == 0) {
            return "1";
        }
        if (index.compareTo(BigInteger.valueOf(1)) == 0) {
            return "x";
        }
        return "x**" + index;
    }

    @Override
    public String toNestString(Exp lexp, Exp rexp, Exp nexp) {
        if (index.compareTo(BigInteger.valueOf(0)) == 0) {
            return "1";
        }
        return nexp.toString();
    }
}
