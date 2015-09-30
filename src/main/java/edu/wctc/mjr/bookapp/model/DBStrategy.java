package edu.wctc.mjr.bookapp.model;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;

/**
 *
 * @author 
 */
public interface DBStrategy {

    void closeConnection() throws SQLException;
    
    void openConnection(DataSource ds) throws Exception;

    void deleteById(String tableName, String primaryKeyFieldName, Object primaryKeyValue) throws SQLException;

    List<Map<String, Object>> findAllRecords(String tableName) throws SQLException;

    void openConnection(String driverClass, String url, String userName, String password) throws Exception;
    
}
