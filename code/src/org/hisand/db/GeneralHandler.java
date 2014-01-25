package org.hisand.db;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.hisand.db.converter.RowConverter;

public class GeneralHandler<T> implements ResultSetHandler<T> {
	
	private RowConverter<T> converter;
	public GeneralHandler(RowConverter<T> converter) {
		super();
		this.converter = converter;
	}
	
	@Override
	public T handle(ResultSet rs) throws SQLException {
		return converter.handler(rs);
	}

}
