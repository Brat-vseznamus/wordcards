package javaFiles;
import java.util.HashSet;
import java.util.Random;
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
        x = x.replaceAll("\\s+","");
        y = y.replaceAll("\\s+","");
        int ds[][] = new int[x.length() + 1][y.length() + 1];
        ds[0][0] = 0;
        for (int i = 0; i <= x.length(); i++) {
            ds[i][0] = i;
        }
        for (int j = 0; j <= y.length(); j++) {
            ds[0][j] = j;
        }
        for (int i = 1; i <= x.length(); i++) {
            for (int j = 1; j <= y.length(); j++) {
                if (x.charAt(i - 1) == y.charAt(j - 1)) {
                    ds[i][j] = ds[i - 1][j - 1];
                } else {
                    ds[i][j] = Math.min(
                        ds[i][j - 1],
                        Math.min(
                            ds[i - 1][j],
                            ds[i - 1][j - 1])
                    ) + 1;
                }
            }
        }
        // // System.out.println("!" + x + ": " + y);
        // if (x.isBlank()) {
        //     return y.length();
        // }

        // if (y.isBlank()) {
        //     return x.length();
        // }

        // int substitution = calculateRadius(x.substring(1), y.substring(1))
        //  + costOfSubstitution(x.charAt(0), y.charAt(0));
        // int insertion = calculateRadius(x, y.substring(1)) + 1;
        // int deletion = calculateRadius(x.substring(1), y) + 1;
        //https://neerc.ifmo.ru/wiki/index.php?title=%D0%97%D0%B0%D0%B4%D0%B0%D1%87%D0%B0_%D0%BE_%D1%80%D0%B5%D0%B4%D0%B0%D0%BA%D1%86%D0%B8%D0%BE%D0%BD%D0%BD%D0%BE%D0%BC_%D1%80%D0%B0%D1%81%D1%81%D1%82%D0%BE%D1%8F%D0%BD%D0%B8%D0%B8,_%D0%B0%D0%BB%D0%B3%D0%BE%D1%80%D0%B8%D1%82%D0%BC_%D0%92%D0%B0%D0%B3%D0%BD%D0%B5%D1%80%D0%B0-%D0%A4%D0%B8%D1%88%D0%B5%D1%80%D0%B0
        return ds[x.length()][y.length()];
    }

    public static int costOfSubstitution(char a, char b) {
        return a == b ? 0 : 1;
    }

    public boolean checkAnswer(String answer, int mode) {
        if (mode == 0) {
            return checkAnswer(answer);
        } else {
            return calculateRadius(answer, word) <= 2;
        }
    }

    public String randomTranslation() {
        Random r = new Random();
        int x = r.nextInt(translations.size());
        int i = 0;
        for (String tr : translations) {
            if (i++ == x) {
                return tr;
            }
        }
        return null;
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