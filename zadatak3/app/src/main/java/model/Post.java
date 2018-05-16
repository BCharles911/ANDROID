package model;

import android.graphics.Bitmap;
import android.location.Location;


import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Post implements Serializable, Comparable<Post> {

    private int id;

    private String title;

    private String description;

    private int avatar;

  //  private Bitmap photo;
    private User author;
    private Date date;
    private Location location;

    private ArrayList<Tag> tags;

    private List<Comment>  comments = new ArrayList<>();

    private int likes;

    private int dislikes;

    public static List<Post> jsonArrayToList(JsonArray jsonArray){
        List<Post> posts = new ArrayList<>();
        for(JsonElement element : jsonArray){
            JsonObject postJsonObject = element.getAsJsonObject();
            Integer id = postJsonObject.get("id").getAsInt();
            String title = postJsonObject.get("title").getAsString();
            String description = postJsonObject.get("description").getAsString();
            int likes = postJsonObject.get("likes").getAsInt();
            int dislikes = postJsonObject.get("dislikes").getAsInt();
            Post post = new Post();
            post.setId(id);
            post.setTitle(title);
            post.setDescription(description);
            post.setLikes(likes);
            post.setDislikes(dislikes);
            posts.add(post);
        }
        return posts;
    }


    public Post(String title, String description, int avatar) {
        this.title = title;
        this.description = description;
        this.avatar = avatar;
    }

    public Post(){

    }



    public Post(String title, String description,String author, int avatar, Date date, int likes, int dislikes) {
        this.title = title;
        this.description = description;
        this.avatar = avatar;
        this.date = date;
        this.likes = likes;
        this.dislikes = dislikes;

    }





    public Post(String title, String description, int avatar, Date date, int likes, int dislikes) {
        this.title = title;
        this.description = description;
        this.avatar = avatar;
        this.date = date;
        this.likes = likes;
        this.dislikes = dislikes;
    }




    @Override
    public int compareTo(Post other) {
        return date.compareTo(other.getDate());
    }








    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public int getPopularity() { return likes - dislikes; }


    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

/*    public Bitmap getPhoto() {
        return photo;
    }

    public void setPhoto(Bitmap photo) {
        this.photo = photo;
    }*/

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public ArrayList<Tag> getTags() {
        return tags;
    }

    public void setTags(ArrayList<Tag> tags) {
        this.tags = tags;
    }

    public ArrayList<Comment> getComments() {
        ArrayList<Comment> retval = new ArrayList<>();
        for (Comment comment : comments) {
            if (!comment.isDeleted())
                retval.add(comment);

        }


     return retval;
    }




    public void addComment(Comment comment){
        comments.add(comment);
    }

    public void setComments(ArrayList<Comment> comments) {
        this.comments = comments;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getDislikes() {
        return dislikes;
    }

    public void setDislikes(int dislikes) {
        this.dislikes = dislikes;
    }


    public int getAvatar() {
        return avatar;
    }

    public void setAvatar(int avatar) {
        this.avatar = avatar;
    }


    @Override
    public String toString() {
        return  "title: " + title + "\n"  +
                "description: " + description + "\n" ;
    }
}
