package pkgDatamanager;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

import AsyncTasks.AsyncMeals;
import bsd.holidayout.Meal;
public  class DatamanagerMeals {

    private static DatamanagerMeals dm = null;

    private List<Meal> listMeal = null;
    private HashMap<Integer, Meal> mealsOrdered = new HashMap<Integer,Meal>();

    private DatamanagerMeals() {
        listMeal = getAll();
    }

    private List<Meal> getAll() {
        try {
            return new AsyncMeals().execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static DatamanagerMeals getInstance() {
        if(dm == null) {
            dm = new DatamanagerMeals();
        }
        return dm;
    }


    public void loadMeals() {
        listMeal = getAll();
    }

    public List<Meal> getNeededMeals(int i) {
        return Stream.of(listMeal).filter(m -> m.getMealType()==i).collect(Collectors.toList());
    }



    public void addOrderToMeal(Meal m)
    {
        System.out.println("about to be insertedxxx");
        System.out.println(m.getMealType()+" "+m+"jjjjjajaj");
        mealsOrdered.put(m.getMealType(), m);
    }

    public HashMap<Integer, Meal> getMealsOrdered() {
        return mealsOrdered;
    }

    public String getSumFromOrderAsString() {
        double sum = 0;
        for (Integer name: getMealsOrdered().keySet()){
            double price = getMealsOrdered().get(name).getPrice();
            sum+=price;
        }
        return String.valueOf(sum);
    }


    public void skipMeal(int i) {
        mealsOrdered.remove(i);
    }

    public List<Integer> getIDsFromOrders() {
        List<Integer> ret = new ArrayList<Integer>();
        for (Integer name: getMealsOrdered().keySet()){
            ret.add(getMealsOrdered().get(name).getId());
        }
        return ret;
    }
}
