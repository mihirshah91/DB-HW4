import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

/** 
 * You can modify this class as you see fit, as long as you correctly update the
 * global centroids.
 */
public class ClusterToPointReducer extends Reducer<Text, Text, Text, Text>
{
	private int counter=0;
	public void reduce(Text key, Iterable<Text> values, Context context)
            throws IOException, InterruptedException
        {
		System.out.println("inside reducer number :" + counter);
            ArrayList<Point> pointsincluster = new ArrayList<Point>();
         	System.out.println("old centroid is " + key.toString() ) ;
         	int position=findPosition(key);
            
            for (Text p : values)
            {
            	System.out.println("point is"+ p.toString());
            	pointsincluster.add(new Point(p.toString()));
            }
               Point new_centroid=new Point(pointsincluster.get(0));
            for (int i=0; i<pointsincluster.get(0).getDimension();i++)
            {
            	float temp_sum=0;
            	for (int j=0; j< pointsincluster.size();j++)
            		temp_sum+= pointsincluster.get(j).coridnates[i];
            	new_centroid.coridnates[i]= temp_sum/pointsincluster.size();
            }
            System.out.println("New centroid is :"+ new_centroid);
            System.out.println("===========================");
            KMeans.centroids.set(counter, new_centroid);
            this.counter++;
            context.write(new Text(new_centroid.toString()), new Text(Integer.toString(counter)));
          
        }

        public static int findPosition(Text key)
        {
        	Point searchPoint= new Point(key.toString());
        	for (int i=0;i< UpdateJobRunner.centroids_old.size();i++)
        	{
        		
        		
        	}
        	return 0;

        }



}
