public class AddCommand extends AbstractCommand { 
    private String word, translation;
    public AddCommand() {
        super(1);
    }

    public AddCommand(String word, String translation) {
        super(1);
        this.word = word;
        this.translation = translation;
    }
    
    public Translation getPair() {
        return new Translation(word, translation);
    }
}
