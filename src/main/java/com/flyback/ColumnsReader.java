package com.flyback;

import java.sql.SQLException;
import java.util.List;

public interface ColumnsReader {
    List<Column> get(String tableName) throws SQLException, ClassNotFoundException;
}
