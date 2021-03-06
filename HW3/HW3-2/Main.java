
import java.util.ArrayList;


public class Main {

    public static void main(String[] args) {
        ArrayList<Cord<Double,Double>> cds = new ArrayList<Cord<Double,Double>>();//koordinat arrayi
        
        // koordinat arrayinin ilklendirilmesi
        cds.add(new Cord<>(1.0,0.7651977));
        cds.add(new Cord<>(1.3,0.6200860));
        cds.add(new Cord<>(1.6,0.4554022));
        cds.add(new Cord<>(1.9,0.2818186));
        cds.add(new Cord<>(2.2,0.1103623));
        
        System.out.println("\nCoordinates : " + cds + "\n");
        System.out.println("f(x) = " + dividedDiffPoly(cds) + "\n");// method cagirma
        
    }
    
    public static String dividedDiffPoly(ArrayList<Cord<Double,Double>> cds){
        double[][] F = new double[cds.size()][cds.size()];
        String out = new String();// dondurulecek fonksiyon(string olarak)
        
        //F y iilklendirme
        for(int i = 0 ; i < cds.size() ; i++){
            F[i][0] = cds.get(i).getValue();
        }
        //F yi ilklendirme
        for(int i = 1 ; i < cds.size() ; i++){
            for(int j = 1 ; j <= i ; j++){
                F[i][j] = (F[i][j-1] - F[i-1][j-1])/(cds.get(i).getKey()- cds.get(i-j).getKey());
            }
        }
        
        out += F[0][0] + " + ";
        for(int i = 1 ; i < cds.size() ; i++){//F yi ve x degerlerini kullanarak fonksiyonu elde etme
            out += String.format("%.7f",F[i][i]); 
            for(int j = 0 ; j < i ; j++){
                out += String.format("(x-" + cds.get(j).getKey() + ")");
            }
            if(i != cds.size() - 1)
                out += " + ";
        }
        
        return out;
    }
    
    
}
