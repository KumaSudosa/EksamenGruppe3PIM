package presentation.commands.editPIMObject;

import businessLogic.BusinessController;
import businessLogic.Attribute;
import businessLogic.Category;
import java.util.TreeSet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import presentation.Command;

public class SelectAttributesForCategoryCommand extends Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, BusinessController businessController) {

        String nextJsp = "editAttributeToCategory";
        int categoryID = Integer.parseInt(request.getParameter("categoryID"));

        String pimObjectType = request.getParameter("PIMObjectType");
        request.setAttribute("PIMObjectType", pimObjectType);

        Category category = businessController.getCategoryFromID(categoryID);
        TreeSet<Attribute> attributeList = businessController.getAttributeList();

        request.setAttribute("attributeList", attributeList);
        request.setAttribute("category", category);

        return nextJsp;
    }
}
