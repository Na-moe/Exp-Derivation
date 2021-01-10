import java.util.Scanner;

public class MainClass {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        try {
            Parse parse = new Parse(scanner.nextLine());
            Exp exp = parse.getExp();
            Exp simpedExp = exp.simp();
            Exp diffedExp = simpedExp.derivate();
            Exp simpedDiffedExp = diffedExp.simp();
            System.out.println(simpedDiffedExp.toString()
                    .replaceAll("\\+-", "-")
                    .replaceAll("--", "+"));
        } catch (Exception e1) {
            System.out.println("WRONG FORMAT!");
            //System.out.println(e1);
        }
    }
}
