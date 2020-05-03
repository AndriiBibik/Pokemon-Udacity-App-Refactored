package udacity.pokemon;

public class Pokemon {

    private String name;
    private int id;
    private String candy;
    private String image;

    public Pokemon(String name, int id, String candy, String image) {
        this.name = name;
        this.id = id;
        this.candy = candy;
        this.image = image;
    }

    public String getName() { return name; }
    public int getId() { return id; }
    public String getCandy() { return candy; }
    public String getImage() { return image; }

}
