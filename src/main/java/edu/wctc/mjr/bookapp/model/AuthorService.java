package edu.wctc.mjr.bookapp.model;

import edu.wctc.mjr.bookapp.model.Author;
import edu.wctc.mjr.bookapp.model.AuthorDao;
import edu.wctc.mjr.bookapp.model.AuthorDaoStrategy;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;

/**
 * The high-level class in the DIP acts as a service to the rest of the app.
 * It depends on one or more strategy objects (DAOs) to delegate the work of 
 * accessing data on the database.
 * 
 * @author 
 */
public class AuthorService {
    private AuthorDaoStrategy dao;


    public AuthorService(AuthorDaoStrategy dao) {
        this.dao = dao;

    }
    
    public final List<Author> getAllAuthors() throws Exception {
        return dao.getAllAuthors();
    }
    
    // Test harness - not used in production
    public static void main(String[] args) throws Exception {
        AuthorService authServ = new AuthorService(
                new AuthorDao(new DBStrategy() {

            @Override
            public void closeConnection() throws SQLException {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void openConnection(DataSource ds) throws Exception {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void deleteById(String tableName, String primaryKeyFieldName, Object primaryKeyValue) throws SQLException {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public List<Map<String, Object>> findAllRecords(String tableName) throws SQLException {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void openConnection(String driverClass, String url, String userName, String password) throws Exception {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        },"com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/book", "root", "admin")
        );
        
        List<Author> authors = authServ.getAllAuthors();
        for(Author a: authors) {
            System.out.println(a);
        }
    }
}