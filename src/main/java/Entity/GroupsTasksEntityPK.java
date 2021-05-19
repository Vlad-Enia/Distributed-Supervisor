package Entity;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public class GroupsTasksEntityPK implements Serializable {
    private String groupname;
    private String task;

    @Column(name = "GROUPNAME")
    @Id
    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    @Column(name = "TASK")
    @Id
    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GroupsTasksEntityPK that = (GroupsTasksEntityPK) o;

        if (groupname != null ? !groupname.equals(that.groupname) : that.groupname != null) return false;
        if (task != null ? !task.equals(that.task) : that.task != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = groupname != null ? groupname.hashCode() : 0;
        result = 31 * result + (task != null ? task.hashCode() : 0);
        return result;
    }
}
