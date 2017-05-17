package com.flyback;

import java.sql.SQLException;
import java.util.List;

public interface TableNamesReader {
    List<String> get(String owner) throws SQLException, ClassNotFoundException;
}
