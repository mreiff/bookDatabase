package edu.wctc.mjr.bookapp.model;

import java.sql.SQLException;
import java.util.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;

/**
 * This DAO is a strategy object that uses a manual connection to a database. 
 * Such connections are not very performant so it's best to use a connection 
 * pool created on the app server. See ConnPoolAuthorDao.java for an 
 * example of a DAO that talks to such a pool.
 * 
 * @author 
 */
public class AuthorDao implements AuthorDaoStrategy {
    
    private DBStrategy db;
    private String driverClass;
    private String url;
    private String userName;
    private String password;
    
    public AuthorDao(DBStrategy db, String driverClass, String url, String userName, String password) {
        this.db = db;
        this.driverClass = driverClass;
        this.url = url;
        this.userName = userName;
        this.password = password;
    }
    
    @Override
    public final List<Author> getAllAuthors() throws Exception {
        db.openConnection(driverClass, url, userName, password);
        List<Author> authors = new ArrayList<>();

        List<Map<String,Object>> results = db.findAllRecords("author");
        for(Map row : results) {
            Author author = new Author();
            
            Object obj = row.get("author_id");
            author.setAuthorId(Integer.parseInt(obj.toString()));
            
            String name = row.get("author_name").toString();
            author.setAuthorName(name);
            
            Date dateCreated = (Date)row.get("date_created");
            author.setDateAdded(dateCreated);
            
            authors.add(author);
        }
        
        db.closeConnection();
        
        return authors;
    }
    
    @Override
    public void deleteAuthorById(int authorId) throws Exception {
        db.openConnection(driverClass, url, userName, password);
        
        db.deleteById("author", "author_id", authorId);
        
        db.closeConnection();
    }

    @Override
    public Author getAuthorById(int authorId) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void createAuthor(String authorName, String date) throws Exception {
        db.openConnection(driverClass, url, userName, password);
        
        db.addRecord("author", Arrays.asList("author_name","date_added"), Arrays.asList(authorName,new Date()));
        
        db.closeConnection();
    }

    @Override
    public void updateAuthor(String authorId, String authorName, String date) throws Exception {
        db.openConnection(driverClass, url, userName, password);
        
        db.updateRecord("author", Arrays.asList("author_name", "date_added"), Arrays.asList(authorName,new Date()), userName, userName);
       
        db.closeConnection();
    }
}
