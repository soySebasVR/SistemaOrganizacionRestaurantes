package Restaurant.Elaboracion;

public class ListaElaboracion {
    Paso head;
    Paso tail;

    public ListaElaboracion()
    {
        this.head = null;
        this.tail = null;
    }

    public Paso getHead() {
        return head;
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

    public void printList()
    {
        Paso current = head;
        while (current != null) {
            System.out.println(current.getData());
            current = current.next;
        }
    }
}
