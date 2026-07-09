package Creational.BuilderDesignPattern;

import java.util.List;

/*
Main Course (e.g., Burger, Pizza, Salad)
Side Dish (e.g., Fries, Garlic Bread, Fruit Cup)
Drink (e.g., Soda, Juice, Water)
Dessert (optional)
Special Instructions (e.g., "No onions", "Extra cheese")

Requirements
Create a Meal class with all the above fields.
Implement a MealBuilder class that allows step-by-step construction of a Meal object.
Ensure the builder supports method chaining.
Add a build() method that returns the final Meal object.
Demonstrate usage with a few sample meal orders.
 */
public class MealOrderSystem {

    public static void main(String[] args) {

        MealMenu meal1 = new MealMenu.MealBuilder()
                .setMainCourses(List.of("Burger"," Pizza","Salad"))
                .setSideDishes(List.of("Fries"," Garlic Bread","Fruit Cup"))
                .setDrinks(List.of("Soda"," Juice","Water"))
                .setDesserts(List.of("Ice Cream"," Cake"))
                .setSpecialInstructions(List.of("No onions", "Extra cheese"))
                .build();

        System.out.println(meal1.getMainCourses());;
        System.out.println(meal1.getSideDishes());
        System.out.println(meal1.getDrinks());
        System.out.println(meal1.getDesserts());
        System.out.println(meal1.getSpecialInstructions());
    }
}
