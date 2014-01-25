package org.hisand.db.converter;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface RowConverter<T> {
	public T handler(ResultSet rs)  throws SQLException;
}
