package presentation.commands;

import businessLogic.BusinessFacade;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import presentation.Command;

/**
 *
 * @author Marcus
 */
public class AddCategoryCommand extends Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, BusinessFacade businessFacade) {
        String nextJsp = "index";

        String categoryName = request.getParameter("Category Name");
        String categoryDescription = request.getParameter("Category Description");
        try {
            businessFacade.createNewCategory(categoryName, categoryDescription);
        } catch (IllegalArgumentException ex) {
            nextJsp = "newCategory";
            request.setAttribute("error", ex.getMessage());
        }

        return nextJsp;
    }
}
