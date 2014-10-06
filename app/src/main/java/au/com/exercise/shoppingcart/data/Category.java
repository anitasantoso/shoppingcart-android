package au.com.exercise.shoppingcart.data;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Anita on 5/10/2014.
 */
@DatabaseTable(tableName = "category")
public class Category {

    @DatabaseField(generatedId = true, canBeNull =  false, columnName = "category_id")
    private int categoryId;

    @DatabaseField(columnName = "category_name", canBeNull =  false)
    private String categoryName;

    public Category() {
    }

    public Category(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public String toString() {
        return getCategoryName();
    }
}
