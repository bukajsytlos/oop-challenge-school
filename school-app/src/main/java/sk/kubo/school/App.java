package sk.kubo.school;

import java.sql.SQLOutput;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import sk.kubo.school.model.Grade;
import sk.kubo.school.model.School;
import sk.kubo.school.model.SchoolClass;
import sk.kubo.school.model.Student;
import sk.kubo.school.model.Subject;
import sk.kubo.school.model.Teacher;

public class App {
    public static void main(String[] args) {
        final Student student1 = new Student("Student 1");
        final Student student2 = new Student("Student 2");
        final Student student3 = new Student("Student 3");
        final Student student4 = new Student("Student 4");
        final Student student5 = new Student("Student 5");
        final Student student6 = new Student("Student 6");

        final Teacher teacher1 = new Teacher("Teacher 1");
        final Teacher teacher2 = new Teacher("Teacher 2");

        final SchoolClass schoolClass1 = new SchoolClass("Class 1", teacher1, Set.of(student1, student2, student3));
        final SchoolClass schoolClass2 = new SchoolClass("Class 2", teacher2, Set.of(student4, student5, student6));

        final School school = new School(Set.of(schoolClass1, schoolClass2));

        final Teacher teacher3 = school.hireTeacher("Teacher 3");

        final Subject subject1 = school.createSubject("Subject 1", teacher1);
        final Subject subject2 = school.createSubject("Subject 2", teacher2);
        final Subject subject3 = school.createSubject("Subject 3", teacher3);
        final Subject subject4 = school.createSubject("Subject 4", teacher1);
        final Subject subject5 = school.createSubject("Subject 5", teacher2);
        final Subject subject6 = school.createSubject("Subject 6", teacher3);

        school.assignStudentToSubject(student1, subject1);
        school.giveGrade(teacher1, subject1, student1, Grade._1);
        school.assignStudentToSubject(student1, subject2);
        school.giveGrade(teacher2, subject2, student1, Grade._2);
        school.assignStudentToSubject(student2, subject2);
        school.giveGrade(teacher2, subject2, student2, Grade._3);
        school.assignStudentToSubject(student2, subject3);
        school.giveGrade(teacher3, subject3, student2, Grade._4);
        school.assignStudentToSubject(student3, subject3);
        school.giveGrade(teacher3, subject3, student3, Grade._5);
        school.assignStudentToSubject(student3, subject4);
        school.giveGrade(teacher1, subject4, student3, Grade._1);
        school.assignStudentToSubject(student4, subject4);
        school.giveGrade(teacher1, subject4, student4, Grade._2);
        school.assignStudentToSubject(student4, subject5);
        school.giveGrade(teacher2, subject5, student4, Grade._3);
        school.assignStudentToSubject(student5, subject5);
        school.giveGrade(teacher2, subject5, student5, Grade._4);
        school.assignStudentToSubject(student5, subject6);
        school.giveGrade(teacher3, subject6, student5, Grade._5);
        school.assignStudentToSubject(student6, subject6);
        school.giveGrade(teacher3, subject6, student6, Grade._1);
        school.assignStudentToSubject(student6, subject1);
        school.giveGrade(teacher1, subject1, student6, Grade._2);

        final Map<Student, Double> averageGradeByStudent = school.calculateAverageGradeByStudents();
        System.out.println("=== Best student's score===");
        averageGradeByStudent.entrySet().stream()
            .sorted(Map.Entry.<Student, Double>comparingByValue().thenComparing(entry -> entry.getKey().getName()))
            .forEach(studentAverageGradeEntry -> System.out.println(studentAverageGradeEntry.getKey().getName() + " - " + studentAverageGradeEntry.getValue()));

        final Map<Subject, Double> averageGradeBySubject = school.calculateAverageGradeBySubjects();
        System.out.println("=== Best subject's score ===");
        averageGradeBySubject.entrySet().stream()
            .sorted(Map.Entry.<Subject, Double>comparingByValue().thenComparing(entry -> entry.getKey().getName()))
            .forEach(subjectAverageGradeEntry -> System.out.println(subjectAverageGradeEntry.getKey().getName() + " - " + subjectAverageGradeEntry.getValue()));

        final Map<SchoolClass, Double> averageGradeBySchoolClass = school.calculateAverageGradeBySchoolClasses();
        System.out.println("=== Best class's score ===");
        averageGradeBySchoolClass.entrySet().stream()
            .sorted(Map.Entry.<SchoolClass, Double>comparingByValue().thenComparing(entry -> entry.getKey().getName()))
            .forEach(classAverageGradeEntry -> System.out.println(classAverageGradeEntry.getKey().getName() + " - " + classAverageGradeEntry.getValue()));
    }
}
