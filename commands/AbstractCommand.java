package commands;
public abstract class AbstractCommand implements Command{
    private int type;
    public AbstractCommand(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

}
