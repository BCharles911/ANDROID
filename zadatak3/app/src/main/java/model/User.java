package model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import model.Role;

public class User {

    private int id;
    private String name;
    private String username;
    private String password;
    private List<Role> roles;
    private List<Post> posts;
    private List<Comment> comments;
    private String image;

    /**
     * No args constructor for use in serialization
     *
     */
    public User() {
    }

    /**
     *
     * @param id
     * @param username
     * @param roles
     * @param name
     * @param posts
     * @param image
     * @param password
     * @param comments
     */
    public User(int id, String name, String username, String password, List<Role> roles, List<Post> posts, List<Comment> comments, String image) {
        super();
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.roles = roles;
        this.posts = posts;
        this.comments = comments;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}