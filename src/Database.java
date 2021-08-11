import java.io.File;
import java.sql.*;
import java.util.ArrayList;

public class Database {

    public boolean createTables(){
        Connection con = null;
        Statement stmt = null;
        boolean created;
        try{
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:Library.db");
            stmt = con.createStatement();
            String sql = "CREATE TABLE USERS " +
                    "(ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "USERNAME STRING NOT NULL UNIQUE, " +
                    "NAME STRING NOT NULL, " +
                    "PASSWORD STRING NOT NULL, " +
                    "ACCESS_LEVEL INT NOT NULL)";
            stmt.executeUpdate(sql);
            sql = "CREATE TABLE BOOKS " +
                    "(ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "TITLE STRING NOT NULL, " +
                    "AUTHOR STRING NOT NULL, " +
                    "ILLUSTRATOR STRING NOT NULL, " +
                    "ISBN STRING NOT NULL, " +
                    "DECIMAL STRING NOT NULL, " +
                    "DESCRIPTION STRING NOT NULL, " +
                    "TAGS STRING NOT NULL, " +
                    "NON_FICTION BOOLEAN NOT NULL, " +
                    "GENRE STRING NOT NULL)";
            stmt.executeUpdate(sql);
            stmt.close();
            con.close();
            created = true;
        }catch(Exception e){
            created = false;
        }
        return created;
    }

    public boolean createUser(User user){
        String name = user.getName();
        String username = user.getUsername();
        String password = user.getPassword();
        Integer accessLevel = user.getAccessLevel();

        Boolean created;

        Connection con = null;
        Statement stmt = null;
        try{
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:Library.db");
            stmt = con.createStatement();
            String sql = "INSERT INTO USERS (USERNAME, NAME, PASSWORD, ACCESS_LEVEL) VALUES ('" + username.toUpperCase() + "', '" + name + "', '" + password + "', '" + accessLevel + "');";
            stmt.executeUpdate(sql);
            created = true;
            con.close();
            stmt.close();
        }catch(Exception e ){

            created = false;
        }
        return created;


    }

    public boolean login(String username, String password, Login loginForm){
        boolean success = false;
        Connection con = null;
        Statement statement = null;
        String hashedPass = null;
        try{
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:Library.db");
            statement = con.createStatement();
            String getHashedPass = "SELECT PASSWORD FROM USERS WHERE USERNAME='"+username.toUpperCase()+"';";
            ResultSet rs = statement.executeQuery(getHashedPass);
            while(rs.next()){
                hashedPass = rs.getString("PASSWORD");
            }
            if(hashedPass.length() > 0){
                if(BCrypt.checkpw(password, hashedPass)){
                    String getUserInfo = "SELECT * FROM USERS WHERE USERNAME='"+username.toUpperCase()+"' AND PASSWORD='"+hashedPass+"';";
                    rs = statement.executeQuery(getUserInfo);

                    String name = null;
                    Integer accessLevel = null;
                    while(rs.next()){
                        name = rs.getString("NAME");
                        accessLevel = rs.getInt("ACCESS_LEVEL");
                    }
                    User currentUser = new User(username, name, password, accessLevel);
                    loginForm.setUser(currentUser);
                    success = true;
                }else{
                    success = false;
                }
                statement.close();
                con.close();
            }

        }catch(SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return success;
    }

    public boolean createAdminAccount(){
        User admin = new User("ADMIN", "Admin", "Admin", 4);
        Boolean created = false;
        if(createUser(admin)){
            created = true;
        }
        else{
            created = false;
        }




        return created;
    }

    public boolean editBook(Book book){
        boolean edited = false;
        //String title, author, illustrator, isbn, decimal, description, tags, genre
        //Boolean non_fiction

        Connection con = null;

        try{
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:Library.db");

            String sql = "UPDATE BOOKS SET TITLE = ? , AUTHOR = ? , ILLUSTRATOR = ? , ISBN = ? , DECIMAL = ? , DESCRIPTION = ? , " +
                    "TAGS = ? , GENRE = ? , NON_FICTION = ? WHERE ID = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, book.getTitle());
            ps.setString(2, book.getAuthor());
            ps.setString(3, book.getIllustrator());
            ps.setString(4, book.getIsbn());
            ps.setString(5, book.getDecimal());
            ps.setString(6, book.getDescription());
            ps.setString(7, book.serializeTags());
            ps.setString(8, book.getGenre());
            ps.setBoolean(9, book.getNonFiction());
            ps.setInt(10, book.getID());
            System.out.println(ps.toString());
            ps.executeUpdate();
            edited = true;
        }
        catch(SQLException | ClassNotFoundException e){
            e.printStackTrace();
            edited = false;
        }
        return edited;
    }


    public boolean addBook(Book book){
        boolean added = false;
        // Strings Title, Author, Illustrator, ISBN, Decimal, Description, Tags, Genre
        // Boolean Fiction
        Connection con = null;
        Statement stmt = null;

        try{
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:Library.db");
            stmt = con.createStatement();
            String sql = "INSERT INTO BOOKS "+
                    "(TITLE, AUTHOR, ILLUSTRATOR, ISBN, DECIMAL, DESCRIPTION, TAGS, NON_FICTION, GENRE)" +
                    " VALUES " +
                    "('" + book.getTitle() + "', '" + book.getAuthor() + "', '" + book.getIllustrator() + "', '" +
                    book.getIsbn() + "', '" + book.getDecimal() + "', '" + book.getDescription() + "', '" + book.serializeTags() + "', '" +
                    book.getNonFiction() + "', '" + book.getGenre() + "');";

            stmt.executeUpdate(sql);
            stmt.close();
            con.close();
            added = true;
        }catch(SQLException | ClassNotFoundException e){
            e.printStackTrace();
            added = false;
        }
        return added;
    }

    public ArrayList<Book> getAllBooksArray(){
        // Strings Title, Author, Illustrator, ISBN, Decimal, Description, Tags, Genre
        // Boolean Fiction
        ArrayList<Book> booksArray = new ArrayList<>();

        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;

        try{
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:Library.db");
            stmt = con.createStatement();
            String sql = "SELECT * FROM BOOKS";
            rs = stmt.executeQuery(sql);
            while(rs.next()){
                Book book = new Book(rs.getString("TITLE"), rs.getString("AUTHOR"), rs.getString("ILLUSTRATOR"), rs.getString("ISBN"), rs.getString("DECIMAL"),
                        rs.getString("DESCRIPTION"), rs.getString("GENRE"), rs.getBoolean("NON_FICTION"));
                String tags = rs.getString("TAGS");
                book.setID(rs.getInt("ID"));
                book.deserializeTags(tags);
                booksArray.add(book);
            }
            rs.close();
            stmt.close();
            con.close();
        }catch(SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return booksArray;
    }
}
