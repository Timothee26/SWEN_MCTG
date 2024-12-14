package mctg.model;

import com.fasterxml.jackson.annotation.JsonAlias;

public class Card {
    @JsonAlias({"Id"})
    private String id;
    @JsonAlias({"Name"})
    private String name;
    @JsonAlias({"Damage"})
    private int damage;
    /*@JsonAlias({"ElementType"})
    private String elementType;*/

    public Card(){}
    public Card(String Id, String Name, int Damage/*, String type*/) {
        this.id = Id;
        this.name = Name;
        this.damage = Damage;
        //this.elementType = elementType;
    }

    public String getId() {return id;}

    public String getName() {return name;}

    public int getDamage() {return damage;}

    //public String getElementType() {return elementType;}

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    /*public void setElementType(String elementType) {
        this.elementType = elementType;
    }*/
}
