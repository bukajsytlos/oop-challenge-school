package sk.kubo.school.model;

import java.util.Objects;

public class Student {
    private final String name;
    private SchoolClass schoolClass;

    public Student(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public boolean hasAssignedClass() {
        return schoolClass != null;
    }

    public SchoolClass getSchoolClass() {
        if (this.schoolClass == null) {
            throw new IllegalStateException("Student [" + getName() + "] has not assigned class");
        }
        return schoolClass;
    }

    public void assignClass(SchoolClass schoolClass) {
        if (this.schoolClass != null) {
            throw new IllegalArgumentException("Student [" + getName() + "] has already assigned class");
        }
        this.schoolClass = schoolClass;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student)) return false;
        Student student = (Student) o;
        return getName().equals(student.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }
}
