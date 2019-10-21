package uniquestrings;

import java.util.function.Function;

public class SimpleStringGenerator implements Function<String, String> {

    /**
     * Generate new String by rules:
     * 1) "file.txt" -> "file_.txt"
     * 2) "file__.txt" -> "file___.txt"
     *
     * @param s string uses for generate new string, must have "." (an extension)
     * @return new generated string
     */
    @Override
    public String apply(String s) {
        int p = s.lastIndexOf(".");
        return s.substring(0, p).concat("_").concat(s.substring(p));
    }

}
