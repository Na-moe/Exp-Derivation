public class Add implements Derivation {

    @Override
    public Exp Derivate(Exp lexp, Exp rexp) {
        return new Exp(lexp.derivate(), rexp.derivate(), new Add());
    }

    @Override
    public String toString(Exp lexp, Exp rexp) {
        String ls = lexp.toString();
        String rs = rexp.toString();
        StringBuilder sb = new StringBuilder();
        if (ls.equals("0")) {
            return rs;
        }
        if (rs.equals("0")) {
            return ls;
        }
        return sb.append(ls).append("+").append(rs).toString();
    }

    @Override
    public String toNestString(Exp lexp, Exp rexp, Exp nexp) {
        return lexp.toNestString(nexp) + "+" + rexp.toNestString(nexp);
    }

}
