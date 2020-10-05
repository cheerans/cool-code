package com.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.controller.constants.ControllerConstants;

public class FormUtils {
	
	private static final Log log = LogFactory.getLog(FormUtils.class);
	
	public static String regExSQLInject = "(script )|(&lt;)|(&gt;)|(%3c)|(%3e)|(SLEEP )|(TRUNCATE )|(VERSION )|(JOIN )|(DROP )|(ORDER )|(SELECT )|(UPDATE )|(INSERT )|(DELETE )|(GRANT )|(REVOKE )|(UNION )|(OR )|(&amp;lt;)|(&amp;gt;)|(;)";
	
	
	// This method is not to be used directly --- Made it public only for the sake JUNIT test temporarily
	public static String deInjectSQLInFieldValue(String sqlMaybeInjected) {
		
		String deinjectedStr = sqlMaybeInjected;
		if(	(null != sqlMaybeInjected) &&
			(false == sqlMaybeInjected.isEmpty())){
	        deinjectedStr = java.util.regex.Pattern.compile(regExSQLInject, java.util.regex.Pattern.CASE_INSENSITIVE).matcher(sqlMaybeInjected).replaceAll("");
		}
		return deinjectedStr;
	}
	
	public final static String SERVER_ERROR = "server.error";
	public final static String SQL_INJECTED_FIELDVALUE = "sql.injected.fieldvalue";
	public final static String SQL_INJECTED_FIELDVALUE_DEFAULT = "You are trying to enter disallowed values as input. If you think this is in error, please contact customer service - customerservice@fmsolutions.tech";
	
	public static boolean checkSQLInjectionForForm(Object formBean, Object ... args) {
		
		boolean bSqlInjectionDetected = false;
		
		List<String> exludeFields = null;
		BindingResult bRes = null;
		if(args.length > 1){
			bRes = (BindingResult) args[0];
		}
		if(args.length > 1){
			exludeFields = (List<String>) args[1];
		}
		if (null != formBean) {
			List<Field> fields = getFieldsArray(formBean.getClass());
			String fieldVal = "";
			String modVal = "";
			for (Field fld : fields) {
				if (	(false == Modifier.isTransient(fld.getModifiers()))
					&& 	(false == Modifier.isFinal(fld.getModifiers()))
					&& 	(fld.getType().equals(String.class))) {	
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
								if(null != bRes){
									bRes.addError(new FieldError(	bRes.getObjectName(),
																	fld.getName(), fieldVal, true,
																	new String[]{SQL_INJECTED_FIELDVALUE},null,SQL_INJECTED_FIELDVALUE_DEFAULT));
								}								
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
	
	public static boolean checkSQLInjectionForFields(String ... fieldsToCheck) {		

		boolean bSqlInjectionDetected = false;
		if( (null != fieldsToCheck) &&
			(fieldsToCheck.length > 0)) {
			
			for(String fld : fieldsToCheck) {	
				if(null != fld) {
					if(false == fld.equals(deInjectSQLInFieldValue(fld))) {
						bSqlInjectionDetected = true;
						break;
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
