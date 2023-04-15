package testData;

public class DataSerializationOrders {
    private String[] ingredients = new String[]{};

    public DataSerializationOrders(String[] ingredients) {
        this.ingredients = ingredients;
    }

    public String[] getIngredients() {
        return ingredients;
    }

    public void setIngredients(String[] ingredients) {
        this.ingredients = ingredients;
    }
}
