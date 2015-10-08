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

    int deleteById(String tableName, String columnName, Object value) throws SQLException;

    List<Map<String, Object>> findAllRecords(String tableName) throws SQLException;

    void openConnection(String driverClass, String url, String userName, String password) throws Exception;
    
    int addRecord(String tableName, List columnName, List values) throws SQLException, Exception;

    public int updateRecord(String tableName, List columnName, List values, String whereField, Object whereValue) throws SQLException;
    
    //public int findById(String author, String author_Id, int authorId) throws SQLException;
    
}
