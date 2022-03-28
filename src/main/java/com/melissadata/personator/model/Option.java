package com.melissadata.personator.model;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Option {

    // Initialize General Options
    private final StringProperty optionCentricHint;
    private final StringProperty optionAppend;
    private final StringProperty optionAdvancedAddressCorrection;
    /**
     * Model Class for an option
     *
     * @author Michael Johnson
     */

    public Option() {
        optionCentricHint = new SimpleStringProperty("");
        optionAppend = new SimpleStringProperty("");
        optionAdvancedAddressCorrection = new SimpleStringProperty("");
    }

    public String generateOptionString() {
        List<String> options = new ArrayList<>();

        if(!getCentricHint().equals(""))
            options.add("CentricHint:" + getCentricHint());
        
        if(!getAppend().equals(""))
            options.add("Append:" + getAppend());

        if(!getAdvancedAddressCorrection().equals(""))
            options.add("AdvancedAddressCorrection:"
                    + getAdvancedAddressCorrection());

        return String.join(",", options);
    }

    //region Getter and Setter for CentricHint Option
    public String getCentricHint() {
        return optionCentricHint.get();
    }

    public void setCentricHint(String centricHint) {
        this.optionCentricHint.set(centricHint);
    }
    //endregion

    //region Getter and Setter for Append Option
    public String getAppend() {
        return optionAppend.get();
    }

    public void setAppend(String append) {
        this.optionAppend.set(append);
    }
    //endregion

    //region Getter and Setter for AdvancedAddressCorrection Option
    public String getAdvancedAddressCorrection() {
        return optionAdvancedAddressCorrection.get();
    }

    public void setAdvancedAddressCorrection(String optionAdvancedAddressCorrection) {
        this.optionAdvancedAddressCorrection.set(optionAdvancedAddressCorrection);
    }
    //endregion

    public String toString(){
        String optionString = "&opt=";
        optionString += "AdvancedAddressCorrection:" + optionAdvancedAddressCorrection.get();
        optionString += ",CentricHint:" + optionCentricHint.get();
        optionString += ",Append:" + optionAppend.get();
        return optionString;
    }

}
