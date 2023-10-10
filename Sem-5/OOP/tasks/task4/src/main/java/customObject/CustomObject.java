package customObject;

import java.util.List;

public class CustomObject {

    {
        int a = 2;
    }
    static String name;

    private List<Thread> threads;

    public CustomObject( List<Thread> threads) {
        this.threads = threads;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Thread> getThreads() {
        return threads;
    }

    public void setThreads(List<Thread> threads) {
        this.threads = threads;
    }
}
