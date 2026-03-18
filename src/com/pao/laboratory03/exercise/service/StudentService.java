package com.pao.laboratory03.exercise.service;

import com.pao.laboratory03.exercise.model.Student;
import com.pao.laboratory03.exercise.model.Subject;
import com.pao.laboratory03.exercise.exception.StudentNotFoundException;

import java.util.*;

public class StudentService {
    private static StudentService instance;
    private final List<Student> students;

    private StudentService() {
        students = new ArrayList<>();
    }

    public static StudentService getInstance() {
        if (instance == null) {
            instance = new StudentService();
        }
        return instance;
    }

    public void addStudent(String name, int age) {
        // Check if student with same name already exists
        boolean exists = students.stream()
                .anyMatch(s -> s.getName().equalsIgnoreCase(name));
        if (exists) {
            throw new RuntimeException("Există deja un student cu numele " + name);
        }
        Student student = new Student(name, age);
        students.add(student);
    }

    public Student findByName(String name) {
        return students.stream()
                .filter(s -> s.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElseThrow(() -> new StudentNotFoundException("Studentul " + name + " nu a fost găsit"));
    }

    public void addGrade(String studentName, Subject subject, double grade) {
        Student student = findByName(studentName);
        student.addGrade(subject, grade);
    }

    public void printAllStudents() {
        if (students.isEmpty()) {
            System.out.println("Nu există studenți înregistrați.");
            return;
        }
        for (Student student : students) {
            System.out.println(student);
            System.out.println("  Note:");
            Map<Subject, Double> grades = student.getGrades();
            if (grades.isEmpty()) {
                System.out.println("    (nu are note)");
            } else {
                for (Map.Entry<Subject, Double> entry : grades.entrySet()) {
                    System.out.println("    " + entry.getKey().name() + ": " + entry.getValue());
                }
            }
        }
    }

    public void printTopStudents() {
        if (students.isEmpty()) {
            System.out.println("Nu există studenți înregistrați.");
            return;
        }

        List<Student> sortedStudents = new ArrayList<>(students);
        sortedStudents.sort((s1, s2) -> Double.compare(s2.getAverage(), s1.getAverage()));

        System.out.println("=== Top studenți (după medie) ===");
        for (int i = 0; i < sortedStudents.size(); i++) {
            Student s = sortedStudents.get(i);
            System.out.println((i + 1) + ". " + s.getName() + " - Medie: " + s.getAverage());
        }
    }

    public Map<Subject, Double> getAveragePerSubject() {
        Map<Subject, List<Double>> subjectGrades = new HashMap<>();

        // Collect all grades per subject
        for (Student student : students) {
            for (Map.Entry<Subject, Double> entry : student.getGrades().entrySet()) {
                Subject subject = entry.getKey();
                Double grade = entry.getValue();

                subjectGrades.computeIfAbsent(subject, k -> new ArrayList<>()).add(grade);
            }
        }

        // Calculate average for each subject
        Map<Subject, Double> averages = new HashMap<>();
        for (Map.Entry<Subject, List<Double>> entry : subjectGrades.entrySet()) {
            List<Double> grades = entry.getValue();
            double sum = 0.0;
            for (Double grade : grades) {
                sum += grade;
            }
            double avg = Math.round((sum / grades.size()) * 100.0) / 100.0;
            averages.put(entry.getKey(), avg);
        }

        return averages;
    }
}