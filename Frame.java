import java.util.ArrayList;

public class Frame {
    
    private int id;
    private ArrayList<Object> data;

    public Frame(int id) {
        this.id = id;
        this.data = new ArrayList<Object>();
    }

    public int getId() {
        return id;
    }

    public ArrayList<Object> getData() {
        return data;
    }

}
