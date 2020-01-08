package sk.kubo.school.model;

import java.util.HashSet;
import java.util.Set;

public class SchoolClass {
    public static final int MIN_CLASS_NUMBER_OF_STUDENTS = 3;

    private final String name;
    private final Teacher teacher;
    private final Set<Student> students;

    public SchoolClass(String name, Teacher teacher, Set<Student> students) {
        if (students.size() < MIN_CLASS_NUMBER_OF_STUDENTS) {
            throw new IllegalArgumentException("Cannot establish class with less than " + MIN_CLASS_NUMBER_OF_STUDENTS + " student(s).");
        }
        this.name = name;
        this.teacher = teacher;
        this.students = new HashSet<>();
        students.forEach(this::assignStudent);
    }

    public void assignStudent(Student student) {
        students.add(student);
        student.assignClass(this);
    }

    public boolean containsStudent(Student student) {
        return students.contains(student);
    }

    public String getName() {
        return name;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public Set<Student> getStudents() {
        return new HashSet<>(students);
    }
}
