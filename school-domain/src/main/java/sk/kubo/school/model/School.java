package sk.kubo.school.model;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class School {
    public static final int MIN_CLASS_COUNT = 2;
    private final Set<SchoolClass> schoolClasses = new HashSet<>();
    private final Set<Teacher> teachers = new HashSet<>();
    private final Set<Student> students = new HashSet<>();
    private final Set<Subject> subjects = new HashSet<>();
    private final Set<StudentSubject> studentSubjects = new HashSet<>();

    public School(Set<SchoolClass> schoolClasses) {
        if (schoolClasses.size() < MIN_CLASS_COUNT) {
            throw new IllegalArgumentException("Cannot create school with less than " + MIN_CLASS_COUNT + " classes");
        }
        schoolClasses.forEach(schoolClass -> {
            final Set<Student> students = schoolClass.getStudents().stream().map(student -> new Student(student.getName())).collect(Collectors.toSet());
            establishSchoolClass(schoolClass.getName(), schoolClass.getTeacher(), students);
        });
    }

    public Teacher hireTeacher(String name) {
        final Teacher teacher = new Teacher(name);
        teachers.add(teacher);
        return teacher;
    }

    public Student acceptStudent(String name) {
        final Student student = new Student(name);
        students.add(student);
        return student;
    }

    public Subject createSubject(String name, Teacher teacher) {
        final Subject subject = new Subject(name, teacher);
        subjects.add(subject);
        return subject;
    }

    public void establishSchoolClass(String name, Teacher teacher, Set<Student> students) {
        final SchoolClass newSchoolClass = new SchoolClass(name, teacher, students);
        schoolClasses.add(newSchoolClass);
    }

    public void assignStudentToSchoolClass(Student student, SchoolClass schoolClass) {
        schoolClass.assignStudent(student);
    }

    public void assignStudentToSubject(Student student, Subject subject) {
        final StudentSubject studentSubject = new StudentSubject(student, subject);
        studentSubjects.add(studentSubject);
    }

    public void giveGrade(Teacher teacher, Subject subject, Student student, Grade grade) {
        final StudentSubject studentSubjectToFind = new StudentSubject(student, subject);
        final StudentSubject studentSubject = studentSubjects.stream()
            .filter(studentSubjectEntry -> studentSubjectEntry.equals(studentSubjectToFind))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Cannot give grade to student[" + student.getName() + "] since he/she is not attending subject [" + subject.getName() + "]"));
        teacher.giveGrade(studentSubject, grade);
    }

    public Map<Student, Double> calculateAverageGradeByStudents() {
        return studentSubjects.stream()
            .collect(Collectors.groupingBy(StudentSubject::getStudent, Collectors.averagingInt(studentSubject -> studentSubject.getGrade().getScore())));
    }

    public Map<Subject, Double> calculateAverageGradeBySubjects() {
        return studentSubjects.stream()
            .collect(Collectors.groupingBy(StudentSubject::getSubject, Collectors.averagingInt(studentSubject -> studentSubject.getGrade().getScore())));
    }

    public Map<SchoolClass, Double> calculateAverageGradeBySchoolClasses() {
        return studentSubjects.stream()
            .collect(Collectors.groupingBy(studentSubject -> studentSubject.getStudent().getSchoolClass(), Collectors.averagingInt(studentSubject -> studentSubject.getGrade().getScore())));
    }
}
