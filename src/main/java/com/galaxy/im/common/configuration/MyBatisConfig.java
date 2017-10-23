package com.galaxy.im.common.configuration;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

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

	/*@Bean(name="dataSource")
	@ConfigurationProperties(prefix="datasource")
	public DataSource getDataSource() throws Exception{
        return new DruidDataSource();
	}*/
	
	 /**
     * 根据数据源创建SqlSessionFactory
     *//*
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
    public PlatformTransactionManager annotationDrivenTransactionManager(DataSource dataSource) throws Exception{
        return new DataSourceTransactionManager(dataSource);
    }*/
	
	
	/**
     * @return
     * @throws Exception
     * @Primary 该注解表示在同一个接口有多个实现类可以注入的时候，默认选择哪一个，而不是让@autowire注解报错
     */
    @Primary
    @Bean("masterDataSource")
    @ConfigurationProperties(prefix = "datasource")
    public DataSource masterDataSource() throws Exception {
        return DataSourceBuilder.create().build();
    }

    @Bean("slaverDataSource")
    @ConfigurationProperties(prefix = "second-datasource")
    public DataSource slaverDataSource() throws Exception {
        return DataSourceBuilder.create().build();
    }

    /**
     * @Qualifier 根据名称进行注入，通常是在具有相同的多个类型的实例的一个注入（例如有多个DataSource类型的实例）
     */
    @Bean("dynamicDataSource")
    public DynamicDataSource dynamicDataSource(@Qualifier("masterDataSource") DataSource masterDataSource,
                                               @Qualifier("slaverDataSource") DataSource slaverDataSource) {
        Map<Object, Object> targetDataSources = new HashMap<Object, Object>();
        targetDataSources.put(DataSourceEnum.master, masterDataSource);
        targetDataSources.put(DataSourceEnum.slaver, slaverDataSource);

        DynamicDataSource dataSource = new DynamicDataSource();
        // 该方法是AbstractRoutingDataSource的方法
        dataSource.setTargetDataSources(targetDataSources);
        // 默认的datasource设置为masterDataSource
        dataSource.setDefaultTargetDataSource(masterDataSource);
        return dataSource;
    }

    /**
     * 根据数据源创建SqlSessionFactory
     */
    @Bean(name="sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("dynamicDataSource") DynamicDataSource dynamicDataSource) throws Exception{
        SqlSessionFactoryBean fb = new SqlSessionFactoryBean();
        //指定数据源(这个必须有，否则报错)
        fb.setDataSource(dynamicDataSource);		
        //下边两句仅仅用于*.xml文件，如果整个持久层操作不需要使用到xml文件的话（只用注解就可以搞定），则不加
        String basePackage = env.getProperty("mybatis.typeAliasesPackage");
        String xmlPackage = env.getProperty("mybatis.mapperLocations");
        fb.setTypeAliasesPackage(basePackage);	//指定基包
        fb.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(xmlPackage));	//指定xml文件位置
        return fb.getObject();
    }

    /**
     * 配置事务管理器
     */
    @Bean
    public DataSourceTransactionManager transactionManager(DynamicDataSource dataSource) throws Exception {
        return new DataSourceTransactionManager(dataSource);
    }
    
    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}








