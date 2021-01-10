public interface Derivation {
    Exp Derivate(Exp lexp, Exp rexp);

    String toString(Exp lexp, Exp rexp);

    String toNestString(Exp lexp, Exp rexp, Exp nexp);
}
