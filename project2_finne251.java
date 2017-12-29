class Poly
{
	private class Term
	{
		private int coef;
		private int expo;
		private Term next;

		private Term(int coef, int expo, Term next)
		{
			this.coef = coef;
			this.expo = expo;
			this.next = next;

		}
	}

	private Term head;

	public Poly()
	{
		head = new Term(0, 0, null);
		head.next = head;

	}
	
	public Poly term(int coef, int expo)
	{	
		Term left = head;
		Term right = head.next;
		
		if(expo < 0)
		{
			throw new IllegalArgumentException("bad exponenet");
		}
		else
		{
			
			while(right != head)
			{
				if(right.expo == expo)
				{
					throw new IllegalArgumentException("bad operation");
				}
					
				else if(right.expo < expo)
				{
					Term value = new Term(coef, expo, right);
					left.next = value;
					return this;
				}
				else
				{

					left = right;
					right = right.next;
				}
	
				
			}
			
				Term value = new Term(coef, expo, head);
				left.next = value;
		

		}
		return this;
		
	}

	public Poly plus(Poly that)
	{
		Poly there = new Poly();
		Term left = head;
		Term right = head.next;
		Term temp = that.head;
		
		while(right!= head)
		{
			there.term(right.coef, right.expo);
			left = right;
			right = right.next;
		}

		

		while(temp.next != that.head)
		{
			there.add(temp.next.coef, temp.next.expo);
			temp = temp.next;
		}
		
		return there;
		

	}

	private void add(int coef, int expo)
	{
		
		if(expo < 0)
		{
			throw new IllegalArgumentException("bad exponent");

		}
		

		else
		{
			Term left = head;
			Term right = head.next;
			while(right != head)
			{
				if(right.expo == expo)
				{
					right.coef = right.coef + coef;
					if(right.coef == 0)
					{
						right = right.next;

					}
					return;
				}
				else if(right.expo < expo)
				{
					Term value = new Term(coef, expo, right);
					left.next = value;
					return;
				}
				else
				{
					left = right;
					right = right.next;
				}
			}
		}

		
		
	}

	public Poly minus()
	{
		Poly there = new Poly();
		Term left = head;
		Term right = head.next;

		while(right != head)
		{
			there.term(-right.coef, right.expo);
			left = right;
			right = right.next;
		}
		
		return there;
		

	}

	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		Term left = head;
		Term right = head.next;

		builder.append(right.coef + "x");
		builder.append(right.expo);
		left = right;
		right = right.next;

		while(right != head)
		{
			
				if(right.coef < 0)
				{
					builder.append(" - ");
					builder.append(-right.coef + "x");
					builder.append(right.expo);
				}
				else 
				{
					builder.append(" + ");
					builder.append(right.coef + "x");
					builder.append(right.expo);
				}
				left = right;
				right = right.next;	
			
			
		}
		

		if(builder.toString().equals(""))
		{
			return "0";
		}
		else
		{
			return builder.toString();
		}
		

		
	}


}

class PollyEsther  
{  
  public static void main(String[] args)  
  {  
    Poly p0 = new Poly();  
    Poly p1 = new Poly().term(1, 3).term(7, 1).term(1, 2);  
    Poly p2 = new Poly().term(-2, 1).term(3, 2);
    Poly p3 = new Poly().term(1, 1).term(1, 2).term(1, 3);
    Poly p4 = new Poly().term(5, 1).term(1, 9).term(2, 3);
    Poly p5 = p1.minus();
    Poly p6 = p2.minus();

  
  
    System.out.println(p0);           //  0  
    System.out.println(p1);           //  1x3 + 1x2 + 1x1  
    System.out.println(p2);           //  3x2 - 2x1  
    System.out.println(p3);			//1x3 + 1x2 + 1x1
    System.out.println(p4);			//1x9 + 2x3	+ 5x1
  	System.out.println(p5);		// -1x3 - 1x2 - 7x1
  	System.out.println(p6);			// -3x2 + 2x1
  
    System.out.println(p1.plus(p2));  //  1x3 + 4x2 + 5x1  
    System.out.println(p4.plus(p3));	//1x9 + 3x3 + 1x2 + 6x1
    System.out.println(p5.plus(p4));	//1x9 + 1x3 - 1x2 - 2x1
    System.out.println(p4.plus(p1));	//1x9 + 3x3 + 1x2 + 7x1
   

   
  }  
}
