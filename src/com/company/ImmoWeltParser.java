package com.company;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ListIterator;

public class ImmoWeltParser {
    String URLMieten = "https://www.immowelt.de/liste/koeln/wohnungen/mieten";
    static String SiteName = "Immowelt.de";
    static String SiteId = "EstateItem-1c115";
    static String ClassOfAddress = "IconFact-e8a23";
    static String ClassOfRealtor = "placeholder-f29e5";
    static String ClassOfPrice = "KeyFacts-efbce";
    static String ClassOfArea = "KeyFacts-efbce";
    static String ClassOfRooms ="KeyFacts-efbce";
    static ArrayList<ImmoItem> ii = new ArrayList<ImmoItem>();

    public void ImmoWeltRunner () throws IOException {
        int i = 1;
        //char quotation = '"';

        ImmoWeltParser ip = new ImmoWeltParser();
        while (i<20) {
            Connection con = Jsoup.connect(URLMieten);
            con.data("sp", String.valueOf(i) );
            //con.newRequest();
            Document doc = con.get();

            if (doc.getElementsByClass(SiteId).isEmpty()){
                break;
            }
            else {
                extractItems(doc);
                i++;
            }

        }
    }

    public void extractItems (Document doc) throws MalformedURLException {

        Elements items = doc.getElementsByClass(SiteId);
        //System.out.println(items.size());
        for(Element item : items) {
            ImmoItem row = new ImmoItem();
            row.SiteName = SiteName;
            row.SiteID = extractID(item);
            //System.out.println(row.SiteID);
            row.UrlItem = extractURL(item);
            row.Address = extractAddress(item);
            row.District = extractDistrict(row.Address);
            row.Address = cleanAddress(row.Address, row.District);
            row.Realtor = extractRealtor(item);
            row.Price = extractPrise(item);
            row.Area = extractArea(item);
            row.Rooms = extractRooms(item);
            ii.add(row);
            //System.out.println(ii.size());
        }
    }
    public String cleanAddress (String Address, String District) {
        String ShortAddress = Address.replace("location", "");
        return ShortAddress.replace("("+District+")", "");
    }
    public String extractDistrict (String Adress) {
        int start = Adress.indexOf("(")+1;
        int end = Adress.indexOf(")");
        return Adress.substring(start,end);
    }
    public String extractArea (Element ElementWithArea) {
        Element Area = ElementWithArea.getElementsByClass(ClassOfPrice).select("div[data-test=\"area\"]").first();
        String qm = Area.text();
        qm = qm.replace("m²", "");
        qm = qm.replace(".",",");
        return qm;
    }
    public String extractRooms (Element ElementWithRooms) {
        Element Rooms = ElementWithRooms.getElementsByClass(ClassOfPrice).select("div[data-test=\"rooms\"]").first();
        String zi = Rooms.text();
        zi = zi.replace("Zi.","");
        zi = zi.replace(".",",");
        return zi;
    }
    public String extractID (Element id) {
        Element idElement = id.getElementsByTag("a").first();
        return idElement.id();
    }
    public URL extractURL (Element URLItem) throws MalformedURLException {
        URL u;
        Element urlElement = URLItem.getElementsByTag("a").first();
        u = new URL(urlElement.attr("href"));
        return u;
    }
    public String extractAddress (Element AddressElement){
        Element Address = AddressElement.getElementsByClass(ClassOfAddress).first();
        return Address.text();
    }
    public String extractRealtor (Element RealtorElement){
        Element Realtor = RealtorElement.getElementsByClass(ClassOfRealtor).first();
        return Realtor.text();
    }
    public String extractPrise (Element PriseElement) {
        Element Prise = PriseElement.getElementsByClass(ClassOfPrice).select("div[data-test=\"price\"]").first();
        String pr = Prise.text();
        return pr.replace("€","");
    }
    public void Result (){
        ImmoItem immo;
        ListIterator <ImmoItem> li = ii.listIterator();
        while (li.hasNext()) {
            immo = li.next();
            System.out.println(immo.toCSV());
        }

    }
    public void toFile (String FileName){
        ImmoItem immo;
        ListIterator<ImmoItem> listImmo = ii.listIterator();

        try {
            FileWriter fw = new FileWriter(FileName);
            while (listImmo.hasNext()) {
                immo = listImmo.next();
                System.out.println(immo.toCSV());
                fw.append(immo.toCSV());
            }
            fw.close();
        }
        catch (Exception e){
            System.err.println(e);
        }

    }

}
