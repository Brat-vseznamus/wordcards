package commands;
public class AddAllCommand extends AbstractCommand {
    private int numberOfPhrases;
    public AddAllCommand() {
        super(4);
        numberOfPhrases = 1;
    }

    public AddAllCommand(int n) {
        super(4);
        numberOfPhrases = n;
    }

    public int getNumberOfPhrases() {
        return numberOfPhrases;
    }
    
}
