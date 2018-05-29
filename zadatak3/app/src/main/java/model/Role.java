package model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Role {


    private int id;
    private String role;

    /**
     * No args constructor for use in serialization
     *
     */
    public Role() {
    }

    /**
     *
     * @param id
     * @param role
     */
    public Role(int id, String role) {
        super();
        this.id = id;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

}
