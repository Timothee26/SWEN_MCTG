package mctg.model;
import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.security.SecureRandom;
import java.util.UUID;
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class User {
    @JsonAlias({"Username"})
    private String username;
    @JsonAlias({"Password"})
    private String password;
    @JsonAlias({"Token"})
    private String token;
    @JsonAlias({"Wins"})
    private int wins;
    @JsonAlias({"Losses"})
    private int losses;
    @JsonAlias({"Elo"})
    private int elo;
    @JsonAlias({"Ties"})
    private int ties;
    @JsonAlias({"Coins"})
    private int coins;

    //needed for curlskript
    /*public User() {}

    public User(String username, String password) {
        this.username = username;
        this.password = password;

    }*/
    public String getUsername() {return username;}
    public void setUsername(String username) {this.username = username;}
    public String getPassword() {return password;}
    public String getToken(){
        return token;
    }
    public int getWins() {return wins;}
    public int getLosses() {return losses;}
    public int getElo() {return elo;}
    public int getTies() {return ties;}
    public int getCoins() {return coins;}

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
