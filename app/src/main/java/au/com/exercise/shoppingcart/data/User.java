package au.com.exercise.shoppingcart.data;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Anita on 6/10/2014.
 */
@DatabaseTable
public class User {

    @DatabaseField(generatedId = true, canBeNull =  false, columnName = "user_id")
    private int userId;

    @DatabaseField(columnName = "username")
    private String username;

    @DatabaseField(columnName = "first_name")
    private String firstName;

    @DatabaseField(columnName = "last_name")
    private String lastName;

    @DatabaseField(columnName = "device_id")
    private String deviceId;

    public User() {}

    public User(String username, String firstName, String lastName, String deviceId) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.deviceId = deviceId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getUserId() {
        return userId;
    }
}
