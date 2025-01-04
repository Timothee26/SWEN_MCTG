package mctg.model;

import com.fasterxml.jackson.annotation.JsonAlias;

public class UserData{
    @JsonAlias({"Name"})
    private String name;
    @JsonAlias({"Bio"})
    private String bio;
    @JsonAlias({"Image"})
    private String image;

    public UserData(){}

    public UserData(String name, String bio, String image) {
        this.name = name;
        this.bio = bio;
        this.image = image;
    }
    public String getName() {return name;}
    public String getBio() {return bio;}
    public String getImage() {return image;}
}