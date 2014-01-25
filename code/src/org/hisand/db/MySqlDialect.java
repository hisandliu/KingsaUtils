package org.hisand.db;

import java.sql.ResultSet;

public class MySqlDialect implements Dialect {
	public MySqlDialect() {
	}
	

	@Override 
	public DBType getDbType() {
		return DBType.MYSQL;
	}

	public String getSysDateFun() {
		return "now()";
	}
	
	public String getSysDateSQL() {
		return "select now()";
	}

	public String getDateDiffSQL(String startDate, String endDate) {
		return getDateDiffSQL(startDate, endDate, "=");
	}
	
	public String getDateDiffSQL(String startDate, String endDate, String op) {
		return getDateDiffSQL(startDate, endDate, op, "0", "day");
	}
	public String getDateDiffSQL(String startDate,String endDate, String op, String value) {
		return getDateDiffSQL(startDate, endDate, op, value ,"day");
	}
	
	public String getDateDiffSQL(String startDate, String endDate, String op, String value, String diff_type) {
		return getDateDiffSQL(startDate, endDate, op, value, diff_type, 0, 0);
	}
	
	public String getDateDiffSQL(String startDate, String endDate, String op, String value, String diff_type, int param1_type, int param2_type) {
		return getDateDiffSQL(startDate, endDate, op, value, diff_type, param1_type, param2_type, false, false);
	}
	
	public String getDateDiffSQL(String startDate, String endDate, String op, String value, String diff_type, int param1_type, int param2_type, boolean param1_is_trunc, boolean param2_is_trunc) {
		return " datediff(" + diff_type + "," + startDate + "," + endDate + ")" + op + value + " ";
	}

	public String getDateDiffAmountSQL(String startDate, String endDate) {
		return getDateDiffAmountSQL(startDate, endDate, "day", 0, 0);
	}
	
	public String getDateDiffAmountSQL(String startDate, String endDate, String diff_type, int param1_type, int param2_type) {
		return " datediff(" + diff_type + "," + startDate + "," + endDate + ") ";
	}	
	
	public String getDateAdd(String startDate, int number){
		return getDateAdd( startDate, number, 0);
	}
	
	public String getDateAdd(String startDate, int number, int param_type) {
		return " dateadd(day," + number + "," + startDate + ") ";
	}
	
	public String getIsNull() {
		return "isnull";
	}	
	
	public String getVirtualTable() {
		return "";
	}
	
	public String getConcatStr() {
		return getConcatStr("", "");
	}
	
	public String getConcatStr(String param1,String param2){
		return param1 + "+" + param2; 
	}

	public String getStrLen(String param) {
		return "len(" + param + ")"; 
	}
	
	public String getCLOB2String(String field_name) {
		return "convert(nvarchar(4000)," + field_name + " ) ";
	}	
	
	public String getXML2String(String field_name) {
		return "convert(nvarchar(4000)," + field_name + " ) ";
	}	
	
	public String getConvertDateStr(String fieldName, String javaFormat) {
		String r = "";
		String format = getDateFormat(javaFormat);
		if ("-111".equals(format)) {
			r = "replace(convert(varchar,getdate(),111),'-','/')";
		}
		else {
			r = "convert(varchar," + fieldName + "," + format + ")";
		}
		return r;
	}	
	
	public String getClobStrFromRS(ResultSet rs,String field_name) throws Exception{
		return rs.getString(field_name);
	}

	public String roundDate(String date) {
		return "cast(round(cast(cast(" + date + " as datetime) as float),0) as datetime) ";
	}

	public String getCharFun() {
		return "char";
	}
	
	public String getDateFormat(String javaFormat) {
		if ("yyyy-MM-dd".equals(javaFormat)) {
			return "-111";
		}
		else if ("yyyy-MM-dd HH:mm:ss".equals(javaFormat)) {
			return "120";
		}
		else {
			throw new RuntimeException("Unsupport java date format!");
		}
	}

	@Override
	public String getToDateFun(String dateString, String javaDateFormat) {
		return dateString;
	}
	@Override
	public String getEmptyString() {
		return "''";
	}
}
