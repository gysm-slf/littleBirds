package cn.littleBird.utils;

public class SingleLinkedList<E> {

    private int open;
//    private int num = 0;

    private Node first;
    private Node last;

    public SingleLinkedList() {
        this.open = 1;
    }

    public void reverse(int flag) {
        if (flag != 0 && flag != 1)
            throw new IllegalArgumentException();
        this.open = flag == 0 ? toClose() : toOpen();
    }

    public void orderReverse() {
        if (size() == 0)
            throw new RuntimeException("linkedList size is zero.");
        Node node = null;
        Node head = first;
        Node reverse = new Node(first.e, null);
        while (head.next != null) {
            head = head.next;
            node = new Node(head.e, reverse);
            reverse = node;
        }
        first = node;
    }

    public int size() {
        int count = 0;
        Node node = first;
        if (node == null)
            return 0;
        count++;
        while (node.next != null) {
            count++;
            node = node.next;
        }
        return count;
    }

    private int toOpen() {
        last.next = null;
        return 1;
    }

    private int toClose() {
        last.next = first;
        return 0;
    }

    public void add(E e) {
        if (last == null) {
            last = new Node(e, null);
            first = last;
        } else {
            if (open == 0) {
                closeAdd(e);
            } else {
                openAdd(e);
            }
        }
    }

    public void openAdd(E e) {
        last.next = new Node(e, null);
        last = last.next;
    }

    public void closeAdd(E e) {
        last.next = new Node(e, first);
        last = last.next;
    }

    public void Josephu(int startNo, int number) {
        if (first == null || startNo < 1 || startNo > size()) {
            System.out.println("非法参数");
            return;
        }
        Node head = new Node(null, first);
        for (int i = 0; i < startNo-1; i++) {
            first=first.next;
            head=head.next;
        }
        for (int i = 0; i < number - 1; i++) {

        }
    }

    public int isOpenLinked() {
        int flag = 1;
        Node fast = first;
        Node slow = first;
        if (slow == null || slow.next == null)
            return flag;
        if (slow.next != null) {
            fast = slow.next;
        }
        while (slow != null && fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            System.out.println(fast);
            if (slow == fast) {
                System.out.println("连接点为" + slow);
                flag = 0;
                break;
            }
        }
        return flag;
    }

    public static int isOpenLinked(Object singleLinkedList) {
        if (singleLinkedList instanceof SingleLinkedList)
            return ((SingleLinkedList<?>) singleLinkedList).open;
        return -1;
    }

    public void show() {
        Node pointer = first;
        while (pointer != null) {
            if (pointer.next == null) {
                System.out.print(pointer.e);
            } else {
                System.out.print(pointer.e + "-->");
            }
            pointer = pointer.next;
        }
        System.out.println();
    }

    private /*static*/ class Node {

        Node next;
        E e;

        public Node(E e, Node next) {
            this.e = e;
            this.next = next;
        }
    }
}
