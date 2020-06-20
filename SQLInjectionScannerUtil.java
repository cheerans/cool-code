package com.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SQLInjectionScannerUtil {

	public static String regExSQLInject = "(script )|(&lt;)|(&gt;)|(%3c)|(%3e)|(SLEEP )|(TRUNCATE )|(VERSION )|(JOIN )|(DROP )|(ORDER )|(SELECT )|(UPDATE )|(INSERT )|(DELETE )|(GRANT )|(REVOKE )|(UNION )|(OR )|(&amp;lt;)|(&amp;gt;)|(;)";
	
	public static String deInjectSQLInFieldValue(String sqlMaybeInjected) {		
		return java.util.regex.Pattern.compile(regExSQLInject, java.util.regex.Pattern.CASE_INSENSITIVE).matcher(sqlMaybeInjected).replaceAll("");
	}
	
	public static boolean checkSQLInjectionForForm(Object formBean, Object ... args) {
		
		boolean bSqlInjectionDetected = false;
		List<String> exludeFields = null;
		if(args.length > 0){
			exludeFields = (List<String>) args[0];
		}
		if (null != formBean) {
			List<Field> fields = getFieldsArray(formBean.getClass());
			String fieldVal = "";
			String modVal = "";
			for (Field fld : fields) {
				if (	(false == Modifier.isTransient(fld.getModifiers())) && 	
				    	(false == Modifier.isFinal(fld.getModifiers())) && 	
				    	(fld.getType().equals(String.class))) {					
					try {
						if(fld.getName().equals(ControllerConstants.CRUDAFIELDNAME)){
							continue;
						}else if( 	(null != exludeFields) &&
								(true == exludeFields.contains(fld.getName()))){
							continue;
						}
						else{
							fld.setAccessible(true);							
							fieldVal = fld.get(formBean).toString();
							modVal = deInjectSQLInFieldValue(fieldVal);
							if(false == fieldVal.equals(modVal)){
								bSqlInjectionDetected = true;							
							}
							fld.setAccessible(false);
						}
					} catch (Exception e) {
					}
				}
			}
		}
		return bSqlInjectionDetected;
	}
	
	private static List<Field> getFieldsArray(Class clazz){
		
		List<Field> outArr = new ArrayList<Field>();
		Class current = clazz;
		if(null != current){
			outArr.addAll(Arrays.asList(current.getDeclaredFields()));
			while(current.getSuperclass()!=null){ 
				// we don't want to process Object.class				
			    	current = current.getSuperclass();			    
				outArr.addAll(Arrays.asList(current.getDeclaredFields()));
			}
		}
		return outArr;
	}
}
