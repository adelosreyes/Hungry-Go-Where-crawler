package ai.preferred.crawler.hungryGoWhere.entity;

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
    private String price;
    private String address;
    private String type;
    private String cuisine;


    public void setUrl(String url) {
        this.url = url;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPrice(String price) {
        this.price = price;
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

    public String getUrl() {
        return url;
    }

    public String getTitle() {
        return title;
    }

    public String getPrice() {
        return price;
    }

    public String getType() {
        return type;
    }

    public String getAddress() {
        return address;
    }

    public String getCuisine() { return cuisine;}

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
