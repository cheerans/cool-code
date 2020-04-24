package test.com.util;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.ssnc.dbservices.utils.SecurityUtils;

public class SQLInjectionUtilTests {
	
	public static String [] reservedWords = null;
	
	static String INJECTED_USER_SQL = "mlance and 1=1;";
	static String INJECTED_CATALOGID_SQL = "mlance; drop product";
	static String INJECTED_REVIEW_SQL = "sjohn; union  by 1";

	@Test
	public void testSQLInjection() {
		
		List<String> reservedWords = getReservedWords();
		String deinjectSample = INJECTED_USER_SQL;
		String deinjectRes = SecurityUtils.deInjectSQLInFieldValue(deinjectSample);
		Assert.assertFalse(deinjectRes.equals(deinjectSample));
		Assert.assertFalse(reservedWords.contains(deinjectRes));
		deinjectSample = INJECTED_CATALOGID_SQL;
		deinjectRes = SecurityUtils.deInjectSQLInFieldValue(deinjectSample);
		Assert.assertFalse(deinjectRes.equals(deinjectSample));
		Assert.assertFalse(reservedWords.contains(deinjectRes));
		deinjectSample = INJECTED_REVIEW_SQL;
		deinjectRes = SecurityUtils.deInjectSQLInFieldValue(deinjectSample);
		Assert.assertFalse(deinjectRes.equals(deinjectSample));
		Assert.assertFalse(reservedWords.contains(deinjectRes));		
	}
	
	@Test
	public void testFormSQLInjection() {
		
		class TestSQLInjectionEntity{
			
			private String userid;
			private String catalogid;
			private String reviewid;
			
			public TestSQLInjectionEntity(String userid, String catalogid, String reviewid) {
				
				super();
				this.userid = userid;
				this.catalogid = catalogid;
				this.reviewid = reviewid;
			}			
		}
		
		Assert.assertTrue(SecurityUtils.checkSQLInjectionForForm(new TestSQLInjectionEntity(INJECTED_USER_SQL,INJECTED_CATALOGID_SQL,INJECTED_REVIEW_SQL)));
		Assert.assertTrue(SecurityUtils.checkSQLInjectionForForm(new TestSQLInjectionEntity(INJECTED_USER_SQL,"","")));
		Assert.assertTrue(SecurityUtils.checkSQLInjectionForForm(new TestSQLInjectionEntity("",INJECTED_CATALOGID_SQL,"")));
		Assert.assertTrue(SecurityUtils.checkSQLInjectionForForm(new TestSQLInjectionEntity("","",INJECTED_REVIEW_SQL)));
		Assert.assertFalse(SecurityUtils.checkSQLInjectionForForm(new TestSQLInjectionEntity("sken","3","sdan")));
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
