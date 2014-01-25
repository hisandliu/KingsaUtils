package org.hisand.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import org.hisand.db.converter.MapConverter;

public class MapHandler implements ResultSetHandler<Map<String, Object>> {
	
	public MapHandler() {
		super();
	}
	
	@Override
	public Map<String, Object> handle(ResultSet rs) throws SQLException {
		return rs.next() ? toMap(rs) : null;
	}
	
	private Map<String, Object> toMap(ResultSet rs) throws SQLException {
        MapConverter converter = new MapConverter();
        return converter.handler(rs);
    }
	
}