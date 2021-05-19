package Entity;

import javax.persistence.*;

@Entity
@Table(name = "DS_GROUPS_PROFESSORS", schema = "STUDENT")
@IdClass(GroupProfessorPK.class)
public class GroupProfessor {

    private String groupname;
    private String professor;


    @Id
    @Column(name = "GROUPNAME")
    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    @Id
    @Column(name = "PROFESSOR")
    public String getProfessor() {
        return professor;
    }

    public void setProfessor(String professor) {
        this.professor = professor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GroupProfessor that = (GroupProfessor) o;

        if (groupname != null ? !groupname.equals(that.groupname) : that.groupname != null) return false;
        if (professor != null ? !professor.equals(that.professor) : that.professor != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = groupname != null ? groupname.hashCode() : 0;
        result = 31 * result + (professor != null ? professor.hashCode() : 0);
        return result;
    }
}
