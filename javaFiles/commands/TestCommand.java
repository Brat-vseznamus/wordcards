package javaFiles.commands;

import java.util.Set;

public class TestCommand extends AbstractCommand{
    private int numberOfTest = 1;
    private String mode;

    public static Set<String> modeSet = Set.of(
        "ru-eng",
        "eng-ru"
    );

    public TestCommand() {
        super(0);
        mode = "eng-ru";
    }

    public TestCommand(int numberOfTest) {
        super(0);
        this.numberOfTest = numberOfTest;
        mode = "eng-ru";
    }

    public TestCommand(int numberOfTest, String mode) {
        super(0);
        this.numberOfTest = numberOfTest;
        this.mode = mode;
    }

    public int getNumberOfTest() {
        return numberOfTest;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    


}
