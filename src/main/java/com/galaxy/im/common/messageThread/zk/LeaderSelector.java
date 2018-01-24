package com.galaxy.im.common.messageThread.zk;

import java.util.Date;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.galaxy.im.common.db.IdGenerator;

@Component
public class LeaderSelector implements Watcher, InitializingBean{
	private static final Logger logger = LoggerFactory.getLogger(LeaderSelector.class);
	private String address;
	private String path;
	private String id;
	private String leaderId;
	private boolean isLeader;
	ZooKeeper zk = null;
	
	@Autowired
	private Environment env;

	public void start(){
		try
		{
			
			this.address = env.getProperty("cluster.zk.address"); 
			this.path = "/leader_" + env.getProperty("cluster.zk.product");
			this.id = IdGenerator.generateId(LeaderSelector.class) + "_" + new Date().getTime();
			
			zk = ZKUtils.connect(address);
			try{
				zk.create(path, id.getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
				zk.setData(path, id.getBytes(), 1);
			} catch (KeeperException e){
				logger.error(String.format("节点[%s]已经存在", path));
			}
			byte[] value = zk.getData(path, this, null);
			leaderId = new String(value);
			if(leaderId.equals(id)){
				isLeader = true;
			}
			logger.debug(String.format("选主结束,当前ID=%s, Leader节点ID=%s, 当前节点是否为Leader=%s", id,leaderId,isLeader));
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public void stop(){
		try{
			ZKUtils.close(zk);
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	@Override
	public void process(WatchedEvent event){
		if(event.getType() == Event.EventType.NodeDeleted){
			logger.debug(String.format("Leader节点[ID=%s]失效，开始重新选取", leaderId));
			start();
		}
	}

	@Override
	public void afterPropertiesSet() throws Exception{
		start();
	}

	public String getId(){
		return id;
	}

	public String getLeaderId(){
		return leaderId;
	}

	public boolean isLeader(){
		return isLeader;
	}
}
