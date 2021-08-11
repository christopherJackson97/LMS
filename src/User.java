public class User {

    private String username, name, password;
    private Integer accessLevel;

//Access levels will be visited later on (8/6/2021)
    public User(String username, String name, String password, Integer accessLevel){
        this.username = username;
        this.name = name;
        this.password = BCrypt.hashpw(password, BCrypt.gensalt(12));
        this.accessLevel = accessLevel;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(Integer accessLevel) {
        this.accessLevel = accessLevel;
    }
}
