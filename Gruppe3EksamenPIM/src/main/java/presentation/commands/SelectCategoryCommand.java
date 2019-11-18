/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentation.commands;

import businessLogic.BusinessFacade;
import businessLogic.Category;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import presentation.Command;

/**
 *
 * @author andre
 */
public class SelectCategoryCommand extends Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, BusinessFacade businessFacade) {

        String nextJsp = null;
        String commandType = request.getParameter("submitButton");
        String categoryChoiceString = request.getParameter("categoryChoice");

        if (categoryChoiceString != null) {

            int categoryChoice = Integer.parseInt(categoryChoiceString);
            Category category = businessFacade.getCategoryFromID(categoryChoice);

            if (commandType.equals("Edit Category")) {
                nextJsp = "editCategory";
            } else if (commandType.equals("Delete Category")) {
                nextJsp = "deleteCategory";
            }

            request.setAttribute("category", category);
        } else {
            ArrayList<Category> categoryList = businessFacade.getCategoryList();
            request.setAttribute("categoryList", categoryList);
            nextJsp = "viewAllCategories";
            
            request.setAttribute("error", "No Category Selected!");
        }

        return nextJsp;
    }

}