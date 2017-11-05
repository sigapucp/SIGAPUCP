package edu.pe.pucp.team_1.dp1.sigapucp.Controllers;

/**
 *
 * @author herbert
 */
public class Validator {
    private String numericRegex = "[-+]?\\d*\\.?\\d+";

    public boolean isNumeric(String str) {
        return str.matches(numericRegex);
    }

    public boolean isObjectNull(Object obj) {
        return obj == null;
    }
    
    public boolean isStringEmpty(String str) {
        return str.equals("");
    }
}
