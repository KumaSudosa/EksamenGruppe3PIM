package presentation.commands;

import businessLogic.BusinessFacade;
import businessLogic.Category;
import businessLogic.Product;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import presentation.Command;

/**
 *
 * @author cahit
 */
public class GoToJspCommand extends Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, BusinessFacade businessFacade) {
        String jspPage = request.getParameter("goToJsp");

        switch (jspPage) {
            case "viewAllProducts":
                ArrayList<Product> productList = businessFacade.getProductList();
                request.setAttribute("productList", productList);
                break;
            case "viewAllCategories":;
                ArrayList<Category> categoryList = businessFacade.getCategoryList();
                request.setAttribute("categoryList", categoryList);
                break;
            default:
                break;
        }

        return jspPage;
    }

}
