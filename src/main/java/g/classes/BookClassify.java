package g.classes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BookClassify implements Library {
    private String name;
    private HashMap<String, Library> classify;

    public BookClassify(String name) {
        this.name = name;
        this.classify = new HashMap<>();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void add(String str, Library library) {
        classify.put(str, library);
    }

    @Override
    public HashMap<String, Library> getChildren() {
        return classify;
    }
}
