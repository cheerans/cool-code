package main.java;

import java.util.Set;
import java.util.TreeSet;

import uk.ydubey.formatter.numtoword.NumberInWordsFormatter;

public class FindAllDivisors {

    // Function will find all divisors
    private static Set<Integer> findDivisors(int num){ 
   
        class NumberSet extends TreeSet<Integer> {

        	private static final long serialVersionUID = 1L;

        	public String toString() {
        		
        		String retVal = "";        		
        		final String AND = " and ";
        		
        		StringBuilder toStringBuilder = new StringBuilder();
        		this.forEach(num -> {
        			
        			toStringBuilder.append(NumberInWordsFormatter.getInstance().format(num))
        						   .append(AND);
        		});
        		
        		int endIndex = toStringBuilder.lastIndexOf(AND);
        		if(endIndex != -1){
        			retVal = toStringBuilder.substring(0, endIndex) + '.';
        		}else{
        			retVal = toStringBuilder.toString();
        		}
        		return retVal;
        	}
        }    	
    	
    	Set<Integer> divisors = new NumberSet();
    	int max = (int) Math.sqrt(num);
    	int dividedResult = 0;
        for(int iCur = 1; iCur <= max; iCur++) { 
        	if(num % iCur == 0) {
        		divisors.add(iCur);
        		dividedResult = num / iCur;
        		divisors.add(dividedResult);      		        		
        	}        	
        }
        if(num != 1) {
        	divisors.remove(1);
        }
        return divisors;
    }
  
    public static void main(String[] args){ 

    	final String NUMLABEL = "The Number '";
    	final String DIVISIBLEHEADING = "' is divisible by ";
    	
    	for(int iCur = 1; iCur <= 100; iCur++) {
    		Set<Integer> divisors = findDivisors(iCur);
    		if(	(divisors.size() == 1  && divisors.size() > 0)) {
    			if(divisors.contains(1)){
    				System.out.println("The number '1' is odd.");
    			}else if(divisors.contains(2)) {
    				System.out.println("The number '2' is even.");
    			} else{
    				System.out.println(NUMLABEL + iCur + DIVISIBLEHEADING + divisors.toString());
    			}
    		}else {
    			System.out.println(NUMLABEL + iCur + DIVISIBLEHEADING + divisors.toString());
        	}    		
    	}
    } 
}
