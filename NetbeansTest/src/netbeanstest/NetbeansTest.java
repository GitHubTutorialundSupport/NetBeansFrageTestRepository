package netbeanstest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import static java.time.Clock.system;
import java.util.Random;

public class NetbeansTest {

    static InputStreamReader input = new InputStreamReader(System.in);
    static BufferedReader keyboardInput = new BufferedReader(input);

    //MAIN Methode
    public static void main(String[] args) throws IOException {

        int min = 1;
        String eingabe;
  
        System.out.print("Maximalwert für Randomzahl eingeben:");
      
        eingabe = keyboardInput.readLine();
        int max = Integer.parseInt(eingabe.toString());
        
        System.out.println("\nDu hast " + eingabe + " eingebeben!\n");
        
        System.out.println("Diese Random Nummer: "
                + randInt(min, max) + "\nbefindet sich zwischen " + min + " und " + max + ".");
        
    }


    
    //Random Create Methode
    public static int randInt(int min, int max) {

        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
    }

}
