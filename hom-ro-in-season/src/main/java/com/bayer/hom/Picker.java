import java.util.Objects;

public class Picker {
    private String id;
    private String type;
    private double harvest_capacity;

    public Picker() {
    }

    public Picker(String id, String type, double harvest_capacity) {
        this.id = id;
        this.type = type;
        this.harvest_capacity = harvest_capacity;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getHarvest_capacity() {
        return this.harvest_capacity;
    }

    public void setHarvest_capacity(double harvest_capacity) {
        this.harvest_capacity = harvest_capacity;
    }

    public Picker id(String id) {
        this.id = id;
        return this;
    }

    public Picker type(String type) {
        this.type = type;
        return this;
    }

    public Picker harvest_capacity(double harvest_capacity) {
        this.harvest_capacity = harvest_capacity;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Picker)) {
            return false;
        }
        Picker picker = (Picker) o;
        return Objects.equals(id, picker.id) && Objects.equals(type, picker.type)
                && harvest_capacity == picker.harvest_capacity;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, harvest_capacity);
    }

    @Override
    public String toString() {
        return "{" + " id='" + getId() + "'" + ", type='" + getType() + "'" + ", harvest_capacity='"
                + getHarvest_capacity() + "'" + "}";
    }

}