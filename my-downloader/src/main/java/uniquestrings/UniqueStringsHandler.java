package uniquestrings;

import java.io.File;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class UniqueStringsHandler {

    private Set<String> stringSet;
    private Function<String, String> stringGenerator;

    public UniqueStringsHandler(File[] files, Function<String, String> stringGenerator) {
        this(Arrays.stream(files)
                .filter(File::isFile)
                .map(File::getName)
                .collect(Collectors.toSet()), stringGenerator);
    }

    public UniqueStringsHandler(String[] initialArray , Function<String, String> stringGenerator) {
        this(new HashSet<>(Arrays.asList(initialArray)), stringGenerator);
    }

    public UniqueStringsHandler(Set<String> initialSet, Function<String, String> stringGenerator) {
        this.stringSet = initialSet;
        this.stringGenerator = stringGenerator;
    }

    public String getUniqueFilename(String s) {
        while (stringSet.contains(s))
            s = stringGenerator.apply(s);
        return s;
    }

    public boolean register(String s) {
        return stringSet.add(s);
    }

    public Set<String> getStringSet() {
        return stringSet;
    }

    public void setStringSet(Set<String> stringSet) {
        this.stringSet = stringSet;
    }

    public Function<String, String> getStringGenerator() {
        return stringGenerator;
    }

    public void setStringGenerator(Function<String, String> stringGenerator) {
        this.stringGenerator = stringGenerator;
    }
}
