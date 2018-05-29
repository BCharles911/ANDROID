package model;

import android.graphics.Bitmap;
import android.location.Location;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Class represents a post in app
 */
public class Post implements Serializable, Comparable<Post>{

    private Integer id;
    private String title;
    private String description;
    private String image;
    private User author;
    private transient Location location;
    private Double longitude;
    private Double latitude;
    private Date date;
    private Set<Tag> tags;
    private List<Comment> comments;
    private int likes;
    private int dislikes;



public Post(){

}


    public Post(int id, String title, String description, User author, Date date, Location location, Set<Tag> tags, ArrayList<Comment> comments, int likes, int dislikes) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.author = author;
        this.location = location;
        this.date = date;
        this.tags = tags;
        this.comments = comments;
        this.likes = likes;
        this.dislikes = dislikes;
    }

    public Post(int id, String title, String description, String photoUrl, Date date, Location location, int likes, int dislikes, User author, Set<Tag> tags, List<Comment> comments) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.image = image;
        this.date = date;
        this.location = location;
        this.likes = likes;
        this.dislikes = dislikes;
        this.author = author;
        this.tags = tags;
        this.comments = comments;
    }

    public Post(String title, String description){
        this.title = title;
        this.description = description;
    }

/*

    public Post(String title, String description, int avatar) {
        this.title = title;
        this.description = description;
        this.avatar = avatar;
    }
*/


    public Post(String title, String description, Date date, int likes, int dislikes, User author) {
        this.title = title;
        this.description = description;
        //this.avatar = avatar;
        this.date = date;
        this.likes = likes;
        this.dislikes = dislikes;
        this.author = author;
    }





    public void like(){
        this.likes++;
    }

    public void dislike(){
        this.dislikes++;
    }

    /**
     * Metoda koja setuje location polje objekta koristeci polja longitude i latitude
     */

    @Override
    public int compareTo(Post other) {
        return date.compareTo(other.getDate());
    }

    /**
     * Metoda kojom dobijamo 'popularnost' odredjenog posta.
     * Posto u projektnoj specifikaciji popularnost kao takva nije definisana, definisem je kao razlika lajkova i dislajkova
     *
     * @return integer - razlika lajkova i dislajkova
     */
    public int getPopularity(){
        return likes - dislikes;
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

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhoto() {
        return image;
    }

    public void setPhoto(String photo) {
        this.image = photo;
    }

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
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


    public void makeLocation(double latitude, double longitude){

    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    /**
     * Filters comments by deleted attribute
     * @return returns a list of not-deleted comments
     */
    public ArrayList<Comment> getComments() {
        ArrayList<Comment> retval = new ArrayList<>();
        for(Comment comment : comments){
            if(!comment.isDeleted())
                retval.add(comment);
        }
        return retval;
    }

    /**
     * Adds a comment to the post
     * @param comment
     */
    public void addComment(Comment comment){
        comments.add(comment);
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

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }


}
