package org.hisand.db;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Clob;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OracleDialect implements Dialect {
	public OracleDialect() {
	}
	
	@Override 
	public DBType getDbType() {
		return DBType.ORACLE;
	}
	
	@Override
	public String getSysDateFun() {
		return "sysdate"; 
	}
	@Override
	public String getSysDateSQL() {
		return "select sysdate from dual";
	}
	@Override
	public String getDateDiffSQL(String startDate, String endDate) {
		return getDateDiffSQL(startDate, endDate, "=");
	}
	@Override
	public String getDateDiffSQL(String startDate, String endDate, String op) {
		return getDateDiffSQL(startDate, endDate, op, "0", "day");
	}
	@Override
	public String getDateDiffSQL(String startDate,String endDate, String op, String value) {
		return getDateDiffSQL(startDate, endDate, op, value ,"day");
	}
	@Override
	public String getDateDiffSQL(String startDate, String endDate, String op, String value, String diff_type) {
		return getDateDiffSQL(startDate, endDate, op, value, diff_type, 0, 0);
	}
	@Override
	public String getDateDiffSQL(String startDate, String endDate, String op, String value, String diff_type, int param1_type, int param2_type) {
		return getDateDiffSQL(startDate, endDate, op, value, diff_type, param1_type, param2_type, false, false);
	}
	@Override
	public String getDateDiffSQL(String startDate, String endDate, String op, String value, String diff_type, int param1_type, int param2_type, boolean param1_is_trunc, boolean param2_is_trunc) {
		if (param1_type == 1) {
			startDate = "to_date(" + startDate + ",'YYYY-MM-DD')";
		}
		if (param2_type==1) {
			endDate = "to_date(" + endDate + ",'YYYY-MM-DD')";
		}
		if (param1_is_trunc) {
			startDate = "trunc("+startDate+")";
		}
		if (param2_is_trunc) {
			endDate = "trunc("+endDate+")";
		}
		
		if ("0".equalsIgnoreCase(value)) {
			return " " + endDate+op+startDate + " ";
		}
		else {
			return " (" + endDate + "-" + startDate + ")" + op + value + " ";
		}
	}
	@Override
	public String getDateDiffAmountSQL(String startDate, String endDate) {
		return getDateDiffAmountSQL(startDate, endDate, "day", 0, 0);
	}
	@Override
	public String getDateDiffAmountSQL(String startDate, String endDate, String diff_type, int param1_type, int param2_type) {
		if (param1_type == 1) {
			//is date string
			startDate = "to_date(" + startDate + ",'YYYY-MM-DD')";
		}
		if (param2_type==1) {
			//is date string
			endDate="to_date(" + endDate + ",'YYYY-MM-DD')";
		}
		return " (trunc(" + endDate + ")-trunc(" + startDate + ")) ";
	}	
	@Override
	public String getDateAdd(String startDate, int number){
		return getDateAdd(startDate, number, 0);
	}
	@Override
	public String getDateAdd(String startDate, int number, int param_type) {
		if (param_type == 1){
			//is date string
			startDate="to_date(" + startDate + ",'YYYY-MM-DD')";
		}
		return " " + startDate + "+" + number + " "; 
	}
	@Override
	public String getIsNull() {
		return "nvl"; 
	}	
	@Override
	public String getVirtualTable() {
		return "from dual"; 
	}
	@Override
	public String getConcatStr() {
		return getConcatStr("", "");
	}
	@Override
	public String getConcatStr(String param1,String param2){
		return param1 + "||" + param2; 
	}
	@Override
	public String getStrLen(String param) {
		return "length(" + param + ")"; 
	}
	@Override
	public String getCLOB2String(String field_name) {
		return "to_char(" + field_name + ")"; 
	}	
	@Override
	public String getXML2String(String field_name) {
		return " " + field_name + ".getStringVal()"; 
	}	
	@Override
	public String getConvertDateStr(String fieldName, String javaFormat) {
		String format = getDateFormat(javaFormat);
		return "to_char(" + fieldName + ", '" + format + "')"; 
	}	
	@Override
	public String getClobStrFromRS(ResultSet rs,String field_name) throws Exception {
		Clob clob = (Clob)(rs.getClob(field_name));
		return clobToString(clob);
	}

	private String clobToString(Clob clob) throws SQLException,
			IOException {

		String reString = "";
		Reader is = clob.getCharacterStream();// 得到流
		BufferedReader br = new BufferedReader(is);
		String s = br.readLine();
		StringBuffer sb = new StringBuffer();
		while (s != null) {
			// 执行循环将字符串全部取出付值给StringBuffer由StringBuffer转成STRING
			sb.append(s);
			s = br.readLine();
		}
		reString = sb.toString();
		return reString;
	}
	@Override
	public String roundDate(String date) {
		return "round(" + date + ")";
	}
	@Override
	public String getCharFun() {
		return "chr";
	}
	@Override
	public String getDateFormat(String javaFormat) {
		if ("yyyy-MM-dd".equals(javaFormat)) {
			return "YYYY-MM-DD";
		}
		else if ("yyyy-MM-dd HH:mm:ss".equals(javaFormat)) {
			return "YYYY-MM-DD HH24:MI:ss";
		}
		else {
			throw new RuntimeException("Unsupport java date format!");
		}
	}

	@Override
	public String getToDateFun(String dateString, String javaDateFormat) {
		String format = getDateFormat(javaDateFormat);
		String r = "to_date(" + dateString + ", '" + format + "')";
		return r;
	}
	
	@Override
	public String getEmptyString() {
		return "' '";
	}

}
