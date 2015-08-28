package ngordnet;
import edu.princeton.cs.introcs.In;
public class ReadDemo
{
 	public ReadDemo()
 	{
 		In in1 = new In("synsets11.txt");
 		System.out.println(in1.readInt());
 	} 
 	public static void main(String[] args)
 	{
 		ReadDemo r=new ReadDemo();
 	}
}