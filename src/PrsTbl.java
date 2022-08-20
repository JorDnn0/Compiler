import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class PrsTbl {
    public static final int pass = 1000;
    public static final int accept = 2000;
    public static final int shift = 3000;
    public static final int reduce = 4000;
    public static final int goTo = 5000;
    public List<List<Integer>> table;
    public List<String> tmlHeader;
    public List<String> nonTmlHeader;

    public List<String> cols;
    public PrsTbl(List<String> tmlHeader,List<String> nonTmlHeader,List<List<Integer>> table){
        this.tmlHeader = tmlHeader;
        this.nonTmlHeader = nonTmlHeader;
        int cols = tmlHeader.size()+nonTmlHeader.size();
        for(int i=0;i< table.size();i++){
            int toAdd = cols-table.get(i).size();
            if(toAdd<0){
                System.out.printf("Expected less rows in col %s%n",i);
            } else if(toAdd>0){
                for(int j=toAdd;j>0;j--){
                    table.get(i).add(pass);
                }
            }
        }

        this.table = table;
        this.cols = Stream.concat(tmlHeader.stream(),nonTmlHeader.stream()).toList();
        System.out.println(this.cols);
        System.out.println(cols);
    }

    public Integer[] getNextAction(int state, String col){
        //System.out.println("state = " + state + ", col = " + col);
        int colnum = cols.indexOf(col);
        List<Integer> row = table.get(state);
        int num = row.get(colnum);
        //System.out.println(num);
        if(num==pass){
            return new Integer[]{pass,pass};
        } else if (num==accept) {
            return new Integer[]{accept,accept};
        } else if(num<0){
            return new Integer[]{reduce,num*-1};
        } else{
            if(tmlHeader.contains(col)){
                return new Integer[]{shift,num};
            } else{
                return new Integer[]{goTo,num};
            }

        }
    }

}


