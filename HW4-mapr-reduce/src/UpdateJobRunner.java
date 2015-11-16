import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.input.KeyValueTextInputFormat;

import java.io.IOException;
import java.util.ArrayList;

public class UpdateJobRunner
{
    /**
     * Create a map-reduce job to update the current centroids.
     * @param jobId Some arbitrary number so that Hadoop can create a directory "<outputDirectory>/<jobname>_<jobId>"
     *        for storage of intermediate files.  In other words, just pass in a unique value for this
     *        parameter.
     * @param The input directory specified by the user upon executing KMeans, in which the points
     *        to find the KMeans point files are located.
     * @param The output directory for which to write job results, specified by user.
     * @precondition The global centroids variable has been set.
     */

    public static ArrayList<Point> centroids_old=null;
    @SuppressWarnings("deprecation")
	public static Job createUpdateJob(int jobId, String inputDirectory, String outputDirectory)
        throws IOException
    {
        Job init_job = new Job(new Configuration(), "clustering");
        init_job.setJarByClass(UpdateJobRunner.class);
        init_job.setMapperClass(PointToClusterMapper.class);
        init_job.setMapOutputKeyClass(Text.class);
        init_job.setMapOutputValueClass(Text.class);
        init_job.setReducerClass(ClusterToPointReducer.class);
        init_job.setOutputKeyClass(Text.class);
        init_job.setOutputValueClass(Text.class);
        FileInputFormat.addInputPath(init_job, new Path(inputDirectory));
        FileOutputFormat.setOutputPath(init_job, new Path(outputDirectory));
        init_job.setInputFormatClass(KeyValueTextInputFormat.class);
        System.out.println("Job created");
        
        return init_job;
        //return null;
    }

    /**
     * Run the jobs until the centroids stop changing.
     * Let C_old and C_new be the set of old and new centroids respectively.
     * We consider C_new to be unchanged from C_old if for every centroid, c, in 
     * C_new, the L2-distance to the centroid c' in c_old is less than [epsilon].
     *
     * Note that you may retrieve publically accessible variables from other classes
     * by prepending the name of the class to the variable (e.g. KMeans.one).
     *
     * @param maxIterations   The maximum number of updates we should execute before
     *                        we stop the program.  You may assume maxIterations is positive.
     * @param inputDirectory  The path to the directory from which to read the files of Points
     * @param outputDirectory The path to the directory to which to put Hadoop output files
     * @return The number of iterations that were executed.
     */
    public static int runUpdateJobs(int maxIterations, String inputDirectory,
        String outputDirectory) throws Exception
    {
      System.out.println("inside update job runner");
        //System.exit(1);
     
    	
        
    	int i,j;
        for(i=1;i<maxIterations;i++)
        {
        	System.out.println("insie interation number: "+i);
       
        centroids_old=copyCentroids();
        System.out.println("Initial centroids are :");
        printCentroid(centroids_old);
        //System.out.println("old centroid="+ centroids_old.get(0).toString());

        Job initJob = null;
		try {
			initJob = createUpdateJob(i,inputDirectory, "file"+i);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        initJob.waitForCompletion(true);
        System.out.println("job number "+i+" completed");
        System.out.println("New centoroids are: ");
        printCentroid(KMeans.centroids);
        
        for (j=0;j< KMeans.centroids.size();j++)
            { System.out.println("Comparing " + KMeans.centroids.get(j)+ " and "+ centroids_old.get(j));
            if(Point.distance(KMeans.centroids.get(j),centroids_old.get(j)) > 0.0001)
            	break;
        }
        if(j==KMeans.centroids.size())
            return i;
        
        
    }
        return i;
}
    
    
public static void printCentroid(ArrayList<Point> centroids)
{
	for (Point p : centroids)
	{
		System.out.println(p.toString());
	}
	System.out.println("=======================");
}
 
public static ArrayList<Point> copyCentroids()
{
    ArrayList<Point> temp= new ArrayList<Point>();
    for(int i=0;i<KMeans.centroids.size();i++)
    {
    	//System.out.println("i="+i);
    	//System.out.println("cen="+KMeans.centroids.get(i).toString());
    	temp.add(i, KMeans.centroids.get(i));
    }
    return temp;
}

}