public class Mult implements Derivation {

    @Override
    public Exp Derivate(Exp lexp, Exp rexp) {
        Exp exp1 = new Exp(lexp.derivate(), rexp, new Mult());
        Exp exp2 = new Exp(lexp, rexp.derivate(), new Mult());
        return new Exp(exp1, exp2, new Add());
    }

    @Override
    public String toString(Exp lexp, Exp rexp) {
        String ls = lexp.toString();
        String rs = rexp.toString();
        StringBuilder sb = new StringBuilder();
        if (ls.equals("0") || rs.equals("0")) {
            return "0";
        }
        if (ls.equals("1")) {
            return rs;
        }
        if (rs.equals("1")) {
            return ls;
        }
        if (ls.equals("-1")) {
            return sb.append("(-(").append(rs).append("))").toString();
        }
        if (rs.equals("-1")) {
            return sb.append("(-(").append(ls).append("))").toString();
        }
        Derivation ld = lexp.getDerivation();
        Derivation rd = rexp.getDerivation();
        if (true) {
            StringBuilder sb1 = new StringBuilder();
            ls = sb1.append("(").append(ls).append(")").toString();
        }
        if (true) {
            StringBuilder sb1 = new StringBuilder();
            rs = sb1.append("(").append(rs).append(")").toString();
        }
        return sb.append(ls).append("*").append(rs).toString();
    }

    @Override
    public String toNestString(Exp lexp, Exp rexp, Exp nexp) {
        String ls = lexp.toNestString(nexp);
        String rs = rexp.toNestString(nexp);
        if (ls.equals("0") || rs.equals("0")) {
            return "0";
        }
        if (ls.equals("1")) {
            return rs;
        }
        if (rs.equals("1")) {
            return ls;
        }
        return lexp.toNestString(nexp) + "*" + rexp.toNestString(nexp);
    }
}
