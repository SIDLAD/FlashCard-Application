package com.example.flashcard;

import ExceptionHandling.sceneChangeException;
import services.AdminServer;
import user.User;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import models.Category;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class UserHomeScreenController implements Initializable{

    private User user;

    @FXML
    private Label nameLabel;

    @FXML
    private Button logoutButton;

    @FXML
    private ListView<Category> categoryList;

    @FXML
    private TextField newCategoryName;

    @FXML
    private Button createCategoryButton;

    @FXML
    private Label activityLabel;

    @FXML
    private Label contributionsLabel;

    // top 3 contributors
    @FXML
    private Label contributor_1;
    @FXML
    private Label contributor_2;
    @FXML
    private Label contributor_3;

    @FXML
    private Label categoryErrorLabel;

    private Scene scene;

    public User getUser() {
        return this.user;
    }

    public void setAllContributionsAndActivity() {
        activityLabel.setText(Integer.toString(user.getActivity()));
        contributionsLabel.setText(Integer.toString(user.getContributions()));

        // set top contributors
        ArrayList<User> topUsers = AdminServer.getInstance().getTopContributors();
        contributor_1.setText("1. " + topUsers.get(0).getUsername() + ": " + Integer.toString(topUsers.get(0).getContributions()));
        contributor_2.setText("2. " + topUsers.get(1).getUsername() + ": " + Integer.toString(topUsers.get(1).getContributions()));
        contributor_3.setText("3. " + topUsers.get(2).getUsername() + ": " + Integer.toString(topUsers.get(2).getContributions()));
    }

    public void setUser(User user) {
        this.user = user;
        setCategories();
        setNameLabel(user.getUsername());

        setAllContributionsAndActivity();
    }
    public void setCategories()
    {
        categoryList.getItems().clear();
        categoryList.getItems().addAll(AdminServer.getInstance().getCategories());
    }

    public void setNameLabel(String name) {
        nameLabel.setText("Hello, " + name);
    }

    public void editCategoryList(String categoryName) {
        Category newCategory = AdminServer.getInstance().createNewCategory(categoryName);

        // if category already exists, show error message then return
        if(newCategory == null) {
            categoryErrorLabel.setVisible(true);
            return;
        }
        categoryErrorLabel.setVisible(false);

        categoryList.getItems().addAll(newCategory);
        System.out.println(AdminServer.getInstance().getCategories());
    }

    public void logout(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setHeaderText("User Log-out Confirmation");
        alert.setContentText("Are you sure you want to log out?");
        if (alert.showAndWait().get() == ButtonType.OK) {
            user.setLoggedIn(false);
            ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
        }
    }

    public void createCategoryAction(ActionEvent event) {
        // if user enters empty string or whitespaces, then don't create a category
        if(newCategoryName.getText().trim().equals(""))
            return;

        editCategoryList(newCategoryName.getText());
        newCategoryName.setText("");
    }

    public void openCategoryScreen(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("CategoryScreenController.fxml"));
        Parent root = loader.load();
        Scene scene1 = new Scene(root);

        CategoryScreenController categoryScreenController = loader.getController();
        categoryScreenController.setUser(user);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene1);
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        categoryList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Category>() {

            @Override
            public void changed(ObservableValue<? extends Category> arg0, Category arg1, Category arg2) {
                Category category = categoryList.getSelectionModel().getSelectedItem();
                System.out.println("ListView Item Selected");
                try {
                    SceneHandler.getInstance().switchToCategoryScreen((Stage) categoryList.getScene().getWindow(),categoryList.getScene(),getUser(),category);
                } catch (sceneChangeException e) {
                    throw new RuntimeException(e);
                }

            }
        });
    }

}