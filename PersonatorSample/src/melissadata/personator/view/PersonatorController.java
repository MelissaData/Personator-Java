package melissadata.personator.view;

import java.io.UnsupportedEncodingException;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import melissadata.personator.model.Option;
import melissadata.personator.Main;

public class PersonatorController  {
    @FXML
    private ComboBox<String> centricHintComboBox;
    @FXML
    private ComboBox<String> appendComboBox;
    @FXML
    private ComboBox<String> advancedAddressCorrectionComboBox;

    @FXML
    private TextArea RequestTextArea;
    @FXML
    private TextArea ResponseTextArea;

    @FXML
    private CheckBox actionVerifyCheckbox;
    @FXML
    private CheckBox actionAppendCheckbox;
    @FXML
    private CheckBox actionMoveCheckbox;

    @FXML
    private CheckBox groupAllCheckbox;
    @FXML
    private CheckBox groupGrpNameDetailsCheckbox;
    @FXML
    private CheckBox groupAddressGroupsCheckbox;
    @FXML
    private CheckBox groupCensusCheckbox;
    @FXML
    private CheckBox groupParseEmailGroupCheckbox;
    @FXML
    private CheckBox groupParsePhoneCheckbox;
    @FXML
    private CheckBox groupGeoCodeGroupCheckbox;
    @FXML
    private CheckBox groupDemographicGroupCheckbox;

    @FXML
    private CheckBox columnCheckAllColumnsCheckbox;
    @FXML
    private CheckBox columnPlus4Checkbox;
    @FXML
    private CheckBox columnSuiteCheckbox;
    @FXML
    private CheckBox columnPrivateMailboxCheckbox;
    @FXML
    private CheckBox columnMoveDateCheckbox;
    @FXML
    private CheckBox columnOccupationCheckbox;
    @FXML
    private CheckBox columnOwnRentCheckbox;
    @FXML
    private CheckBox columnPhoneCountryCodeCheckbox;
    @FXML
    private CheckBox columnPhoneCountryNameCheckbox;

    @FXML
    private TextField inputLicenseKeyText;
    @FXML
    private TextField inputCompanyText;
    @FXML
    private TextField inputFullNameText;
    @FXML
    private TextField inputPhoneNumberText;
    @FXML
    private TextField inputEmailAddressText;
    @FXML
    private TextField inputAddressLine1Text;
    @FXML
    private TextField inputAddressLine2Text;
    @FXML
    private TextField inputCityText;
    @FXML
    private TextField inputPostalText;
    @FXML
    private TextField inputStateText;
    @FXML
    private TextField inputCountryText;

    @FXML
    private RadioButton jsonResponseFormatRadio;
    @FXML
    private RadioButton xmlResponseFormatRadio;

    @FXML
    private Tab responseTabButtonArea;
    @FXML
    private TabPane tabPane;
    private final int CONFIGURATION_TAB = 0;
    private final int RESPONSE_TAB = 1;

    @FXML
    private Button buttonSend;

    @FXML
    private Button buttonClear;
    private PersonatorTransactionController Transaction;

    private Option option;
    private Main main;

    /**
     * The constructor. The constructor is called before the initialize()
     * method.
     */
    public PersonatorController() {
		Transaction = new PersonatorTransactionController();
		option = new Option();
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        setupOptionSelections();
        setActions();
        sendButtonAction();
        clearButtonAction();
        initializeTextFields();
        selectAllGroupAction();
        selectGroupCheckbox();
        selectAllColumnAction();
        selectColumnCheckbox();
        initializeFormatRadioButtons();
        updateRequestText();

    }

    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param mainApp
     */
    public void setMainApp(Main main) {
        this.main = main;
    }


    public void setupOptionSelections() {

        // Set up the Options General Tab Choice Boxes (CentricHint, Append, Demographics, SSNCascade)
        centricHintComboBox.setItems(FXCollections.observableArrayList("Auto", "Address", "Phone", "Email"));
        appendComboBox.setItems(FXCollections.observableArrayList("Blank", "CheckError", "Always"));
        advancedAddressCorrectionComboBox.setItems(FXCollections.observableArrayList("Off", "On"));
    }

    public void setActions() {

        actionVerifyCheckbox.setOnAction((event) -> {
            Transaction.setVerify(actionVerifyCheckbox.isSelected());
            updateRequestText();
            returnToConfiguration();
        });

        actionAppendCheckbox.setOnAction((event) -> {
            Transaction.setAppend(actionAppendCheckbox.isSelected());
            updateRequestText();
            returnToConfiguration();
        });

        actionMoveCheckbox.setOnAction((event) -> {
            Transaction.setMove(actionMoveCheckbox.isSelected());
            updateRequestText();
            returnToConfiguration();
        });
    }

    public void setAdvancedAddressCorrectionOption() {
        option.setAdvancedAddressCorrection(advancedAddressCorrectionComboBox.getValue());
        Transaction.setOptions(option);
        updateRequestText();
        returnToConfiguration();
    }
    public void setCentricHintOption() {

        option.setCentricHint(centricHintComboBox.getValue());
        Transaction.setOptions(option);
        updateRequestText();
        returnToConfiguration();
    }

    public void setAppendOption() {
        option.setAppend(appendComboBox.getValue());
        Transaction.setOptions(option);
        updateRequestText();
        returnToConfiguration();
    }

    public void initializeFormatRadioButtons(){
        jsonResponseFormatRadio.setOnAction((event) -> {
            Transaction.setFormat("JSON");
            xmlResponseFormatRadio.setSelected(false);
            updateRequestText();
        });

        xmlResponseFormatRadio.setOnAction((event) -> {
            Transaction.setFormat("XML");
            jsonResponseFormatRadio.setSelected(false);
            updateRequestText();
        });
    }

    public void initializeTextFields(){
        inputLicenseKeyText.textProperty().addListener((observable, oldvalue, newvalue) -> {
            Transaction.setIdentNumber(newvalue);
            updateRequestText();
            returnToConfiguration();
        });

        inputCompanyText.textProperty().addListener((observable, oldvalue, newvalue) -> {
            Transaction.setCompany(newvalue);
            updateRequestText();
            returnToConfiguration();
        });

        inputFullNameText.textProperty().addListener((observable, oldvalue, newvalue) -> {
            Transaction.setFullName(newvalue);
            updateRequestText();
            returnToConfiguration();
        });

        inputPhoneNumberText.textProperty().addListener((observable, oldvalue, newvalue) -> {
            Transaction.setPhoneNumber(newvalue);
            updateRequestText();
            returnToConfiguration();
        });

        inputEmailAddressText.textProperty().addListener((observable, oldvalue, newvalue) -> {
            Transaction.setEmailAddress(newvalue);
            updateRequestText();
            returnToConfiguration();

        });

        inputAddressLine1Text.textProperty().addListener((observable, oldvalue, newvalue) -> {
            Transaction.setAddressLine1(newvalue);
            updateRequestText();
            returnToConfiguration();
        });

        inputAddressLine2Text.textProperty().addListener((observable, oldvalue, newvalue) -> {
            Transaction.setAddressLine2(newvalue);
            updateRequestText();
            returnToConfiguration();
        });

        inputCityText.textProperty().addListener((observable, oldvalue, newvalue) -> {
            Transaction.setCity(newvalue);
            updateRequestText();
            returnToConfiguration();
        });

        inputPostalText.textProperty().addListener((observable, oldvalue, newvalue) -> {
            Transaction.setPostal(newvalue);
            updateRequestText();
            returnToConfiguration();
        });

        inputStateText.textProperty().addListener((observable, oldvalue, newvalue) -> {
            Transaction.setState(newvalue);
            updateRequestText();
            returnToConfiguration();
        });

        inputCountryText.textProperty().addListener((observable, oldvalue, newvalue) -> {
            Transaction.setCountry(newvalue);
            updateRequestText();
            returnToConfiguration();
        });


    }

    public void updateRequestText(){
        try {
            RequestTextArea.setText(Transaction.generateRequestString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public void returnToConfiguration() {
        if(tabPane.getSelectionModel().getSelectedIndex() != 0)	tabPane.getSelectionModel().select(CONFIGURATION_TAB);
    }
    public void sendButtonAction() {
        buttonSend.setOnAction((event) -> {
            ResponseTextArea.setText(Transaction.processTransaction(RequestTextArea.getText()));
            tabPane.getSelectionModel().select(RESPONSE_TAB);
        });
    }

    public void clearButtonAction(){
        buttonClear.setOnAction((event) -> {
            inputCompanyText.clear();
            inputFullNameText.clear();
            inputPhoneNumberText.clear();
            inputEmailAddressText.clear();
            inputAddressLine1Text.clear();
            inputAddressLine2Text.clear();
            inputCityText.clear();
            inputPostalText.clear();
            inputStateText.clear();
            inputCountryText.clear();
            returnToConfiguration();
        });
    }

    public void selectAllGroupAction() {
        groupAllCheckbox.setOnAction((event) -> {
            if(!Transaction.isSelectAllGroups()){
                Transaction.setGrpNameDetails(true);
                groupGrpNameDetailsCheckbox.setSelected(true);

                Transaction.setGrpAddressDetails(true);
                groupAddressGroupsCheckbox.setSelected(true);

                Transaction.setGrpCensus(true);
                groupCensusCheckbox.setSelected(true);

                Transaction.setGrpParsedEmail(true);
                groupParseEmailGroupCheckbox.setSelected(true);

                Transaction.setGrpParsedPhone(true);
                groupParsePhoneCheckbox.setSelected(true);

                Transaction.setGrpGeoCode(true);
                groupGeoCodeGroupCheckbox.setSelected(true);

                Transaction.setGrpDemographicBasic(true);
                groupDemographicGroupCheckbox.setSelected(true);
            } else {
                Transaction.setGrpNameDetails(false);
                groupGrpNameDetailsCheckbox.setSelected(false);

                Transaction.setGrpAddressDetails(false);
                groupAddressGroupsCheckbox.setSelected(false);

                Transaction.setGrpCensus(false);
                groupCensusCheckbox.setSelected(false);

                Transaction.setGrpParsedEmail(false);
                groupParseEmailGroupCheckbox.setSelected(false);

                Transaction.setGrpParsedPhone(false);
                groupParsePhoneCheckbox.setSelected(false);

                Transaction.setGrpGeoCode(false);
                groupGeoCodeGroupCheckbox.setSelected(false);

                Transaction.setGrpDemographicBasic(false);
                groupDemographicGroupCheckbox.setSelected(false);
            }
            Transaction.setSelectAllGroups(groupAllCheckbox.isSelected());
            updateRequestText();
        });
    }

    public void selectGroupCheckbox(){
        groupGrpNameDetailsCheckbox.setOnAction((event) -> {
            Transaction.setGrpNameDetails(groupGrpNameDetailsCheckbox.isSelected());
            updateRequestText();
        });

        groupAddressGroupsCheckbox.setOnAction((event) -> {
            Transaction.setGrpAddressDetails(groupAddressGroupsCheckbox.isSelected());
            updateRequestText();
        });

        groupCensusCheckbox.setOnAction((event) -> {
            Transaction.setGrpCensus(groupCensusCheckbox.isSelected());
            updateRequestText();
        });

        groupParseEmailGroupCheckbox.setOnAction((event) -> {
            Transaction.setGrpParsedEmail(groupParseEmailGroupCheckbox.isSelected());
            updateRequestText();
        });

        groupParsePhoneCheckbox.setOnAction((event) -> {
            Transaction.setGrpParsedPhone(groupParsePhoneCheckbox.isSelected());
            updateRequestText();
        });

        groupGeoCodeGroupCheckbox.setOnAction((event) -> {
            Transaction.setGrpGeoCode(groupGeoCodeGroupCheckbox.isSelected());
            updateRequestText();
        });

        groupDemographicGroupCheckbox.setOnAction((event) -> {
            Transaction.setGrpDemographicBasic(groupDemographicGroupCheckbox.isSelected());
            updateRequestText();
        });
    }

    public void selectAllColumnAction() {
        columnCheckAllColumnsCheckbox.setOnAction((event) -> {
            if(!Transaction.isSelectAllColumns()){
                Transaction.setColumnsPlus4(true);
                columnPlus4Checkbox.setSelected(true);

                Transaction.setColumnsSuite(true);
                columnSuiteCheckbox.setSelected(true);

                Transaction.setColumnsPrivateMailbox(true);
                columnPrivateMailboxCheckbox.setSelected(true);

                Transaction.setColumnsMoveDate(true);
                columnMoveDateCheckbox.setSelected(true);

                Transaction.setColumnsOccupation(true);
                columnOccupationCheckbox.setSelected(true);

                Transaction.setColumnsOwnRent(true);
                columnOwnRentCheckbox.setSelected(true);

                Transaction.setColumnsPhoneCountryCode(true);
                columnPhoneCountryCodeCheckbox.setSelected(true);

                Transaction.setColumnsPhoneCountryName(true);
                columnPhoneCountryNameCheckbox.setSelected(true);
            } else {
                Transaction.setColumnsPlus4(false);
                columnPlus4Checkbox.setSelected(false);

                Transaction.setColumnsSuite(false);
                columnSuiteCheckbox.setSelected(false);

                Transaction.setColumnsPrivateMailbox(false);
                columnPrivateMailboxCheckbox.setSelected(false);

                Transaction.setColumnsMoveDate(false);
                columnMoveDateCheckbox.setSelected(false);

                Transaction.setColumnsOccupation(false);
                columnOccupationCheckbox.setSelected(false);

                Transaction.setColumnsOwnRent(false);
                columnOwnRentCheckbox.setSelected(false);

                Transaction.setColumnsPhoneCountryCode(false);
                columnPhoneCountryCodeCheckbox.setSelected(false);

                Transaction.setColumnsPhoneCountryName(false);
                columnPhoneCountryNameCheckbox.setSelected(false);
            }
            Transaction.setSelectAllColumns(columnCheckAllColumnsCheckbox.isSelected());
            updateRequestText();
        });
    }

    public void selectColumnCheckbox() {
        columnPlus4Checkbox.setOnAction((event) -> {
            Transaction.setColumnsPlus4(columnPlus4Checkbox.isSelected());
            updateRequestText();
        });

        columnSuiteCheckbox.setOnAction((event) -> {
            Transaction.setColumnsSuite(columnSuiteCheckbox.isSelected());
            updateRequestText();
        });

        columnMoveDateCheckbox.setOnAction((event) -> {
            Transaction.setColumnsMoveDate(columnMoveDateCheckbox.isSelected());
            updateRequestText();
        });

        columnPrivateMailboxCheckbox.setOnAction((event) -> {
            Transaction.setColumnsPrivateMailbox(columnPrivateMailboxCheckbox.isSelected());
            updateRequestText();
        });

        columnOccupationCheckbox.setOnAction((event) -> {
            Transaction.setColumnsOccupation(columnOccupationCheckbox.isSelected());
            updateRequestText();
        });

        columnOwnRentCheckbox.setOnAction((event) -> {
            Transaction.setColumnsOwnRent(columnOwnRentCheckbox.isSelected());
            updateRequestText();
        });

        columnPhoneCountryCodeCheckbox.setOnAction((event) -> {
            Transaction.setColumnsPhoneCountryCode(columnPhoneCountryCodeCheckbox.isSelected());
            updateRequestText();
        });

        columnPhoneCountryNameCheckbox.setOnAction((event) -> {
            Transaction.setColumnsPhoneCountryName(columnPhoneCountryNameCheckbox.isSelected());
            updateRequestText();
        });
    }

}
