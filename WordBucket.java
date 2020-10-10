import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class WordBucket {
    public int number;
    public Set<Translation> words;

    public WordBucket() {
        this.words = new HashSet<Translation>();
    }

    public WordBucket(int number) {
        this.words = new HashSet<Translation>();
        this.number = number;
    }

    public Translation randomPair() {
        Random random = new Random();
        int x = random.nextInt(words.size());
        int i = 0;
        for (Translation wp : words) {
            if (i++ == x) {
                return wp;
            }
        }
        return null;
    }

    public void put(String word, String translation) {
        for (Translation pair : words) {
            if (pair.word.equals(word)) {
                pair.add(translation);
                return;
            }
        }
        words.add(new Translation(word, translation));
    }

    public void put(Translation wp) {
        words.add(wp);
    }

    public void delete(Translation wp) {
        words.remove(wp);
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("WordBucket[" + number + "]:\n");
        for (Translation wp : words) {
            str.append(wp).append("\n");
        }
        return str.toString();
    }

    

}
