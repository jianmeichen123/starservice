package com.galaxy.im.business.contracts.dao;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.galaxy.im.bean.contracts.ContractsBean;
import com.galaxy.im.common.BeanUtils;
import com.galaxy.im.common.db.BaseDaoImpl;
import com.galaxy.im.common.exception.DaoException;

@Repository
public class ContractsDaoImapl extends BaseDaoImpl<ContractsBean, Long> implements IContractsDao{
	private final Logger log = LoggerFactory.getLogger(ContractsDaoImapl.class);
	
	public ContractsDaoImapl(){
		super.setLogger(log);
	}

	/**
	 * 查询联系人是否存在
	 */
	@Override
	public ContractsBean selectPersonByName(ContractsBean bean) {
		try{
			Map<String, Object> map = BeanUtils.toMap(bean);
			return sqlSessionTemplate.selectOne(getSqlName("selectPersonByName"),map);
		}catch(Exception e){
			log.error(getSqlName("selectPersonByName"),e);
			throw new DaoException(e);
		}
	}

	/**
	 * 保存联系人
	 */
	@Override
	public Long savePerson(ContractsBean bean) {
		try{
			sqlSessionTemplate.insert(getSqlName("savePerson"),bean);
			return bean.getId();
		}catch(Exception e){
			log.error(getSqlName("savePerson"),e);
			throw new DaoException(e);
		}
	}

	/**
	 * 更新联系人
	 */
	@Override
	public Long updatePerson(ContractsBean bean) {
		try{
			sqlSessionTemplate.update(getSqlName("updatePerson"),bean);
			return bean.getId();
		}catch(Exception e){
			log.error(getSqlName("updatePerson"),e);
			throw new DaoException(e);
		}
	}

	/**
	 * 联系人列表
	 */
	@Override
	public List<ContractsBean> selectPersonList(ContractsBean bean) {
		try{
			Map<String, Object> map = BeanUtils.toMap(bean);
			return sqlSessionTemplate.selectList(getSqlName("selectPersonByName"),getPageMap(map));
		}catch(Exception e){
			log.error(getSqlName("selectPersonList"),e);
			throw new DaoException(e);
		}
	}

}
