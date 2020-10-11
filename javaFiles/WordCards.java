package javaFiles;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import javaFiles.commands.*;

public class WordCards {
    public static int numberOfBuckets = 10;
    public List<WordBucket> buckets;
    private Scanner userScanner = new Scanner(System.in);

    static String tab = "   ";
    static String defTab = "\n" + tab.repeat(2) + "-- ";
    static File input = new File("/home/fedor/Documents/sources/word_cards/resources", "words.txt");

    static Map<String, Integer> firstWordMap = new HashMap<>(
            Map.of("addall", 4, "exit", 3, "help", 2, "add", 1, "test", 0));

    static Map<String, Set<String>> descriptionMap = new LinkedHashMap<>(
            Map.of("help", Set.of(defTab + "show realized functions"), "add",
                    Set.of(" <phrase> - <translated phrase>" + defTab + "add your phrases and words"), "addAll",
                    Set.of(" <number of phrases>" + defTab + "add all phraeses and words", defTab + "same as add"),
                    "test", Set.of(" <number of tests>" + defTab + "activate tests"), "exit",
                    Set.of(defTab + "end session and exit from program")));

    void fillTable(File file) throws FileNotFoundException {
        buckets = new ArrayList<>();
        for (int i = 0; i < numberOfBuckets; i++) {
            buckets.add(new WordBucket(i));
        }
        Scanner bucketsScanner = new Scanner(file);
        while (bucketsScanner.hasNextLine()) {
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
            buckets.get(bucket).put(w1.toString(), w2.toString());
            lineScanner.close();
        }
        bucketsScanner.close();
    }

    static int bucketNumber(double p) {
        double pn = p * (Math.exp(10 * Math.log(1.5)) - 1) + 1;
        return (int) (Math.log(pn) / Math.log(1.5));
    }

    static Command add(Scanner scanner) {
        StringBuilder fisrtWords = new StringBuilder();
        StringBuilder secondWords = new StringBuilder();
        if (!scanner.hasNext()) {
            scanner.close();
            return new FailCommand("missing first word");
        }
        String token = scanner.next();
        while (scanner.hasNext() && !token.equals("-")) {
            fisrtWords.append(token).append(" ");
            token = scanner.next();
        }
        if (!scanner.hasNext()) {
            scanner.close();
            return new FailCommand("missing second word");
        }
        token = scanner.next();
        secondWords.append(token);
        while (scanner.hasNext()) {
            fisrtWords.append(" ").append(token);
            token = scanner.next();
        }
        scanner.close();
        return new AddCommand(fisrtWords.toString().toLowerCase(), secondWords.toString().toLowerCase());
    }

    static Command parseCommand(String source) {
        Scanner scanner = new Scanner(source);
        String w1 = scanner.next().toLowerCase();
        if (firstWordMap.containsKey(w1)) {
            switch (firstWordMap.get(w1)) {
                case 0: {
                    if (!scanner.hasNext()) {
                        scanner.close();
                        return new FailCommand("missing number of test");
                    }
                    int ntest = scanner.nextInt();
                    if (ntest <= 0) {
                        scanner.close();
                        return new FailCommand("wrong number of tests");
                    }
                    scanner.close();
                    return new TestCommand(ntest);
                }
                case 1: {
                    return add(scanner);
                }
                case 2: {
                    scanner.close();
                    return new HelpCommand();
                }
                case 3: {
                    scanner.close();
                    return new ExitCommand();
                }
                case 4: {
                    if (!scanner.hasNext()) {
                        scanner.close();
                        return new AddAllCommand();
                    }
                    int nphrases = scanner.nextInt();
                    scanner.close();
                    return new AddAllCommand(nphrases);
                }
                default: {
                    scanner.close();
                    return new FailCommand("unknown problem");
                }
            }
        } else {
            scanner.close();
            return new FailCommand("unknown command");
        }
    }

    void runCommand(Command cmd) throws UnsupportedEncodingException, FileNotFoundException {
        switch (cmd.getType()) {
            case 0: {
                int ntest = ((TestCommand) cmd).getNumberOfTest();
                test(ntest);
                break;
            }
            case 1: {
                AddCommand addcmd = (AddCommand) cmd;
                buckets.get(numberOfBuckets - 1).put(addcmd.getPair());
                break;
            }
            case 2: {
                System.out.println("usage: \n");
                for (String comd : descriptionMap.keySet()) {
                    for (String desc : descriptionMap.get(comd)) {
                        System.out.print(comd);
                        System.out.println(desc);
                    }
                }
                break;
            }
            case 3: {
                System.out.println("end session.");
                PrintWriter out = new PrintWriter(
                        new BufferedWriter(new OutputStreamWriter(new FileOutputStream(input), "UTF-8")));
                for (int i = 0; i < numberOfBuckets; i++) {
                    for (Translation words : buckets.get(i).words) {
                        for (String translation : words.translations) {
                            out.println(words.word + " | " + translation + " | " + i);
                        }
                    }
                }
                out.close();
                userScanner.close();
                break;
            }
            case 4: {
                AddAllCommand addcmd = (AddAllCommand) cmd;
                int n = addcmd.getNumberOfPhrases();
                while (n-- > 0) {
                    Scanner linesc = new Scanner(userScanner.nextLine());
                    Command cmd2 = add(linesc);
                    if (cmd2.getType() == -1) {
                        runCommand(cmd2);
                        break;
                    } else {
                        buckets.get(numberOfBuckets - 1).put(((AddCommand) cmd2).getPair());
                    }
                }
                break;
            }
            case -1: {
                FailCommand failcmd = (FailCommand) cmd;
                System.out.println("command fail: " + failcmd.getMessage());
                break;
            }
        }
    }

    void test(int ntest) {
        Random r = new Random();
        while (ntest-- > 0) {
            int n = bucketNumber(r.nextDouble());
            while (buckets.get(n).isEmpty()) {
                n = bucketNumber(r.nextDouble());
            }
            Translation tr = buckets.get(n).randomPair();
            System.out.print("? " + tr.word + " - ");
            String answer = userScanner.nextLine();
            if (tr.checkAnswer(answer)) {
                System.out.println("\u001B[32mRight!\u001B[0m");
                if (n > 0) {
                    buckets.get(n).delete(tr);
                    buckets.get(n - 1).put(tr);
                }
            } else {
                System.out.println("\u001B[31mWrong!\u001B[0m");
                System.out.println("Right answer: " + tr);
                buckets.get(n).delete(tr);
                buckets.get(numberOfBuckets - 1).put(tr);
            }
        }
    };

    private Command readCommand() {
        Command cmd;
        if (userScanner.hasNext()) {
            cmd = parseCommand(userScanner.nextLine());
        } else {
            cmd = new ExitCommand();
        }
        return cmd;
    }

    public static void main(String[] args) throws UnsupportedEncodingException, FileNotFoundException {
        WordCards wc = new WordCards();
        wc.fillTable(input);
        // System.out.print(wc);
        System.out.print("> ");
        Command cmd = wc.readCommand();
        while (cmd.getType() != 3) {
            wc.runCommand(cmd);
            System.out.print("> ");
            cmd = wc.readCommand();
        }
        wc.runCommand(cmd);
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (WordBucket wb : buckets) {
            str.append(wb.toString()).append("\n");
        }
        return str.toString();
    }

    public WordCards(Scanner userScanner) {
        this.userScanner = userScanner;
    }

    public WordCards() {
        this.userScanner = new Scanner(System.in);
    }

}