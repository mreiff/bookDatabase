/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.mjr.bookapp.model;

import com.mysql.jdbc.jdbc2.optional.MysqlConnectionPoolDataSource;
import java.util.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.naming.spi.InitialContextFactory;
import javax.naming.spi.InitialContextFactoryBuilder;
import javax.naming.spi.NamingManager;
import javax.sql.DataSource;

/**
 *
 * @author Matthew
 */
public class ConnectionPoolAuthorDao implements AuthorDaoStrategy{
    
    private DataSource ds;
    private DBStrategy db;

    public ConnectionPoolAuthorDao(DataSource ds, DBStrategy db) {
        this.ds = ds;
        this.db = db;
    }
    
    @Override
    public final Author getAuthorById(int authorId) throws Exception {
        db.openConnection(ds);
        
        //Map<String,Object> result = db.findById("author", "author_id", authorId);
        Author author = new Author();
        //author.setAuthorId((Integer)result.get("author_id"));
        //author.setAuthorName(result.get("author_name").toString());
        //author.setDateAdded((Date)result.get("date_added"));
        
        return author;
    }

    @Override
    public final List<Author> getAllAuthors() throws Exception {
        db.openConnection(ds);
        List<Author> records = new ArrayList<>();

        List<Map<String,Object>> row = db.findAllRecords("author");
        for(Map result : row) {
            Author author = new Author();
            Object obj = result.get("author_id");
            author.setAuthorId(Integer.parseInt(obj.toString()));
            
            String name = result.get("author_name") == null ? "" : result.get("author_name").toString();
            author.setAuthorName(name);
            
            obj = result.get("date_added");
            Date dateAdded = (obj == null) ? new Date() : (Date)result.get("date_added");
            author.setDateAdded(dateAdded);
            records.add(author);
        }
        
        return records;
        
    }
    
    @Override
    public void deleteAuthorById(int authorId) throws Exception {
        db.openConnection(ds);
        
        db.deleteById("author", "author_id", authorId);    
    }
    
    @Override
    public void createAuthor(String authorName, String date) throws Exception {
        db.openConnection(ds);
        
            db.addRecord("author", Arrays.asList("author_name","date_added"), Arrays.asList(authorName,new Date()));
               
    }

    public static void main(String[] args) throws Exception {
        
        NamingManager.setInitialContextFactoryBuilder(new InitialContextFactoryBuilder() {

            @Override
            public InitialContextFactory createInitialContextFactory(Hashtable<?, ?> environment) throws NamingException {
                return new InitialContextFactory() {

                    @Override
                    public Context getInitialContext(Hashtable<?, ?> environment) throws NamingException {
                        return new InitialContext(){

                            private Hashtable<String, DataSource> dataSources = new Hashtable<>();

                            @Override
                            public Object lookup(String name) throws NamingException {

                                if (dataSources.isEmpty()) { 
                                    MysqlConnectionPoolDataSource ds = new MysqlConnectionPoolDataSource();
                                    ds.setURL("jdbc:mysql://localhost:3306/book");
                                    ds.setUser("root");
                                    ds.setPassword("admin");
                                    dataSources.put("jdbc/book", ds);

                                }

                                if (dataSources.containsKey(name)) {
                                    return dataSources.get(name);
                                }

                                throw new NamingException("Unable to find datasource: "+name);
                            }
                        };
                    }

                };
            }

        });
        
        Context ctx = new InitialContext();
        DataSource ds = (DataSource) ctx.lookup("jdbc/book");

        AuthorDaoStrategy dao = new ConnectionPoolAuthorDao(ds, new MySqlDb());

        List<Author> authors = dao.getAllAuthors();
        for (Author a : authors) {
            System.out.println(a);
        }
    }

    @Override
    public void updateAuthor(String authorId, String authorName, String date) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
