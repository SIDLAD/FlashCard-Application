package com.example.flashcard;

import ExceptionHandling.containerNotFoundException;
import ExceptionHandling.sceneChangeException;
import services.AdminServer;
import user.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import models.Card;
import models.Deck;

import java.io.IOException;
import java.util.ArrayList;

public class DeckSceneController{

    private Deck deck;

    private User user;
    private Scene previousScene;
    @FXML
    private Button CreateTfButton;

    @FXML
    private ListView<Card> cardList;

    @FXML
    private Button createDefButton;

    @FXML
    private Button createFIBButton;

    @FXML
    private Button createMCQButton;

    @FXML
    private Label deckNameLabel;

    @FXML
    private Button deleteCardButton;

    @FXML
    private Button editCardButton;

    @FXML
    private Button goBackButton;

    public void setDeck(Deck deck){
        this.deck = deck;
        deckNameLabel.setText(deck.getName());
        setCards();
    }

    public User getUser() {
        return user;
    }

    public void setUser(String username) {
        this.user = AdminServer.getInstance().findUser(username);
    }

    public void setPreviousScene(Scene previousScene){
        this.previousScene = previousScene;
    }
    public void setCards(){
        ArrayList<Card> cards = deck.getCards();
        cardList.getItems().clear();
        cardList.getItems().addAll(cards);
    }
    public void createDefCard(ActionEvent event) throws sceneChangeException {
        SceneHandler.getInstance().switchToCreateDefCardScene((Stage)((Node)event.getSource()).getScene().getWindow(),((Node)event.getSource()).getScene(),deck);
    }
    public void createFIBCard(ActionEvent event) throws sceneChangeException {
        SceneHandler.getInstance().switchToCreateFIBCardScene((Stage)((Node)event.getSource()).getScene().getWindow(),((Node)event.getSource()).getScene(),deck);
    }
    public void createTfCard(ActionEvent event) throws sceneChangeException {
        SceneHandler.getInstance().switchToCreateTfCardScene((Stage)((Node)event.getSource()).getScene().getWindow(),((Node)event.getSource()).getScene(),deck);
    }
    public void createMCQCard(ActionEvent event) throws sceneChangeException {
        SceneHandler.getInstance().switchToCreateMCQCardScene((Stage)((Node)event.getSource()).getScene().getWindow(),((Node)event.getSource()).getScene(),deck);
    }

    public void editCard(ActionEvent event) throws sceneChangeException {
        if(cardList.getSelectionModel().getSelectedIndex() == -1)
        {
            return;
        }
        Card card = cardList.getSelectionModel().getSelectedItem();
        SceneHandler.getInstance().switchToEditCardScene((Stage)((Node)event.getSource()).getScene().getWindow(),((Node)event.getSource()).getScene(),card);
    }

    public void deleteCard(ActionEvent event) throws containerNotFoundException {
        if(cardList.getSelectionModel().getSelectedIndex() == -1)
        {
            return;
        }
        Card card = cardList.getSelectionModel().getSelectedItem();
        try {
            if(deck.containsCard(card))
                deck.removeCard(card);
            setCards();
        }
        catch (Exception e)
        {
            throw new containerNotFoundException();
        }
        if(deck.isPublic())
        {
            user.setContributions(user.getContributions() - 1);
        }
        System.out.println("Contributions appended");
    }

    public void goBack(ActionEvent event) throws IOException {
        SceneHandler.getInstance().switchToScene((Stage)((Node)event.getSource()).getScene().getWindow(),previousScene);
    }
}
