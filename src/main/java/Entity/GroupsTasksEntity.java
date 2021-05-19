package Entity;

import javax.persistence.*;

@Entity
@Table(name = "DS_GROUPS_TASKS", schema = "STUDENT")
@IdClass(GroupsTasksEntityPK.class)
public class GroupsTasksEntity {
    private String groupname;
    private String task;


    @Id
    @Column(name = "GROUPNAME")
    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    @Id
    @Column(name = "TASK")
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

        GroupsTasksEntity that = (GroupsTasksEntity) o;

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
