package com.melissadata.personator.view;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.melissadata.personator.model.Option;

// import com.melissadata.personator.model.Option;

public class PersonatorTransactionController {

    private final String endpoint;
    private Option options;
    private String columns;
    private String identNumber;
    private String actions;
    private boolean verify, append, move;
    private String company, fullName, phoneNumber, emailAddress, addressLine1,
                    addressLine2, city, postal, state, country, format;
    
    /** All Columns */
    private boolean selectAllColumns;

    /** Column Groups */
    private boolean selectAllGroups;
    private boolean grpNameDetails;
    private boolean grpAddressDetails;
    private boolean grpCensus;
    private boolean grpParsedEmail;
    private boolean grpParsedPhone;
    private boolean grpGeoCode;
    private boolean grpDemographicBasic;

    /** Individual Columns */
    private boolean columnsPlus4;
    private boolean columnsSuite;
    private boolean columnsPrivateMailbox;
    private boolean columnsMoveDate;
    private boolean columnsOccupation;
    private boolean columnsOwnRent;
    private boolean columnsPhoneCountryCode;
    private boolean columnsPhoneCountryName;

    public PersonatorTransactionController() {
        endpoint = "https://personator.melissadata.net/v3/WEB/ContactVerify/doContactVerify?";
        options = new Option();
        setColumns("");
        setIdentNumber("");
        actions = "Check";
        company = "";
        fullName = "";
        phoneNumber = "";
        emailAddress = "";
        addressLine1 = "";
        addressLine2 = "";
        city     = "";
        postal   = "";
        state    = "";
        country  = "";
        format = "";

    }

    public String generateRequestString() throws UnsupportedEncodingException {
        StringBuilder sb = new StringBuilder();
        sb.append(endpoint)
            .append("&id=" + getIdentNumber())
            .append("&opt=" + options.generateOptionString())
            .append("&cols=" + generateGroupColumnString())
            .append("&act=" + getActions());

        if(!getFullName().equals(""))
            sb.append("&full=" + URLEncoder.encode(getFullName(), "UTF-8"));

        if(!getPhoneNumber().equals(""))
            sb.append("&phone=" + URLEncoder.encode(getPhoneNumber(), "UTF-8"));

        if(!getEmailAddress().equals(""))
            sb.append("&email=" + URLEncoder.encode(getEmailAddress(), "UTF-8"));

        if(!getAddressLine1().equals(""))
            sb.append("&a1=" + URLEncoder.encode(getAddressLine1(), "UTF-8"));

        if(!getAddressLine2().equals(""))
            sb.append("&a2=" + URLEncoder.encode(getAddressLine2(), "UTF-8"));

        if(!getCity().equals(""))
            sb.append("&city=" + URLEncoder.encode(getCity(), "UTF-8"));

        if(!getState().equals(""))
            sb.append("&state=" + URLEncoder.encode(getState(), "UTF-8"));

        if(!getPostal().equals(""))
            sb.append("&postal=" + URLEncoder.encode(getPostal(), "UTF-8"));

        if(!getCountry().equals(""))
            sb.append("&ctry=" + URLEncoder.encode(getCountry(), "UTF-8"));

        sb.append("&format=" + getFormat());

        return sb.toString();
    }

    public String processTransaction(String request) {
        String response = "";
        try {
            URL url = new URL(request);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(url.openStream()));
            String responseBody = in.lines().collect(Collectors.joining());
            response = format.equals("JSON")
                ? getPrettyJSON(responseBody)
                : getPrettyXML(responseBody);

        } catch (Exception e){
            System.out.println("Error sending request: \n" + e);
        }
        return response;
    }

    private String getPrettyJSON(String json) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonObject responseObject = gson.fromJson(json, JsonObject.class);
        return gson.toJson(responseObject);
    }

    private String getPrettyXML(String xml) {
        String prettyXML = "";
        try {
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer t = tf.newTransformer();
            String indentSize = "{http://xml.apache.org/xslt}indent-amount";
            t.setOutputProperty(indentSize, "2");
            t.setOutputProperty(OutputKeys.INDENT, "yes");
            Writer writer = new StringWriter();
            t.transform(new StreamSource(new StringReader(xml)),
                        new StreamResult(writer));
            prettyXML = writer.toString();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
        return prettyXML;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getOption() {
        return options.toString();
    }

    public void setOptions(Option options) {
        this.options = options;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostal() {
        return postal;
    }

    public void setPostal(String postal) {
        this.postal = postal;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public String getCompany(){
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getColumns() {
        return columns;
    }

    public void setColumns(String columns) {
        this.columns = columns;
    }

    public String getIdentNumber() {
        return identNumber;
    }

    public void setIdentNumber(String identNumber) {
        this.identNumber = identNumber;
    }

    public String getActions() {
        StringBuilder tmp = new StringBuilder();
        tmp.append("Check");
        if(this.verify)
            tmp.append(",verify");
        if(this.append)
            tmp.append(",append");
        if(this.move)
            tmp.append(",move");
        return tmp.toString();
    }

    public String setActions() {
        return actions;
    }

    public boolean getVerify() {
        return verify;
    }

    public void setVerify(boolean verify) {
        this.verify = verify;
    }

    public boolean getAppend() {
        return append;
    }

    public void setAppend(boolean append) {
        this.append = append;
    }

    public boolean getMove() {
        return move;
    }

    public void setMove(boolean move) {
        this.move = move;
    }

    public boolean isSelectAllGroups() {
        return selectAllGroups;
    }

    public void setSelectAllGroups(boolean selectAllGroups) {
        this.selectAllGroups = selectAllGroups;
    }

    public boolean isGrpNameDetails() {
        return grpNameDetails;
    }

    public void setGrpNameDetails(boolean grpNameDetails) {
        this.grpNameDetails = grpNameDetails;
    }

    public boolean isGrpAddressDetails() {
        return grpAddressDetails;
    }

    public void setGrpAddressDetails(boolean grpAddressDetails) {
        this.grpAddressDetails = grpAddressDetails;
    }

    public boolean isGrpCensus() {
        return grpCensus;
    }

    public void setGrpCensus(boolean grpCensus) {
        this.grpCensus = grpCensus;
    }

    public boolean isGrpParsedEmail() {
        return grpParsedEmail;
    }

    public void setGrpParsedEmail(boolean grpParsedEmail) {
        this.grpParsedEmail = grpParsedEmail;
    }

    public boolean isGrpParsedPhone() {
        return grpParsedPhone;
    }

    public void setGrpParsedPhone(boolean grpParsedPhone) {
        this.grpParsedPhone = grpParsedPhone;
    }

    public boolean isGrpGeoCode() {
        return grpGeoCode;
    }

    public void setGrpGeoCode(boolean grpGeoCode) {
        this.grpGeoCode = grpGeoCode;
    }

    public boolean isGrpDemographicBasic() {
        return grpDemographicBasic;
    }

    public void setGrpDemographicBasic(boolean grpDemographicBasic) {
        this.grpDemographicBasic = grpDemographicBasic;
    }

    public boolean isSelectAllColumns() {
        return selectAllColumns;
    }

    public void setSelectAllColumns(boolean selectAllColumns) {
        this.selectAllColumns = selectAllColumns;
    }

    public boolean isColumnsPlus4() {
        return columnsPlus4;
    }

    public void setColumnsPlus4(boolean columnsPlus4) {
        this.columnsPlus4 = columnsPlus4;
    }

    public boolean isColumnsSuite() {
        return columnsSuite;
    }

    public void setColumnsSuite(boolean columnsSuite) {
        this.columnsSuite = columnsSuite;
    }

    public boolean isColumnsPrivateMailbox() {
        return columnsPrivateMailbox;
    }

    public void setColumnsPrivateMailbox(boolean columnsPrivateMailbox) {
        this.columnsPrivateMailbox = columnsPrivateMailbox;
    }

    public boolean isColumnsMoveDate() {
        return columnsMoveDate;
    }

    public void setColumnsMoveDate(boolean columnsMoveDate) {
        this.columnsMoveDate = columnsMoveDate;
    }

    public boolean isColumnsOccupation() {
        return columnsOccupation;
    }

    public void setColumnsOccupation(boolean columnsOccupation) {
        this.columnsOccupation = columnsOccupation;
    }

    public boolean isColumnsOwnRent() {
        return columnsOwnRent;
    }

    public void setColumnsOwnRent(boolean columnsOwnRent) {
        this.columnsOwnRent = columnsOwnRent;
    }

    public boolean isColumnsPhoneCountryCode() {
        return columnsPhoneCountryCode;
    }

    public void setColumnsPhoneCountryCode(boolean columnsPhoneCountryCode) {
        this.columnsPhoneCountryCode = columnsPhoneCountryCode;
    }

    public boolean isColumnsPhoneCountryName() {
        return columnsPhoneCountryName;
    }

    public void setColumnsPhoneCountryName(boolean columnsPhoneCountryName) {
        this.columnsPhoneCountryName = columnsPhoneCountryName;
    }

    public String generateGroupColumnString() {
        List<String> columns = new ArrayList<>();
        if(isSelectAllGroups()) {
            columns.add("grpAll");
        } else {
            if(isGrpNameDetails()) columns.add("grpNameDetails");
            if (isGrpAddressDetails()) {
                columns.add("grpParsedAddress");
                columns.add("grpAddressDetails");
            }
            if (isGrpCensus()) {
                columns.add("grpCensus");
                columns.add("grpCensus2");
            }
            if (isGrpParsedEmail()) columns.add("grpParsedEmail");
            if (isGrpParsedPhone()) columns.add("grpParsedPhone");
            if (isGrpGeoCode()) columns.add("grpGeocode");
            if (isGrpDemographicBasic()) columns.add("grpDemographicBasic");
        }

        if (isColumnsPlus4()) columns.add("Plus4");
        if (isColumnsSuite()) columns.add("Suite");
        if (isColumnsPrivateMailbox()) columns.add("PrivateMailbox");
        if (isColumnsMoveDate()) columns.add("MoveDate");
        if (isColumnsOccupation()) columns.add("Occupation");
        if (isColumnsOwnRent()) columns.add("OwnRent");
        if (isColumnsPhoneCountryCode()) columns.add("PhoneCountryCode");
        if (isColumnsPhoneCountryName()) columns.add("PhoneCountryName");

        return String.join(",", columns);
    }

}
