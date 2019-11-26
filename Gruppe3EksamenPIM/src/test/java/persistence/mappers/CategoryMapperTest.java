package persistence.mappers;

import businessLogic.Attribute;
import businessLogic.Category;
import factory.SystemMode;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import persistence.DB;

/**
 *
 * @author Michael N. Korsgaard
 */
public class CategoryMapperTest {

    private static Connection testConnection;
    private final DB database = new DB(SystemMode.TEST);
    private final CategoryMapper categoryMapper = new CategoryMapper(database);
    private final int numberOfCategoriesInDB = 3;
    private ArrayList<Attribute> attributeList = new ArrayList();

    @Before
    public void setup() {
        try {
            testConnection = database.getConnection();
            // reset test database
            try (Statement stmt = testConnection.createStatement()) {
                stmt.execute("drop table if exists Product_Distributor");
                stmt.execute("drop table if exists Product_Categories");
                stmt.execute("drop table if exists Product_Attributes");
                stmt.execute("drop table if exists Category_Attributes");
                stmt.execute("drop table if exists Product");
                stmt.execute("drop table if exists Categories");
                stmt.execute("drop table if exists Attributes");
                stmt.execute("drop table if exists Distributor");

                stmt.execute("create table Product like Product_Test");
                stmt.execute("insert into Product select * from Product_Test");

                stmt.execute("create table Categories like Categories_Test");
                stmt.execute("insert into Categories select * from Categories_Test");

                stmt.execute("create table Attributes like Attributes_Test");
                stmt.execute("insert into Attributes select * from Attributes_Test");
                
                stmt.execute("create table Distributor like Distributor_Test");
                stmt.execute("insert into Distributor select * from Distributor_Test");

                stmt.execute("create table Product_Distributor like Product_Distributor_Test");
                stmt.execute("ALTER TABLE Product_Distributor ADD FOREIGN KEY(Product_ID) REFERENCES Product(Product_ID)");
                stmt.execute("ALTER TABLE Product_Distributor ADD FOREIGN KEY(Distributor_ID) REFERENCES Distributor(Distributor_ID)");
                stmt.execute("insert into Product_Distributor select * from Product_Distributor_Test");

                stmt.execute("create table Product_Categories like Product_Categories_Test");
                stmt.execute("ALTER TABLE Product_Categories ADD FOREIGN KEY(Category_ID) REFERENCES Categories(Category_ID)");
                stmt.execute("ALTER TABLE Product_Categories ADD FOREIGN KEY(Product_ID) REFERENCES Product(Product_ID)");
                stmt.execute("insert into Product_Categories select * from Product_Categories_Test");

                stmt.execute("create table Product_Attributes like Product_Attributes_Test");
                stmt.execute("ALTER TABLE Product_Attributes ADD FOREIGN KEY(Product_ID) REFERENCES Product(Product_ID)");
                stmt.execute("ALTER TABLE Product_Attributes ADD FOREIGN KEY(Attribute_ID) REFERENCES Attributes(Attribute_ID)");
                stmt.execute("insert into Product_Attributes select * from Product_Attributes_Test");

                stmt.execute("create table Category_Attributes like Category_Attributes_Test");
                stmt.execute("ALTER TABLE Category_Attributes ADD FOREIGN KEY(Category_ID) REFERENCES Categories(Category_ID)");
                stmt.execute("ALTER TABLE category_attributes ADD FOREIGN KEY(Attribute_ID) REFERENCES Attributes(Attribute_ID)");
                stmt.execute("insert into Category_Attributes select * from Category_Attributes_Test");
            }

        } catch (SQLException ex) {
            testConnection = null;
            System.out.println("Could not open connection to database: " + ex.getMessage());
        }
        attributeList.clear();
    }

    @Test
    public void testSetUpOK() {
        // Just check that we have a connection.
        assertNotNull(testConnection);

    }

    public CategoryMapperTest() {
    }

    /**
     * Test of addNewCategory method, of class CategoryMapper.
     */
    @Test
    public void testAddNewCategory() {
        //arrange
        String categoryName = "New Category";
        String categoryDescription = "This is a new category";

        //act
        Category result = categoryMapper.addNewCategory(categoryName, categoryDescription);

        //assert
        int expResultID = 4;
        assertEquals(expResultID, result.getCategoryID());
        assertTrue(categoryName.equals(result.getName()));
        assertTrue(categoryDescription.equals(result.getDescription()));
    }

    /**
     * Negative Test of addNewCategory method, of class CategoryMapper.<br>
     * Name is unique, so Category Mapper should throw IllegalArgumentException if dublicate name is attempted to be uploaded to DB
     */
    @Test(expected = IllegalArgumentException.class)
    public void testNegativeAddNewCategoryDublicateName() {
        //arrange
        String categoryName = "New Category";
        String categoryDescriptionNr1 = "First new description";
        String categoryDescriptionNr2 = "Second new description";

        //act
        categoryMapper.addNewCategory(categoryName, categoryDescriptionNr1);
        categoryMapper.addNewCategory(categoryName, categoryDescriptionNr2);
    }

    /**
     * Negative Test of addNewCategory method, of class CategoryMapper.<br>
     * Name field in DB is made to be not null, uniqie varchar(255).<br>
     * Names with null value should throw an exception.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testNegativeAddNewCategoryNullName() {
        //arrange
        String categoryName = null;
        String categoryDescription = "new description";

        //act
        categoryMapper.addNewCategory(categoryName, categoryDescription);
    }

    /**
     * Test of addNewCategory method, of class CategoryMapper.<br>
     * Name field in DB is made to be not null, uniqie varchar(255).
     */
    @Test
    public void testAddNewCategoryNameLengthAtLimit() {
        //arrange
        String categoryName = "";
        for (int i = 0; i < 255; i++) {
            categoryName += "n";
        }
        String categoryDescription = "new description";

        //act
        Category result = categoryMapper.addNewCategory(categoryName, categoryDescription);

        //assert
        int expResultID = 4;
        assertEquals(expResultID, result.getCategoryID());
        assertTrue(categoryName.equals(result.getName()));
        assertTrue(categoryDescription.equals(result.getDescription()));
    }

    /**
     * Negative Test of addNewCategory method, of class CategoryMapper.<br>
     * Name field in DB is made to be not null, uniqie varchar(255).<br>
     * Names exceeding the 255 varchar limit should cause an exception to be thrown
     */
    @Test(expected = IllegalArgumentException.class)
    public void testNegativeAddNewCategoryNameLengthExceedLimit() {
        //arrange
        String categoryName = "";
        for (int i = 0; i < 256; i++) {
            categoryName += "n";
        }
        String categoryDescription = "new description";

        //act
        categoryMapper.addNewCategory(categoryName, categoryDescription);
    }

    /**
     * Negative Test of addNewCategory method, of class CategoryMapper.<br>
     * Description field in DB is made to be not null, varchar(2550).<br>
     * Descriptions with null value should throw an exception.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testNegativeAddNewCategoryNullDesciption() {
        //arrange
        String categoryName = "New Category";
        String categoryDescription = null;

        //act
        categoryMapper.addNewCategory(categoryName, categoryDescription);
    }

    /**
     * Test of addNewCategory method, of class CategoryMapper.<br>
     * Description field in DB is made to be not null, varchar(2550).
     */
    @Test
    public void testAddNewCategoryDesciptionLengthAtLimit() {
        //arrange
        String categoryName = "New Category";
        String categoryDescription = "";
        for (int i = 0; i < 2550; i++) {
            categoryDescription += "n";
        }

        //act
        Category result = categoryMapper.addNewCategory(categoryName, categoryDescription);

        //assert
        int expResultID = 4;
        assertEquals(expResultID, result.getCategoryID());
        assertTrue(categoryName.equals(result.getName()));
        assertTrue(categoryDescription.equals(result.getDescription()));
    }

    /**
     * Negative Test of addNewCategory method, of class CategoryMapper.<br>
     * Description field in DB is made to be not null, varchar(2550).<br>
     * Descriptions exceeding the 2550 varchar limit should cause an exception to be thrown
     */
    @Test(expected = IllegalArgumentException.class)
    public void testNegativeAddNewCategoryDesciptionLengthExceedLimit() {
        //arrange
        String categoryName = "New Category";
        String categoryDescription = "";
        for (int i = 0; i < 2551; i++) {
            categoryDescription += "n";
        }

        //act
        categoryMapper.addNewCategory(categoryName, categoryDescription);
    }

    /**
     * Test of getCategories method, of class CategoryMapper.
     */
    @Test
    public void testGetCategories() {
        //arrange

        //act
        ArrayList<Category> result = categoryMapper.getCategories(attributeList);

        //assert
        assertEquals(numberOfCategoriesInDB, result.size());
    }

    /**
     * Negative Test of getCategories method, of class CategoryMapper.<br>
     * The only way this method should be able to fail is if there is a structural change in the DB.<br>
     * We will try to simulate this change by removing the Category_Attributes table before running the test.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testNegativeGetCategoriesNoCategoriesTableInDB() {
        //arrange
        try {
            database.getConnection().createStatement().execute("drop table if exists Category_Attributes");
        } catch (SQLException ex) {
            fail("Could not make the structural change to the DB-table Categories");
        }

        //act
        ArrayList<Category> result = categoryMapper.getCategories(attributeList);

    }

    /**
     * Test of deleteCategory method, of class CategoryMapper.
     */
    @Test
    public void testDeleteCategory() {
        int categoryID = 1;

        //act
        int result = categoryMapper.deleteCategory(categoryID);

        //assert
        int expResult = 6;
        assertEquals(expResult, result);
    }

    /**
     * Negative Test of deleteCategory method, of class CategoryMapper.<br>
     * If given an ID that does not match an CategoryID in the DB, it will affect 0 rows, and should therefore return 0.
     */
    @Test
    public void testNegativeDeleteCategoryNoMatchingID() {
        int categoryID = 0;

        //act
        int result = categoryMapper.deleteCategory(categoryID);

        //assert
        int expResult = 0;
        assertEquals(expResult, result);
    }

    /**
     * Negative Test of deleteCategory method, of class CategoryMapper.<br>
     * The only way this method should be able to fail is if there is a structural change in the DB.<br>
     * We will try to simulate this change by removing the Category_Attributes table before running the test.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testNegativeDeleteCategoryNoCategoriesTableInDB() {
        try {
            database.getConnection().createStatement().execute("drop table if exists Category_Attributes");
        } catch (SQLException ex) {
            fail("Could not make the structural change to the DB-table Categories");
        }

        int categoryID = 1;

        //act
        int result = categoryMapper.deleteCategory(categoryID);
    }

    @Test
    public void testEditCategory() {
        int categoryID = 1;
        String categoryName = "kæledyr";
        String categoryDescription = "ting til kæledyr";
        ArrayList<Attribute> categoryAttributes = new ArrayList();
        Category category = new Category(categoryID, categoryName, categoryDescription, categoryAttributes);

        //act
        int result = categoryMapper.editCategory(category);

        //assert
        int expresult = 1;
        assertEquals(expresult, result);
    }

    @Test
    public void testEditAttributesToCategory() {
        int categoryID = 1;
        String categoryName = "hej";
        String categoryDescription = "hejhej";
        attributeList.add(new Attribute(2, "jeh", new HashMap<Integer, String>()));
        attributeList.add(new Attribute(3, "ejh", new HashMap<Integer, String>()));
        Category category = new Category(categoryID, categoryName, categoryDescription, attributeList);

        //act
        categoryMapper.editAttributeToCategories(category);
        ArrayList<Category> result = categoryMapper.getCategories(attributeList);

        //assert
        assertTrue(category.getCategoryAttributes().containsAll(result.get(0).getCategoryAttributes()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void negativeTestEditCategoryNoMatchingID() {
        int categoryID = 69;
        String categoryName = "hej";
        String categoryDescription = "hejhej";
        attributeList.add(new Attribute(0, categoryName, new HashMap<Integer, String>()));
        Category category = new Category(categoryID, "No", categoryDescription, attributeList);

        categoryMapper.editAttributeToCategories(category);

    }

    /**
     * Trying to update a category with a null value and expecting a crash due to it.
     */
    @Test(expected = IllegalArgumentException.class)
    public void negativeTestEditCategory() {
        int categoryID = 1;
        String categoryName = "kæledyr";
        String categoryDescription = null;
        ArrayList<Attribute> categoryAttributes = new ArrayList();
        Category category = new Category(categoryID, categoryName, categoryDescription, categoryAttributes);

        //act
        int result = categoryMapper.editCategory(category);
    }
}
