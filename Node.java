/* ***************************************************
 * Michael Krueger
 * 09/29/2024
 *
 * Node Class - handles any form of data
 *************************************************** */

public class Node<Type>
{
	private Type data;
	private Node<Type> link;

	// Default constructor (optional, but useful if you need to initialize with no data)
    public Node() {
        this.data = null;
        this.link = null;
    }
	
	// constructor
	public Node(Type data)
	{
		this.data = data;
		this.link = null;
	}


	// accessor and mutator for the data component
	public Type getData()
	{
		return this.data;
	}

	public void setData(Type data)
	{
		this.data = data;
	}

	// accessor and mutator for the link component
	public Node<Type> getLink()
	{
		return this.link;
	}

	public void setLink(Node<Type> link)
	{
		this.link = link;
	}
}
