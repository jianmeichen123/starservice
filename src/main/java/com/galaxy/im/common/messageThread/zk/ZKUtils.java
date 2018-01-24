package com.galaxy.im.common.messageThread.zk;

import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.Watcher.Event.KeeperState;

public class ZKUtils
{
	public static ZooKeeper connect(String address) throws Exception
	{
		final CountDownLatch latch = new CountDownLatch(1);
		ZooKeeper zk = new ZooKeeper(address, 5000, new Watcher(){
			@Override
			public void process(WatchedEvent event)
			{
				if(event.getState() == KeeperState.SyncConnected)
				{
					latch.countDown();
				}
			}
		}); 
		latch.await();
		return zk;
	}
	
	public static void close(ZooKeeper zk) throws Exception
	{
		if(zk != null)
		{
			zk.close();
		}
	}
	
}
