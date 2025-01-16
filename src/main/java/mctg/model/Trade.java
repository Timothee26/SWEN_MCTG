package mctg.model;
import com.fasterxml.jackson.annotation.JsonAlias;

public class Trade {
    @JsonAlias({"Id"})
    private String id;
    @JsonAlias({"CardToTrade"})
    private String cardToTrade;
    @JsonAlias({"Type"})
    private String type;
    @JsonAlias({"MinimumDamage"})
    private float minimumDamage;
    @JsonAlias({"Username"})
    private String username;

    public Trade(){}

    public Trade(String Id, String CardToTrade, String Type, float MinimumDamage, String Username) {
        this.id = Id;
        this.cardToTrade = CardToTrade;
        this.type = Type;
        this.minimumDamage = MinimumDamage;
        this.username = Username;
    }
    public String getId() {return id;}
    public String getCardToTrade() {return cardToTrade;}
    public String getType() {return type;}
    public float getMinimumDamage() {return minimumDamage;}
    public String getUser() {return username;}

    public void setId(String id) {this.id = id;}
    public void setCardToTrade(String cardToTrade) {this.cardToTrade = cardToTrade;}
    public void setType(String type) {this.type = type;}
    public void setMinimumDamage(float minimumDamage) {this.minimumDamage = minimumDamage;}
    public void setUser(String username) {this.username = username;}

}
