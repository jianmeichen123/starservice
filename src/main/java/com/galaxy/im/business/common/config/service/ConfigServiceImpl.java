package com.galaxy.im.business.common.config.service;


import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.galaxy.im.bean.common.Config;
import com.galaxy.im.business.common.config.dao.ConfigDao;
import com.galaxy.im.common.db.IBaseDao;
import com.galaxy.im.common.db.service.BaseServiceImpl;


@Service
public class ConfigServiceImpl extends BaseServiceImpl<Config> implements ConfigService{
	
	@Autowired
	private ConfigDao configDao;

	@Override
	protected IBaseDao<Config, Long> getBaseDao() {
		return this.configDao;
	}

	@Override
	@Transactional
	public Config createCode() throws Exception {
		Config config = configDao.selectById(1L);
		config.setValue(String.valueOf(Integer.parseInt(config.getValue()) + 1));
		configDao.updateById(config);
		return config;
	}
	private ReentrantLock lock = new ReentrantLock();
	@Override
	public Config getByKey(String key, boolean createIfNotExist) throws Exception {
		Config config = null;
		try
		{
			lock.lock();
			Config query = new Config();
			query.setKey(key);
			List<Config> list = configDao.selectList(query);
			if(list == null || list.size() == 0)
			{
				if(createIfNotExist)
				{
					config = new Config();
					config.setKey(key);
					config.setValue("1");
					configDao.insert(config);
				}
				
			}
			else
			{
				config = list.iterator().next();
				config.setValue(String.valueOf(Integer.parseInt(config.getValue()) + 1));
				configDao.updateById(config);
			}
		}
		catch(Exception e)
		{
			throw new Exception("查询config信息失败:key="+key,e);
		}
		finally
		{
			lock.unlock();
		}
		return config;
	}
	

}
