package com.flyback;

import java.sql.SQLException;
import java.util.List;

public interface ViewsReader {
    List<SaveableAsTextFile> get() throws SQLException, ClassNotFoundException;
}
