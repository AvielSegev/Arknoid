// 318807104 Aviel Segev
package Game.General;

/**
 * The Counter class represents a simple counter that can be increased or decreased.
 */
public class Counter {
    private int count = 0;

    /**
     * Increases the counter by the specified number.
     *
     * @param number The number to increase the counter by.
     */
    public void increase(int number) {
        this.count += number;
    }

    /**
     * Decreases the counter by the specified number.
     *
     * @param number The number to decrease the counter by.
     */
    public void decrease(int number) {
        this.count -= number;
    }

    /**
     * Returns the current value of the counter.
     *
     * @return The current count value.
     */
    public int getValue() {
        return this.count;
    }
}
