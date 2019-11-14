package businessLogic;

import java.util.ArrayList;
import java.util.Arrays;
import persistence.mappers.ProductMapperInterface;

/**
 *
 * @author Michael N. Korsgaard
 */
public class Product {

    private static ProductMapperInterface productMapper;

    private int productID;
    private String name;
    private String description;
    private String picturePath;
    private ArrayList<String> distributors;

    private static ArrayList<Product> ProductList = new ArrayList();

    public Product(int productID, String name, String description, String picturePath, ArrayList<String> distributors) {
        this.productID = productID;
        this.name = name;
        this.description = description;
        this.picturePath = picturePath;
        this.distributors = distributors;
    }

    private Product(String name, String description, String picturePath, ArrayList<String> distributors) {
        this.name = name;
        this.description = description;
        this.picturePath = picturePath;
        this.distributors = distributors;
    }

    public static void setProductMapper(ProductMapperInterface newMapper) {
        productMapper = newMapper;
    }

    public static Product createNewProduct(String name, String description, String picturePath, ArrayList<String> distributors)
            throws IllegalArgumentException {
        distributors.removeAll(Arrays.asList("", null));
        Product product = new Product(name, description, picturePath, distributors);

        validateProductInput(product);
        int newProductID = productMapper.addNewProduct(product);
        product.productID = newProductID;
        ProductList.add(product);
        return product;
    }

    public static void setupProductListFromDB() {
        ProductList = productMapper.getProducts();
    }

    public static boolean deleteProductOnID(int productID) {
        productMapper.deleteProduct(productID);
        return ProductList.remove(findProductOnID(productID));
    }

    public static Product findProductOnID(int productID) {
        for (Product product : ProductList) {
            if (product.productID == productID) {
                return product;
            }
        }
        return null;
    }

    public static void editProduct(int productID, String name, String description, ArrayList<String> distributors) {
        distributors.removeAll(Arrays.asList("", null));
        Product product = findProductOnID(productID);
        product.name = name;
        product.description = description;
        product.distributors = distributors;
        productMapper.editProduct(productID, product.name, product.description, product.distributors);
    }

    public static void updatePicturePath(int productID, String picturePath) {
        findProductOnID(productID).picturePath = picturePath;
        productMapper.updatePicturePath(productID, picturePath);
    }

    public static boolean validateProductInput(Product product) throws IllegalArgumentException {

        if (product.name.isEmpty()) {
            throw new IllegalArgumentException("please fill out product-name field");
        }
        if (product.description.isEmpty()) {
            throw new IllegalArgumentException("please fill out product-description field");
        }
        if (product.distributors.isEmpty()) {
            throw new IllegalArgumentException("please fill out distributors field");
        }

        return true;
    }

    public static void emptyProductList() {
        ProductList.clear();
    }

    public int getProductID() {
        return productID;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getPicturePath() {
        return picturePath;
    }

    public ArrayList<String> getDistributors() {
        return distributors;
    }

    public static ArrayList<Product> getProductList() {
        return ProductList;
    }
}
