package mctg.model;
import com.fasterxml.jackson.annotation.JsonAlias;

import java.security.SecureRandom;
import java.util.UUID;

public class User {
    @JsonAlias({"Username"})
    private String username;
    @JsonAlias({"Password"})
    private String password;
    @JsonAlias({"Token"})
    private String token;

    //needed for curlskript
    public User() {}

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
    public String getUsername() {return username;}
    public void setUsername(String username) {this.username = username;}
    public String getPassword() {return password;}
    public String getToken(){
        return token;
    }

    public String createToken() {
        /*SecureRandom random = new SecureRandom();
        byte bytes[] = new byte[20];
        random.nextBytes(bytes);
        String token = bytes.toString();
        return token;*/
        String token = username+"-mtcgToken";
        return token;
    }
    public void setPassword(String password) {this.password = password;}

}
