//package assignment4;

import java.util.*;
import java.io.*;


/*
 * Collaborators: Marie-Lise Beland, Chris Rafia
 */
public class Multiply{

    private static int randomInt(int size) {
        Random rand = new Random();
        int maxval = (1 << size) - 1;
        return rand.nextInt(maxval + 1);
    }
    
    public static int[] naive(int size, int x, int y) {

        // YOUR CODE GOES HERE  (Note: Change return statement)
    	boolean yChange = false, xChange = false;
		if (x<0) {
			x = -x;
			xChange = true;
		}
		if (y<0) {
			y=-y;
			yChange = true;
		}
        // bit shifting done here
    	x = (x<<(32-size))>>>(32-size);
    	y = (y<<(32-size))>>>(32-size);
    	int [] aCost = new int [] {0};
    	int result = naiveMult(size, x, y, aCost);
        if ((xChange && !yChange)|| (!xChange&&yChange)) result*=-1;
        int [] myNaive = {result, aCost[0]};
        return myNaive;
    }
    
   
    private static int naiveMult(int size, int x, int y, int[] cost) {
    	if (size==1) {
    		cost[0] += size;
        	return x*y;
        }
    	int m = (int)Math.ceil(size/2.0);

    	int a  = (int)(x>>m);
    	int c  = (int)(y>>m);
    	int b = x- (a<<m);
    	int d = y- (c<<m);
    	//
    	int e = naiveMult(m, a,c, cost);
    	int f = naiveMult(m, b,d, cost);
    	int g = naiveMult(m, b, c, cost);
    	int h = naiveMult(m, a, d, cost);
    	//cost of 3 for the three additions 
    	cost[0] += 3*m;
    	//
    	return (int)Math.pow(2, 2*m)*e+(int)Math.pow(2, m)*(g+h) + f;
	}

	public static int[] karatsuba(int size, int x, int y) {
        
        // YOUR CODE GOES HERE  (Note: Change return statement)
		boolean yChange = false, xChange = false;
		if (x<0) {
			x = -x;
			xChange = true;
		}
		if (y<0) {
			y=-y;
			yChange = true;
		}
		// bit shifting done here
    	x = (x<<(32-size))>>>(32-size);
    	y = (y<<(32-size))>>>(32-size);
        int [] aCost = new int [] {0};
        int result = karatsubaMult(size, x, y, aCost);
        if ((xChange && !yChange)|| (!xChange&&yChange)) result*=-1;
        int [] mykarat = {result, aCost[0]};
        
    	
    	return mykarat;
        
    }
    private static int karatsubaMult(int size, int x, int y, int[] cost) {
    	if (size==1) {
    		cost[0] += size;
        	return x*y;
        }
    	int m = (int)Math.ceil(size/2.0);
    	int a  = (int)(x>>m);
    	int c  = (int)(y>>m);
    	int b = x- (a<<m);
    	int d = y- (c<<m);
    	int e = karatsubaMult(m, a, c, cost);
    	int f = karatsubaMult(m, b,d, cost);
    	int g = karatsubaMult(m, a-b, c-d, cost);
    	cost[0] += 6*m;
    	return (int)Math.pow(2, 2*m)*e+(int)Math.pow(2, m)*(e+f-g) + f;
    }
    public static void main(String[] args){
        try{
            int maxRound = 20;
            int maxIntBitSize = 16;
            for (int size=1; size<=maxIntBitSize; size++) {
                int sumOpNaive = 0;
                int sumOpKaratsuba = 0;
                for (int round=0; round<maxRound; round++) {
                    int x = randomInt(size);
                    int y = randomInt(size);
                    int[] resNaive = naive(size,x,y);
                    int[] resKaratsuba = karatsuba(size,x,y);
            
                    if (resNaive[0] != resKaratsuba[0]) {
                        throw new Exception("Return values do not match! (x=" + x + "; y=" + y + "; Naive=" + resNaive[0] + "; Karatsuba=" + resKaratsuba[0] + ")");
                    }
                    
                    if (resNaive[0] != (x*y)) {
                        int myproduct = x*y;
                        throw new Exception("Evaluation is wrong! (x=" + x + "; y=" + y + "; Your result=" + resNaive[0] + "; True value=" + myproduct + ")");
                    }
                    
                    sumOpNaive += resNaive[1];
                    sumOpKaratsuba += resKaratsuba[1];
                }
                int avgOpNaive = sumOpNaive / maxRound;
                int avgOpKaratsuba = sumOpKaratsuba / maxRound;
                System.out.println(size + "," + avgOpNaive + "," + avgOpKaratsuba);
            }
        }
        catch (Exception e){
            System.out.println(e);
        }

   } 
}
