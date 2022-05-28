package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML ChoiceBox<String> chosen_type;

    @FXML Rectangle spot1, spot2, spot3, spot4, spot5, spot6, spot7, spot8;
    @FXML Rectangle spot9, spot10, spot11, spot12, spot13, spot14, spot15, spot16;

    @FXML AnchorPane mainPane;

    @FXML Button solveButton, resultsButton;

    @FXML ScrollPane resultsPane;

    @FXML Text resultsLabel;

    private final Button[] buttons = new Button[16];

    Rectangle[] spots = new Rectangle[16];

    ArrayList<Group> groups = new ArrayList<>();

    ArrayList<String> choices1 = new ArrayList<>();
    ArrayList<String> choices2 = new ArrayList<>();
    ArrayList<String> choices3 = new ArrayList<>();
    ArrayList<String> choices4 = new ArrayList<>();

    ArrayList<ChoiceBox<String>> inputs1 = new ArrayList<>();
    ArrayList<ChoiceBox<String>> inputs2 = new ArrayList<>();
    ArrayList<ChoiceBox<String>> inputs3 = new ArrayList<>();
    ArrayList<ChoiceBox<String>> inputs4 = new ArrayList<>();

    ArrayList<Gate> gates1 = new ArrayList<>();
    ArrayList<Gate> gates2 = new ArrayList<>();
    ArrayList<Gate> gates3 = new ArrayList<>();
    ArrayList<Gate> gates4 = new ArrayList<>();

    private final String[] types = {"AND", "OR", "XOR", "NAND", "NOR", "XNOR", "NOT"};

    private final HashMap<String, Boolean> values = new HashMap<>();

    public void add(double x, double y, Button button){
        button.setDisable(true);
        resultsPane.setDisable(true);
        resultsPane.setOpacity(0.0);
        if(solveButton.isDisable()){
            solveButton.setDisable(false);
        }
        resultsButton.setDisable(true);
        resultsButton.setOpacity(0.0);
        int n = Integer.parseInt(button.getText());
        Rectangle rect = newRectangle(x, y);
        Label label = newLabel(x, y, chosen_type.getValue());
        ChoiceBox<String> choiceBox = newInputCB(x, y, n);
        ChoiceBox<String> choiceBox1 = newInputCB(x, y + 70, n);
        if(chosen_type.getValue().equals("NOT")){
            choiceBox1.setDisable(true);
            choiceBox1.setOpacity(0.0);
            choiceBox1.setValue("0");
            choiceBox.setLayoutY(y + 38);
        }
        TextField out = newOutTF(x, y, button);
        Gate gate = new Gate();
        gate.setVar(out.getText());
        gate.setType(label.getText());
        if(n < 4){
            gates1.add(gate);
            inputs1.add(choiceBox);
            inputs1.add(choiceBox1);
            choices2.add(out.getText());
            choices3.add(out.getText());
            choices4.add(out.getText());
            refreshChoices(2);
        }else if(n < 8){
            gates2.add(gate);
            choices3.add(out.getText());
            choices4.add(out.getText());
            inputs2.add(choiceBox);
            inputs2.add(choiceBox1);
            refreshChoices(3);
        }else if(n < 12){
            gates3.add(gate);
            choices4.add(out.getText());
            inputs3.add(choiceBox);
            inputs3.add(choiceBox1);
            refreshChoices(4);
        }else{
            gates4.add(gate);
            inputs4.add(choiceBox);
            inputs4.add(choiceBox1);
        }
        Group group = new Group(rect, label, choiceBox, choiceBox1, out);
        mainPane.getChildren().addAll(group);
        groups.add(group);
    }

    private TextField newOutTF(double x, double y, Button button){
        TextField out = new TextField();
        out.setText(getVar(Integer.parseInt(button.getText())));
        out.setPrefHeight(25);
        out.setPrefWidth(25);
        out.setFont(new Font("System", 12));
        out.setLayoutX(x + 86);
        out.setLayoutY(y + 38);
        out.setEditable(false);
        return out;
    }

    private Rectangle newRectangle(double x, double y){
        Rectangle rect = new Rectangle();
        rect.setLayoutY(y);
        rect.setLayoutX(x);
        rect.setWidth(100);
        rect.setHeight(100);
        rect.setFill(Color.rgb(68, 66, 66));
        rect.setStroke(Color.BLACK);
        rect.setStrokeWidth(1.0);
        return rect;
    }

    private Label newLabel(double x, double y, String type){
        Label label = new Label(type);
        label.setAlignment(Pos.CENTER);
        label.setLayoutY(y);
        label.setLayoutX(x);
        label.setTextFill(Color.WHITE);
        label.setPrefWidth(100);
        label.setPrefHeight(100);
        label.setFont(Font.font("System", FontWeight.BOLD, 16));
        return label;
    }

    private String getVar(int n){
        String[] variables1 = {"A", "B", "C", "D"};
        String[] variables2 = {"E", "F", "G", "H"};
        String[] variables3 = {"I", "J", "K", "L"};
        String[] variables4 = {"M", "N", "O", "P"};
        if(n < 4){
            return variables1[n];
        }else if(n < 8){
            return variables2[n % 4];
        }else if(n < 12){
            return variables3[n % 4];
        }else{
            return variables4[n % 4];
        }
    }

    private ArrayList<String> getChoices(int n){
        ArrayList<String> choices;
        if(n < 4){
            choices = choices1;
        }else if(n < 8){
            choices = choices2;
        }else if(n < 12){
            choices = choices3;
        }else{
            choices = choices4;
        }
        return choices;
    }

    private void refreshChoices(int column){
        ArrayList<ChoiceBox<String>> cbs;
        ArrayList<String> choices;
        if(column == 2){
            cbs = inputs2;
            choices = getChoices(5);
            ObservableList<String> list = FXCollections.observableArrayList(choices);
            for (ChoiceBox<String> cb : cbs) {
                String temp = cb.getValue();
                cb.setItems(list);
                cb.setValue(temp);
            }
        }
        if(column == 3 || column == 2){
            cbs = inputs3;
            choices = getChoices(9);
            ObservableList<String> list = FXCollections.observableArrayList(choices);
            for (ChoiceBox<String> cb : cbs) {
                String temp = cb.getValue();
                cb.setItems(list);
                cb.setValue(temp);
            }
        }
        cbs = inputs4;
        choices = getChoices(13);
        ObservableList<String> list = FXCollections.observableArrayList(choices);
        for (ChoiceBox<String> cb : cbs) {
            String temp = cb.getValue();
            cb.setItems(list);
            cb.setValue(temp);
        }
    }

    private ChoiceBox<String> newInputCB(double x, double y, int column){
        ChoiceBox<String> choiceBox = new ChoiceBox<>();
        choiceBox.setPrefWidth(43);
        choiceBox.setPrefHeight(25);
        choiceBox.setLayoutX(x - 39);
        choiceBox.setLayoutY(y);
        choiceBox.setItems(FXCollections.observableArrayList(getChoices(column)));
        choiceBox.setValue("0");
        return choiceBox;
    }

    @FXML
    public void clear(){
        for(Button button : buttons){
            button.setDisable(false);
        }
        for (Group group : groups) {
            mainPane.getChildren().removeAll(group);
        }
        groups.clear();
        inputs2.clear();
        inputs3.clear();
        inputs4.clear();
        choices2.clear();
        choices3.clear();
        choices4.clear();
        choices2.add("0");
        choices2.add("1");
        choices3.add("0");
        choices3.add("1");
        choices4.add("0");
        choices4.add("1");
        gates1.clear();
        gates2.clear();
        gates3.clear();
        gates4.clear();
        solveButton.setDisable(true);
        resultsButton.setDisable(true);
        resultsButton.setOpacity(0.0);
        resultsPane.setDisable(!resultsPane.isDisable());
        resultsPane.setOpacity(0.0);
    }

    public void showResults(){
        resultsPane.setDisable(!resultsPane.isDisable());
        resultsPane.setOpacity(1.0);
        if(resultsPane.isDisable()){
            resultsPane.setOpacity(0.0);
        }
    }

    public void solve(){
        solveButton.setDisable(true);
        ArrayList<ArrayList<Gate>> allGates = new ArrayList<>();
        allGates.add(gates1);
        allGates.add(gates2);
        allGates.add(gates3);
        allGates.add(gates4);
        ArrayList<ArrayList<ChoiceBox<String>>> allInputs = new ArrayList<>();
        allInputs.add(inputs1);
        allInputs.add(inputs2);
        allInputs.add(inputs3);
        allInputs.add(inputs4);
        resultsLabel.setText(Solver.solveAll(allGates, allInputs));
        resultsButton.setOpacity(1.0);
        resultsButton.setDisable(false);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        spots[0] = spot1;
        spots[1] = spot2;
        spots[2] = spot3;
        spots[3] = spot4;
        spots[4] = spot5;
        spots[5] = spot6;
        spots[6] = spot7;
        spots[7] = spot8;
        spots[8] = spot9;
        spots[9] = spot10;
        spots[10] = spot11;
        spots[11] = spot12;
        spots[12] = spot13;
        spots[13] = spot14;
        spots[14] = spot15;
        spots[15] = spot16;
        chosen_type.setItems(FXCollections.observableArrayList(types));
        chosen_type.setValue("AND");
        choices1.add("0");
        choices1.add("1");
        choices2.add("0");
        choices2.add("1");
        choices3.add("0");
        choices3.add("1");
        choices4.add("0");
        choices4.add("1");
        values.put("0", false);
        values.put("1", true);
        values.put("None", false);
        for (int i = 0; i < spots.length; i++) {
            double x = spots[i].getLayoutX();
            double y = spots[i].getLayoutY();
            Button button = new Button();
            button.setText(String.valueOf(i));
            button.setLayoutX(x);
            button.setLayoutY(y);
            button.setPrefWidth(100);
            button.setPrefHeight(100);
            button.setCursor(Cursor.HAND);
            button.setOpacity(0.0);
            button.setOnAction(e -> add(x, y, button));
            buttons[i] = button;
            mainPane.getChildren().add(button);
        }
    }
}
