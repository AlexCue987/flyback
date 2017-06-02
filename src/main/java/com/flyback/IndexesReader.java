package com.flyback;

import java.sql.SQLException;
import java.util.List;

public interface IndexesReader {
    List<Index> get() throws SQLException, ClassNotFoundException;
}
