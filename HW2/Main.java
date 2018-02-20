
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 *
 * @author emre
 */
public class Main {
    public static void main(String[] args) throws FileNotFoundException , Exception{
        
        if(args.length != 4){
            System.err.println("Argumants must be 4!");
            return;
        }
        
        String filename = args[1];
        String method = args[3];
        
        ArrayList<ArrayList<Double>> matrix = new ArrayList<ArrayList<Double>>();
        ArrayList<Double> roots = new ArrayList<Double>();
        try{
            ReadMatrix(filename, matrix);
            PrintMatrix(matrix);
            System.out.println();

            if(method.equalsIgnoreCase("GESP"))
                roots = SolveMatrixGESP(matrix);  
            else if(method.equalsIgnoreCase("JCB"))
                roots = SolveMatrixJCB(matrix);
            else{
                System.out.println("Wrong argumants!");
                return;
            }
            if(roots!= null)
                System.out.println("Roots : " + roots.toString() + "\n");
        } catch(Exception e){
            System.out.println("Exception caught : " + e.toString());
        }
    }
    
    public static ArrayList<Double> SolveMatrixJCB(ArrayList<ArrayList<Double>> matrix){
        ArrayList<Double> vars = new ArrayList<>();
        ArrayList<Double> pre = null;
        
        for(int i = 0 ; i < matrix.size() ; ++i)
            vars.add((double)(0));
        
        int iter = 1;
        Double totalCur = 0.0;
        do{
            System.out.println("Iteration " + iter++);
            pre = new ArrayList<>(vars);
            System.out.println("Values : " + pre);
            for(int i = 0 ; i < matrix.size() ; ++i){
                double coef = matrix.get(i).get(i);
                totalCur = 0.0;
                //System.out.print("x" + (i+1) + " = ");
                //System.out.print(matrix.get(i).get(matrix.size()) + " / " + coef + " - (");
                for(int j = 0 ; j < matrix.size() ; ++j){
                    if(j != i){
                        totalCur += matrix.get(i).get(j) * pre.get(j);
                        /*System.out.print("(" + matrix.get(i).get(j) + "*" + pre.get(j) + ")");
                        if( j != matrix.size() - 1)
                            System.out.print(" + ");
                        */
                    }
                }
                totalCur = (matrix.get(i).get(matrix.size()) - totalCur) / coef;
                //System.out.println(") / " + coef + " = " + totalCur);
                vars.set(i, totalCur);
                if(totalCur.equals(Double.NaN)){
                    System.out.println("System has no solution!");
                    return null;
                }
                if(Double.isInfinite(totalCur)){
                    System.out.println("System has infinite solution!");
                    return null;
                }
            }
            System.out.println();
        }while(Norm(DiffOfVects(pre,vars)) / Norm(vars) >= 0.001);
        System.out.println();
        return vars;
    }
    
    public static double Norm(ArrayList<Double> vect){
        double total = 0;
        for(int i = 0 ; i < vect.size() ; ++i){
            total += Math.pow(vect.get(i),2);
        }
        return Math.sqrt(total);
    }
    
    public static ArrayList<Double> DiffOfVects(ArrayList<Double> vect,ArrayList<Double> vect2){
        ArrayList<Double> res = new ArrayList<Double>();
        for(int i = 0 ; i < vect.size() ; ++i){
            res.add(vect2.get(i) - vect.get(i));
        }
        return res;
    }
    
    public static ArrayList<Double> SolveMatrixGESP(ArrayList<ArrayList<Double>> matrix){
        ArrayList<Double> maxRows = new ArrayList<>();
        
        for(int i = 0 ; i < matrix.size() ; ++i)
            maxRows.add(Max(matrix.get(i)));
        
        System.out.println("maxRows : " + maxRows + "\n");
        for(int i = 0 ; i < matrix.size() - 1; ++i){
            int maxInd = 0;
            double max = 0;
            for(int j = i ; j < matrix.size() ; ++j){
                double rate = Math.abs(matrix.get(j).get(i)/maxRows.get(j));
                //System.out.println(matrix.get(j).get(i) + " / " + maxRows.get(j));
                //System.out.println(rate);
                if(rate > max){
                    max = rate;
                    maxInd = j;
                }
            }
            System.out.println("max rate: " + max + " , max rate index : " + maxInd);
            SwapRows(matrix, i, maxInd);
            
            double tmp = maxRows.get(i);
            maxRows.set(i,maxRows.get(maxInd));
            maxRows.set(maxInd, tmp);
            
            PrintMatrix(matrix);
            OperateRows(matrix, i);
            System.out.println();
            PrintMatrix(matrix);
            System.out.println();
        }
        
        ArrayList<Double> roots = new ArrayList<>();
        ArrayList<Double> rightSide = new ArrayList<>();
        for(int i = 0 ; i < matrix.size() ; ++i){
            rightSide.add(matrix.get(i).get(matrix.size()));
        }
        for(int i = matrix.size() - 1; i >= 0  ; --i){
            double factor = matrix.get(i).get(i);
            double total = 0;
            for(int j = matrix.size() - 1 ; j > i  ; --j){
                double num = matrix.get(i).get(j);
                total += num * roots.get(roots.size() - 1);
            }
            roots.add((rightSide.get(i) - total) / factor);
        }
        /////////////HATA KONTROLU
        int i = 0;
        for( ; i < matrix.size() ; ++i)
            if(!matrix.get(matrix.size() - 1).get(i).equals(0))
                break;
        
        if(i == matrix.size() - 1 || roots.get(0).equals(Double.NaN)){
            System.out.println("System has infinite solutions");
            return null;
        }
        if(i != matrix.size() && matrix.get(matrix.size() - 1).get(matrix.size()).equals(0)
                || Double.isInfinite(roots.get(0))){
            System.out.println("No Solution Exists!");
            return null;
        }
        ////////////////////////////
        Collections.reverse(roots);
        return roots;
        
    }
    
    public static void OperateRows(ArrayList<ArrayList<Double>> matrix,int pivot){
        double mult = matrix.get(pivot).get(pivot);
        //System.out.println("mult : " + mult);
        for(int i = pivot + 1 ; i < matrix.size() ; ++i){
            double div = matrix.get(i).get(pivot);
            if(div != 0){
                for(int j = 0 ; j < matrix.size() + 1; ++j){
                    double pvtCur = matrix.get(pivot).get(j);
                    double cur = matrix.get(i).get(j);
                    matrix.get(i).set(j,cur*(-1*mult)/div + pvtCur);
                }
            }
        }
    }
    
    public static double Max(ArrayList<Double> list){
        double max = list.get(0);
        for(int i = 0 ; i < list.size() - 1; ++i){
            double item = Math.abs(list.get(i));
            max = ( item > max)? item : max;
        }
        
        return max;
    }
    
    public static ArrayList<ArrayList<Double>> SwapRows
        (ArrayList<ArrayList<Double>> matrix , int i1 , int i2){
        ArrayList<Double> tmp1 = matrix.get(i1);
        matrix.set(i1, matrix.get(i2));
        matrix.set(i2, tmp1); 
        
        return matrix;
    }
    
    public static ArrayList<ArrayList<Double>> ReadMatrix(String filename,
                          ArrayList<ArrayList<Double>> matrix) throws Exception{
        
        
        try{
            Scanner sc = new Scanner(new FileReader(filename));
            while(sc.hasNextLine()){
                String line = sc.nextLine();
                if(!line.contains(","))
                    break;
                
                String[] nums = line.split(",");

                ArrayList<Double> temp = new ArrayList<Double>();

                for(String num : nums){
                    temp.add(Double.parseDouble(num.trim()));
                }
                
                matrix.add(temp);
                
            }
        } catch(Exception e){
            throw new Exception("File Couldn't be opened!");
        }
        
        return matrix;
    }
    
    public static void PrintMatrix(ArrayList<ArrayList<Double>> matrix){
        for(int i = 0 ; i < matrix.size() ; ++i){
           for(int j = 0 ; j < matrix.size() + 1; ++j){
               System.out.print(matrix.get(i).get(j) + " ");
           }
           System.out.println();
        }
        
    }
}
