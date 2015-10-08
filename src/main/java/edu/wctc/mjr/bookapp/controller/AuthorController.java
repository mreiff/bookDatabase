package edu.wctc.mjr.bookapp.controller;

import edu.wctc.mjr.bookapp.model.Author;
import edu.wctc.mjr.bookapp.model.AuthorDao;
import edu.wctc.mjr.bookapp.model.AuthorDaoStrategy;
import edu.wctc.mjr.bookapp.model.AuthorService;
import edu.wctc.mjr.bookapp.model.DBStrategy;
import edu.wctc.mjr.bookapp.model.MySqlDb;
import java.io.IOException;
import java.util.List;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * The main controller for author-related activities
 *
 * @author Matthew
 */
@WebServlet(name = "AuthorController", urlPatterns = {"/AuthorController"})
public class AuthorController extends HttpServlet {

    private static final String NO_PARAM_ERR_MSG = "No request parameter identified";
    private static final String LIST_PAGE = "/listAuthors.jsp";
    private static final String LIST_ACTION = "list";
    private static final String ADD_ACTION = "add";
    private static final String UPDATE_ACTION = "update";
    private static final String DELETE_ACTION = "delete";
    private static final String ACTION_PARAM = "action";
    
    //For init method
    private String driverClass;
    private String url;
    private String userName;
    private String password;
    private String dbStrategyClassName;
    private String daoClassName;
    private DBStrategy db;
    private AuthorDaoStrategy authorDao;

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

        DBStrategy db = (DBStrategy) new MySqlDb();
        AuthorDaoStrategy authDao
                = new AuthorDao(db, "com.mysql.jdbc.Driver",
                        "jdbc:mysql://localhost:3306/book", "root", "admin");
        AuthorService authService = new AuthorService(authDao);

        try {
            
            if (action.equals(LIST_ACTION)) {
                List<Author> authors = null;
                authors = authService.getAllAuthors();
                request.setAttribute("authors", authors);
                destination = LIST_PAGE;

            } else if (action.equals(ADD_ACTION)) {
                // coming soon
                String authorNameAdd = request.getParameter("addAuthorName");
                String dateAdd = request.getParameter("addDate");
                
                
                //authService.addAuthor(tableName, authorName, date);
                authService.addAuthor(authorNameAdd, dateAdd);
                
                response.sendRedirect("http://localhost:8080/bookApp/AuthorController?action=list");
                return;
            } else if (action.equals(UPDATE_ACTION)) {
                String authorIdUpdate = request.getParameter("updateIdSelector");
                String authorNameUpdate = request.getParameter("updateAuthorName");
                String authorDateUpdate = request.getParameter("updateAuthorDate");
                // coming soon
                
                //authService
                response.sendRedirect("http://localhost:8080/bookApp/AuthorController?action=list");
                return;
            } else if (action.equals(DELETE_ACTION)) {
                String authorIdDelete = request.getParameter("delete");
                
                authService.deleteAuthor(authorIdDelete);
                
                response.sendRedirect("http://localhost:8080/bookApp/AuthorController?action=list");
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
        driverClass = getServletConfig().getInitParameter("driverClass");
        url = getServletConfig().getInitParameter("url");
        userName = getServletConfig().getInitParameter("userName");
        password = getServletConfig().getInitParameter("password");
        dbStrategyClassName = this.getServletConfig().getInitParameter("dbStrategy");
        daoClassName = this.getServletConfig().getInitParameter("authorDao");
    }

}
