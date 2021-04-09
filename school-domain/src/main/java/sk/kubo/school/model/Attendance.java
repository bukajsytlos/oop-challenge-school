package sk.kubo.school.model;

import java.util.Objects;

public class Attendance {
    private final Student student;
    private final Subject subject;
    private Grade grade;

    public Attendance(Student student, Subject subject) {
        this.student = student;
        this.subject = subject;
    }

    public Attendance(Student student, Subject subject, Grade grade) {
        this.student = student;
        this.subject = subject;
        this.grade = grade;
    }

    public void giveGrade(Teacher teacher, Grade grade) {
        if (!subject.getTeacher().equals(teacher)) {
            throw new IllegalArgumentException("Teacher [" + teacher.getName() + "] cannot give grade on subject [" + subject.getName() + "] not being teach by.");
        }
        this.grade = grade;
    }

    public Student getStudent() {
        return student;
    }

    public Subject getSubject() {
        return subject;
    }

    public boolean hasBeenGraded() {
        return grade != null;
    }

    public Grade getGrade() {
        if (grade == null) {
            throw new IllegalStateException("Could not calculate average grade as student [" + student.getName() + "] has not been graded on subject [" + subject.getName() + "]");
        }
        return grade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Attendance)) return false;
        Attendance that = (Attendance) o;
        return student.equals(that.student) &&
            subject.equals(that.subject);
    }

    @Override
    public int hashCode() {
        return Objects.hash(student, subject);
    }
}
