// Srija Konjarla
// skonjarl
// pa3
// List.java

public class List {

    // private inner Node class
    private class Node {

        Object data;
        Node next;
        Node previous;

        Node(Object data) {
            this.data = data;
            next = null;
            previous = null;
        }

        // toString():  overrides Object's toString() method
        public String toString() {
            return String.valueOf(data);
        }

        // equals(): overrides Object's equals() method
        public boolean equals(Object x) {
            boolean eq = false;
            Node that;
            if (x instanceof Node) {
                that = (Node) x;
                eq = (this.data == that.data);
            }
            return eq;
        }
    }

    // Fields for the List class
    private Node front;     // reference to first Node in List
    private Node back;		// reference to last Node in the list
    private Node cursor;	// reference to the current Node in the List
    private int length; 	// number of items in this List
    private int index;		// index of cursor

    // Constructor
    // Creates a new empty list.
    List() {
        front = null;
        back = null;
        cursor = null;
        length = 0;
        index = -1;
    }

    // Access functions
    // Returns the number of elements in this List.
    int length() {
        return length;
    }

    // If cursor is defined, returns the index of the cursor element, otherwise returns -1.
    int index() {
        if (cursor != null) {
            return index;
        } else {
            return -1;
        }
    }

    // Returns front element. Pre: length()>0
    Object front() {
        if (length == 0) {
            throw new RuntimeException("List Error: Can not call front() on empty List");
        } else {
            return front.data;
        }
    }

    // Returns back element. Pre: length()>0
    Object back() {
        if (length == 0) {
            throw new RuntimeException("List Error: Can not call back() on empty List");
        } else {
            return back.data;
        }
    }

    // Returns cursor element. Pre: length()>0, index()>=0
    Object get() {
        if (length == 0) {
            throw new RuntimeException("List Error: Can not call get() empty List");
        } else if (index < 0) {
            throw new RuntimeException("List Error: Can not call get() non-existant cursor");
        }
        return cursor.data;
    }

    // Returns true if and only if this List and L are the same
    // integer sequence. The states of the cursors in the two Lists
    // are not used in determining equality.
    public boolean equals(Object x) {
        boolean eq = false;
        Node N, M;
        List L = (List) x;
        N = this.front;
        M = (Node) L.front;
        if (L.length == this.length) {
            eq = true;
            while (eq && N != null) {
                eq = (N.data.equals(M.data));
                N = N.next;
                M = M.next;
            }
        }
        return eq;
    }

    // Manipulation procedures
    // Resets this List to its original empty state.
    void clear() {
        front = null;
        back = null;
        cursor = null;
        length = 0;
        index = -1;
    }

    // If List is non-empty, places the cursor under the front element,
    // otherwise does nothing.
    void moveFront() {
        if (length != 0) {
            cursor = front;
            index = 0;
        }
    }

    // If List is non-empty, places the cursor under the back element,
    // otherwise does nothing.
    void moveBack() {
        if (length != 0) {
            cursor = back;
            index = length - 1;
        }
    }

    // If cursor is defined and not at front, moves cursor one step toward
    // front of this List, if cursor is defined and at front, cursor becomes
    // undefined, if cursor is undefined does nothing.
    void movePrev() {
        if (cursor != null && cursor != front) {
            cursor = cursor.previous;
            index--;
        } else {
            cursor = null;
            index = -1;
        }
    }

    // If cursor is defined and not at back, moves cursor one step toward
    // back of this List, if cursor is defined and at back, cursor becomes
    // undefined, if cursor is undefined does nothing.
    void moveNext() {
        if (cursor != null && cursor != back) {
            cursor = cursor.next;
            index++;
        } else {
            cursor = null;
            index = -1;
        }
    }

    // Insert new element into this List. If List is non-empty,
    // insertion takes place before front element.
    void prepend(Object data) {
        Node N = new Node(data);
        if (length == 0) {
            front = N;
            back = N;
        } else {
            front.previous = N;
            N.next = front;
            front = N;
        }
        index++;
        length++;
    }

    // Insert new element into this List. If List is non-empty,
    // insertion takes place after back element.
    void append(Object data) {
        Node N = new Node(data);
        if (length == 0) {
            front = N;
            back = N;
            length = 1;
        } else {
            back.next = N;
            N.previous = back;
            back = N;
            length++;
        }
    }

    // Insert new element before cursor.
    // Pre: length()>0, index()>=0
    void insertBefore(Object data) {
        if (length == 0) {
            throw new RuntimeException("List Error: Can not call insertBefore() on empty List");
        }

        if (index < 0) {
            throw new RuntimeException("List Error: Can not call insertBefore() on undefined cursor");
        }

        if (index == 0) {
            prepend(data);
        } else {
            Node N = new Node(data);
            Node P = cursor.previous;
            N.previous = P;
            N.next = cursor;
            P.next = N;
            cursor.previous = N;
            length++;
            index++;
        }
    }

    // Inserts new element after cursor.
    // Pre: length()>0, index()>=0
    void insertAfter(Object data) {
        if (length == 0) {
            throw new RuntimeException("List Error: Can not call insertAfter() on empty List");
        }

        if (index < 0) {
            throw new RuntimeException("List Error: Can not call insertAfter() on undefined cursor");
        }

        if (index == length - 1) {
            append(data);
        } else {
            Node N = new Node(data);
            Node P = cursor.next;
            N.next = cursor.next;
            N.previous = P;
            P.previous = N;
            cursor.next = N;
            length++;
        }
    }

    // Deletes the front element. Pre: length()>0
    void deleteFront() {
        if (length == 0) {
            throw new RuntimeException("List Error: can not call deleteFront() on empty List");
        } else if (length == 1) {
            front = null;
            back = null;
            index = -1;
            length--;
        } else {
            front = front.next;
            front.previous = null;
            length--;
            index--;
        }
    }

// Deletes the back element. Pre: length()>0
    void deleteBack() {
        if (length == 0) {
            throw new RuntimeException("List Error: can not call deleteBack() on empty List");
        } else {
            if (length == 1) {
                front = null;
                back = null;
                index = -1;
                length--;
            } else {
                if (cursor == back) {
                    cursor = null;
                    index = -1;
                }
                back = back.previous;
                back.next = null;
                length--;
            }
        }
    }

// Deletes cursor element, making cursor undefined.
// Pre: length()>0, index()>=0
    void delete() {
        if (length == 0) {
            throw new RuntimeException("List Error: can not call delete() on empty List");
        } else if (index < 0) {
            throw new RuntimeException("List Error: can not call delete() on non-existant List");
        } else {
            if (length == 1){
                deleteBack();
            } else if (cursor == front) {
                front = front.next;
                front.previous = null;
                cursor = null;
                length--;
            } else if (cursor == back) {
                back = back.previous;
                back.next = null;
                cursor = null;
                length--;
            } else {
                cursor.previous.next = cursor.next;
                cursor.next.previous = cursor.next;
                cursor = null;
                index--;
                length--;
            }
        }
    }
    
    // Other methods
    // Overrides Object's toString method. Returns a String
    // representation of this List consisting of a space
    // separated sequence of integers, with front on left.

    public String toString() {
        StringBuffer sb = new StringBuffer();
        for (Node N = front; N != null; N = N.next) {
            if (N.next == null) {
                sb.append(N.toString());
            } else {
                sb.append(N.toString());
                sb.append(" ");
            }
        }
        return new String(sb);
    }
}