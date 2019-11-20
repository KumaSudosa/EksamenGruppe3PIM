package presentation.commands;

import businessLogic.BusinessFacade;
import businessLogic.Category;
import java.util.ArrayList;
import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import presentation.Command;

/**
 * 
 * @author Marcus
 */
public class EditAttributesToCategoryCommand extends Command {
    
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, BusinessFacade businessFacade) {
        String nextJsp = "editCategory";
        ArrayList<String> attributeChoices;
        
        if(request.getParameterValues("attributeChoices") != null){
            attributeChoices = new ArrayList(Arrays.asList(request.getParameterValues("attributeChoices")));
        } else {
            attributeChoices = new ArrayList();
        }
        Category category = businessFacade.getCategoryFromID(Integer.parseInt(request.getParameter("categoryID")));
        
        businessFacade.editAttributesToCategory(category, attributeChoices);
        
        request.setAttribute("category", category);
        
        return nextJsp;
    }
}