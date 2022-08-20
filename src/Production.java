import java.util.ArrayList;
import java.util.Stack;

public class Production {
    public final String head;
    public final String[] body;

    public Production(String head, String[] body) {
        this.head=head;
        this.body = body;
    }

    public String matchTail(Stack<String> symbols) {
        for (int i=this.body.length-1; i >=0 ;i--){
            String sym = symbols.peek();
            if(!this.body[i].equals(sym)) {
                //System.out.printf("%s != %s%n",body[i],sym);
                return "$";
            } else{
                //System.out.printf("%s == %s%n",body[i],sym);
            }
        }
        //System.out.printf("Match!: %s->%s%n",this.head,String.join("",this.body));
        return this.head;
    }
}
