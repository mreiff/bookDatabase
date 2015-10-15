package edu.wctc.mjr.bookapp.model;

import java.util.Date;
import java.util.List;

/**
 * The high-level class in the DIP acts as a service to the rest of the app.
 * It depends on one or more strategy objects (DAOs) to delegate the work of 
 * accessing data on the database.
 * 
 * @author Matthew
 */
public class AuthorService {
    private AuthorDaoStrategy dao;


    public AuthorService(AuthorDaoStrategy dao) {
        this.dao = dao;
    }
    
    public final List<Author> getAllAuthors() throws Exception {
        return dao.getAllAuthors();
    }
    
    public void deleteAuthor(String authorId) throws Exception{
        dao.deleteAuthorById(Integer.parseInt(authorId));
    }
    
    public void addAuthor(String authorName, String date) throws Exception{
        dao.createAuthor(authorName, date);
    }
    
}