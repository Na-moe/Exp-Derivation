import java.math.BigInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parse {
    private static Pattern signedNum = Pattern.compile("(?<value>[+\\-]?[0-9]+)");
    private static Pattern indexP = Pattern.compile("^\\*\\*[ \t]*" + signedNum);

    private int now;
    private String input;

    Parse(String rawInput) throws Exception {
        now = 0;
        Pattern illegalChar = Pattern.compile("[^+\\-* \\t0-9sincox()]");
        Matcher matcher = illegalChar.matcher(rawInput);
        if (matcher.find()) {
            throw new Exception("illegal Char");
        }
        input = rawInput;
    }

    private void skipBlank() {
        while (now < input.length() && (input.charAt(now) == ' ' || input.charAt(now) == '\t')) {
            now++;
        }
    }

    public Exp getExp() throws Exception {
        Exp exp;
        skipBlank();
        if (input.charAt(now) == '+') {
            now++;
            skipBlank();
            exp = new Exp(
                    new Exp(null, null, new Const(BigInteger.valueOf(1))),
                    getTerm(),
                    new Mult());
        } else if (input.charAt(now) == '-') {
            now++;
            skipBlank();
            exp = new Exp(
                    new Exp(null, null, new Const(BigInteger.valueOf(-1))),
                    getTerm(),
                    new Mult());
        } else {
            exp = getTerm();
        }
        skipBlank();
        while (now < input.length() && (input.charAt(now) == '+' || input.charAt(now) == '-')) {
            if (input.charAt(now) == '+') {
                now++;
                skipBlank();
                exp = new Exp(exp, getTerm(), new Add());
                skipBlank();
            } else {
                now++;
                skipBlank();
                Exp negOne = new Exp(null, null, new Const(BigInteger.valueOf(-1)));
                exp = new Exp(
                        exp,
                        new Exp(negOne, getTerm(), new Mult()),
                        new Add());
                skipBlank();
            }
        }
        if (now >= input.length()) {
            return exp;
        }
        throw new Exception("unknown error in getExp");
    }

    private Exp getTerm() throws Exception {
        Exp term;
        if (input.charAt(now) == '+') {
            now++;
            skipBlank();
            term = new Exp(
                    new Exp(null, null, new Const(BigInteger.valueOf(1))),
                    getFactor(),
                    new Mult());
        } else if (input.charAt(now) == '-') {
            now++;
            skipBlank();
            term = new Exp(
                    new Exp(null, null, new Const(BigInteger.valueOf(-1))),
                    getFactor(),
                    new Mult());
        } else {
            term = getFactor();
        }
        skipBlank();
        while (now < input.length() && input.charAt(now) == '*') {
            now++;
            skipBlank();
            term = new Exp(term, getFactor(), new Mult());
            skipBlank();
        }
        return term;
    }

    private Exp getFactor() throws Exception {
        Exp exp;
        try {
            exp = getPower();
        } catch (Exception e0) {
            try {
                exp = getSin();
            } catch (Exception e1) {
                try {
                    exp = getCos();
                } catch (Exception e2) {
                    try {
                        exp = getConst();
                    } catch (Exception e3) {
                        exp = getExpF();
                    }
                }
            }
        }
        return exp;
    }

    private Exp getPower() throws Exception {
        Pattern powerP = Pattern.compile("^x[ \t]*");
        Matcher matcher = powerP.matcher(input.substring(now));
        if (matcher.find()) {
            now += matcher.group().length();
        } else {
            throw new Exception("Not a Power");
        }
        BigInteger index = null;
        if (now < input.length()) {
            Matcher indexM = indexP.matcher(input.substring(now));
            if (indexM.find()) {
                now += indexM.group().length();
                index = new BigInteger(indexM.group("value"));
            }
        }
        if (index == null) {
            index = BigInteger.valueOf(1);
        } else if (index.abs().compareTo(BigInteger.valueOf(50)) > 0) {
            throw new Exception("Power Index BT50");
        }
        return new Exp(null, null, new Power(index));
    }

    private Exp getSin() throws Exception {
        Pattern sinP = Pattern.compile("^sin[ \t]*\\(");
        Matcher matcher = sinP.matcher(input.substring(now));
        Exp factor;
        if (matcher.find()) {
            now += matcher.group().length();
            skipBlank();
            factor = getFactor();
            skipBlank();
        } else {
            throw new Exception("Not a Sin");
        }
        if (now >= input.length() || input.charAt(now) != ')') {
            throw new Exception("Bracs not even in sin");
        }
        now++;
        skipBlank();
        BigInteger index = null;
        if (now < input.length()) {
            Matcher indexM = indexP.matcher(input.substring(now));
            if (indexM.find()) {
                now += indexM.group().length();
                index = new BigInteger(indexM.group("value"));
            }
        }
        if (index == null) {
            index = BigInteger.valueOf(1);
        } else if (index.abs().compareTo(BigInteger.valueOf(50)) > 0) {
            throw new Exception("Sin Index BT50");
        }
        return new Exp(new Exp(null, null, new Sin(index)), factor, new Nest());
    }

    private Exp getCos() throws Exception {
        Pattern cosP = Pattern.compile("^cos[ \t]*\\(");
        Matcher matcher = cosP.matcher(input.substring(now));
        Exp factor;
        if (matcher.find()) {
            now += matcher.group().length();
            skipBlank();
            factor = getFactor();
            skipBlank();
        } else {
            throw new Exception("Not a Cos");
        }
        if (now >= input.length() || input.charAt(now) != ')') {
            throw new Exception("Bracs not even in cos");
        }
        now++;
        skipBlank();
        BigInteger index = null;
        if (now < input.length()) {
            Matcher indexM = indexP.matcher(input.substring(now));
            if (indexM.find()) {
                now += indexM.group().length();
                index = new BigInteger(indexM.group("value"));
            }
        }
        if (index == null) {
            index = BigInteger.valueOf(1);
        } else if (index.abs().compareTo(BigInteger.valueOf(50)) > 0) {
            throw new Exception("Cos Index BT50");
        }
        return new Exp(new Exp(null, null, new Cos(index)), factor, new Nest());
    }

    private Exp getConst() throws Exception {
        Pattern constP = Pattern.compile("^" + signedNum);
        Matcher matcher = constP.matcher(input.substring(now));
        if (matcher.find()) {
            now += matcher.group().length();
            return new Exp(null, null, new Const(new BigInteger(matcher.group("value"))));
        }
        throw new Exception("Not a Const");
    }

    private Exp getExpF() throws Exception {
        if (input.charAt(now) == '(') {
            int l = ++now;
            int lbrac = 1;
            int rbrac = 0;
            while (now < input.length() && lbrac != rbrac) {
                if (input.charAt(now) == ')') {
                    rbrac++;
                } else if (input.charAt(now) == '(') {
                    lbrac++;
                }
                now++;
            }
            if (lbrac != rbrac) {
                throw new Exception("Not Match Bracs");
            }
            Parse expfParse = new Parse(input.substring(l, now - 1));
            return expfParse.getExp();
        }
        throw new Exception("Not a ExpF");
    }
}
