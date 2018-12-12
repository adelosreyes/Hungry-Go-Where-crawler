package ai.preferred.crawler.hungryGoWhereV2.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class Business {

    public static List<Object> getHeader() {
        final List<Object> result = new ArrayList<>();
        for (final Field field : Business.class.getDeclaredFields()) {
            result.add(field.getName());
        }
        return result;
    }

    private String url;
    private String title;
    private String priceLevel;
    private String address;
    private String type;
    private String cuisine;
    private String summary;

    private String reservationNum;
    private String nearestMrt;
    private String openingHours;
    private String avgPrice;
    private String suitableFor;
    private String phoneNum;
    private String website;
    private String goodFor;


    public void setUrl(String url) {
        this.url = url;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPriceLevel(String priceLevel) {
        this.priceLevel = priceLevel;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setCuisine(String cuisine) {
        this.cuisine = cuisine;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setReservationNum(String reservationNum) {
        this.reservationNum = reservationNum;
    }

    public void setNearestMrt(String nearestMrt) {
        this.nearestMrt = nearestMrt;
    }

    public void setOpeningHours(String openingHours) {
        this.openingHours = openingHours;
    }

    public void setAvgPrice(String avgPrice) {
        this.avgPrice = avgPrice;
    }

    public void setSuitableFor(String suitableFor) {
        this.suitableFor = suitableFor;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public void setGoodFor(String goodFor) {
        this.goodFor = goodFor;
    }



    public String getUrl() {
        return url;
    }

    public String getTitle(){
        return title;
    }

    public String getPriceLevel(){
        return priceLevel;
    }

    public String getAddress(){
        return address;
    }

    public String getType(){
        return type;
    }

    public String getCuisine(){
        return cuisine;
    }

    public String getSummary(){
        return summary;
    }

    public String getReservationNum(){
        return reservationNum;
    }

    public String getNearestMrt(){
        return nearestMrt;
    }

    public String getOpeningHours(){
        return openingHours;
    }

    public String getAvgPrice(){
        return avgPrice;
    }

    public String getSuitableFor(){
        return suitableFor;
    }

    public String getPhoneNum(){
        return phoneNum;
    }

    public String getWebsite(){
        return website;
    }

    public String getGoodFor(){
        return goodFor;
    }

    public List<Object> asList() {
        try {
            final List<Object> result = new ArrayList<>();
            for (final Field field : getClass().getDeclaredFields()) {
                result.add(field.get(this));
            }
            return result;
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Unable to convert this entity to a list!", e);
        }
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
