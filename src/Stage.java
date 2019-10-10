public abstract class Stage { // этап
    protected int length;
    protected String description; // описание
    public String getDescription() {
        return description;
    }
    public abstract void go(Car c);
}