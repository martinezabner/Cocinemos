package Models;

public class Recipe {
    private String id;
    private String name;
    private String image;
    private String description;
    private String category;
    private int favourite;
    private int recommended;
    private String time;
    private String servings;
    private String numingredients;
    private String ingredients;
    private String preparation;

    public Recipe() {

    }

    public Recipe(String name, String image, String description, String category) {
        this.name = name;
        this.image = image;
        this.description = description;
        this.category = category;
    }

    public Recipe(String id, String name, String image, String description, String category, int favourite, int recommended, String time, String servings, String numingredients, String ingredients, String preparation) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.description = description;
        this.category = category;
        this.favourite = favourite;
        this.recommended = recommended;
        this.time = time;
        this.servings = servings;
        this.numingredients = numingredients;
        this.ingredients = ingredients;
        this.preparation = preparation;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getFavourite() {
        return favourite;
    }

    public void setFavourite(int favourite) {
        this.favourite = favourite;
    }

    public int getRecommended() {
        return recommended;
    }

    public void setRecommended(int recommended) {
        this.recommended = recommended;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getServings() {
        return servings;
    }

    public void setServings(String servings) {
        this.servings = servings;
    }

    public String getNumingredients() {
        return numingredients;
    }

    public void setNumingredients(String numingredients) {
        this.numingredients = numingredients;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getPreparation() {
        return preparation;
    }

    public void setPreparation(String preparation) {
        this.preparation = preparation;
    }
}
