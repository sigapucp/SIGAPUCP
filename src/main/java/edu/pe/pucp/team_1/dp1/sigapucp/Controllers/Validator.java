package edu.pe.pucp.team_1.dp1.sigapucp.Controllers;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.DatePicker;

/**
 *
 * @author herbert
 */
public class Validator {
    private String numericRegex = "[-+]?\\d*\\.?\\d+";

    private String getTxtFromField(Node node, String type) {
        switch(type) {
            case "TextField":
                TextField txtField = (TextField) node;
                return txtField.getText();
            case "ComboBox":
                ComboBox comboBox = (ComboBox) node;
                return String.valueOf(comboBox.getSelectionModel().getSelectedItem());
            case "DatePicker":
                DatePicker datePicker = (DatePicker) node;
                return datePicker == null ? "" : datePicker.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            default:
                return "";
        }
    }

    private boolean validateField(Node node, String nodeType, String nodeRules) {
        String[] rules = nodeRules.split(",");
        boolean condition = true;
        String txt;
        for(String rule : rules) {
            switch(rule) {
                case "notNull":
                    condition = condition && !isNullObject(node);
                    break;
                case "notEmpty":
                    txt = getTxtFromField(node, nodeType);
                    condition = condition && !isEmptyString(txt);
                    break;
                case "numeric":
                    txt = getTxtFromField(node, nodeType);
                    condition = condition && isNumeric(txt);
                    break;
                default:
                    break;
            }
        }
        return condition;
    }

    // Paso previo para usar esta funcion, a todos los fields que van a validar, es necesario que 
    // define el atributo id (que no es el mismo que el fx:id), en sus fields por scene builder.
    // Este atributo se encuentra en la sección properties de scene builder.
    public boolean validate(HashMap<String, String> rules, Node... params) {
        boolean condition = true;
        for(Node param : params) {
            String type = param.getClass().getSimpleName();
            String nodeId = param.getId();
            String nodeRules = rules.get(nodeId);

            condition = condition && validateField(param, type, nodeRules);
        }
        return condition;
    }

    public boolean isNumeric(String str) {
        return str.matches(numericRegex);
    }

    public boolean isNullObject(Object obj) {
        return obj == null;
    }
    
    public boolean isEmptyString(String str) {
        return str.equals("");
    }
}
