package com.flyback;

import java.sql.SQLException;
import java.util.List;

public interface IndexColumnsReader {
    List<IndexColumn> get() throws SQLException, ClassNotFoundException;
}
