
//Collaborators: Maire-Lise Beland

import java.io.*;
import java.util.*;


public class main {     

     
    public static void main(String[] args) {
    //TODO:build the hash table and insert keys using the insertKeyArray function.
    //Testing arrays
		
    	int [] keysX = new int[]{52, 71, 34, 95, 68, 25, 44, 38, 47, 77, 92, 84, 94, 12, 61, 9, 89, 56, 28, 75};
    	int [] keysY = new int[]{86, 85, 6, 97, 19, 66, 26, 14, 15, 49, 75, 64, 35, 54, 31, 9, 82, 29, 81, 13};
    	int A1 = 1018;
    	int A2 = 554; 
    
    	Chaining chaining1 = new Chaining(10,0,A1);
    	Chaining chaining2 = new Chaining(10,0,A2);
    	Open_Addressing openMap1 = new Open_Addressing(10, 0, A1);
	Open_Addressing openMap2 = new Open_Addressing(10, 0, A2);
	
	int collisions1 = chaining1.insertKeyArray(keysX);
	int collisions2 = chaining2.insertKeyArray(keysY);
	int collisions3 = openMap1.insertKeyArray(keysX);
	int collisions4 = openMap2.insertKeyArray(keysY);
	System.out.println("Chaining collisions: "+ collisions1+"\nchaining2 coll:"+collisions2);
	System.out.println("openMap1 collisions: "+ collisions3+"\nopenMap2 coll:"+collisions4);
    }
}
