package Entity;

import javax.persistence.*;

@Entity
@Table(name = "DS_GROUPS_STUDENTS", schema = "STUDENT")
@IdClass(GroupStudentPK.class)
public class GroupStudent {

    private String groupname;
    private String student;

    public GroupStudent(String groupname, String student) {
        this.groupname = groupname;
        this.student = student;
    }

    public GroupStudent() {
    }

    @Id
    @Column(name = "GROUPNAME")
    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    @Id
    @Column(name = "STUDENT")
    public String getStudent() {
        return student;
    }

    public void setStudent(String student) {
        this.student = student;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GroupStudent that = (GroupStudent) o;

        if (groupname != null ? !groupname.equals(that.groupname) : that.groupname != null) return false;
        if (student != null ? !student.equals(that.student) : that.student != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = groupname != null ? groupname.hashCode() : 0;
        result = 31 * result + (student != null ? student.hashCode() : 0);
        return result;
    }
}
