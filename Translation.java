import java.util.HashSet;
import java.util.Set;

public class Translation {
    public String word;
    public Set<String> translations;
    public Translation(String word, Set<String> translations) {
        this.word = word;
        this.translations = translations;
    }

    public Translation(String word, String translation) {
        this.word = word;
        this.translations = new HashSet<>(Set.of(translation));
    }

    static int calculateRadius(String x, String y) {
        if (x.isEmpty()) {
            return y.length();
        }

        if (y.isEmpty()) {
            return x.length();
        }

        int substitution = calculateRadius(x.substring(1), y.substring(1))
         + costOfSubstitution(x.charAt(0), y.charAt(0));
        int insertion = calculateRadius(x, y.substring(1)) + 1;
        int deletion = calculateRadius(x.substring(1), y) + 1;

        return Math.min(substitution, Math.min(insertion, deletion));
    }

    public static int costOfSubstitution(char a, char b) {
        return a == b ? 0 : 1;
    }

    public boolean checkAnswer(String answer) {
        for (String trans : translations) {
            int r = calculateRadius(answer, trans);
            if (r <= 2) return true;
        }
        return false;
    }

    public void add(String translation) {
        translations.add(translation);
    }

    @Override
    public String toString() {
        StringBuilder trans = new StringBuilder();
        int size = translations.size();
        for (String uniqueTrans : translations) {
            --size;
            trans.append(uniqueTrans);
            if (size != 0) {
                trans.append(", ");
            }
        }
        return word + " - " + trans.toString();
    }
    
}