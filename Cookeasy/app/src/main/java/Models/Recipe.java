package Models;

public class Recipe {
    private String name;
    private String image;
    private String description;
    private String category;
    private int favourite;
    private int recommended;

    public Recipe() {

    }

    public Recipe(String name, String image, String description, String category) {
        this.name = name;
        this.image = image;
        this.description = description;
        this.category = category;
    }

    public Recipe(String name, String image, String description, String category, int favourite, int recommended) {
        this.name = name;
        this.image = image;
        this.description = description;
        this.category = category;
        this.favourite = favourite;
        this.recommended = recommended;
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
}
