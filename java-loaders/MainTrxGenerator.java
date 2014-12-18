import com.couchbase.client.CouchbaseClient;
import com.couchbase.client.CouchbaseConnectionFactoryBuilder;
import com.couchbase.client.CouchbaseConnectionFactory;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import net.spy.memcached.internal.OperationFuture;
import net.spy.memcached.internal.OperationCompletionListener;
import net.spy.memcached.internal.GetFuture;
import net.spy.memcached.internal.GetCompletionListener;
import java.util.Random;

import java.nio.charset.Charset;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;

//ScheduledExecutor classes
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public class MainTrxGenerator {

  public static void main(String[] args) throws Exception {

    //Thread pool will be used to re-issue failed ops
    ScheduledExecutorService schExSvc = Executors.newScheduledThreadPool(4);

    // ******* Client Object Setup and Connection **********
    // *****************************************************

    // (Subset) of nodes in the cluster to establish a connection
    List<URI> hosts = Arrays.asList(
      new URI("http://ec2-54-76-39-165.eu-west-1.compute.amazonaws.com:8091/pools")
    );
 
    // Name of the Bucket to connect to
    String bucket = "default";
 
    // Password of the bucket (empty) string if none
    String password = "";

    CouchbaseConnectionFactoryBuilder cfb = new CouchbaseConnectionFactoryBuilder();

    // Ovveride default values on CouchbaseConnectionFactoryBuilder
    // For example - wait up to 5 seconds for an operation to succeed
    cfb.setOpTimeout(5000);
    cfb.setOpQueueMaxBlockTime(30000);

    CouchbaseConnectionFactory cf = cfb.buildCouchbaseConnection(hosts, bucket, "");

    CouchbaseClient client = new CouchbaseClient(cf); 

    // ******* Create some data to use as our value ********
    // *****************************************************

    // Create a string to use as our 'value' for storing
    // Loop adding multiples of that to give larger value for testing
    // Creating 1000 char string (100x 10 characters) here by default
	/*
    String value = "";
    for (int i = 0; i < 100; i++) {
      value += "0123456789";
    }
	*/

	FileReader fr = new FileReader();
	String value = fr.readFile("trx-data.csv",Charset.defaultCharset());
	System.out.println(value);

    System.out.println("Length of value string: " + value.getBytes("UTF-8").length);

    // Set the number of key/value pairs to create
    int iterations = 1000000; //live
    //int iterations = 10000; //local test

    // This is just a class to track operations to aid with debugging
    OpTracker opTracker = new OpTracker();


    // ******* Create and issue async sets initial data ****
    // *****************************************************

    long phaseOneStartTime = System.currentTimeMillis();

    // We want to limit the maximum number of operations we issue in one go
    // This is just so we don't blow the size of our java heap up massively
    // Do this by simply isuing a max 1/10th of ops at any one time.
    // When the 1/10th is complete, the latch will fire and next 1/10th started
	LocalDate date = new LocalDate("2014-01-01");

	int itCount = 0;
    for (int s=1; s<=100; s++)
    {
	  final CountDownLatch latch = new CountDownLatch(iterations*12/100);

	  for (int i = 1; i <= (iterations/100); i++) {
		  itCount++;
		while(date.getYear() < 2015) {
          	String key = "acct:" + itCount + "-trx-" + date.getYear() + "-" + date.getMonthOfYear(); 
			date = date.plusMonths(1);
          	try {
  	        	OperationFuture<Boolean> future = client.set(key, value);
            	opTracker.setScheduled(key);
	   	    	future.addListener(new MyListener(client,latch, 0, value, key, schExSvc, opTracker));
          	}
          	catch (Exception e) {
            	System.out.println("exception: " + e.getMessage());
            	throw e;
         	 }
		}
		date = date.parse("2014-01-01");
	  }
	  latch.await();

      //We've finished that batch, so hint to system now is good time for gc before next batch
      System.gc();
    }

    long phaseOneEndTime = System.currentTimeMillis();

    System.out.println("completed " + iterations + " *SET* opeerations in " + ((phaseOneEndTime - phaseOneStartTime)/1000) + " seconds" );
    //opTracker.printTracker();

    // Shutting down properly
    client.shutdown();
    // Shutdown ScheduledExecutor
    schExSvc.shutdown(); 
  }
}
