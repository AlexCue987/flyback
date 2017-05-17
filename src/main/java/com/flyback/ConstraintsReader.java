package com.flyback;

import java.sql.SQLException;
import java.util.List;

public interface ConstraintsReader {
    List<Constraint> get(String owner) throws SQLException, ClassNotFoundException;
}
