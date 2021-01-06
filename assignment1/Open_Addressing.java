//Collaborators: Maire-Lise Beland


import java.io.*;
import java.util.*;

public class Open_Addressing {
     public int m; // number of SLOTS AVAILABLE
     public int A; // the default random number
     int w;
     int r;
     public int[] Table;

     protected Open_Addressing(int w, int seed, int A) {

         this.w = w;
         this.r = (int) (w-1)/2 +1;
         this.m = power2(r);
         if (A==-1){
            this.A = generateRandom((int) power2(w-1), (int) power2(w),seed);
         }
        else{
            this.A = A;
        }
         this.Table = new int[m];
         for (int i =0; i<m; i++) {
             Table[i] = -1;
         }
         
     }
     
                 /** Calculate 2^w*/
     public static int power2(int w) {
         return (int) Math.pow(2, w);
     }
     public static int generateRandom(int min, int max, int seed) {     
         Random generator = new Random(); 
                 if(seed>=0){
                    generator.setSeed(seed);
                 }
         int i = generator.nextInt(max-min-1);
         return i+min+1;
     }
        /**Implements the hash function g(k)*/
        public int probe(int key, int i) {
            return (new Chaining(w, key, A).chain(key)+i)%(power2(r));
     }
     
     
     /**Inserts key k into hash table. Returns the number of collisions encountered*/
        public int insertKey(int key){
            //TODO : implement this and change the return statement.
        	int collisions=0;
        	
        	//probe the hash table using the hash function in order to find
		//an open slot to put your key in. The slots should either have a -1 or -2.
        	for(int i = 0; i<Table.length; i++) {
        		if (Table[probe(key, collisions)]==-1 || Table[probe(key, collisions)]==-2) {
        			Table[probe(key, collisions)]=key;
        			break;
        		}
        		collisions++;
        	}
            return collisions;  
        }
        
        /**Sequentially inserts a list of keys into the HashTable. Outputs total number of collisions */
        public int insertKeyArray (int[] keyArray){
            //TODO
            int collision = 0;
            for (int key: keyArray) {
                collision += insertKey(key);
            }
            return collision;
        }
            
         /**Inserts key k into hash table. Returns the number of collisions encountered*/
        public int removeKey(int key){
            //TODO: implement this and change the return statement
        	int collisions=0;
        	/*using a loop, we will hash values and find the key. if the key is found return
		*it to -2 in order to know which slots have had a node and which ones haven't.
		*If it isn't in the table, it will return the number of slots in the table
		*/
        	for(int i = 0; i<Table.length; i++) {
			//stop if you find a not previously used spot
			if(Table[probe(key, collisions)]==-1){
				break;
			}
			//stop if we find the key
        		if (Table[probe(key, collisions)]==key) {
        			Table[probe(key, collisions)]=-2;
        			break;
        		}
        		collisions++;
        	}
            return collisions;  
        }
}
