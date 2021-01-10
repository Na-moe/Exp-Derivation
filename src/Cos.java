import java.math.BigInteger;

public class Cos implements Derivation {
    private BigInteger index;

    Cos(BigInteger index) {
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
        Exp exp1 = new Exp(null, null, new Const(index.negate()));
        Exp exp2 = new Exp(null, null, new Sin(BigInteger.valueOf(1)));
        Exp exp3 = new Exp(exp1, exp2, new Mult());
        Exp exp4 = new Exp(null, null, new Cos(index.add(BigInteger.valueOf(-1))));
        return new Exp(exp3, exp4, new Mult());
    }

    @Override
    public String toString(Exp lexp, Exp rexp) {
        if (index.compareTo(BigInteger.valueOf(0)) == 0) {
            return "1";
        }
        if (index.compareTo(BigInteger.valueOf(1)) == 0) {
            return "cos(x)";
        }
        return "cos(x)**" + index;
    }

    @Override
    public String toNestString(Exp lexp, Exp rexp, Exp nexp) {
        String indexs = "";
        String nest = nexp.toString();
        if (index.equals(BigInteger.valueOf(0))) {
            return "1";
        }
        if (!index.equals(BigInteger.valueOf(1))) {
            StringBuilder sb1 = new StringBuilder();
            indexs = sb1.append("**").append(index).toString();
        }
        if (nest.equals("0")) {
            return "1";
        }
        Derivation nd = nexp.getDerivation();
        if (nd instanceof Add || nd instanceof Mult || nd instanceof Nest) {
            StringBuilder sb1 = new StringBuilder();
            nest = sb1.append("(").append(nest).append(")").toString();
        }
        StringBuilder sb = new StringBuilder();
        return sb.append("cos(").append(nest).append(")").append(indexs).toString();
    }
}
