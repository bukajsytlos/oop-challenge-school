package sk.kubo.school.model;

import java.util.Objects;

public class Subject {
    private final String name;
    private final Teacher teacher;

    public Subject(String name, Teacher teacher) {
        this.name = name;
        this.teacher = teacher;
    }

    public String getName() {
        return name;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Subject)) return false;
        Subject subject = (Subject) o;
        return getName().equals(subject.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }
}
