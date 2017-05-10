package com.galaxy.im.common.db;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import com.galaxy.im.common.BeanUtils;
import com.galaxy.im.common.exception.DaoException;

public abstract class BaseDaoImpl<T extends PrimaryKeyObject<ID>, ID extends Serializable> implements IBaseDao<T, ID> {
	//private Logger log = LoggerFactory.getLogger(BeanUtils.getGenericClass(BaseDaoImpl.class));
	public static final String SQLNAME_SEPARATOR = ".";
	private String sqlNamespace = getDefaultSqlNamespace();
	private Logger log = null;
	
	@Autowired
	protected SqlSessionTemplate sqlSessionTemplate;
	
//	public BaseDaoImpl(){
//		log = LoggerFactory.getLogger(BeanUtils.getGenericClass(BaseDaoImpl.class));
//	}

	@Override
	public T selectOne(T query) {
		try {
			return sqlSessionTemplate.selectOne(getSqlName(SqlId.SQL_SELECT), query);
		} catch (Exception e) {
			log.error(String.format("查询一条记录出错！语句：%s", getSqlName(SqlId.SQL_SELECT)), e);
			throw new DaoException(e);
		}
	}

	@Override
	public T selectById(ID id) {
		try {
			return sqlSessionTemplate.selectOne(getSqlName(SqlId.SQL_SELECT_BY_ID), id);
		} catch (Exception e) {
			log.error(String.format("根据ID查询对象出错！语句：%s", getSqlName(SqlId.SQL_SELECT_BY_ID)), e);
			throw new DaoException(e);
		}
	}

	@Override
	public List<T> selectList(T query) {
		try {
			return sqlSessionTemplate.selectList(getSqlName(SqlId.SQL_SELECT), query);
		} catch (Exception e) {
			log.error(String.format("查询对象列表出错！语句：%s", getSqlName(SqlId.SQL_SELECT)), e);
			throw new DaoException(e);
		}
	}

	@Override
	public List<T> selectAll() {
		try {
			return sqlSessionTemplate.selectList(getSqlName(SqlId.SQL_SELECT));
		} catch (Exception e) {
			log.error(String.format("查询所有对象列表出错！语句：%s", getSqlName(SqlId.SQL_SELECT)), e);
			throw new DaoException(e);
		}
	}

	@Override
	public <K, V extends T> Map<K, V> selectMap(T query, String mapKey) {
		try {
			return sqlSessionTemplate.selectMap(getSqlName(SqlId.SQL_SELECT), query, mapKey);
		} catch (Exception e) {
			log.error(String.format("查询对象Map时出错！语句：%s", getSqlName(SqlId.SQL_SELECT)), e);
			throw new DaoException(e);
		}
	}

	/**
	 * 设置分页
	 * 
	 * @param pageInfo
	 *            分页信息
	 * @return SQL分页参数对象
	 */
	protected RowBounds getRowBounds(Pageable pageable) {
		RowBounds bounds = RowBounds.DEFAULT;
		if (null != pageable) {
			bounds = new RowBounds(pageable.getOffset(), pageable.getPageSize());
		}
		return bounds;
	}

	/**
	 * 获取分页查询参数
	 * 
	 * @param query
	 *            查询对象
	 * @param pageable
	 *            分页对象
	 * @return Map 查询参数
	 */
	protected Map<String, Object> getParams(T query, Pageable pageable) {
		Map<String, Object> params = BeanUtils.toMap(query, getRowBounds(pageable));
		if (pageable != null && pageable.getSort() != null) {
			String sorting = pageable.getSort().toString();
			params.put("sorting", sorting.replace(":", ""));
			String str=(String)params.get("sorting");
			if(str.contains("---")){
				params.put("sorting", str.replace("---", ":"));
			}
		
		}
		return params;
	}

	@Override
	public List<T> selectList(T query, Pageable pageable) {
		try {
			return sqlSessionTemplate.selectList(getSqlName(SqlId.SQL_SELECT), getParams(query, pageable));
		} catch (Exception e) {
			log.error(String.format("根据分页对象查询列表出错！语句:%s", getSqlName(SqlId.SQL_SELECT)), e);
			throw new DaoException(e);
		}
	}

	@Override
	public Page<T> selectPageList(Query query) {
		try {
			Map<String, Object> params = BeanUtils.toMap(query);
			params.put("offset", query.getOffset());
			params.put("limit", query.getPageSize());
			List<T> contentList = sqlSessionTemplate.selectList(getSqlName(SqlId.SQL_SELECT),params);
			System.err.println("contentList==>>"+GSONUtil.toJson(contentList));
			return new  Page<T>(contentList, null, this.selectQueryCount(query));
		} catch (Exception e) {
			log.error(String.format("根据分页对象查询列表出错！语句:%s", getSqlName(SqlId.SQL_SELECT)), e);
			throw new DaoException(e);
		}
	}

	
	@Override
	public Page<T> selectPageList(T query, Pageable pageable) {
		try {
			List<T> contentList = sqlSessionTemplate.selectList(getSqlName(SqlId.SQL_SELECT),
					getParams(query, pageable));
			return new  Page<T>(contentList, pageable, this.selectCount(query));
		} catch (Exception e) {
			log.error(String.format("根据分页对象查询列表出错！语句:%s", getSqlName(SqlId.SQL_SELECT)), e);
			throw new DaoException(e);
		}
	}
	
	@Override
	public <K, V extends T> Map<K, V> selectMap(T query, String mapKey, Pageable pageable) {
		try {
			return sqlSessionTemplate.selectMap(getSqlName(SqlId.SQL_SELECT), getParams(query, pageable), mapKey);
		} catch (Exception e) {
			log.error(String.format("根据分页对象查询列表出错！语句:%s", getSqlName(SqlId.SQL_SELECT)), e);
			throw new DaoException(e);
		}
	}

	@Override
	public Long selectCount() {
		try {
			return sqlSessionTemplate.selectOne(getSqlName(SqlId.SQL_SELECT_COUNT));
		} catch (Exception e) {
			log.error(String.format("查询对象总数出错！语句：%s", getSqlName(SqlId.SQL_SELECT_COUNT)), e);
			throw new DaoException(e);
		}
	}

	@Override
	public Long selectCount(T query) {
		try {
			Map<String, Object> params = BeanUtils.toMap(query);
			return sqlSessionTemplate.selectOne(getSqlName(SqlId.SQL_SELECT_COUNT), params);
		} catch (Exception e) {
			log.error(String.format("查询对象总数出错！语句：%s", getSqlName(SqlId.SQL_SELECT_COUNT)), e);
			throw new DaoException(e);
		}
	}

	public Long selectQueryCount(Query query) {
		try {
			Map<String, Object> params = BeanUtils.toMap(query);
			return sqlSessionTemplate.selectOne(getSqlName(SqlId.SQL_SELECT_COUNT), params);
		} catch (Exception e) {
			log.error(String.format("查询对象总数出错！语句：%s", getSqlName(SqlId.SQL_SELECT_COUNT)), e);
			throw new DaoException(e);
		}
	}
	
	@Override
	@Transactional
	public ID insert(T entity) {
		try {
			/*ID id = entity.getId();
			if (null == id) {
				if (StringUtils.isBlank(stringId)) {
					entity.setId((ID) generateId());
				}
			}*/
			appendCreatedTime(entity);
			sqlSessionTemplate.insert(getSqlName(SqlId.SQL_INSERT), entity);
			return entity.getId();
		} catch (Exception e) {
			log.error(String.format("添加对象出错！语句：%s", getSqlName(SqlId.SQL_INSERT)), e);
			throw new DaoException(e);
		}
	}

	@Override
	@Transactional
	public int delete(T query) {
		try {
			Map<String, Object> params = BeanUtils.toMap(query);
			return sqlSessionTemplate.delete(getSqlName(SqlId.SQL_DELETE), params);
		} catch (Exception e) {
			log.error(String.format("删除对象出错！语句：%s", getSqlName(SqlId.SQL_DELETE)), e);
			throw new DaoException(e);
		}
	}

	@Override
	@Transactional
	public int deleteById(ID id) {
		try {
			return sqlSessionTemplate.delete(getSqlName(SqlId.SQL_DELETE_BY_ID), id);
		} catch (Exception e) {
			log.error(String.format("根据ID删除对象出错！语句：%s", getSqlName(SqlId.SQL_DELETE_BY_ID)), e);
			throw new DaoException(e);
		}
	}

	@Override
	@Transactional
	public int deleteAll() {
		try {
			return sqlSessionTemplate.delete(getSqlName(SqlId.SQL_DELETE));
		} catch (Exception e) {
			log.error(String.format("删除所有对象出错！语句：%s", getSqlName(SqlId.SQL_DELETE)), e);
			throw new DaoException(e);
		}
	}

	@Override
	@Transactional
	public int updateById(T entity) {
		appendUpdatedTime(entity);
		try {
			return sqlSessionTemplate.update(getSqlName(SqlId.SQL_UPDATE_BY_ID), entity);
		} catch (Exception e) {
			log.error(String.format("根据ID更新对象出错！语句：%s", getSqlName(SqlId.SQL_UPDATE_BY_ID)), e);
			throw new DaoException(e);
		}
	}

	@Override
	@Transactional
	public int updateByIdSelective(T entity) {
		appendUpdatedTime(entity);
		try {
			return sqlSessionTemplate.update(getSqlName(SqlId.SQL_UPDATE_BY_ID_SELECTIVE), entity);
		} catch (Exception e) {
			log.error(String.format("根据ID更新对象某些属性出错！语句：%s", getSqlName(SqlId.SQL_UPDATE_BY_ID_SELECTIVE)),e);
			throw new DaoException(e);
		}
	}

	@Override
	@Transactional
	public void deleteByIdInBatch(List<ID> idList) {
		if (idList == null || idList.isEmpty())
			return;
		for (ID id : idList) {
			this.deleteById(id);
		}
	}

	@Override
	@Transactional
	public void updateInBatch(List<T> entityList) {
		if (entityList == null || entityList.isEmpty())
			return;
		for (T entity : entityList) {
			this.updateByIdSelective(entity);
		}
	}

	@Override
	@Transactional
	public void insertInBatch(List<T> entityList) {
		if (entityList == null || entityList.isEmpty())
			return;
		for (T entity : entityList) {
			this.insert(entity);
		}
	}

	@Override
	public <V extends T> List<V> executeSql(DbExecuteType type, String sqlId, Object params) {
		String sqlName = sqlNamespace + SQLNAME_SEPARATOR + sqlId;
		List<V> results = null;
		try {
			switch (type) {
			case INSERT:
				sqlSessionTemplate.insert(sqlName, params);
				break;
			case UPDATE:
				sqlSessionTemplate.update(sqlName, params);
				break;
			case DELETE:
				sqlSessionTemplate.delete(sqlName, params);
				break;
			default:
				results = sqlSessionTemplate.selectList(sqlName, params);
				break;
			}
		} catch (Exception e) {
			log.error(String.format("执行" + type.getDescription() + "出错！语句：%s", sqlName), e);
			throw new DaoException(e);
		}
		return results;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List runSql(DbExecuteType type, String sqlId, Object params) {
		String sqlName = sqlNamespace + SQLNAME_SEPARATOR + sqlId;
		try {
			switch (type) {
			case INSERT:
				sqlSessionTemplate.insert(sqlName, params);
				break;
			case UPDATE:
				sqlSessionTemplate.update(sqlName, params);
				break;
			case DELETE:
				sqlSessionTemplate.delete(sqlName, params);
				break;
			default:
				return sqlSessionTemplate.selectList(sqlName, params);
			}
		} catch (Exception e) {
			log.error(String.format("执行" + type.getDescription() + "出错！语句：%s", sqlName), e);
			throw new DaoException(e);
		}
		return null;
	}

	@Override
	public <V extends T> V selectOne(String sqlId, T query) {
		String sqlName = "";
		try {
			Map<String, Object> params = BeanUtils.toMap(query);
			sqlName = query.getClass().getSuperclass().getName() + SQLNAME_SEPARATOR + sqlId;
			return sqlSessionTemplate.selectOne(sqlName, params);
		} catch (Exception e) {
			log.error(String.format("查询一条记录出错！语句：%s", sqlName), e);
			throw new DaoException(e);
		}
	}

	@Override
	public <V extends T> List<V> selectAll(String sqlId, T query) {
		try {
			Map<String, Object> params = BeanUtils.toMap(query);
			return sqlSessionTemplate.selectList(getSqlName(sqlId), params);
		} catch (Exception e) {
			log.error(String.format("查询所有对象列表出错！语句：%s", getSqlName(SqlId.SQL_SELECT)), e);
			throw new DaoException(e);
		}
	}

	@Override
	public <V extends T> Page<V> selectPageList(String sqlId, T query, Pageable pageable) {
		String sqlName = "";
		try {
			sqlName = query.getClass().getSuperclass().getName() + SQLNAME_SEPARATOR + sqlId;
			List<V> contentList = sqlSessionTemplate.selectList(sqlName, getParams(query, pageable));
			return new Page<V>(contentList, pageable, this.selectCount(query));
		} catch (Exception e) {
			log.error(String.format("根据分页对象查询列表出错！语句:%s", sqlName), e);
			throw new DaoException(e);
		}
	}

	private final void appendCreatedTime(T entity) {
		entity.setCreatedTime(new Date().getTime());
	}

	private final void appendUpdatedTime(T entity) {
		entity.setUpdatedTime(new Date().getTime());
	}	
	
	/**
	 * 获取泛型类型的实体对象类全名
	 */
	protected String getDefaultSqlNamespace() {
		Class<?> genericClass = BeanUtils.getGenericClass(this.getClass());
		return genericClass == null ? null : genericClass.getName();
	}
	
	/**
	 * 获取SqlMapping命名空间
	 */
	public String getSqlNamespace() {
		return sqlNamespace;
	}

	/**
	 * 设置SqlMapping命名空间。 以改变默认的SqlMapping命名空间， 不能滥用此方法随意改变SqlMapping命名空间。
	 */
	public void setSqlNamespace(String sqlNamespace) {
		this.sqlNamespace = sqlNamespace;
	}

	/**
	 * 将SqlMapping命名空间与给定的SqlMapping名组合在一起。
	 * 
	 * @param sqlName
	 *            SqlMapping名
	 * @return 组合了SqlMapping命名空间后的完整SqlMapping名
	 */
	protected String getSqlName(String sqlName) {
		return sqlNamespace + SQLNAME_SEPARATOR + sqlName;
	}

	/**
	 * 生成全局唯一主键。
	 * 
	 * @param entity
	 *            要持久化的对象
	 */
	protected Long generateId() {
		return IdGenerator.generateId(BeanUtils.getGenericClass(this.getClass()));
	}
	
	protected void setLogger(Logger log){
		this.log = log;
	}
	
}
