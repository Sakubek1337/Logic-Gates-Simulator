package sample;

import javafx.scene.control.ChoiceBox;

import java.util.ArrayList;
import java.util.HashMap;

public class Solver {

    public Solver(){
    }

    public static Boolean solve(String type, boolean a, boolean b){
        type = type.toLowerCase();
        switch (type){
            case "and":
                return a && b;
            case "or":
                return a || b;
            case "xor":
                return a != b;
            case "nand":
                return !(a && b);
            case "nor":
                return !(a || b);
            case "not":
                return !a;
            default:
                return a == b;
        }
    }

    public static String solveAll(ArrayList<ArrayList<Gate>> allGates, ArrayList<ArrayList<ChoiceBox<String>>> allInputs){
        HashMap<String, Boolean> values = new HashMap<>();
        values.put("1", true);
        values.put("0", false);
        for(int i = 0; i < allGates.size(); i++){
            ArrayList<Gate> gates = allGates.get(i);
            ArrayList<ChoiceBox<String>> inputs = allInputs.get(i);
            for(int j = 0; j < gates.size(); j++){
                Gate gate = gates.get(j);
                String input1 = inputs.get(j * 2).getValue();
                String input2 = inputs.get(j * 2 + 1).getValue();
                String var = gate.getVar();
                String type = gate.getType();
                values.put(var, solve(type ,values.get(input1), values.get(input2)));
            }
        }
        StringBuilder sb = new StringBuilder();
        for(int i = allGates.size() - 1; i >= 0; i--){
            ArrayList<Gate> gates = allGates.get(i);
            for (Gate gate : gates) {
                sb.append(gate.getVar()).append(" ==== ");
                boolean value = values.get(gate.getVar());
                sb.append(String.valueOf(value).toUpperCase()).append("\n");
            }
        }
        return sb.toString().trim();
    }
}
