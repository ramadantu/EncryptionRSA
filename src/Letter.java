public class Letter{

    private char value;
    private int key;

    public Letter(char value, int key) {
        this.value = value;
        this.key = key;
    }

    public char getValue() {
        return value;
    }

    public void setValue(char value) {
        this.value = value;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }
}
