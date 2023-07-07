package com.example.flashcard;

import ExceptionHandling.invalidParameterException;
import ExceptionHandling.notFXMLLoaderInstanceException;
import services.AdminServer;
import user.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import models.Card;
import models.CardType;
import models.Deck;
import models.cardFactory.CardGenerator;

import java.io.IOException;

public class CreateDefCardController {

    private Scene previousScene;

    public Scene getPreviousScene() {
        return previousScene;
    }

    public void setPreviousScene(Scene previousScene) {
        this.previousScene = previousScene;
    }
    private Deck deck;

    public Deck getDeck() {
        return deck;
    }

    public void setDeck(Deck deck) {
        this.deck = deck;
    }
    @FXML
    private TextArea answerText;

    @FXML
    private Button createCardButton;

    @FXML
    private Button goBackButton;

    @FXML
    private TextArea questionText;
    private User user;
    public User getUser() {
        return user;
    }

    public void setUser(String username) {
        this.user = AdminServer.getInstance().findUser(username);
    }

    public  void createCard(ActionEvent event) throws invalidParameterException, notFXMLLoaderInstanceException {
        if(questionText.getText().isBlank() || answerText.getText().isBlank())
        {
            throw new invalidParameterException();
        }

        Card card = (new CardGenerator()).newCard(questionText.getText(), answerText.getText(), deck.getCategory(), CardType.DEFINITION);
        deck.addCard(card);
        if(!(previousScene.getUserData() instanceof FXMLLoader))
            throw new notFXMLLoaderInstanceException();
        ((DeckSceneController)((FXMLLoader)previousScene.getUserData()).getController()).setCards();
        System.out.println("new card added in deck");
        if(deck.isPublic())
        {
            user.setContributions(user.getContributions() + 1);
        }
        System.out.println("Contributions appended");
        goBackButton.fire();
    }

    public void goBack(ActionEvent event) throws IOException {
        SceneHandler.getInstance().switchToScene((Stage)((Node)event.getSource()).getScene().getWindow(),previousScene);
    }

}
