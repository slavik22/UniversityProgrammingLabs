package object;

import java.io.Serializable;

public class MyObject implements Serializable {
    private String data;

    public MyObject(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }

    @Override
    public String toString() {
        return "MyObject{" +
                "data='" + data + '\'' +
                '}';
    }
}
