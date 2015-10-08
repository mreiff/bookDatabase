/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.mjr.bookapp.model;

import java.util.Date;
import java.util.List;

/**
 *
 * @author Matthew
 */
public interface AuthorDaoStrategy {
    
    Author getAuthorById(int authorId) throws Exception;
    
    List<Author> getAllAuthors() throws Exception;
    
    void deleteAuthorById(int authorId) throws Exception;
    
    void createAuthor(String authorName, String date) throws Exception;
    
    void updateAuthor(String authorId, String authorName, String date) throws Exception;
}
