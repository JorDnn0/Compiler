import java.util.*;

public class Main {
    public static int p = PrsTbl.pass;
    public static int acc = PrsTbl.accept;
    public static void main(String[] args) {
        Compiler compiler = new Compiler();
        System.out.println(compiler.lex("2"));
        String[] heads = new String[]{
                T.Adsh, //1
                T.A,    //2
                T.A,    //3
                T.S,    //4
                T.S,    //5
                T.P,    //6
                T.P,    //7
                T.C,    //8
                T.C,    //9
                T.F,    //10
                T.N,    //11
                T.N,    //12
                T.N,    //13
                T.G,    //14
                T.G,    //15
                T.H,    //16
                T.H,    //17
                T.D,    //18
                T.D,    //19
                T.d,
                T.d};   //20
        String[][] bodies = new String[][] {
                {T.A},  //0
                {T.A,T.pls,T.S},    //1
                {T.S},  //2
                {T.S,T.mns,T.P},    //3
                {T.P},  //4
                {T.P,T.pwr,T.C},    //5
                {T.C},  //6
                {T.cos,T.F},    //7
                {T.F},  //8
                {T.N},  //9
                {T.G},  //10
                {T.pls,T.G},    //11
                {T.mns,T.G},    //12
                {T.H,T.dot,T.D},    //13
                {T.H},  //14
                {T.d},  //15
                {T.one9,T.D},   //16
                {T.d},  //17
                {T.d,T.D},  //18
                {T.one9},   //19
                {T.zro} //20
        };

        Grammar grammar = new Grammar(heads,bodies);

        //-ve means reduce, +ve means shift, except accept and pass which are assigned unique large numbers
        PrsTbl table = new PrsTbl(
            List.of(T.pls,T.mns,T.pwr,T.cos,T.dot,T.one9,T.zro,T.nul),
            List.of(T.Adsh,T.A,T.S,T.P,T.C,T.F,T.N,T.G,T.H,T.D,T.d),
            List.of(Arrays.asList(9,10, p,5,p,13,14,p,p,1,2,3,4,6,7,8,11,p,12),
                new ArrayList<>(List.of(15,p,p,p,p,p,p,acc)),
                new ArrayList<>(List.of(-2,16,p,p,p,p,p,-2)),
                new ArrayList<>(List.of(-4,-4,17,p,p,p,p,-4)),
                new ArrayList<>(List.of(-6,-6,-6,p,p,p,p,-6)),
                new ArrayList<>(List.of(9,10,p,p,p,13,14,p,p,p,p,p,p,18,7,8,11,p,12)),
                new ArrayList<>(List.of(-8,-8,-8,p,p,p,p,-8)),
                new ArrayList<>(List.of(-9,-9,-9,p,p,p,p,-9)),
                new ArrayList<>(List.of(-10,-10,-10,p,p,p,p,-10)),
                new ArrayList<>(List.of(p,p,p,p,p,13,14,p,p,p,p,p,p,p,p,19,11,p,12)),
                new ArrayList<>(List.of(p,p,p,p,p,13,14,p,p,p,p,p,p,p,p,20,11,p,12)),
                new ArrayList<>(List.of(-14,-14,-14,p,21,p,p,-14)),
                new ArrayList<>(List.of(-15,-15,-15,p,-15,p,p,-15)),
                new ArrayList<>(List.of(-19,-19,-19,p,-19,24,25,-19,p,p,p,p,p,p,p,p,p,22,23)),
                new ArrayList<>(List.of(-20,-20,-20,p,-20,p,p,-20)),
                new ArrayList<>(List.of(9,10,p,5,p,13,14,p,p,p,26,3,4,6,7,8,11,p,12)),
                new ArrayList<>(List.of(9,10,p,5,p,13,14,p,p,p,p,27,4,6,7,8,11,p,12)),
                new ArrayList<>(List.of(9,10,p,5,p,13,14,p,p,p,p,p,28,6,7,8,11,p,12)),
                new ArrayList<>(List.of(-7,-7,-7,p,p,p,p,-7)),
                new ArrayList<>(List.of(-11,-11,-11,p,p,p,p,-11)),
                new ArrayList<>(List.of(-12,-12,-12,p,p,p,p,-12)),
                new ArrayList<>(List.of(p,p,p,p,p,31,32,p,p,p,p,p,p,p,p,p,p,29,30)),
                new ArrayList<>(List.of(-16,-16,-16,p,-16,p,p,-16)),
                new ArrayList<>(List.of(-17,-17,-17,p,-17,24,25,-17,p,p,p,p,p,p,p,p,p,33,23)),
                new ArrayList<>(List.of(-19,-19,-19,p,-19,-19,-19,-19)),
                new ArrayList<>(List.of(-20,-20,-20,p,-20,-20,-20,-20)),
                new ArrayList<>(List.of(-1,16,p,p,p,p,p,-1)),
                new ArrayList<>(List.of(-3,-3,17,p,p,p,p,-3)),
                new ArrayList<>(List.of(-5,-5,-5,p,p,p,p,-5)),
                new ArrayList<>(List.of(-13,-13,-13,p,p,p,p,-13)),
                new ArrayList<>(List.of(-17,-17,-17,p,p,31,32,17,p,p,p,p,p,p,p,p,p,34,30)),
                new ArrayList<>(List.of(-19,-19,-19,p,p,-19,-19,-19)),
                new ArrayList<>(List.of(-20,-20,-20,p,p,-20,-20,-20)),
                new ArrayList<>(List.of(-18,-18,-18,p,-18,p,p,-18)),
                new ArrayList<>(List.of(-18,-18,-18,p,p,p,p,-18))
            )
        );
//        Stack<String> testStack = new Stack<>();
//        testStack.push("F");
//        testStack.push("F");
//        testStack.push("T");
//        Production prod = new Production("F", new String[]{"F", "T"});
//        System.out.println(prod.matchTail(testStack));

        compiler.lr1Parse(grammar,table,compiler.lex("0.214+3.123-5^cos2"));
    }
}
//"0+2-5cos"