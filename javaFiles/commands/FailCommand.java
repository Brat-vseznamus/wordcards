package javaFiles.commands;
public class FailCommand extends AbstractCommand{
    private String message;
    public FailCommand() {
        super(-1);
    }

    public FailCommand(String message) {
        super(-1);
        this.message = message;
    }
    
    public String getMessage() {
        return message;
    }
}
