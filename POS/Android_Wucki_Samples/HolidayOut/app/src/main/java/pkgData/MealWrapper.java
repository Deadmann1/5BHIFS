package pkgData;

import java.util.ArrayList;
import java.util.List;

import bsd.holidayout.Meal;



public class MealWrapper {
    private List<Integer> listOfMeals;
    private String username;

    public MealWrapper() {
        listOfMeals = new ArrayList<Integer>();
    }

    public MealWrapper(List<Integer> listOfMeals, String username) {
        this.listOfMeals = listOfMeals;
        this.username = username;
    }

    public List<Integer> getListOfMeals() {
        return listOfMeals;
    }

    public void setListOfMeals(List<Integer> listOfMeals) {
        this.listOfMeals = listOfMeals;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}