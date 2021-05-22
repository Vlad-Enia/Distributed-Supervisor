import Entity.Grade;
import Entity.Student;

import java.util.ArrayList;
import java.util.List;

public class GradeCustomObject {
     String task;
     List<String> student;
     List<Double> grade;

     public GradeCustomObject() {
          student=new ArrayList<>();
          grade=new ArrayList<>();
     }
}
