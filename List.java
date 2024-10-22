/* ***************************************************
 * Michael Krueger
 * 09/03/2024
 *
 * List Class - handles any form of data
 *************************************************** */

public class List<Type>
{
    // We don't actually have to set a max size with linked lists
    // But it is a good idea.
    // Just picture an infinite loop adding to the list! :O
    public static final int MAX_SIZE = 50;

    private Node<Type> head;
    private Node<Type> tail;
    private Node<Type> curr;
    private int num_items;

    // constructor
    // remember that an empty list has a "size" of -1 and its "position" is at -1
    public List()
    {
        this.head = null;
        this.tail = null;
        this.curr = null;
        this.num_items = 0;
    }

    // copy constructor
    // clones the list l and sets the last element as the current
    public List(List<Type> l)
    {
        Node<Type> n = l.head;

        this.head = this.tail = this.curr = null;
        this.num_items = 0;

        while (n != null)
        {
            this.InsertAfter(n.getData());
            n = n.getLink();
        }
        
    }

    // navigates to the beginning of the list
    //* */
    public void First()
    {
        this.curr = this.head;
    }

    // navigates to the end of the list
    // the end of the list is at the last valid item in the list
    //* */
    public void Last()
    {
        this.curr = this.tail;
    }

    // navigates to the specified element (0-index)
    // this should not be possible for an empty list
    // this should not be possible for invalid positions
    //* */
    public void SetPos(int pos)
    {
        this.curr = this.head;
        if(pos < 0 || pos >= this.num_items)
        {
            return;
        }
        for(int i = 0; i < pos; i++)
        {
            this.curr = this.curr.getLink();
        }
    }

    // navigates to the previous element
    // this should not be possible for an empty list
    // there should be no wrap-around
    //* */
    public void Prev()
    {
        if (this.curr == this.head || this.curr == null) 
        {
            return; // curr is already at the first element or the list is empty
        }
        Node<Type> temp = this.head;
        while(temp != null && temp.getLink() != this.curr)
        {
            temp = temp.getLink();
        }
        this.curr = temp;
    }


    
    // navigates to the next element
    // this should not be possible for an empty list
    // there should be no wrap-around
    //* */
    public void Next()
    {
        if (this.curr != null && this.curr != this.tail)
        {
            this.curr = this.curr.getLink();
        }
    }

    // returns the value of the current element at the position
    public Type GetValueAt(int pos){
        this.SetPos(pos);
        return this.GetValue();
    }

    // returns the location of the current element (or -1)
    public int GetPos()
    {
        Node<Type> temp = this.head;
        for(int counter = 0; counter < num_items; counter++)
        {
            if(temp == this.curr)
            {
                return counter;
            }
            temp = temp.getLink();
        }
        return -1; // Moved outside the loop
    }


    // returns the value of the current element (or -1)
    public Type GetValue()
    {
        if(this.curr == null)
        {
            return null;
        }
        else
        {
            return this.curr.getData();
        }
    }

    // returns the size of the list
    // size does not imply capacity
    public int GetSize()
    {
        return this.num_items;
    }

    // inserts an item before the current element
    // the new element becomes the current
    // this should not be possible for a full list
    //** */
    public void InsertBefore(Type data)
    {
        Node<Type> n = new Node<Type>(data);
        if(this.head == null)
        {
            this.head = n;
            this.tail = n;
            this.curr = n;
        }
        else
        {
            if(this.curr == this.head)
            {
                n.setLink(this.head);
                this.head = n;
            }
            else
            {
                Node<Type> temp = this.head;
                while(temp.getLink() != this.curr)
                {
                    temp = temp.getLink();
                }
                n.setLink(this.curr);
                temp.setLink(n);
            }
        }
        this.num_items++;
    }

    // inserts an item after the current element
    // the new element becomes the current
    // this should not be possible for a full list
    public void InsertAfter(Type data)
    {
    

        //Creating the new node// 

        Node<Type> n = new Node<Type>();
        n.setData(data);


        if (this.curr == null) {
            this.head = this.tail = this.curr = n;
        }
        else{
        n.setLink(this.curr.getLink());
        this.curr.setLink(n);
        if (this.curr == this.tail){
            this.tail = n;
        }

        this.curr = n;

        }

        this.num_items += 1;
    }

    // removes the current element 
    // this should not be possible for an empty list
    public void Remove()
    {
        if(this.head == null)
        {
            return; // List is empty
        }
        if(this.curr == this.head)
        {
            this.head = this.head.getLink();
            this.curr = this.head;
        }
        else
        {
            Node<Type> temp = this.head;
            while(temp.getLink() != this.curr)
            {
                temp = temp.getLink();
            }
            temp.setLink(this.curr.getLink());
            if(this.curr == this.tail)
            {
                this.tail = temp;
            }
            this.curr = temp;
        }
        this.num_items--;
        if (this.num_items == 0) {
            this.head = this.tail = this.curr = null; // List is empty
        }
    }


    // replaces the value of the current element with the specified value
    // this should not be possible for an empty list
    public void Replace(Type data)
    {
        if(this.head != null)
        {
            this.curr.setData(data);
        }
    }

    // returns if the list is empty
    public boolean IsEmpty()
    {
        if(this.head == null)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    // returns if the list is full
    public boolean IsFull()
    {
        if(this.num_items == MAX_SIZE)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    // returns if two lists are equal (by value)
    public boolean Equals(List<Type> l)
    {
        if(this.num_items != l.num_items)
        {
            return false;
        }
        Node<Type> n = this.head;
        Node<Type> m = l.head;
        while(n != null)
        {
            if(!n.getData().equals(m.getData()))  // Use .equals() instead of !=
            {
                return false;
            }
            n = n.getLink();
            m = m.getLink();
        }
        return true;
    }


    // returns the concatenation of two lists
    // l should not be modified
    // l should be concatenated to the end of *this
    // the returned list should not exceed MAX_SIZE elements
    // the last element of the new list is the current
    public List<Type> Add(List<Type> l)
    {
        List<Type> n = new List<Type>(this);
        Node<Type> m = l.head;
        while(m != null)
        {
            n.InsertAfter(m.getData());
            m = m.getLink();
        }
        return n;
    }

    // returns a string representation of the entire list (e.g., 1 2 3 4 5)
    // the string "NULL" should be returned for an empty list
    public String toString()
    {
        if(this.head == null)
        {
            return "NULL";
        }
        else
        {
            Node<Type> n = this.head;
            String s = "";
            while(n != null)
            {
                s += n.getData() + " ";
                n = n.getLink();
            }
            return s;
        }
    }
}