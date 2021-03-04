package ua.axiom.apply.model;

import java.util.*;

/**
 * Implements subscriber model, that stores subscribers according to
 *      a) Priority,
 *      b) subscription time
 * @param <T> type of an object, that is being subscribed to (Feed, Event, Product, etc)
 * @param <U> type of object, that subscribes (User, etc)
 */
public class PriorityAwareSubscriberModel<T, U> {
    private final Map<T, SortedMap<Integer, Queue<U>>> subscribedObjectToPriorityMapToFIFOList = new HashMap<>();

    public void addSubscriber(T subscribant, U subscriber, Integer priority) {
        Map<Integer, Queue<U>> subscribersForSubscriber = subscribedObjectToPriorityMapToFIFOList.computeIfAbsent(subscribant, s -> new TreeMap<>());

        Queue<U> orderedSubscribersForPriority = subscribersForSubscriber.computeIfAbsent(priority, p -> new LinkedList<>());

        orderedSubscribersForPriority.offer(subscriber);

    }

    //  todo pagination?
    //  todo carefully test
    public List<U> getOrderedSubscribers(T forObject) {
        Map<Integer, Queue<U>> subscribersBy = subscribedObjectToPriorityMapToFIFOList.computeIfAbsent(forObject, o -> new TreeMap<>());

        List<U> subscribers = new ArrayList<>();

        //  todo check are integers sorted
        for (Integer priority : subscribersBy.keySet()) {
            Queue<U> subscribersWithPriority = subscribersBy.computeIfAbsent(priority, p -> new LinkedList<U>());

            subscribers.addAll(subscribersWithPriority);
        }

        return subscribers;
    }


}
