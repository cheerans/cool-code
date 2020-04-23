package com.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SQLInjectionScannerUtil {

	public static String deInjectSQLInFieldValue(String sqlMaybeInjected) {
		
		String regExSQLInject = "(script )|(&lt;)|(&gt;)|(%3c)|(%3e)|(SLEEP )|(TRUNCATE )|(VERSION )|(JOIN )|(DROP )|(ORDER )|(SELECT )|(UPDATE )|(INSERT )|(DELETE )|(GRANT )|(REVOKE )|(UNION )|(&amp;lt;)|(&amp;gt;)";
		return java.util.regex.Pattern.compile(regExSQLInject, java.util.regex.Pattern.CASE_INSENSITIVE).matcher(sqlMaybeInjected).replaceAll("");
	}
	
	public static boolean deInjectSQLForForm(Object formBean) {
		
		boolean bSqlInjectionDetected = false;
		if (null != formBean) {
			List<Field> fields = getFieldsArray(formBean.getClass());
			Field inField = null;
			String fieldVal = "";
			String modVal = "";
			for (Field fld : fields) {
				if (	(false == Modifier.isTransient(fld.getModifiers()))
					&& 	(false == Modifier.isFinal(fld.getModifiers()))
					&& 	(fld.getType().equals(String.class))) {
					fld.setAccessible(true);
					try {
						fld.setAccessible(true);
						inField = formBean.getClass().getDeclaredField(fld.getName());
						inField.setAccessible(true);
						fieldVal = fld.get(formBean).toString();
						modVal = deInjectSQLInFieldValue(fieldVal);
						if(false == fieldVal.equals(modVal)){				
							bSqlInjectionDetected = true;								
						}						
						inField.set(formBean, deInjectSQLInFieldValue(modVal));
						fld.setAccessible(false);
						inField.setAccessible(false);
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
			while(current.getSuperclass()!=null){ // we don't want to process Object.class				
			    current = current.getSuperclass();			    
			    outArr.addAll(Arrays.asList(current.getDeclaredFields()));
			}
		}
		return outArr;
	}
}
