/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PracticaI;
import java.util.Scanner;


/**
 *
 * @author Guillermo
 */
public class Ejercicio1 {

    /**
     * import java.util.Scanner;
public class Ejercicio1 {
    public static void main(String[] args) {
        int count;
        
        for(count=0;count<0;count++){
            
            if (count == 0)
               try(Scanner reader = new Scanner(System.in)){
                System.out.println("Enter a number: ");
                int n = reader.nextInt();
               
                do
                   System.out.print("The number "+n+" is an even number ");
                while (n % 2 > 0);
                do   
                   System.out.print("The number "+n+" is an odd number ");
                while (n % 2 == 0);}
                else{
                System.out.print("This is not a number");
                count = count++;
                }   
                    
        }      
    }   
}
     * @param args the command line arguments
     */
    public static void main(String[] args) {
               try(Scanner reader = new Scanner(System.in)){
                System.out.println("Enter a number: ");
                int n = reader.nextInt();
               
                if(n%2==0)
                   System.out.print("The number "+n+" is an even number");
                else{
                    System.out.print("The number "+n+" is an odd number");
            }
                
        }
    }
}
                
               
                    
              
    

