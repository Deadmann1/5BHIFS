package bsd.holidayout;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Meal {

    private int id;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private String name;
    private Date time;
    private int mealType;
    private double price;

    public Meal() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        android.text.format.DateFormat df = new android.text.format.DateFormat();
        return df.format("dd.MMM.yyyy", time).toString();
    }

    public void setTime(String checkin) {
        String dtStart = checkin;
        SimpleDateFormat format = new SimpleDateFormat("dd.MMM.yyyy");
        try {
            Date date = format.parse(dtStart);
            time = date;
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public int getMealType() {
        return mealType;
    }

    public void setMealType(int mealType) {
        this.mealType = mealType;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return name+" "+price;
    }
}