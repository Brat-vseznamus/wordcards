package javaFiles.commands;
public class TestCommand extends AbstractCommand{
    private int numberOfTest = 1;
    public TestCommand() {
        super(0);
    }

    public TestCommand(int numberOfTest) {
        super(0);
        this.numberOfTest = numberOfTest;
    }

    public int getNumberOfTest() {
        return numberOfTest;
    }
    
}
