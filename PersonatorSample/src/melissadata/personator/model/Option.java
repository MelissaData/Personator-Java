package melissadata.personator.model;
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
        String optionString = "";

        if(!getCentricHint().equals(""))
            optionString += "CentricHint:" + getCentricHint();
        if(!getAppend().equals("") && !optionString.equals(""))
            optionString += ",Append:" + getAppend();
        else if(!getAppend().equals("") && optionString.equals(""))
            optionString += "Append:" + getAppend();
        if(!getAdvancedAddressCorrection().equals("") && !optionString.equals(""))
            optionString += ",AdvancedAddressCorrection:" + getAdvancedAddressCorrection();
        else if(!getAdvancedAddressCorrection().equals("") && optionString.equals(""))
            optionString += "AdvancedAddressCorrection:" + getAdvancedAddressCorrection();
        return optionString;
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
