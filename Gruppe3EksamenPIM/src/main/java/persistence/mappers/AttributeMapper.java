package persistence.mappers;

import businessLogic.Attribute;
import businessLogic.Product;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import persistence.DB;

/**
 *
 * @author Andreas
 */
public class AttributeMapper {

    private DB database;

    public AttributeMapper(DB database) {
        this.database = database;
    }

    public Attribute addNewAttribute(String attributeName) {
        try {
            String SQL = "INSERT INTO Attributes (Attribute_Name) VALUES (?)";
            PreparedStatement ps = database.getConnection().prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            HashMap<Integer, String> value = new HashMap();
            ps.setString(1, attributeName);
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            int id = rs.getInt(1);

            Attribute attribute = new Attribute(id, attributeName, value);
            return attribute;

        } catch (SQLException ex) {
            Logger.getLogger(AttributeMapper.class.getName()).log(Level.SEVERE, null, ex);
            throw new IllegalArgumentException("Attribute cannot be inserted in the database");
        }
    }

    public ArrayList<Attribute> getAttributes() {
        try {
            String SQL = "SELECT * FROM product_attributes";
            PreparedStatement ps = database.getConnection().prepareStatement(SQL);
            ResultSet rs = ps.executeQuery();
            HashMap<Integer, HashMap<Integer, String>> productAttributeValues = new HashMap();
            while (rs.next()) {
                int productID = rs.getInt("Product_ID");
                int attributeID = rs.getInt("Attribute_ID");
                String productAttributeValue = rs.getString("Attribute_Info");
                if (productAttributeValues.get(attributeID) != null) {
                    productAttributeValues.get(attributeID).put(productID, productAttributeValue);
                } else {
                    HashMap<Integer, String> value = new HashMap();
                    value.put(productID, productAttributeValue);
                    productAttributeValues.put(attributeID, value);
                }
            }

            ArrayList<Attribute> attributeList = new ArrayList();
            SQL = "SELECT * FROM Attributes";
            ps = database.getConnection().prepareStatement(SQL);

            rs = ps.executeQuery();
            while (rs.next()) {
                int attribute_ID = rs.getInt("Attribute_ID");
                String attribute_Name = rs.getString("Attribute_Name");
                HashMap<Integer, String> value = productAttributeValues.get(attribute_ID);
                if (value == null) {
                    value = new HashMap();
                }

                Attribute attribute = new Attribute(attribute_ID, attribute_Name, value);
                attributeList.add(attribute);
            }
            return attributeList;

        } catch (SQLException ex) {
            Logger.getLogger(AttributeMapper.class.getName()).log(Level.SEVERE, null, ex);
            throw new IllegalArgumentException("Can't get attributes from Database");
        }
    }

    public int deleteAttribute(int attributeID) {
        int rowsAffected = 0;

        try {
            database.setAutoCommit(false);

            String sqlDeleteCategoryAttributes = "DELETE FROM category_attributes WHERE Attribute_ID = ?";
            PreparedStatement psDeleteCategoryAttributes = database.getConnection().prepareStatement(sqlDeleteCategoryAttributes);
            psDeleteCategoryAttributes.setInt(1, attributeID);
            rowsAffected += psDeleteCategoryAttributes.executeUpdate();

            String sqlDeleteProductAttributes = "DELETE FROM product_attributes WHERE Attribute_ID = ?";
            PreparedStatement psDeleteProductAttributes = database.getConnection().prepareStatement(sqlDeleteProductAttributes);
            psDeleteProductAttributes.setInt(1, attributeID);
            rowsAffected += psDeleteProductAttributes.executeUpdate();

            String sqlDeleteAttribute = "DELETE FROM Attributes WHERE Attribute_ID = ?";
            PreparedStatement psDeleteAttribute = database.getConnection().prepareStatement(sqlDeleteAttribute);
            psDeleteAttribute.setInt(1, attributeID);
            rowsAffected += psDeleteAttribute.executeUpdate();

            database.getConnection().commit();

        } catch (SQLException ex) {
            Logger.getLogger(AttributeMapper.class.getName()).log(Level.SEVERE, null, ex);
            database.rollBack();
            database.setAutoCommit(true);
            throw new IllegalArgumentException("Can't delete selected attribute from DB");
        }

        database.setAutoCommit(true);
        return rowsAffected;
    }

    public int updateProductAttributeSelections(Product product) {
        int rowsAffected = 0;

        try {
            database.setAutoCommit(false);
            int productID = product.getProductID();
            String sqlDeleteProductAttributes = "DELETE FROM Product_Attributes WHERE product_ID = ?";
            PreparedStatement ps = database.getConnection().prepareStatement(sqlDeleteProductAttributes);
            ps.setInt(1, productID);
            rowsAffected += ps.executeUpdate();

            if (!product.getProductAttributes().isEmpty()) {
                String sqlInsertProductAttributes = "INSERT INTO Product_Attributes(Product_ID, Attribute_ID, Attribute_Info) VALUES ";
                boolean firstline = true;
                for (Attribute productAttribute : product.getProductAttributes()) {
                    if (firstline) {
                        firstline = false;
                    } else {
                        sqlInsertProductAttributes += ", ";
                    }
                    String attributeValue = productAttribute.getAttributeValueForID(productID);
                    if (attributeValue == null) {
                        attributeValue = "";
                    }
                    sqlInsertProductAttributes += "(" + productID + ", " + productAttribute.getAttributeID() + ", '" + attributeValue + "')";

                }
                rowsAffected += database.getConnection().prepareStatement(sqlInsertProductAttributes).executeUpdate();
            }

            database.getConnection().commit();

        } catch (SQLException ex) {
            Logger.getLogger(AttributeMapper.class.getName()).log(Level.SEVERE, null, ex);
            database.rollBack();
            database.setAutoCommit(true);
            throw new IllegalArgumentException("Can't update the new product-attribute connections in the database");
        }

        database.setAutoCommit(true);
        return rowsAffected;
    }

    public int updateProductAttributeValues(Product product) {
        int rowsAffacted = 0;

        try {
            database.setAutoCommit(false);
            int productID = product.getProductID();
            for (Attribute productAttribute : product.getProductAttributes()) {
                String SQL = "UPDATE product_attributes SET Attribute_Info = ? WHERE Product_ID = ? AND Attribute_ID = ?";
                PreparedStatement ps = database.getConnection().prepareStatement(SQL);
                ps.setString(1, productAttribute.getAttributeValueForID(productID));
                ps.setInt(2, productID);
                ps.setInt(3, productAttribute.getAttributeID());
                rowsAffacted += ps.executeUpdate();
            }
            database.getConnection().commit();

        } catch (SQLException ex) {
            Logger.getLogger(AttributeMapper.class.getName()).log(Level.SEVERE, null, ex);
            database.rollBack();
            database.setAutoCommit(true);
            throw new IllegalArgumentException("Can't update the new product-attribute connections in the database");
        }

        database.setAutoCommit(true);
        return rowsAffacted;
    }

}
