package kz.project.carrental.entity;

public class Vendor extends Entity {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vendor)) return false;
        if (!super.equals(o)) return false;

        Vendor vendor = (Vendor) o;

        if (name != null ? !name.equals(vendor.name) : vendor.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Vendor");
        sb.append("{name='").append(name).append('\'');
        sb.append('}');
        return sb.toString();
    }


}
