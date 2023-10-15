import java.util.ArrayList;

public class Page {
    
    private int id;
    private ArrayList<Object> data;

    public Page(int id) {
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
