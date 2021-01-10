import java.math.BigInteger;
import java.util.ArrayList;

public class Exp {
    private Exp lexp;
    private Exp rexp;
    private Derivation derivation;

    Exp(Exp lexp, Exp rexp, Derivation derivation) {
        this.lexp = lexp;
        this.rexp = rexp;
        this.derivation = derivation;
    }

    Derivation getDerivation() {
        return derivation;
    }

    public Exp derivate() {
        return derivation.Derivate(lexp, rexp);
    }

    @Override
    public String toString() {
        return derivation.toString(lexp, rexp);
    }

    String toNestString(Exp nexp) {
        return derivation.toNestString(lexp, rexp, nexp);
    }

    public Exp simp() {
        if (derivation instanceof Mult) {
            return multSimp();
        }
        if (derivation instanceof Add) {
            return addSimp();
        }
        if (lexp != null && rexp != null) {
            return new Exp(lexp.simp(), rexp.simp(), derivation);
        }
        return this;
    }

    private Exp multSimp() {
        if (lexp.derivation instanceof Add) {
            Exp exp1 = new Exp(lexp.lexp.simp(), rexp.simp(), new Mult());
            Exp exp2 = new Exp(lexp.rexp.simp(), rexp.simp(), new Mult());
            return new Exp(exp1, exp2, new Add()).simp();
        } else if (rexp.derivation instanceof Add) {
            Exp exp1 = new Exp(lexp.simp(), rexp.lexp.simp(), new Mult());
            Exp exp2 = new Exp(lexp.simp(), rexp.rexp.simp(), new Mult());
            return new Exp(exp1, exp2, new Add()).simp();
        } else {
            if (lexp.derivation instanceof Const &&
                    ((Const) lexp.derivation).getIndex().equals(BigInteger.valueOf(0))) {
                return new Exp(null, null, new Const(BigInteger.valueOf(0)));
            }
            if (rexp.derivation instanceof Const &&
                    ((Const) rexp.derivation).getIndex().equals(BigInteger.valueOf(0))) {
                return new Exp(null, null, new Const(BigInteger.valueOf(0)));
            }
            if (lexp.derivation instanceof Const &&
                    ((Const) lexp.derivation).getIndex().equals(BigInteger.valueOf(1))) {
                return rexp.simp();
            }
            if (rexp.derivation instanceof Const &&
                    ((Const) rexp.derivation).getIndex().equals(BigInteger.valueOf(1))) {
                return lexp.simp();
            }
            if (lexp.derivation instanceof Const && rexp.derivation instanceof Const) {
                return new Exp(null, null,
                        new Const(((Const) lexp.derivation).getIndex().multiply(
                                ((Const) rexp.derivation).getIndex()))
                ).simp();
            }
            if (lexp.derivation instanceof Power && rexp.derivation instanceof Power) {
                return new Exp(null, null,
                        new Power(((Power) lexp.derivation).getIndex().add(
                                ((Power) rexp.derivation).getIndex()))
                ).simp();
            }
            if (lexp.derivation instanceof Sin && rexp.derivation instanceof Sin) {
                return new Exp(null, null,
                        new Sin(((Sin) lexp.derivation).getIndex().add(
                                ((Sin) rexp.derivation).getIndex()))
                ).simp();
            }
            if (lexp.derivation instanceof Cos && rexp.derivation instanceof Cos) {
                return new Exp(null, null,
                        new Cos(((Cos) lexp.derivation).getIndex().add(
                                ((Cos) rexp.derivation).getIndex()))
                ).simp();
            }
            if (lexp.derivation instanceof Mult || rexp.derivation instanceof Mult) {
                return multUp();
            }
            return this;
        }
    }

    private Exp multUp() {
        ArrayList<Exp> multList = new ArrayList<>();
        intoMultList(multList, lexp);
        intoMultList(multList, rexp);
        ArrayList<Exp> finalList = new ArrayList<>();
        Const constant = new Const(BigInteger.valueOf(1));
        Power power = new Power(BigInteger.valueOf(0));
        Sin sin = new Sin(BigInteger.valueOf(0));
        Cos cos = new Cos(BigInteger.valueOf(0));
        finalList.add(new Exp(null, null, constant));
        finalList.add(new Exp(null, null, power));
        finalList.add(new Exp(null, null, sin));
        finalList.add(new Exp(null, null, cos));
        for (Exp exp : multList) {
            if (exp.derivation instanceof Const) {
                ((Const) finalList.get(0).derivation)
                        .multIndex(((Const) exp.derivation).getIndex());
            } else if (exp.derivation instanceof Power) {
                ((Power) finalList.get(1).derivation)
                        .addIndex(((Power) exp.derivation).getIndex());
            } else if (exp.derivation instanceof Sin) {
                ((Sin) finalList.get(2).derivation)
                        .addIndex(((Sin) exp.derivation).getIndex());
            } else if (exp.derivation instanceof Cos) {
                ((Cos) finalList.get(3).derivation)
                        .addIndex(((Cos) exp.derivation).getIndex());
            } else {
                finalList.add(exp);
            }
        }
        Exp lastExp = null;
        for (Exp exp : finalList) {
            if (lastExp == null) {
                lastExp = exp;
            } else {
                lastExp = new Exp(lastExp, exp, new Mult());
            }
        }
        return lastExp;
    }

    private void intoMultList(ArrayList<Exp> multList, Exp exp) {
        if (exp.derivation instanceof Mult) {
            intoMultList(multList, exp.lexp);
            intoMultList(multList, exp.rexp);
        } else {
            multList.add(exp);
        }
    }

    private Exp addSimp() {
        ArrayList<Exp> addList = new ArrayList<>();
        intoAddList(addList, lexp);
        intoAddList(addList, rexp);
        ArrayList<Exp> finalList = new ArrayList<>();
        for (Exp expi : addList) {
            boolean addFlag = false;
            for (Exp expj : finalList) {
                if (expi.derivation instanceof Const && expj.derivation instanceof Const) {
                    ((Const) expj.derivation).addIndex(((Const) expi.derivation).getIndex());
                    addFlag = true;
                }
            }
            if (!addFlag) {
                finalList.add(expi);
            }
        }
        Exp lastExp = null;
        for (Exp exp : finalList) {
            if (lastExp == null) {
                lastExp = exp;
            } else {
                lastExp = new Exp(lastExp, exp, new Add());
            }
        }
        return lastExp;
    }

    private void intoAddList(ArrayList<Exp> addList, Exp exp) {
        if (exp.derivation instanceof Add) {
            intoAddList(addList, exp.lexp);
            intoAddList(addList, exp.rexp);
        } else {
            addList.add(exp);
        }
    }
}
