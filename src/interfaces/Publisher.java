package interfaces;

import core.GameGridEvent;

public interface Publisher<E> {
    void addSubscriber(Subscriber<E> subscriber);
    void removeSubscriber(Subscriber<E> subscriber);
    void notifySubscribers(E event, int row, int col);
}
