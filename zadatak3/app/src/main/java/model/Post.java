package model;

import android.graphics.Bitmap;
import android.location.Location;

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
    private Double longitude;
    private Double latitude;


    private Date date;
    private Set<Tag> tags = new HashSet<>();
    private List<Comment> comments = new ArrayList<>();
    private int likes;
    private int dislikes;


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


    public Post(){

    }


    public Post(Integer id,
                   String title,
                   String description,
                   String image,
                   User author,
                   Date date,
                   int likes,
                   int dislikes) {

        super();
        this.id = id;
        this.title = title;
        this.description = description;
        this.image = image;
        this.author = author;
        this.date = date;
        this.likes = likes;
        this.dislikes = dislikes;

    }

    public Post(String title, String description){
        this.title = title;
        this.description = description;
    }

    public Post(String title, String description, Date date){
        this.title = title;
        this.description = description;
        this.date = date;
    }

    public Post(String title, String description, Date date, int likes, int dislikes){
        this.title = title;
        this.description = description;
        this.date = date;
        this.likes = likes;
        this.dislikes = dislikes;
    }


    public Post(String title, String description, String image, User author, Date date, int likes, int dislikes){
        this.title = title;
        this.description = description;
        this.image = image;
        this.date = date;
        this.likes = likes;
        this.author = author;
        this.dislikes = dislikes;
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
