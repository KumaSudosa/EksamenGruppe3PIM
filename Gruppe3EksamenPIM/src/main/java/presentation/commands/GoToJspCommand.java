
package presentation.commands;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import presentation.Command;

/**
 *
 * @author cahit
 */
public class GoToJspCommand extends Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        // Get parameters and set initial nextJspPage String
        //String jspPage = "newProduct";
        String jspPage = request.getParameter("goToJsp");

        // Set Attributes and go to next Page
        return jspPage;
    }
    
}