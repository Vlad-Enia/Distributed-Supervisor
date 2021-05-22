package Entity;

import javax.persistence.*;

@Entity
@NamedQuery(name="Grade.findByGroup",
            query="SELECT Grade FROM Grade Grade JOIN GroupStudent GroupStudent ON Grade.student=GroupStudent.student WHERE GroupStudent.groupname LIKE :gr ORDER BY Grade.task,Grade.student")
@Table(name = "DS_GRADES", schema = "STUDENT")
@IdClass(GradePK.class)
public class Grade {
    private String task;
    private String student;
    private Double grade;
    /*  Test la Java漢Robert漢8.25  */
    public Grade() {
    }

    public Grade(String task, String student, Double grade) {
        this.task = task;
        this.student = student;
        this.grade = grade;
    }

    @Id
    @Column(name = "TASK")
    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    @Id
    @Column(name = "STUDENT")
    public String getStudent() {
        return student;
    }

    public void setStudent(String student) {
        this.student = student;
    }

    @Basic
    @Column(name = "GRADE")
    public Double getGrade() {
        return grade;
    }

    public void setGrade(Double grade) {
        this.grade = grade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Grade that = (Grade) o;

        if (task != null ? !task.equals(that.task) : that.task != null) return false;
        if (student != null ? !student.equals(that.student) : that.student != null) return false;
        if (grade != null ? !grade.equals(that.grade) : that.grade != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = task != null ? task.hashCode() : 0;
        result = 31 * result + (student != null ? student.hashCode() : 0);
        result = 31 * result + (grade != null ? grade.hashCode() : 0);
        return result;
    }
}
