package Creational.BuilderDesignPattern;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Immutable MealMenu class using Builder pattern.
 * Allows optional fields and provides defensive copies for safety.
 */
public class MealMenu {
    private final List<String> mainCourses;
    private final List<String> sideDishes;
    private final List<String> drinks;
    private final List<String> desserts;



    private final List<String> specialInstructions;

    private MealMenu(MealBuilder builder) {
        this.mainCourses = Collections.unmodifiableList(builder.mainCourses);
        this.sideDishes = Collections.unmodifiableList(builder.sideDishes);
        this.drinks = Collections.unmodifiableList(builder.drinks);
        this.desserts = Collections.unmodifiableList(builder.desserts);
        this.specialInstructions = Collections.unmodifiableList(builder.specialInstructions);
    }

    public List<String> getSpecialInstructions() {
        return specialInstructions;
    }
    /**
     * @return Unmodifiable list of drinks
     */
    public List<String> getDrinks() {
        return drinks;
    }

    /**
     * @return Unmodifiable list of main courses
     */
    public List<String> getMainCourses() {
        return mainCourses;
    }

    /**
     * @return Unmodifiable list of side dishes
     */
    public List<String> getSideDishes() {
        return sideDishes;
    }

    /**
     * @return Unmodifiable list of desserts
     */
    public List<String> getDesserts() {
        return desserts;
    }

    /**
     * Builder for MealMenu. Allows optional fields and validates main courses.
     */
    public static class MealBuilder {
        private List<String> mainCourses = Collections.emptyList();
        private List<String> sideDishes = Collections.emptyList();
        private List<String> drinks = Collections.emptyList();
        private List<String> desserts = Collections.emptyList();
        private List<String> specialInstructions = Collections.emptyList();

        public MealBuilder setSpecialInstructions(List<String> specialInstructions) {
            this.specialInstructions = specialInstructions;
            return this;
        }
        /**
         * Set main courses. Must not be null or empty.
         */
        public MealBuilder setMainCourses(List<String> mainCourses) {
            Objects.requireNonNull(mainCourses, "mainCourses cannot be null");
            this.mainCourses = mainCourses;
            return this;
        }

        /**
         * Set side dishes. Optional, defaults to empty list.
         */
        public MealBuilder setSideDishes(List<String> sideDishes) {
            this.sideDishes = sideDishes == null ? Collections.emptyList() : sideDishes;
            return this;
        }

        /**
         * Set drinks. Optional, defaults to empty list.
         */
        public MealBuilder setDrinks(List<String> drinks) {
            this.drinks = drinks == null ? Collections.emptyList() : drinks;
            return this;
        }

        /**
         * Set desserts. Optional, defaults to empty list.
         */
        public MealBuilder setDesserts(List<String> desserts) {
            this.desserts = desserts;
            return this;
        }

        /**
         * Build MealMenu. Validates at least one main course is present.
         */
        public MealMenu build() {
            if (mainCourses == null || mainCourses.isEmpty()) {
                throw new IllegalStateException("At least one main course is required.");
            }
            return new MealMenu(this);
        }
    }
}
