package kz.project.carrental.entity;

public class UserAccess extends Entity {
    //TODO jUnit
    private User user;
    private Access access;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Access getAccess() {
        return access;
    }

    public void setAccess(Access access) {
        this.access = access;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserAccess)) return false;

        UserAccess that = (UserAccess) o;

        if (access != null ? !access.equals(that.access) : that.access != null) return false;
        if (user != null ? !user.equals(that.user) : that.user != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = user != null ? user.hashCode() : 0;
        result = 31 * result + (access != null ? access.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("UserAccess");
        sb.append("{user=").append(user);
        sb.append(", access=").append(access);
        sb.append('}');
        return sb.toString();
    }
}
