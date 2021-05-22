package Entity;

import javax.persistence.*;

@Entity
@NamedQuery(name="Group.findAllGroups",
            query="SELECT Group FROM Group Group")
@Table(name = "DS_GROUPS", schema = "STUDENT")
public class Group {

    private String name;

    public Group() {
    }

    public Group(String name) {
        this.name = name;
    }

    @Id
    @Column(name = "NAME")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Group that = (Group) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}
