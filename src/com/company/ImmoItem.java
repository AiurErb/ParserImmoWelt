package com.company;
import java.net.URL;
import java.time.LocalDate;

public class ImmoItem {
    String SiteName;
    String SiteID;
    URL UrlItem;
    LocalDate DateOfParsing;
    String Address;
    String District;
    String Realtor;
    String Price;
    String Area;
    String Rooms;

    public ImmoItem () {
        DateOfParsing = LocalDate.now();

    }

    public String toCSV() {
        String CSV="";
        CSV += SiteName + ":";
        CSV += SiteID + ":";
        CSV += UrlItem.toString() +":";
        CSV += DateOfParsing.toString() + ":";
        CSV += Address + ":";
        CSV += District + ":";
        CSV += Realtor + ":";
        CSV += Price + ":";
        CSV += Area + ":";
        CSV += Rooms + "\n";
        return CSV;
    }

}
