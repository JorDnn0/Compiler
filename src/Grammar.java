import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class Grammar {
    ArrayList<Production> productions = new ArrayList<>();

    public Grammar(String[] heads, String[][] bodies){
        if(heads.length!= bodies.length){
            System.out.printf("Invalid production input: %s != %s%n",heads.length,bodies.length);
            System.out.println(Arrays.deepToString(bodies));
        } else{
            for (int i=0;i<heads.length;i++){
                this.productions.add(new Production(heads[i],bodies[i]));

            }
            //System.out.printf("%s----->%s%n",productions.get(productions.size()-1).head,productions.get(productions.size()-1).body[0]);
        }
    }

    public Production findProdMatch(Stack<String> input){
        for (Production production : productions) {
            if (!production.matchTail(input).equals("$")) {
                return production;
            }
        }
        return new Production("$",new String[] {"$"}); //return empty production if no matches
    }

    public Stack<String> stackReduce(int index,Stack<String> stack){
        String head = productions.get(index).head;
        Stack<String> body = new Stack<>();
        body.addAll(List.of(productions.get(index).body));

        for(int i =0; i<10000;i++){ //avoid using while loop
            if(body.size()==0){
                stack.push(head);
                return stack;
            }else{
                String top = stack.pop();
                if(body.peek().equals(top)){
                    body.pop();
                }
            }

        }
        System.out.println("stack Reduce Failed!");
        return new Stack<>();
    }
}
