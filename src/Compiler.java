import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class Compiler {
    ArrayList<String> terminals = new ArrayList<>(Arrays.asList("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "+", "-", "^", "cos", "!","."));
    String oneToNine = "123456789";
    public Stack<String> lexemes;

    public Stack<String> lex(String str) {
        lexemes = new Stack<>();
        int i = 0;
        String[] arr = str.split("");
        System.out.println(arr[0]);
        while (i < arr.length) { //loop across input string
            Boolean found = false;
            for (String tml : terminals) {//loop across terminals
                if (arr[i].equals(tml)) {
                    lexemes.add(getLexeme(tml));
                    //multiple word terminal
                } else if (tml.contains(arr[i])) {
                    if (multiWordCheck(tml, arr, i)) {
                        i += tml.length() - 1;
                    } else {
                        throw new RuntimeException("Invalid character in input");
                    }
                }

            }
            i++;
        }
        System.out.println(lexemes);
        return lexemes;
    }

    private Boolean multiWordCheck(String terminal, String[] arrInput, int startingChar){
        String cache = "";
        for(int k = startingChar;k<terminal.length()+startingChar;k++){
            System.out.println(k);
            cache+=arrInput[k];
        }
        if(terminal.equals(cache)){
            lexemes.add(getLexeme(cache));
            return true;
        } else {
            return false;
        }
    }

    private String getLexeme(String terminal) {
        if (oneToNine.contains(terminal)){
            return T.one9;
        } else if (terminal.equals("0")){
            return T.zro;
        } else if (terminals.contains(terminal)){
            return terminal;
        } else {
            throw new java.lang.RuntimeException("Tried to lex Invalid terminal");
        }
    }

    public void parser(Grammar grammar, Stack<String> ipt){
        Stack<String> states = new Stack<>();
        Stack<String> symbols = new Stack<>();
        Stack<String> input = new Stack<>();

        //flip input stack
        while(ipt.size()>0){
            input.push(ipt.pop());
        }

        System.out.printf("|  $%s  |  %s$  |%n",symbols,input);
        symbols.push(input.pop());
        for(int j=0;j<100;j++){
            Production match = grammar.findProdMatch(symbols);
            if(!match.head.equals("$")){
                //replace production body with head in symbols stack
                for(int i=0;i<=match.body.length-1;i++){
                    symbols.pop();
                }
                symbols.push(match.head);
            }else{
                System.out.println("test");
                symbols.push(input.pop());
            }
            System.out.printf("|  $%s  |  %s$  |%n",symbols,input);
        }
    }

    public void lr1Parse(Grammar grammar, PrsTbl parseTable, Stack<String> ipt){
        Stack<String> stack = new Stack<>();
        Stack<String> input = new Stack<>();
        input.push("$");
        Integer[] action;

        //flip input stack
        while(ipt.size()>0){
            input.push(ipt.pop());
        }
        stack.push("0");
        for(int j=0;j<10000;j++){
            int num;
            System.out.printf("%s|  $%s  |  %s  |%n",j,stack,input);
            String top = stack.peek();
            try{
                //top of stack is an integer
                num = Integer.parseInt(top);

                action = parseTable.getNextAction(num,input.peek());
            } catch (Exception e){
                //top of stack is a terminal/non-terminal
                stack.pop();
                num = Integer.parseInt(stack.pop());
                action = parseTable.getNextAction(num,top);
            }
            switch (action[0]){
                case PrsTbl.pass:
                    System.out.println("Reached invalid state. Terminating");
                    return;
                case PrsTbl.shift:
                    stack.push(input.pop()); //push symbol onto stack
                    stack.push(String.valueOf(action[1])); //push state onto stack
                    break;
                case PrsTbl.reduce:
                    stack = grammar.stackReduce(action[1],stack);
                    break;
                case PrsTbl.goTo:
                    stack.push(String.valueOf(num));
                    stack.push(top);
                    stack.push(String.valueOf(action[1]));
                    break;
                case PrsTbl.accept:
                    System.out.println("Syntax valid! :-)");
                    return;
            }
        }
    }

}