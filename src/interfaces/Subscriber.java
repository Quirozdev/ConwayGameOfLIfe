package interfaces;

import core.GameGridEvent;

public interface Subscriber<E> {
    void update(E event, int row, int column);
}
