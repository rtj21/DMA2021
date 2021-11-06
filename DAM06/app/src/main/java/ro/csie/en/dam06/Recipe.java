package ro.csie.en.dam06;

import androidx.annotation.Nullable;

public class Recipe
{
    private String denumire;
    private String descriere;
    private String dataAdaugarii;
    private int calorii;
    private String categorie;

    @Override
    public boolean equals(@Nullable Object obj) {
        Recipe recipe = (Recipe) obj;
        return (this.denumire.equals(recipe.denumire) && this.calorii == recipe.calorii);
    }

    public Recipe(String denumire, String descriere, String dataAdaugarii, int calorii, String categorie) {
        this.denumire = denumire;
        this.descriere = descriere;
        this.dataAdaugarii = dataAdaugarii;
        this.calorii = calorii;
        this.categorie = categorie;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "denumire='" + denumire + '\'' +
                ", descriere='" + descriere + '\'' +
                ", dataAdaugarii='" + dataAdaugarii + '\'' +
                ", calorii=" + calorii +
                ", categorie='" + categorie + '\'' +
                '}';
    }
}
