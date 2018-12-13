import java.util.HashSet;
import java.util.Set;

class FindAllDivisors { 
	  
    // Function will find all divisors
    static Set<Integer> findDivisors(int num){ 
    	
    	Set<Integer> divisors = new HashSet<Integer>();
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

    	for(int iCur = 1; iCur <= 100; iCur++) {
    		Set<Integer> divisors = findDivisors(iCur);
    		if(	(divisors.size() == 1  && divisors.size() > 0)) {
    			if(divisors.contains(1)){
    				System.out.println("1 is an odd number");
    			}else if(divisors.contains(2)) {
    				System.out.println("2 is an even number");
    			}    				
    		}else {
        		System.out.println(divisors.toString());
        	}    		
    	}
    } 
}
