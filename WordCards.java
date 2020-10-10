import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class WordCards {
    public int numberOfBuckets = 10;
    public List<WordBucket> buckets; 

    void fillTable(File file) throws FileNotFoundException {
        buckets = new ArrayList<>();
        for (int i = 0; i < numberOfBuckets; i++) {
            buckets.add(new WordBucket(i));
        }
        Scanner bucketsScanner = new Scanner(file);
        while(bucketsScanner.hasNextLine()) {
            String tmp = bucketsScanner.nextLine();
            Scanner lineScanner = new Scanner(tmp);
            String token = lineScanner.next();
            StringBuilder w1 = new StringBuilder(token);
            int bucket = 0;
            token = lineScanner.next();
            while (!token.equals("|")) {
                w1.append(" ").append(token);
                token = lineScanner.next();
            }
            token = lineScanner.next();
            StringBuilder w2 = new StringBuilder(token);
            token = lineScanner.next();
            while (!token.equals("|")) {
                w2.append(" ").append(token);
                token = lineScanner.next();
            }
            bucket = lineScanner.nextInt();
            buckets.get(bucket).put(
                    w1.toString(), 
                    w2.toString());
            lineScanner.close();
        }
        bucketsScanner.close();
    }


    public static void main(String[] args) throws UnsupportedEncodingException, FileNotFoundException {
        WordCards wc = new WordCards();
        File input = new File("/home/fedor/Documents/sources/word_cards", "words.txt");
        wc.fillTable(input);
        System.out.println(wc);
        // PrintWriter out = new PrintWriter(
        //     new BufferedWriter(
        //         new OutputStreamWriter(
        //             new FileOutputStream(input),
        //             "UTF-8"
        //         )
        //     )
        // );
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (WordBucket wb : buckets) {
            str.append(wb.toString()).append("\n");
        }
        return str.toString();
    }
    
}