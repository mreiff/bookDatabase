/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.mjr.bookapp.repository;

import edu.wctc.mjr.bookapp.entity.Book;
import java.io.Serializable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Matthew
 */
public interface BookRepository extends JpaRepository<Book, Integer>, Serializable{
    
}
