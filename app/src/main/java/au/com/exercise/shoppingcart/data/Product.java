package au.com.exercise.shoppingcart.data;

import com.j256.ormlite.field.DatabaseField;

/**
 * Created by Anita on 5/10/2014.
 */
public class Product {
    @DatabaseField(generatedId = true, canBeNull =  false, columnName = "product_id")
    private int productId;

    @DatabaseField(columnName = "product_name", canBeNull =  false)
    private String productName;

    @DatabaseField(columnName = "product_desc")
    private String productDesc;

    @DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, columnName = "category_id")
    private Category category;

    @DatabaseField(columnName = "image_name")
    private String imageName;

    @DatabaseField(columnName = "unit_price")
    private float unitPrice;

    public Product() {
    }

    public Product(String productName, String productDesc, Category category, String imageName, float unitPrice) {
        this.productName = productName;
        this.productDesc = productDesc;
        this.category = category;
        this.imageName = imageName;
        this.unitPrice = unitPrice;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public float getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(float unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
