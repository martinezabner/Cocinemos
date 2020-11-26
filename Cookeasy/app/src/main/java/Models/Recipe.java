package Models;

public class Recipe {
    private String name;
    private String image;
    private String description;
    private String category;

    public Recipe() {

    }

    public Recipe(String name, String image, String description, String category) {
        this.name = name;
        this.image = image;
        this.description = description;
        this.category = category;
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
}
