package org.hisand.db;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.hisand.db.converter.XmlConverter;

public class XmlHandler implements ResultSetHandler<String> {

	@Override
	public String handle(ResultSet rs) throws SQLException {
		XmlConverter converter = new XmlConverter();
		return converter.handler(rs);
	}

}
