package melissadata.personator.view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;

import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import org.apache.sling.commons.json.JSONObject;
import melissadata.personator.model.Option;

public class PersonatorTransactionController {

    private final String endpoint;
    private Option options;
    private String columns;
    private String identNumber;
    private String actions;
    private boolean verify, append, move;
    private String company, fullName, phoneNumber, emailAddress, addressLine1, addressLine2, city, postal, state, country, format;
    private boolean selectAllGroups, grpNameDetails, grpAddressDetails, grpCensus, grpParsedEmail, grpParsedPhone, grpGeoCode, grpDemographicBasic;
    private boolean selectAllColumns, columnsPlus4, columnsSuite, columnsPrivateMailbox,
            columnsMoveDate, columnsOccupation, columnsOwnRent, columnsPhoneCountryCode, columnsPhoneCountryName;

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
        String request = "";
        request = endpoint;
        request += "&id=" + getIdentNumber();
        request += "&opt=" + options.generateOptionString();
        request += "&cols=" + generateGroupColumnString();
        request += "&act=" + getActions();
        if(!getCompany().equals(""));
        request += "&comp=" + URLEncoder.encode(getCompany(), "UTF-8");

        if(!getFullName().equals(""));
        request += "&full=" + URLEncoder.encode(getFullName(), "UTF-8");

        if(!getPhoneNumber().equals(""));
        request += "&phone=" + URLEncoder.encode(getPhoneNumber(), "UTF-8");

        if(!getEmailAddress().equals(""));
        request += "&email=" + URLEncoder.encode(getEmailAddress(), "UTF-8");

        if(!getAddressLine1().equals(""));
        request += "&a1=" + URLEncoder.encode(getAddressLine1(), "UTF-8");

        if(!getAddressLine2().equals(""));
        request += "&a2=" + URLEncoder.encode(getAddressLine2(), "UTF-8");

        if(!getCity().equals(""));
        request += "&city=" + URLEncoder.encode(getCity(), "UTF-8");

        if(!getState().equals(""));
        request += "&state=" + URLEncoder.encode(getState(), "UTF-8");

        if(!getPostal().equals(""));
        request += "&postal=" + URLEncoder.encode(getPostal(), "UTF-8");

        if(!getCountry().equals(""));
        request += "&ctry=" + URLEncoder.encode(getCountry(), "UTF-8");

        request += "&format=" + getFormat();

        return request;
    }

    @SuppressWarnings("deprecation")
    public String processTransaction(String request) {
        String response = "";
        URI uri;
        URL url;
        try {
            uri = new URI(request);
            url = new URL(uri.toURL().toString());

            HttpURLConnection urlConn = (HttpURLConnection)(url.openConnection());

            BufferedReader in = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));

            StringReader reader;
            StringWriter writer = new StringWriter();
            StringBuffer jsonResponse = new StringBuffer();
            String inputLine = "";
            if (format.equals("JSON"))
            {
                while ((inputLine = in.readLine()) != null) {
                    jsonResponse.append(inputLine);
                }
                @SuppressWarnings("deprecation")
                JSONObject test = new JSONObject(jsonResponse.toString());
                response = test.toString(4);

            } else {
                String xmlLine = "";
                String xmlString = "";
                while((xmlLine = in.readLine()) != null) {
                    xmlString = xmlLine;
                }
                TransformerFactory tf = TransformerFactory.newInstance();
                Transformer t = tf.newTransformer();
                t.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
                t.setOutputProperty(OutputKeys.INDENT, "yes");

                reader = new StringReader(xmlString);
                try {
                    t.transform(new javax.xml.transform.stream.StreamSource(reader), new javax.xml.transform.stream.StreamResult(writer));
                } catch (TransformerException e) {
                    e.printStackTrace();
                }
                response = writer.toString();
            }


        } catch (Exception e){
            System.out.println("Error sending request: \n" + e);
        }
        return response;
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
        if(verify == true)
            tmp.append(",verify");
        if(append == true)
            tmp.append(",append");
        if(move == true)
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
        String columnString = "";
        if(isSelectAllGroups()){
            columnString += "grpAll";
        } else {
            if(isGrpNameDetails()){
                columnString += "grpNameDetails";
            }

            if (isGrpAddressDetails() && columnString.equals(""))
                columnString += "grpParsedAddress,grpAddressDetails";
            else if (isGrpAddressDetails() && !columnString.equals(""))
                columnString += ",grpParsedAddress,grpAddressDetails";

            if (isGrpCensus() && columnString.equals(""))
                columnString += "grpCensus,grpCensus2";
            else if (isGrpCensus() && !columnString.equals(""))
                columnString += ",grpCensus,grpCensus2";

            if (isGrpParsedEmail() && columnString.equals(""))
                columnString += "grpParsedEmail";
            else if (isGrpParsedEmail() && !columnString.equals(""))
                columnString += ",grpParsedEmail";

            if (isGrpParsedPhone() && columnString.equals(""))
                columnString += "grpParsedPhone";
            else if (isGrpParsedPhone() && !columnString.equals(""))
                columnString += ",grpParsedPhone";

            if (isGrpGeoCode() && columnString.equals(""))
                columnString += "grpGeocode";
            else if (isGrpGeoCode() && !columnString.equals(""))
                columnString += ",grpGeocode";

            if (isGrpDemographicBasic() && columnString.equals(""))
                columnString += "grpDemographicBasic";
            else if (isGrpDemographicBasic() && !columnString.equals(""))
                columnString += ",grpDemographicBasic";
        }

        if (isColumnsPlus4() && columnString.equals(""))
            columnString += "Plus4";
        else if (isColumnsPlus4() && !columnString.equals(""))
            columnString += ",Plus4";

        if (isColumnsSuite() && columnString.equals(""))
            columnString += "Suite";
        else if (isColumnsSuite() && !columnString.equals(""))
            columnString += ",Suite";

        if (isColumnsPrivateMailbox() && columnString.equals(""))
            columnString += "PrivateMailbox";
        else if (isColumnsPrivateMailbox() && !columnString.equals(""))
            columnString += ",PrivateMailbox";

        if (isColumnsMoveDate() && columnString.equals(""))
            columnString += "MoveDate";
        else if (isColumnsMoveDate() && !columnString.equals(""))
            columnString += ",MoveDate";

        if (isColumnsOccupation() && columnString.equals(""))
            columnString += "Occupation";
        else if (isColumnsOccupation() && !columnString.equals(""))
            columnString += ",Occupation";

        if (isColumnsOwnRent() && columnString.equals(""))
            columnString += "OwnRent";
        else if (isColumnsOwnRent() && !columnString.equals(""))
            columnString += ",OwnRent";

        if (isColumnsPhoneCountryCode() && columnString.equals(""))
            columnString += "PhoneCountryCode";
        else if (isColumnsPhoneCountryCode() && !columnString.equals(""))
            columnString += ",PhoneCountryCode";

        if (isColumnsPhoneCountryName() && columnString.equals(""))
            columnString += "PhoneCountryName";
        else if (isColumnsPhoneCountryName() && !columnString.equals(""))
            columnString += ",PhoneCountryName";

        return columnString;
    }

}
