package edu.wctc.mjr.bookapp.controller;

import edu.wctc.mjr.bookapp.entity.Book;
import edu.wctc.mjr.bookapp.service.BookService;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.inject.Inject;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * The main controller for book-related activities
 *
 * @book Matthew
 */
@WebServlet(name = "BookController", urlPatterns = {"/BookController"})
public class BookController extends HttpServlet {

    private static final String NO_PARAM_ERR_MSG = "No request parameter identified";
    private static final String LIST_PAGE = "/listBooks.jsp";
    private static final String LIST_ACTION = "list";
    private static final String ADD_ACTION = "add";
    private static final String UPDATE_ACTION = "update";
    private static final String DELETE_ACTION = "delete";
    private static final String ACTION_PARAM = "action";  
    
    private static final String LIST_PAGE_URI = "http://localhost:8080/bookApp/BookController?action=list";
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String destination = LIST_PAGE;
        String action = request.getParameter(ACTION_PARAM);

        ServletContext sctx = getServletContext();
        WebApplicationContext ctx
                = WebApplicationContextUtils.getWebApplicationContext(sctx);
        BookService bookService = (BookService) ctx.getBean("bookService");
        
        try {
            
            if (action.equals(LIST_ACTION)) {
                List<Book> books = null;
                //List<Book> books = null;
                //books = authService.findAll();
                books = bookService.findAll();
                request.setAttribute("books", books);
                destination = LIST_PAGE;

            } else if (action.equals(ADD_ACTION)) {
                // coming soon
                String bookTitleAdd = request.getParameter("addBookTitle");
                String bookIsbnAdd = request.getParameter("addBookIsbn");
                Book book = null;
                
                book = new Book(0);
                book.setTitle(bookTitleAdd);
                book.setIsbn(bookIsbnAdd);
                
                bookService.edit(book);
                
                response.sendRedirect(LIST_PAGE_URI);
                return;
            } else if (action.equals(UPDATE_ACTION)) {
                String bookIdUpdate = request.getParameter("updateIdSelector");
                String bookTitleUpdate = request.getParameter("updatebookTitle");
                String bookIsbnUpdate = request.getParameter("updatebookIsbn");
                Book book = new Book(new Integer(bookIdUpdate));
                // coming soon
                
                book.setTitle(bookTitleUpdate);
                book.setIsbn(bookIsbnUpdate);
                                
                bookService.edit(book);
                
                //authService
                response.sendRedirect(LIST_PAGE_URI);
                return;
            } else if (action.equals(DELETE_ACTION)) {
                String bookIdDelete = request.getParameter("delete");
                
                Book book = bookService.findById(bookIdDelete);
                bookService.remove(book);
                
                response.sendRedirect(LIST_PAGE_URI);
                return;
            } else {
                // no param identified in request, must be an error
                request.setAttribute("errMsg", NO_PARAM_ERR_MSG);
                destination = LIST_PAGE;
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Forward to destination page
        RequestDispatcher dispatcher
                = getServletContext().getRequestDispatcher(destination);
        dispatcher.forward(request, response);
        
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
    
    @Override
    public void init() throws ServletException {
        // Get init params from web.xml
        
    }

}
