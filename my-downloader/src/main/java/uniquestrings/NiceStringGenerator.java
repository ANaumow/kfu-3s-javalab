package uniquestrings;

import java.util.function.Function;

public class NiceStringGenerator implements Function<String, String> {

    /**
     * Generate new String by rules:
     * 1) "file.txt" -> "file (2).txt"
     * 2) "file (5).txt" -> "file (6).txt"
     *
     * @param s string uses for generate new string, must have "." (an extension)
     * @return new generated string
     */
    @Override
    public String apply(String s) {
        int num;
        int point = s.lastIndexOf(".");
        String start = s.substring(0, point);
        String end = s.substring(point + 1);

        int gap = start.lastIndexOf(" ");
        if (gap != -1 && start.substring(gap + 1).matches("[(]\\d+[)]$")) {
            num = Integer.parseInt(start.substring(gap + 2, start.length() - 1));
            num++;
            start = start.substring(0, gap);
        } else {
            num = 2;
        }
        return start + " (" + num + ")." + end;
    }

}
