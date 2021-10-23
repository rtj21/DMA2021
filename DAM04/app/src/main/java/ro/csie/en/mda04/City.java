package ro.csie.en.mda04;

import android.graphics.Bitmap;

public class City {
    private long code;
    private String name;
    private int population;
    private Bitmap logo;

    public City(long code, String name, int population) {
        this.code = code;
        this.name = name;
        this.population = population;
    }

    public void setLogo(Bitmap logo) {
        this.logo = logo;
    }

    public long getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public int getPopulation() {
        return population;
    }

    public Bitmap getLogo() {
        return logo;
    }

    @Override
    public String toString() {
        return "City{" +
                "code=" + code +
                ", name='" + name + '\'' +
                ", population=" + population +
                '}';
    }
}
