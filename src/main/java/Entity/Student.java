package Entity;

import javax.persistence.*;

@Entity
@Table(name = "DS_STUDENTS", schema = "STUDENT")
public class Student {
    private String username;

    public Student(){
    }

    public Student(String name) {
        this.username=name;
    }

    @Id
    @Column(name = "USERNAME")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Student that = (Student) o;

        if (username != null ? !username.equals(that.username) : that.username != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return username != null ? username.hashCode() : 0;
    }
}
