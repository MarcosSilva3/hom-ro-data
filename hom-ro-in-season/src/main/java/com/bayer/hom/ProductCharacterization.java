package com.bayer.hom;

import java.util.Objects;

public class ProductCharacterization {
    private String name;
    private String highestHarvestMoisture;
    private String lowestHarvestMoisture;
    private String huskingDifficulty;
    private int lowest_rec;
    private int highest_rec;

    public ProductCharacterization() {
    }

    public ProductCharacterization(String name, String highestHarvestMoisture, String lowestHarvestMoisture,
            String huskingDifficulty, int lowest_rec, int highest_rec) {
        this.name = name;
        this.highestHarvestMoisture = highestHarvestMoisture;
        this.lowestHarvestMoisture = lowestHarvestMoisture;
        this.huskingDifficulty = huskingDifficulty;
        this.lowest_rec = lowest_rec;
        this.highest_rec = highest_rec;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHighestHarvestMoisture() {
        return this.highestHarvestMoisture;
    }

    public void setHighestHarvestMoisture(String highestHarvestMoisture) {
        this.highestHarvestMoisture = highestHarvestMoisture;
    }

    public String getLowestHarvestMoisture() {
        return this.lowestHarvestMoisture;
    }

    public void setLowestHarvestMoisture(String lowestHarvestMoisture) {
        this.lowestHarvestMoisture = lowestHarvestMoisture;
    }

    public String getHuskingDifficulty() {
        return this.huskingDifficulty;
    }

    public void setHuskingDifficulty(String huskingDifficulty) {
        this.huskingDifficulty = huskingDifficulty;
    }

    public int getLowest_rec() {
        return this.lowest_rec;
    }

    public void setLowest_rec(int lowest_rec) {
        this.lowest_rec = lowest_rec;
    }

    public int getHighest_rec() {
        return this.highest_rec;
    }

    public void setHighest_rec(int highest_rec) {
        this.highest_rec = highest_rec;
    }

    public ProductCharacterization name(String name) {
        this.name = name;
        return this;
    }

    public ProductCharacterization highestHarvestMoisture(String highestHarvestMoisture) {
        this.highestHarvestMoisture = highestHarvestMoisture;
        return this;
    }

    public ProductCharacterization lowestHarvestMoisture(String lowestHarvestMoisture) {
        this.lowestHarvestMoisture = lowestHarvestMoisture;
        return this;
    }

    public ProductCharacterization huskingDifficulty(String huskingDifficulty) {
        this.huskingDifficulty = huskingDifficulty;
        return this;
    }

    public ProductCharacterization lowest_rec(int lowest_rec) {
        this.lowest_rec = lowest_rec;
        return this;
    }

    public ProductCharacterization highest_rec(int highest_rec) {
        this.highest_rec = highest_rec;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof ProductCharacterization)) {
            return false;
        }
        ProductCharacterization productCharacterization = (ProductCharacterization) o;
        return Objects.equals(name, productCharacterization.name)
                && Objects.equals(highestHarvestMoisture, productCharacterization.highestHarvestMoisture)
                && Objects.equals(lowestHarvestMoisture, productCharacterization.lowestHarvestMoisture)
                && Objects.equals(huskingDifficulty, productCharacterization.huskingDifficulty)
                && lowest_rec == productCharacterization.lowest_rec
                && highest_rec == productCharacterization.highest_rec;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, highestHarvestMoisture, lowestHarvestMoisture, huskingDifficulty, lowest_rec,
                highest_rec);
    }

    @Override
    public String toString() {
        return "{" + " name='" + getName() + "'" + ", highestHarvestMoisture='" + getHighestHarvestMoisture() + "'"
                + ", lowestHarvestMoisture='" + getLowestHarvestMoisture() + "'" + ", huskingDifficulty='"
                + getHuskingDifficulty() + "'" + ", lowest_rec='" + getLowest_rec() + "'" + ", highest_rec='"
                + getHighest_rec() + "'" + "}";
    }

}