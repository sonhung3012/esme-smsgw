package com.fis.esme.core.util;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <p>Title: MCA Core</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2011</p>
 *
 * <p>Company: FIS-SOT</p>
 *
 * @author LiemLT
 * @version 1.0
 */
public class LinkQueue<T> {
    private int maxQueueSize = 0;
    private ConcurrentLinkedQueue<T> queueData = new ConcurrentLinkedQueue<T>();
    private AtomicInteger miQueueSize = new AtomicInteger();
    private Object mutex;

    public LinkQueue() {
        mutex = this;
    }

    public LinkQueue(int maxSize) {
        maxQueueSize = maxSize;
        mutex = this;
    }

//
//    /**
//     *
//     * Appends an element to the end of the queue. If the queue
//     *
//     * has set limit on maximum elements and there is already specified
//     *
//     * max count of elements in the queue throws IndexOutOfBoundsException.
//     */
//    public void enqueue(T objMsg) throws IndexOutOfBoundsException {
//        if ((maxQueueSize > 0) && (getSize() >= maxQueueSize)) {
//            throw new IndexOutOfBoundsException(
//                    "Queue is full. Element not added.");
//        }
//        synchronized (queueData) {
//            queueData.add(objMsg);
//        }
//    }

    /**
     *
     * Appends an element to the end of the queue. If the queue
     *
     * has set limit on maximum elements and there is already specified
     *
     * max count of elements in the queue throws IndexOutOfBoundsException.
     *
     * notify to all waiting object
     */
    public void enqueueNotify(T objMsg) {
        if ((maxQueueSize > 0) && (miQueueSize.intValue() >= maxQueueSize)) {
            System.out.println("Queue is full. Element not added.");
            return;
        }
        synchronized(queueData){
            queueData.add(objMsg);
        }
        miQueueSize.incrementAndGet();

        synchronized (mutex) {
            mutex.notify();
        }
    }

//
//    /**
//     *
//     * Removes first element form the queue and returns it.
//     *
//     * If the queue is empty, returns null.
//     */
//    public T dequeue() {
//        T objMsg = poolFirstSync();
//
//        return objMsg;
//    }
//
//    /**
//     *
//     * Removes first element form the queue and returns it.
//     *
//     * If the queue is empty, returns null.
//     */
//    public Object dequeueWait() {
//        T objMsg = objMsg = poolFirstSync();
//
//        while (objMsg == null) {
//            synchronized (mutex) {
//                try {
//                    mutex.wait(1000);
//                } catch (InterruptedException e) {
//                }
//            }
//            objMsg = poolFirstSync();
//        }
//
//        return objMsg;
//    }

    public T dequeueWait(int iSecondTimeout) {
        T objMsg = objMsg = poolFirstSync();

        if (objMsg == null) {
            for (int i = 0; i < iSecondTimeout; i++) {
                synchronized (mutex) {
                    try {
                        mutex.wait(1000);
                    } catch (InterruptedException e) {
                    }
                }
                objMsg = poolFirstSync();

                if (objMsg != null) {
                    miQueueSize.decrementAndGet();
                    return objMsg;
                }
            }
        }
        if (objMsg != null) {
            miQueueSize.decrementAndGet();
        }

        return objMsg;
    }

    private T poolFirstSync() {
        synchronized (queueData) {
            try {
                return queueData.poll();
            } catch (Exception ex) {
                ex.printStackTrace();
                return null;
            }
        }
    }

    /**
     *
     * Current count of the elements in the queue.
     */
    public int getSize() {
        synchronized (mutex) {
            return miQueueSize.intValue();
        }
    }

    /**
     *
     * If there is no element in the queue.
     */
    public boolean isEmpty() {
        synchronized (mutex) {
            return queueData.isEmpty();
        }
    }

    public void notify2Closed() {
        synchronized (mutex) {
            mutex.notifyAll();
        }
    }

}
