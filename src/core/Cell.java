package core;

import interfaces.Publisher;
import interfaces.Subscriber;

import java.util.ArrayList;
import java.util.List;

public class Cell implements Publisher<CellEvent> {
    private boolean isAlive;
    private List<Subscriber<CellEvent>> subscribers;

    public Cell() {
        this.isAlive = false;
        this.subscribers = new ArrayList<>();
    }

    public boolean isAlive() {
        return this.isAlive;
    }

    public void setAlive(boolean alive) {
        if (alive) {
            this.live();
        } else {
            this.die();
        }
    }

    public void live() {
        this.isAlive = true;
        this.notifySubscribers(CellEvent.LIVE);
    }

    public void die() {
        this.isAlive = false;
        this.notifySubscribers(CellEvent.DIE);
    }

    @Override
    public String toString() {
        return isAlive ? "O" : ".";
    }

    @Override
    public void addSubscriber(Subscriber<CellEvent> subscriber) {
        this.subscribers.add(subscriber);
    }

    @Override
    public void removeSubscriber(Subscriber<CellEvent> subscriber) {
        this.subscribers.remove(subscriber);
    }

    @Override
    public void notifySubscribers(CellEvent event) {
        for (Subscriber<CellEvent> subscriber : this.subscribers) {
            subscriber.update(event);
        }
    }
}
