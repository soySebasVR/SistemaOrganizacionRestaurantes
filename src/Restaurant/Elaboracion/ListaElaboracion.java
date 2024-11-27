package Restaurant.Elaboracion;

public class ListaElaboracion {
    Paso head;
    Paso tail;

    public ListaElaboracion()
    {
        this.head = null;
        this.tail = null;
    }

    // Traversing from head to the end of the list
    public void traverseForward()
    {
        Paso current = head;
        while (current != null) {
            System.out.print(current.data + " ");
            current = current.next;
        }
    }
    // Traversing from tail to the head
    public void traverseBackward()
    {
        Paso current = tail;
        while (current != null) {
            System.out.print(current.data + " ");
            current = current.prev;
        }
    }

    public void insertAtEnd(String data)
    {
        Paso temp = new Paso(data);
        if (tail == null) {
            head = temp;
        }
        else {
            tail.next = temp;
            temp.prev = tail;
        }
        tail = temp;
    }

}
