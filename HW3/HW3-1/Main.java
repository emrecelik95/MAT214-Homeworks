
import java.util.ArrayList;


public class Main {

    
    public static void main(String[] args) {
        Integer[][] matrix = new Integer[10][10]; // orjinal matris
        Integer[][] enlarged = null; // enlarged
        
        System.out.println("Original Matrix : ");
        for(int i = 0 ;i < matrix.length ; i++){//ekrana bas
            for(int j = 0 ; j < matrix.length ; j++){
                Double d = Math.random() * 98 + 1; // random sayi
                matrix[i][j] = d.intValue();
                System.out.printf("%d\t",matrix[i][j]);
            }      
            System.out.println();
        }
        
        enlarged = NearestNeighbor(matrix, 2); // nearestneighbor
        System.out.println("Enlarged Matrix(Nearest NeighBor Interpolation) , z = 2:");
        for(int i = 0 ;i < enlarged.length ; i++){// ekrana bas
            for(int j = 0 ; j < enlarged.length ; j++){
                System.out.printf("%2d\t",enlarged[i][j]);
            }
            System.out.println();
        }
        
        enlarged = NearestNeighbor(matrix, 3); // nearestneighbor
        System.out.println("Enlarged Matrix(Nearest NeighBor Interpolation) , z = 3:");
        for(int i = 0 ;i < enlarged.length ; i++){// ekrana bas
            for(int j = 0 ; j < enlarged.length ; j++){
                System.out.printf("%2d  ",enlarged[i][j]);
            }
            System.out.println();
        }
        
    }
    
    
    public static Integer[][] NearestNeighbor(Integer[][] matrix,int z){
        // genisletilmis matris
        Integer[][] enlarged = new Integer[matrix.length * z][matrix.length * z];
        // zoom islemi
        for(int i = 0 ;i < enlarged.length; i++){
            for(int j = 0 ; j < enlarged.length; j++){
                enlarged[i][j] = matrix[i/z][j/z];
            }
        }
        
        return enlarged;
    }
 
}
