package Entity;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public class GroupStudentPK implements Serializable {
    private String groupname;
    private String student;

    public GroupStudentPK() {
    }

    public GroupStudentPK(String groupname, String student) {
        this.groupname = groupname;
        this.student = student;
    }

    @Column(name = "GROUPNAME")
    @Id
    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    @Column(name = "STUDENT")
    @Id
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

        GroupStudentPK that = (GroupStudentPK) o;

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
