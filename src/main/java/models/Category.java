package models;

import java.util.ArrayList;

public class Category{

    private int id;
    private ArrayList<Deck> decks;
    private String name;

    public Category(String name) {
        this.name = name;
        decks = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString(){
        return getName();
    }
}
