/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package netbeanstest;

import java.util.Random;

/**
 * Nur zum testen hier
 * @author Markus Kulas
 */
public class NetbeansTest {

    public static void main(String[] args) {
 
       int min = 2;
       int max = 5;
    System.out.println("Diese Random Nummer: "
            + randInt(min,max) + "\n befindet sich zwischen " + min + "und " + max);
    }

    
    public static int randInt(int min, int max) {
        
        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;      
        return randomNum;
        
    }
    
}
