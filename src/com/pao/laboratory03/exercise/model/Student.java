package com.pao.laboratory03.exercise.model;

import com.pao.laboratory03.exercise.exception.InvalidGradeException;
import com.pao.laboratory03.exercise.exception.InvalidStudentException;

import java.util.HashMap;
import java.util.Map;

public class Student {
    private final String name;
    private final int age;
    private final Map<Subject, Double> grades;

    public Student(String name, int age) {
        if (age < 18 || age > 60) {
            throw new InvalidStudentException("Vârsta trebuie să fie între 18 și 60 ani");
        }
        this.name = name;
        this.age = age;
        this.grades = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public Map<Subject, Double> getGrades() {
        return new HashMap<>(grades); // return a copy to preserve encapsulation
    }

    public void addGrade(Subject subject, double grade) {
        if (grade < 1 || grade > 10) {
            throw new InvalidGradeException("Nota trebuie să fie între 1 și 10");
        }
        grades.put(subject, grade);
    }

    public double getAverage() {
        if (grades.isEmpty()) {
            return 0.0;
        }
        double sum = 0.0;
        for (Double grade : grades.values()) {
            sum += grade;
        }
        return Math.round((sum / grades.size()) * 100.0) / 100.0;
    }

    @Override
    public String toString() {
        return "Student{name='" + name + "', age=" + age + ", avg=" + getAverage() + "}";
    }
}