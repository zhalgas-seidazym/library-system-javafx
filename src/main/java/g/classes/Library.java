package g.classes;

import java.util.HashMap;
import java.util.List;

public interface Library {
    String getName();
    void add(String str, Library library);
    HashMap<String, Library> getChildren();
}
