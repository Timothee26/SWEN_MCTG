package mctg.model;

import com.fasterxml.jackson.annotation.JsonAlias;

public class Card {
    @JsonAlias({"Id"})
    private String id;
    @JsonAlias({"Name"})
    private String name;
    @JsonAlias({"Damage"})
    private float damage;
    @JsonAlias({"Bought"})
    private String bought;
    @JsonAlias({"ElementType"})
    private String elementType;

    public Card(){}
    public Card(String Id, String Name, float Damage, String Bought/*, String type*/) {
        this.id = Id;
        this.name = Name;
        this.damage = Damage;
        this.bought = Bought;
        this.elementType = elementType;
    }

    public String getId() {return id;}

    public String getName() {return name;}

    public float getDamage() {return damage;}

    public String getBought() {return bought;}

    public String getElementType() {return elementType;}

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDamage(float damage) {
        this.damage = damage;
    }

    public void setBought(String bought) {
        this.bought = bought;
    }

    public void setElementType(String elementType) {
        this.elementType = elementType;
    }

}
