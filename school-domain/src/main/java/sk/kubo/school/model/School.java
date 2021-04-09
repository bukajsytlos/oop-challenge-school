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
    private final Set<Attendance> attendances = new HashSet<>();

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

    public void registerStudentAttendance(Student student, Subject subject) {
        final Attendance attendance = new Attendance(student, subject);
        attendances.add(attendance);
    }

    public void giveGrade(Teacher teacher, Subject subject, Student student, Grade grade) {
        final Attendance attendanceToFind = new Attendance(student, subject);
        final Attendance attendance = attendances.stream()
            .filter(attendanceEntry -> attendanceEntry.equals(attendanceToFind))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Cannot give grade to student[" + student.getName() + "] since he/she is not attending subject [" + subject.getName() + "]"));
        teacher.giveGrade(attendance, grade);
    }

    public Map<Student, Double> calculateAverageGradeByStudents() {
        return attendances.stream()
            .collect(Collectors.groupingBy(Attendance::getStudent, Collectors.averagingInt(attendance -> attendance.getGrade().getScore())));
    }

    public Map<Subject, Double> calculateAverageGradeBySubjects() {
        return attendances.stream()
            .collect(Collectors.groupingBy(Attendance::getSubject, Collectors.averagingInt(attendance -> attendance.getGrade().getScore())));
    }

    public Map<SchoolClass, Double> calculateAverageGradeBySchoolClasses() {
        return attendances.stream()
            .collect(Collectors.groupingBy(attendance -> attendance.getStudent().getSchoolClass(), Collectors.averagingInt(attendance -> attendance.getGrade().getScore())));
    }
}
