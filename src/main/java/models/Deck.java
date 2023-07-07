package models;

import java.util.ArrayList;

public class Deck {
    private int id;
    private String name;
    private boolean isPublic;

    private final Category category;
    private ArrayList<Card> cards;

    public Deck(String name,Category category)
    {
        this(name,false,category);
    }

    public Deck(String name, boolean isPublic, Category category) {
        this.name = name;
        this.isPublic = isPublic;
        this.cards = new ArrayList<Card>();
        this.category = category;

//        if(isPublic) {
//            DataService.getInstance().registerPublicDeck(this);
//        }
    }

    public int addCard(Card card) {
        if(this.cards.add(card)) {
            return card.getId();
        }
        return -1;
    }

    public void removeCard(Card card) throws Exception {
        this.cards.remove(card);
    }

    public boolean isEmpty() {
        return cards.size() == 0;
    }

    public boolean containsCard(Card card) {
        return this.cards.contains(card);
    }


    public ArrayList<Card> getCards() {
        return this.cards;
    }

    public void setCards(ArrayList<Card> cards) {
        this.cards = cards;
    }

    public int getId() {
        return id;
    }

    public int getSize() {
        return this.cards.size();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getCategory() {
        return category;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void makePublic() {
        isPublic = true;
    }

    @Override
    public String toString() {
//        return "Deck [name=" + name + ", cards=" + cards + "]";
//        return "Deck [name=" + name + "]";
        return  name;
    }
}
