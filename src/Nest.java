public class Nest implements Derivation {

    @Override
    public Exp Derivate(Exp lexp, Exp rexp) {
        Exp exp = new Exp(lexp.derivate(), rexp, new Nest());
        return new Exp(exp, rexp.derivate(), new Mult());
    }

    @Override
    public String toString(Exp lexp, Exp rexp) {
        return lexp.toNestString(rexp);
    }

    @Override
    public String toNestString(Exp lexp, Exp rexp, Exp nexp) {
        return null;
    }
}
