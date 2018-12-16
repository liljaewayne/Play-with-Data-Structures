import java.util.Arrays;

public class LoopQueue<E> implements Queue<E> {

    // 储存数据的数组
    private E[] data;
    private int front, tail;
    private int size;   // 有兴趣的同学，在完成这一章后，可以思考一下：
    // LoopQueue中不声明size，如何完成所有的逻辑？
    // 这个问题可能会比大家想象的要难一点点：）

    public LoopQueue(int capacity) {
        data = (E[]) new Object[capacity + 1];
        front = 0;
        tail = 0;
        size = 0;
    }

    public LoopQueue() {
        this(10);
    }

    // 对data.length取模得到的值作为下一个位置, 最后一个位置不储存, 所以容积是data.length - 1
    public int getCapacity() {
        return data.length - 1;
    }

    @Override
    public boolean isEmpty() {
        return front == tail;
    }

    @Override
    public int getSize() {
        return size;
    }

    // 下一小节再做具体实现
    @Override
    public void enqueue(E e) {
        if ((tail + 1) % data.length == front) {// 如果队列满, resize
            resize(getCapacity() * 2);
        }

        data[tail] = e;
        tail = (tail + 1) % data.length;
        size++;
    }

    private void resize(int newCapacity) {
        E[] newData = (E[]) new Object[newCapacity + 1];

        for (int i = 0; i < size; i++) {
            newData[i] = data[(front + i) % data.length];
        }
        data = newData;
        front = 0;
        tail = size;
    }

    // 下一小节再做具体实现
    @Override
    public E dequeue() {
        if (isEmpty()) {
            throw new IllegalArgumentException("empty");
        }
        E ret = data[front];
        data[front] = null;
        front = (front + 1) % data.length;
        size--;

        if (size < getCapacity() / 4 && getCapacity() / 2 > 0) {
            resize(getCapacity() / 2);
        }

        return ret;


    }

    // 下一小节再做具体实现
    @Override
    public E getFront() {
        if (isEmpty()) {
            throw new IllegalArgumentException("empty");
        }
        return data[front];
    }

    @Override
    public String toString() {

        StringBuilder res = new StringBuilder();
        res.append(String.format("Queue: size = %d , capacity = %d\t", size, getCapacity()));
        res.append("front [");
        for (int i = front; i != tail; i = (i + 1) % data.length) {
            res.append(data[i]);
            if ((i + 1) % data.length != tail)
                res.append(", ");
        }
        res.append("] tail");
        res.append("\t " + Arrays.toString(data));
        return res.toString();
    }

    public static void main(String[] args) {

        LoopQueue<Integer> queue = new LoopQueue<>(5);
        for (int i = 0; i < 20; i++) {
            queue.enqueue(i);
            System.out.println(queue);

            if (i % 3 == 2) {
                queue.dequeue();
                System.out.println(queue);
            }
        }
    }
}
