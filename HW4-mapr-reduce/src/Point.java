import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.io.WritableComparable;
/**
 * A Point is some ordered list of floats.
 * 
 * A Point implements WritableComparable so that Hadoop can serialize
 * and send Point objects across machines.
 *
 * NOTE: This implementation is NOT complete.  As mentioned above, you need
 * to implement WritableComparable at minimum.  Modify this class as you see fit.
 */
public class Point implements WritableComparable  {
    /**
     * Construct a Point with the given dimensions [dim]. The coordinates should all be 0.
     * For example:
     * Constructing a Point(2) should create a point (x_0 = 0, x_1 = 0)
     */
	//List<Float> x =new ArrayList<Float>();
	float coridnates[];
	
    public Point(int dim)
    {
    	coridnates=new float[dim];
    	    	      
    }

    /**
     * Construct a point from a properly formatted string (i.e. line from a test file)
     * @param str A string with coordinates that are space-delimited.
     * For example: 
     * Given the formatted string str="1 3 4 5"
     * Produce a Point {x_0 = 1, x_1 = 3, x_2 = 4, x_3 = 5}
     */
    public Point(String str)
    {
    	String temp[]=str.split(" ");
    	coridnates=new float[temp.length];
    	for (int i=0;i<temp.length;i++)
    	{
    		coridnates[i]= Float.parseFloat(temp[i]);
    	}
    	        
    }

    /**
     * Copy constructor
     */
    public Point(Point other)
    {
        System.out.println("TODO");
        this.coridnates= other.coridnates;
      
    }

    /**
     * @return The dimension of the point.  For example, the point [x=0, y=1] has
     * a dimension of 2.
     */
    public int getDimension()
    {
        return this.coridnates.length;
    }

    /**
     * Converts a point to a string.  Note that this must be formatted EXACTLY
     * for the autograder to be able to read your answer.
     * Example:
     * Given a point with coordinates {x=1, y=1, z=3}
     * Return the string "1 1 3"
     */
    public String toString()
    {
    	String pointString="";
        for (int i=0;i<this.coridnates.length;i++)
        {
        	pointString=pointString + " " + this.coridnates[i]; 
        }
        return pointString.trim();
    }

    /**
     * One of the WritableComparable methods you need to implement.
     * See the Hadoop documentation for more details.
     * You should order the points "lexicographically" in the order of the coordinates.
     * Comparing two points of different dimensions results in undefined behavior.
     */
    public int compareTo(Object o)
    {   
        
        return 0;
    }

    /**
     * @return The L2 distance between two points.
     */
    public static final float distance(Point x, Point y)
    {
        
        float squaresum=(float)0.0;
        for (int i=0;i<x.coridnates.length;i++)
        {
            squaresum+= (x.coridnates[i] - y.coridnates[i])*(x.coridnates[i] - y.coridnates[i]);
        }

        float distance= (float)Math.sqrt(squaresum);
        return distance;
    }

    /**
     * @return A new point equal to [x]+[y]
     */
    public static final Point addPoints(Point x, Point y)
    {
       if (x.getDimension() != y.getDimension())
       {
    	   System.out.println("can't be added");
    	   return null;
       }
       else
       {
    	   Point temp=new Point(x.getDimension());
    	   for (int i=0;i< temp.getDimension();i++)
    		   temp.coridnates[i]=x.coridnates[i]+y.coridnates[i];
    	   return temp;
       }
    
    }

    /**
     * @return A new point equal to [c][x]
     */
    public static final Point multiplyScalar(Point x, float c)
    {
    	for(int i=0;i<x.getDimension();i++)
    	{
    		x.coridnates[i]*=c;
    	}
    	return x;
    }

	@Override
	public void readFields(DataInput arg0) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void write(DataOutput arg0) throws IOException {
		// TODO Auto-generated method stub
		
	}
}
