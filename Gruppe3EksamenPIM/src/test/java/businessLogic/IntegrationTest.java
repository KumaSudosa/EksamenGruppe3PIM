/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessLogic;

import factory.SystemMode;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

/**
 *
 * @author Michael N. Korsgaard
 */
public class IntegrationTest {

    private static Connection testConnection;
    private final BusinessFacade businessFacade = new BusinessFacade(SystemMode.TEST);

    ;
    
    @Before
    public void setup() {
        try {
            testConnection = businessFacade.getStorageFacade().getDatabase().getConnection();
            // reset test database
            try (Statement stmt = testConnection.createStatement()) {
                stmt.execute("drop table if exists Categories");
                stmt.execute("create table Categories like Categories_Test");
                stmt.execute("insert into Categories select * from Categories_Test");
                stmt.execute("drop table if exists Product");
                stmt.execute("create table Product like Product_Test");
                stmt.execute("insert into Product select * from Product_Test");
                stmt.execute("drop table if exists Product_Distributor");
                stmt.execute("create table Product_Distributor like Product_Distributor_Test");
                stmt.execute("insert into Product_Distributor select * from Product_Distributor_Test");
                businessFacade.setupListsFromDB();
            }

        } catch (SQLException ex) {
            testConnection = null;
            System.out.println("Could not open connection to database: " + ex.getMessage());
        }
    }

    @Test
    public void testSetUpOK() {
        // Just check that we have a connection.
        assertNotNull(testConnection);
    }

    //Category createNewCategory(String categoryName, String categoryDescription) throws IllegalArgumentException
    @Test
    public void testCreateNewCategory(){
        //arrange
        String categoryName = "New Category";
        String categoryDescription = "This is a new category, for new things";
        
        //act
        Category result = businessFacade.createNewCategory(categoryName, categoryDescription);
        
        //assert
        assertTrue(categoryName.equals(result.getName()));
        assertTrue(categoryDescription.equals(result.getDescription()));
        assertTrue(businessFacade.getCategoryList().contains(result));
    }
    
    //boolean deleteCategory(int categoryID)
    @Test
    public void testDeleteCategory(){
        //arrange
        int categoryID = 1;
        
        //act
        boolean result = businessFacade.deleteCategory(categoryID);
        
        //assert
        ArrayList<Category> categoryList = businessFacade.getCategoryList();
        assertTrue(result);
        for (Category category : categoryList) {
            assertNotEquals(categoryID, category.getCategoryID());
        }
        
    }
    
    //Product createNewProduct(String productName, String productDescription, ArrayList<String> productDistributors)
    @Test
    public void testCreateNewProduct() {
        //arrange
        String productName = "Newest Product";
        String productDescription = "This is new newest product for testing";
        String firstDistributor = "1st Distributor";
        String secondDistributor = "2nd Distributor";
        ArrayList<String> productDistributors = new ArrayList(Arrays.asList(new String[]{firstDistributor, secondDistributor}));

        //act
        Product result = businessFacade.createNewProduct(productName, productDescription, productDistributors);

        //assert
        assertTrue(productName.equals(result.getName()));
        assertTrue(productDescription.equals(result.getDescription()));
        assertEquals(productDistributors, result.getDistributors());
        assertTrue(businessFacade.getProductList().contains(result));
    }
    
    //boolean deleteProduct(int productID)
    @Test
    public void testDeleteProduct(){
        //arrange
        int productID = 1;
        
        //act
        boolean result = businessFacade.deleteProduct(productID);
        
        //assert
        ArrayList<Product> productList = businessFacade.getProductList();
        assertTrue(result);
        for (Product product : productList) {
            assertNotEquals(productID, product.getProductID());
        }
    }
    
    //void editProduct(int productID, String productName, String productDescription, ArrayList<String> productDistributors)
    @Test
    public void testEditProduct(){
        //arrange
        int productID = 1;
        String productName = "Newest Product";
        String productDescription = "This is new newest product for testing";
        String firstDistributor = "1st Distributor";
        String secondDistributor = "2nd Distributor";
        ArrayList<String> productDistributors = new ArrayList(Arrays.asList(new String[] {firstDistributor, secondDistributor}));
        
        //act
        businessFacade.editProduct(productID, productName, productDescription, productDistributors);
        Product result = businessFacade.getProductFromID(productID);
        
        //assert
        assertTrue(result.getName().equals(productName));
        assertTrue(result.getDescription().equals(productDescription));
        assertTrue(result.getDistributors().contains(firstDistributor));
        assertTrue(result.getDistributors().contains(secondDistributor));
        
    }
    
    //void updatePicturePath(int productID, String picturePath)
    @Test
    public void testUpdatePicturePath(){
        //arrange
        int productID = 1;
        String picturePath = "newPic.img";
        
        //act
        businessFacade.updatePicturePath(productID, picturePath);
        Product result = businessFacade.getProductFromID(productID);
        
        //assert
        assertTrue(picturePath.equals(result.getPicturePath()));
        
    }
}