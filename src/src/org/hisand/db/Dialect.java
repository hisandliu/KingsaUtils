package org.hisand.db;

import java.sql.ResultSet;

public interface Dialect {
	
	public DBType getDbType();
	
	public String getSysDateFun();
	
	public String getSysDateSQL();

	public String getDateDiffSQL(String startDate, String endDate);
	
	public String getDateDiffSQL(String startDate, String endDate, String op);
	public String getDateDiffSQL(String startDate,String endDate, String op, String value);
	public String getDateDiffSQL(String startDate, String endDate, String op, String value, String diff_type);
	public String getDateDiffSQL(String startDate, String endDate, String op, String value, String diff_type, int param1_type, int param2_type);
	public String getDateDiffSQL(String startDate, String endDate, String op, String value, String diff_type, int param1_type, int param2_type, boolean param1_is_trunc, boolean param2_is_trunc);
	
	public String getDateDiffAmountSQL(String startDate, String endDate);
	
	public String getDateDiffAmountSQL(String startDate, String endDate, String diff_type, int param1_type, int param2_type);
	
	public String getDateAdd(String startDate, int number);
	
	public String getDateAdd(String startDate, int number, int param_type);
	
	public String getIsNull();
	
	public String getVirtualTable();
	
	public String getConcatStr();
	
	public String getConcatStr(String param1,String param2);

	public String getStrLen(String param);
	
	public String getCLOB2String(String field_name);
	
	public String getXML2String(String field_name);
	
	public String getConvertDateStr(String fieldName, String javaFormat);
	
	public String getClobStrFromRS(ResultSet rs,String field_name) throws Exception;
	
	public String roundDate(String date);

	public String getCharFun();
	
	public String getDateFormat(String javaFormat);
	
	public String getToDateFun(String dateString, String javaDateFormat);
	
	public String getEmptyString();
}
