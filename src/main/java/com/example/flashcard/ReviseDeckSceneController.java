package com.example.flashcard;

import ExceptionHandling.containerEmptyException;
import ExceptionHandling.sceneChangeException;
import ExceptionHandling.timerThreadException;
import services.AdminServer;
import user.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import models.Card;
import models.Deck;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class ReviseDeckSceneController {

    @FXML
    private TextArea answerText;

    @FXML
    private Label counter;

    @FXML
    private Button goBackButton;

    @FXML
    private Button nextCardButton;

    @FXML
    private TextArea questionText;

    @FXML
    private Label adaptiveLabel;

    @FXML
    private RadioButton adaptiveYesRadio;

    @FXML
    private RadioButton adaptiveNoRadio;

    private Deck deck;
    private Card currentCard;
    private int index;
    private int counterInt;
    private int counterChangeInt;
    private int initialCountInt = 10;
    private int maxCount = 20;
    private int minCount = 4;
    private Scene previousScene;
    private User user;
    public User getUser() {
        return user;
    }

    public void setUser(String username) {
        this.user = AdminServer.getInstance().findUser(username);
        user.setActivity(user.getActivity() + 1);
    }

    public Scene getPreviousScene() {
        return previousScene;
    }

    public void setPreviousScene(Scene previousScene) {
        this.previousScene = previousScene;
    }

    public int getInitialCountInt() {
        return initialCountInt;
    }

    public int getCounterInt() {
        return counterInt;
    }
    public void setCounterInt(int counterInt){
        this.counterInt = counterInt;
    }

    public void setInitialCountInt(int counterChangeInt) throws timerThreadException {
        this.counterChangeInt = counterChangeInt;
        int sumCount = this.initialCountInt + counterChangeInt;
        if(sumCount > maxCount) {
            sumCount = maxCount;
            this.counterChangeInt -= 2;
        }
        else if(sumCount < minCount) {
            sumCount = minCount;
            this.counterChangeInt += 2;
        }
        this.initialCountInt = sumCount;
        setCounterInt(initialCountInt);
        this.counter.setText(((Integer)counterInt).toString());
        startCount();
    }

    public void startCount() throws timerThreadException {
        try {
            Timer timer = new Timer();
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    if (getCounterInt() > 0)
                        setCounterInt(getCounterInt() - 1);
                    else {
                        showHiddenElements();
                        this.cancel();
                    }
                }
            };
            timer.scheduleAtFixedRate(timerTask, 1000, 1000);
        }
        catch (Exception e) {
            throw new timerThreadException();
            }

        }

    public void showHiddenElements(){
        answerText.setVisible(true);
        adaptiveLabel.setVisible(true);
        adaptiveNoRadio.setVisible(true);
        adaptiveYesRadio.setVisible(true);
        nextCardButton.setVisible(true);
    }

    public Deck getDeck() {
        return deck;
    }

    public void setDeck(Deck deck, int index) {
        this.deck = deck;
        this.index = index;
        setCurrentCard(deck.getCards().get(index));
    }

    public Card getCurrentCard() {
        return currentCard;
    }

    public void setCurrentCard(Card currentCard) {
        this.currentCard = currentCard;
        this.questionText.setText(currentCard.getQuestion());
        this.answerText.setText(currentCard.getCardType() + ":\n" + currentCard.getAnswer());
    }
    public void nextCard(ActionEvent event) throws sceneChangeException, containerEmptyException, timerThreadException {//prototype
        int increment = 0;
        if(adaptiveYesRadio.isSelected())
        {
            increment = -2;
        }
        else if(adaptiveNoRadio.isSelected())
        {
            increment = 2;
        }
        SceneHandler.getInstance().switchToReviseDeckScreen((Stage)((Node)event.getSource()).getScene().getWindow(),previousScene,deck,(index + 1)%deck.getSize(),counterChangeInt + increment);
    }

    public void goBack(ActionEvent event) throws IOException {
        SceneHandler.getInstance().switchToScene((Stage)((Node)event.getSource()).getScene().getWindow(),previousScene);
    }

}
