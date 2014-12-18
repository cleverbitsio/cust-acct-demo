
import com.couchbase.client.CouchbaseClient;
import java.util.concurrent.CountDownLatch;
import net.spy.memcached.internal.GetFuture;
import net.spy.memcached.internal.GetCompletionListener;

import net.spy.memcached.internal.OperationFuture;
import net.spy.memcached.internal.OperationCompletionListener;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import java.util.Date;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;


import com.google.gson.Gson;

    class TrxAddingListener extends MyGetListener {
      CountDownLatch setLatch;

	  Account acc;

      TrxAddingListener(CouchbaseClient c, CountDownLatch l, int b, Object v, String k,
          ScheduledExecutorService s, CountDownLatch sLatch, OpTracker ot) {
        super(c, l, b, v, k, s, ot);
        setLatch = sLatch;
      }

      public void doPostProcess(GetFuture<?> future) throws Exception {
    	 Gson gson = new Gson();

        try {
		  acc = gson.fromJson(future.get().toString(), Account.class);
        }
        catch (Exception e) {
          System.out.println("exception: " + e.getMessage());
          System.exit(1);
        }

        try {
          acc.setAccount_name("TOM GREEN");
		  int credit = 2000;
		  Transaction trx = new Transaction(new Date(), "BAC", "EmployeePayRoll", credit, 0);
		  acc.getRecent_trxns().add(trx);
		  if (acc.getRunning_balance() == null) {
			acc.setRunning_balance(acc.getBalance() + credit);
		  }
		  else {
			acc.setRunning_balance(acc.getRunning_balance() + credit);
		  }
		  value = gson.toJson(acc);
        }
        catch (Exception e) {
          System.out.println("exception reversing string: " + e.getMessage());
          System.exit(1);
        }

        try {
    	  OperationFuture<Boolean> set_future = client.set(key, value);
          opTracker.setScheduled(key);
    	  set_future.addListener(new MyListener(client, setLatch, 0, value, key, sch, opTracker));
        }
        catch (Exception e) {
          System.out.println("exception: " + e.getMessage());
          System.exit(1);
        }
      } 

      public Callable getCallableForRetry() {
        return new TrxAddingCallable(this, opTracker);  
      }
    } //TrxAddingListener
