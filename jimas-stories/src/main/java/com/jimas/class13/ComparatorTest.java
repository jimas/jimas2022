package com.jimas.class13;

import org.junit.Test;

import java.util.Arrays;
import java.util.Comparator;

/**
 * @author liuqj
 */
public class ComparatorTest {

    @Test
    public void testIdAscSort() {
        final Student s1 = new Student(1, "jimas", 31);
        final Student s2 = new Student(2, "shan", 29);
        final Student s3 = new Student(3, "janet", 8);
        final Student s4 = new Student(4, "eleven", 1);
        final Student[] students = new Student[]{s1, s2, s3, s4};
        Arrays.sort(students, new AgeAscAndIdDesc());
        for (Student student : students) {
            System.out.println(student);
        }
    }
}

class IdAsc implements Comparator<Student> {
    @Override
    public int compare(Student o1, Student o2) {
        return o1.getId() - o2.getId();
    }
}

class IdDesc implements Comparator<Student> {
    @Override
    public int compare(Student o1, Student o2) {
        return o2.getId() - o1.getId();
    }
}

class AgeAscAndIdDesc implements Comparator<Student> {
    @Override
    public int compare(Student o1, Student o2) {
        return o1.getAge().equals(o2.getAge()) ? (o2.getId() - o1.getId()) : o1.getAge() - o2.getAge();
    }
}

class Student {
    private Integer id;
    private String name;
    private Integer age;

    public Student(Integer id, String name, Integer age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
