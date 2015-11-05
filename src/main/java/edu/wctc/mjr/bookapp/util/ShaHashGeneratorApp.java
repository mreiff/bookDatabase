package edu.wctc.mjr.bookapp.util;

import java.util.Scanner;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;

/**
 * This app will generate a salted, SHA-512 hashed password based on the 
 * username and password field values in the users table in the db. Use this 
 * to initially seed your database with the hashed passwords. NOTE that you
 * can just run this class as a standalone app. A similar routine is provided
 * in the web app for registering new users. You only need to seed the
 * database with one account -- an admin account. From there on you can use
 * the web app instead of this program to create new users.
 * 
 * 
 *       THIS WAS MODIFIED BY CHIRS BISHOP
 * 
 *       I, Matthew Reiff, WANTED TO TEST HIS UNIQUE CODE SINCE IT FUNCTIONED IN A COOL WAY
 * 
 * @author Jim Lombardo, james.g.lombardo@gmail.com
 */
public class ShaHashGeneratorApp {

    public static int SHA_TYPE = 512;
    public static int SHA_ITERATIONS = 1024;
    
    /**
     * @param args the command line arguments - not used.
     */
    public static void main(String[] args) {
        try(Scanner scan = new Scanner(System.in)){
            while(true){            
                System.out.println("Please enter a username:");
                String username = scan.nextLine();
                if(username.equalsIgnoreCase("exit")){
                    break;
                }

                System.out.println("\nPlease enter a password:");
                String password = scan.nextLine();
                if(username.equalsIgnoreCase("exit")){
                    break;
                }

                System.out.println("\nResults:");
                System.out.println(hash(password, username) + "\n\n");
            }
        }
    }
    
    /**
     * Hash the password.
     * @param password
     * @param salt
     * @return 
     */
    public static String hash(String password, String salt) {
        ShaPasswordEncoder pe = new ShaPasswordEncoder(SHA_TYPE);
        pe.setIterations(SHA_ITERATIONS);
        String hash = pe.encodePassword(password, salt);

        return hash;
    }
}
