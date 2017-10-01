package com.example.han.newtravel30.AR;

import java.util.ArrayList;

public class Touris {
    private String Data;
    private String Name;
    private String Title;
    private String Introduction;
    private String Address;
    private String Longitude;
    private String Latitude;
    private String ImageURL;
    private int Classification;

    public Touris(String name, String title, String introduction, String address,
                  String longitude, String latitude, String imageURL, int classification) {
        Name = name;
        Title = title;
        Introduction = introduction;
        Address = address;
        Longitude = longitude;
        Latitude = latitude;
        ImageURL = imageURL;
        Classification = classification;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getImageURL() {
        return ImageURL;
    }

    public void setImageURL(String imageURL) {
        ImageURL = imageURL;
    }

    public int getClassification() {
        return Classification;
    }

    public void setClassification(int classification) {
        Classification = classification;
    }

    public String getData() {
        return Data;
    }

    public void setData(String data) {
        Data = data;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getIntroduction() {
        return Introduction;
    }

    public void setIntroduction(String introduction) {
        Introduction = introduction;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
    }

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }

    public static ArrayList<Touris> copyList(ArrayList<Touris> source, int count) {
        ArrayList<Touris> list = new ArrayList<>();

        for(int i=0; i<count; i++) {
            Touris touris = new Touris(source.get(i).getName(), source.get(i).getTitle(), source.get(i).getIntroduction(),
                    source.get(i).getAddress(), "0", "0", source.get(i).getImageURL(), 0);
            list.add(touris);
        }

        return list;
    }

    public static void createEmptyEntry(ArrayList<Touris> source) {
        Touris touris = new Touris("", "", "", "", "0", "0", "", 0);
        source.add(touris);
    }
}
