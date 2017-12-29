import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;



class LinkedList
{
	class LLNode 
	{
		public int line;
		public LLNode next;
		
		public LLNode(int line, LLNode next)
		{
			this.line = line;
			this.next = null;
		}
	}
	
	public LLNode head;
	
	public LinkedList(int line)
	{
		head =  new LLNode(line, null);
	}
	
	
	public void put(int line)
	{
		LLNode left = head;
		LLNode right = head.next;
		while(right != null)
		{
			if(line == left.line)
			{
				return;
			}
			else if(line > left.line)
			{
				left.next = new LLNode(line, right);
				return;
			}
			left = right;
			right = right.next;
	
		}
		left.next = new LLNode(line, null);
	}
	
	public void get(LLNode head)
	{
		LLNode temp = head;
		
		while(temp != null)
		{
			System.out.format("%05d", temp.line);
			temp = temp.next;
		}
	}
		
}
		
		
		
	


class BinarySearchTree
{
	
	class Node 
	{
		private String key;
		public LinkedList value;
		private Node left;
		private Node right;
		
		public Node(String key, int number)
		{
			this.key = key;
			value = new LinkedList(number);
			left = null;
			right = null;
		}
	}
	
	public Node root;
	
	public BinarySearchTree()
	{
		root = new Node("", 0);
	}
	
	public void add(int number, String key)
	{
		Node temp = root;
		if(temp.key == "")
		{
			root = new Node(key, number);
		}
		else 
		{
			while(true)
			{
				int lexi = key.compareTo(temp.key);
				
				if(lexi < 0)
				{
					if(temp.left == null)
					{
						temp.left = new Node(key, number);
						return;
					}
					else
					{
						temp = temp.left;
					}
				}
				else if(lexi > 0)
				{
					if(temp.right == null)
					{
						temp.right = new Node(key, number);
						return;
					}
					else
					{
						temp = temp.right;
					}
				}
				else
				{
					temp.value.put(number);
					return;
				}
				
			}
		}
	}
	
	
	public void traverse(Node root)
	{
		Node temp = root;
		if(temp != null )
		{	
			
			traverse(temp.left);
			
			System.out.print(temp.key + "     ");
			temp.value.get(temp.value.head);
			System.out.println();
		
			traverse(temp.right);
		}
	}
}

//
//  NOMENCLATOR. Read names from a Java source file.
//
//    James Moen
//    04 Dec 17
//
//  The version of 01 Dec 17 could not handle /*...*/ comments that extend over
//  multiple lines, but this one can.
//

//  NOMENCLATOR. Read names from a Java source file.  It acts like an ITERATOR,
//  but it has two NEXT methods: one for names, and one for the line numbers of
//  those names.

class Nomenclator
{
  private char              ch;                 //  Current CHAR from READER.
  private static final char eof = (char) 0x00;  //  End of file sentinel.
  private static final char eol = (char) 0x0A;  //  End of line sentinel.
  private int               index;              //  Index into LINE.
  private String            line;               //  Current LINE from READER.
  private boolean           listing;            //  Are we listing the file?
  private String            name;               //  Current name.
  private int               number;             //  Current line number.
  private String            path;               //  Pathname to READER's file.
  private BufferedReader    reader;             //  Read CHARs from here.

//  Constructor. Initialize a new NOMENCLATOR that reads from a text file whose
//  pathname is PATH. If we can't open it then throw an exception. LISTING says
//  whether we should copy the file to standard output as we read it.

  public Nomenclator(String path, boolean listing)
  {
    try
    {
      index = 0;
      line = "";
      this.listing = listing;
      number = 0;
      this.path = path;
      reader = new BufferedReader(new FileReader(path));
      skipChar();
    }
    catch (IOException ignore)
    {
      throw new IllegalArgumentException("Can't open '" + path + "'.");
    }
  }

//  HAS NEXT. Test if there's another name waiting to be read. If so, then read
//  it, so NEXT NAME and NEXT NUMBER can return it and its line number later.

  public boolean hasNext()
  {
    while (true)
    {
      if (Character.isJavaIdentifierStart(ch))
      {
        skipName();
        return true;
      }
      else if (Character.isDigit(ch))
      {
        skipNumber();
      }
      else
      {
        switch (ch)
        {
          case eof:
          {
            return false;
          }
          case '"':
          case '\'':
          {
            skipDelimited();
            break;
          }
          case '/':
          {
            skipComment();
            break;
          }
          default:
          {
            skipChar();
            break;
          }
        }
      }
    }
  }

//  NEXT NAME. If HAS NEXT was true, then return the next name. If HAS NEXT was
//  false, then return an undefined string.

  public String nextName()
  {
    return name;
  }

//  NEXT NUMBER. If HAS NEXT was true, then return the line number on which the
//  next name appears. If HAS NEXT was false, then return an undefined INT.

  public int nextNumber()
  {
    return number;
  }

//  SKIP CHAR. If no more CHARs remain unread in LINE, then read the next line,
//  adding an EOL at the end. If no lines can be read, then read a line with an
//  EOF char in it. Otherwise just read the next char from LINE and return it.

  private void skipChar()
  {
    if (index >= line.length())
    {
      index = 0;
      number += 1;
      try
      {
        line = reader.readLine();
        if (line == null)
        {
          line = "" + eof;
        }
        else
        {
          if (listing)
          {
            System.out.format("%05d ", number);
            System.out.println(line);
          }
          line += eol;
        }
      }
      catch (IOException ignore)
      {
        line = "" + eof;
      }
    }
    ch = line.charAt(index);
    index += 1;
  }

//  SKIP COMMENT. We end up here if we read a '/'. If it is followed by another
//  '/', or by a '*', then we skip a comment. We must skip comments so that any
//  names that appear in them will be ignored.

  private void skipComment()
  {
    skipChar();
    if (ch == '*')
    {
      skipChar();
      while (true)
      {
        if (ch == '*')
        {
          skipChar();
          if (ch == '/')
          {
            skipChar();
            return;
          }
        }
        else if (ch == eof)
        {
          return;
        }
        else
        {
          skipChar();
        }
      }
    }
    else if (ch == '/')
    {
      skipChar();
      while (true)
      {
        if (ch == eof)
        {
          return;
        }
        else if (ch == eol)
        {
          skipChar();
          return;
        }
        else
        {
          skipChar();
        }
      }
    }
  }

//  SKIP DELIMITED. Skip a string constant or a character constant, so that any
//  names that appear inside them will be ignored.  Throw an exception if there
//  is a missing delimiter at the end.

  private void skipDelimited()
  {
    char delimiter = ch;
    skipChar();
    while (true)
    {
      if (ch == delimiter)
      {
        skipChar();
        return;
      }
      else
      {
        switch (ch)
        {
          case eof:
          case eol:
          {
            throw new IllegalStateException("Bad string in '" + path + "'.");
          }
          case '\\':
          {
            skipChar();
            if (ch == eol || ch == eof)
            {
              throw new IllegalStateException("Bad string in '" + path + "'.");
            }
            else
            {
              skipChar();
            }
            break;
          }
          default:
          {
            skipChar();
            break;
          }
        }
      }
    }
  }

//  SKIP NAME. Skip a name, but convert it to a STRING, stored in NAME.

  private void skipName()
  {
    StringBuilder builder = new StringBuilder();
    while (Character.isJavaIdentifierPart(ch))
    {
      builder.append(ch);
      skipChar();
    }
    name = builder.toString();
  }

//  SKIP NUMBER. Skip something that might be a number. It starts with a digit,
//  followed by zero or more letters and digits. We must do this so the letters
//  aren't treated as names.

  private void skipNumber()
  {
    skipChar();
    while (Character.isJavaIdentifierPart(ch))
    {
      skipChar();
    }
  }

//  MAIN. Get a file pathname from the command line. Read a series of names and
//  their line numbers from the file, and write them one per line. For example,
//  the command "java Nomenclator Nomenclator.java" reads names from the source
//  file you are now looking at. This method is only for debugging!

  public static void main(String [] args)
  {
    Nomenclator reader = new Nomenclator(args[0], false);
    while (reader.hasNext())
    {
	
      System.out.println(reader.nextNumber() + " " + reader.nextName());
    }
  }
}



class Main
{
	public static void main(String[] args)
	{
	BinarySearchTree tree = new BinarySearchTree();  
	Nomenclator nomenclator = new Nomenclator("lab5_finne251.java", true);  

	while (nomenclator.hasNext())  
	{  
	  tree.add(nomenclator.nextNumber(), nomenclator.nextName());  
	}  
		tree.traverse(tree.root);
	}
	
}


//OUTPUT is from lab5 this semester

//00001 class Polygon  
//00002 {  
//00003   private int[] sideLengths;  
//00004   
//00005   public Polygon(int sides, int ... lengths)  
//00006   {  
//00007     int index = 0;  
//00008     sideLengths = new int[sides];  
//00009     for (int length: lengths)  
//00010     {  
//00011       sideLengths[index] = length;  
//00012       index += 1;  
//00013     }  
//00014   }  
//00015   
//00016   public int side(int number)  
//00017   {  
//00018     return sideLengths[number];  
//00019   }  
//00020   
//00021   public int perimeter()  
//00022   {  
//00023     int total = 0;  
//00024     for (int index = 0; index < sideLengths.length; index += 1)  
//00025     {  
//00026       total += side(index);  
//00027     }  
//00028     return total;  
//00029   }  
//00030 }
//00031 
//00032 class Rectangle extends Polygon
//00033 {
//00034 	public Rectangle(int a, int b)
//00035 	{
//00036 		super(4, a, b, a, b);
//00037 	}
//00038 	
//00039 	public int area()
//00040 	{
//00041 		int surface = 0;
//00042 		surface = side(1)*side(2);
//00043 		return surface;
//00044 	}
//00045 }
//00046 
//00047 class Square extends Rectangle
//00048 {
//00049 	public Square(int a)
//00050 	{
//00051 		super(a, a);
//00052 	}
//00053 	
//00054 }
//00055 


//
//Polygon     0000100032
//Rectangle     0003200047
//Square     0004700049
//a     0003400051
//area     00039
//b     0003400036
//class     0000100047
//extends     0003200047
//for     0000900024
//index     0000700026
//int     0000300049
//length     0000900024
//lengths     0000500009
//new     00008
//number     0001600018
////perimeter     00021
//private     00003
//public     0000500049
//return     0001800043
//side     0001600042
//sideLengths     0000300024
//sides     0000500008
//super     0003600051
//surface     0004100043
//total     0002300028
//
			
	
	
