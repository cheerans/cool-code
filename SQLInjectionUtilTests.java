package test.com.util;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import SQLInjectionScannerUtil;

public class SQLInjectionUtilTests {
	
	public static String [] reservedWords = null;

	@Test
	public void contextLoads() {
		
		List<String> reservedWords = getReservedWords();
		String deinjectSample = "select order by 1";
		String deinjectRes = SQLInjectionScannerUtil.deInjectSQLInFieldValue(deinjectSample);
		assert(false == deinjectRes.equals(deinjectSample));
		assert(false == reservedWords.contains(deinjectRes));
		deinjectSample = "bsmith; drop product";
		deinjectRes = SQLInjectionScannerUtil.deInjectSQLInFieldValue(deinjectSample);
		assert(false == deinjectRes.equals(deinjectSample));
		assert(false == reservedWords.contains(deinjectRes));
		deinjectSample = "bsmith; union  by 1";
		deinjectRes = SQLInjectionScannerUtil.deInjectSQLInFieldValue(deinjectSample);
		assert(false == deinjectRes.equals(deinjectSample));
		assert(false == reservedWords.contains(deinjectRes));		
	}
	
	private List<String> getReservedWords(){
		
		String[] reservedWords = SecurityUtils.regExSQLInject.split("\\|");
		if(null != reservedWords){
			for(int iCur = 0; iCur < reservedWords.length; iCur++){
				if(	(null != reservedWords[iCur]) &&
					(false == reservedWords[iCur].isEmpty())){
					reservedWords[iCur] = reservedWords[iCur].replace("(", "");
					reservedWords[iCur] = reservedWords[iCur].replace(")", "");
					reservedWords[iCur] = reservedWords[iCur].replace(" ", "");
				}
			}
		}
		return Arrays.asList(reservedWords);
	}
}
