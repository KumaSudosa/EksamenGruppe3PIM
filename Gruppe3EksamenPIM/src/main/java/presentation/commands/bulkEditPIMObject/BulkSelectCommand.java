/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentation.commands.bulkEditPIMObject;

import businessLogic.BusinessController;
import businessLogic.Category;
import businessLogic.PIMObject;
import businessLogic.Product;
import java.util.TreeSet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import presentation.Command;

/**
 *
 * @author Andreas
 */
public class BulkSelectCommand extends Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, BusinessController businessController) {
        String nextJsp = "bulkEdit";
        int pimObjectChoice = Integer.parseInt(request.getParameter("categoryID"));
        Category category = businessController.getCategoryFromID(pimObjectChoice);

        try {
            String[] productChoices = request.getParameterValues("productChoice");
            if (productChoices == null) {
                throw new IllegalArgumentException("Need at least 1 product to perform bulk edit");
            }
            request.getSession().setAttribute("productChoices", productChoices);
        } catch (IllegalArgumentException ex) {

            request.setAttribute("error", ex.getMessage());
            PIMObject pimObject = businessController.getCategoryFromID(pimObjectChoice);
            request.setAttribute("pimObject", pimObject);

            nextJsp = "bulkSelect";
        }

        request.setAttribute("category", category);
        return nextJsp;
    }

}