package services;

import user.User;
import models.Category;
import models.Deck;

import java.util.*;

public class AdminServer {
    private static AdminServer instance;
    private HashMap<String, String> userData;
    private HashSet<User> userList;
    private ArrayList<Category> categories;
    private ArrayList<Deck> publicDecks;
    private AdminServer() {
        userData = new HashMap<>();
        userList = new HashSet<>();
        categories = new ArrayList<>();
        publicDecks = new ArrayList<>();

        // create users
        userData.put("cordelia", "testing321");
        User testUser = new User("cordelia", "testing321");
        userList.add(testUser);

        userData.put("othello", "testing321");
        User othello = new User("othello", "testing321");
        userList.add(othello);

        userData.put("iago", "testing321");
        User iago = new User("iago", "testing321");
        userList.add(iago);

        // Create mock Categories
        Category math = new Category("Mathematics");
        Category biology = new Category("Biology");
        Category chem = new Category("Chemistry");
        categories.add(math);
        categories.add(biology);
        categories.add(chem);

        // create mock public decks
        Deck deck1 = new Deck("Deck1", true, math);
        Deck deck2 = new Deck("Deck2", true, math);
        othello.addNewDeck(deck1);
        othello.addNewDeck(deck2);

        Deck deck3 = new Deck("Deck3", true, biology);
        Deck deck4 = new Deck("Deck4", true, biology);
        iago.addNewDeck(deck3);
        iago.addNewDeck(deck4);

        Deck deck5 = new Deck("Deck5", true, chem);
        Deck deck6 = new Deck("Deck6", true, chem);
        iago.addNewDeck(deck5);
        testUser.addNewDeck(deck6);

        registerPublicDeck(deck1);
        registerPublicDeck(deck2);
        registerPublicDeck(deck3);
        registerPublicDeck(deck4);
        registerPublicDeck(deck5);
        registerPublicDeck(deck6);

        // create mock private decks
        Deck deck7 = new Deck("private1", math);
        othello.addNewDeck(deck7);

        Deck deck8 = new Deck("private 2", math);
        othello.addNewDeck(deck8);
    }

    synchronized public static AdminServer getInstance() {
        if(instance == null)
            instance = new AdminServer();

        return instance;
    }

    // checks if user exists or not and if password and username match
    synchronized public boolean checkLoginCredentials(String username, String password) {
        if(!userData.containsKey(username))
            return false;

        return userData.get(username).equals(password);
    }

    synchronized public boolean registerUser(String username, String password) {
        if(userData.containsKey(username)) {
            System.out.println("Username already taken.");
            return false;
        }

        User user = new User(username, password);
        userList.add(user);
        userData.put(username, password);
        return true;
    }

    synchronized public User findUser(String username) {
        for(User user : userList) {
            if(user.getUsername().equals(username))
                return user;
        }
        System.out.println(userList);
        return null;
    }

    synchronized public Category createNewCategory(String name)
    {
        // check if category already exists
        for(Category category : categories) {
            if(category.getName().equals(name))
                return null;
        }

        Category category = new Category(name);
        categories.add(category);
        return category;
    }

    synchronized public ArrayList<Category> getCategories() {
        return this.categories;
    }

    synchronized public boolean registerPublicDeck(Deck deck) {
        for(Deck d: publicDecks)
        {
            if(d.getCategory().getName().equals(deck.getCategory().getName()) && d.getName().equals(deck.getName()))
                return false;
        }

        publicDecks.add(deck);
        return true;
    }

    synchronized public ArrayList<User> getTopContributors() {
        final int contributorSize = 3;

        // create a list to users from hashset
        ArrayList<User> userArrayList = new ArrayList<>(userList);
        Collections.sort(userArrayList);

        if(userList.size() < contributorSize)
            return userArrayList;

        ArrayList<User> topContributors = new ArrayList<>();
        for (User user : userArrayList) {
            if (topContributors.size() >= contributorSize)
                break;
            topContributors.add(user);
        }
        return topContributors;
    }

    synchronized public ArrayList<Deck> getPublicDecks() {
        return publicDecks;
    }
}
