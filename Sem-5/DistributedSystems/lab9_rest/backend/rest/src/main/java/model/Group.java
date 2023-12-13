package model;

import java.io.Serializable;

public class Group implements Serializable {
    public String name;
    public Integer id;

    public Group(String name, Integer id) {
        this.name = name;
        this.id = id;
    }
    public Group(String name) {
        this.name = name;
    }
    @Override
    public String toString() {
        return "Group{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}