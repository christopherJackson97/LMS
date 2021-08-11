import java.util.ArrayList;


public class Book {

    private String title, author, illustrator, isbn, decimal, description, genre;
    private ArrayList<String> tags = new ArrayList<>();
    private Boolean nonFiction;
    private int ID;

    public Book(String title, String author, String illustrator, String isbn, String decimal, String description, String genre, boolean nonFiction){
        this.title = title;
        this.author = author;
        this.illustrator = illustrator;
        this.isbn = isbn;
        this.decimal = decimal;
        this.description = description;
        this.genre = genre;
        this.nonFiction = nonFiction;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIllustrator() {
        return illustrator;
    }

    public void setIllustrator(String illustrator) {
        this.illustrator = illustrator;
    }

    public String getIsbn() {
        return isbn;
    }
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getDecimal() {
        return decimal;
    }

    public void setDecimal(String decimal) {
        this.decimal = decimal;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Boolean getNonFiction() {
        return nonFiction;
    }

    public void setNonFiction(Boolean nonFiction) {
        this.nonFiction = nonFiction;
    }

    public int getID() {
        return ID;
    }

    public void setID(Integer ID){
        this.ID = ID;
    }

    public void addTag(String tag){
        this.tags.add(tag);
    }
    public void removeTage(String tag){
        this.tags.remove(tag);
    }
    public String serializeTags(){
        StringBuilder sb = new StringBuilder();
        for(String tag : this.tags){
            sb.append(tag);
            sb.append(",");
        }
        return sb.toString();
    }
    public void deserializeTags(String tags){
        String[] tagParts = tags.split(",");
        for(String tagPart : tagParts){
            if(tagPart.length() > 0){
                this.tags.add(tagPart);
            }
        }
    }
}
