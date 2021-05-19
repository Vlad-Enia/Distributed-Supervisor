package Entity;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public class GradesEntityPK implements Serializable {
    private String task;
    private String student;

    @Column(name = "TASK")
    @Id
    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
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

        GradesEntityPK that = (GradesEntityPK) o;

        if (task != null ? !task.equals(that.task) : that.task != null) return false;
        if (student != null ? !student.equals(that.student) : that.student != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = task != null ? task.hashCode() : 0;
        result = 31 * result + (student != null ? student.hashCode() : 0);
        return result;
    }
}
