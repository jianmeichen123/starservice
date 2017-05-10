package com.galaxy.im.common.configuration;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import com.alibaba.druid.pool.DruidDataSource;

/**
 * 用于配置mybatis
 * @author liyanqiao
 *
 */
@Configuration    //该注解类似于spring配置文件
@MapperScan(basePackages="com.galaxy.im.*dao.*")
public class MyBatisConfig {
	
	@Autowired
	private Environment env;

	@Bean(name="dataSource")
	@ConfigurationProperties(prefix="datasource")
	public DataSource getDataSource() throws Exception{
        return new DruidDataSource();
	}
	
	 /**
     * 根据数据源创建SqlSessionFactory
     */
	@Bean(name="sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception{
        SqlSessionFactoryBean fb = new SqlSessionFactoryBean();
        fb.setDataSource(dataSource);		//指定数据源(这个必须有，否则报错)
        //下边两句仅仅用于*.xml文件，如果整个持久层操作不需要使用到xml文件的话（只用注解就可以搞定），则不加
        String basePackage = env.getProperty("mybatis.typeAliasesPackage");
        String xmlPackage = env.getProperty("mybatis.mapperLocations");
        fb.setTypeAliasesPackage(basePackage);	//指定基包
        
        fb.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(xmlPackage));	//指定xml文件位置
        return fb.getObject();
    }
	
	@Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Bean
    public PlatformTransactionManager annotationDrivenTransactionManager() throws Exception{
        return new DataSourceTransactionManager(getDataSource());
    }
    
//    /**
//	 * @Title: transactionManager   
//	 * @Description: 配置事务管理器
//	 */
//	@Bean
//	public DataSourceTransactionManager transactionManager(DataSource dataSource) throws Exception {
//		return new DataSourceTransactionManager(dataSource);
//	}
}








