package model;

import java.io.Serializable;

public class Student implements Serializable {
    public int id;
    public String first_name;
    public String last_name;
    public Integer group_id;

    public Student(int id, String first_name, String last_name, Integer group_id) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.group_id = group_id;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", group=" + group_id +
                '}';
    }
}