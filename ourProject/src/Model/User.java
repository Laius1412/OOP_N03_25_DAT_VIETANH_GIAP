package Model;

public class User{
    private String username;
    private String password;
    private String role;

    // Construcor default
    public User() {}

    // Cac ham set cho cac thuoc tinh
    public void setUsername(String username){
        this.username = username;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public void setRole(String role){
        this.role = role;
    }
    
    // Cac ham get
    public String getUsername(){
        return username;
    }

    public String getPassword(){
        return password;
    }

    public String getRole(){
        return role;
    }
}