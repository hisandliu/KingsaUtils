package org.hisand.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hisand.db.converter.MapConverter;

public class MapListHandler implements
		ResultSetHandler<List<Map<String, Object>>> {

	@Override
	public List<Map<String, Object>> handle(ResultSet rs) throws SQLException {
		List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();
		while (rs.next()) {
			rows.add(toMap(rs));
		}
		return rows;
	}

	private Map<String, Object> toMap(ResultSet rs) throws SQLException {
        MapConverter converter = new MapConverter();
        return converter.handler(rs);
    }
}
