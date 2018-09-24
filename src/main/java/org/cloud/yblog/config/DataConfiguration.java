/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2017 d05660@163.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package org.cloud.yblog.config;

import java.beans.PropertyVetoException;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.transaction.SpringManagedTransactionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.alibaba.druid.pool.DruidDataSource;

/**
 * Created by d05660ddw on 2017/4/8.
 */

@Configuration
@MapperScan(basePackages = "org.cloud.yblog.mapper")
@EnableTransactionManagement
public class DataConfiguration {

	@Value("${spring.datasource.url}")
	private String url;

	@Value("${spring.datasource.username}")
	private String username;

	@Value("${spring.datasource.password}")
	private String password;

	@Value("${spring.datasource.driver-class-name}")
	private String driverClass;

	@Value("${spring.datasource.initialSize}")
	private Integer initialSize;

	@Value("${spring.datasource.maxActive}")
	private Integer maxActive;

	@Value("${spring.datasource.minIdle}")
	private Integer minIdle;

	@Value("${spring.datasource.maxWait}")
	private Integer maxWait;

	@Value("${spring.datasource.timeBetweenEvictionRunsMillis}")
	private Integer timeBetweenEvictionRunsMillis;

	@Value("${spring.datasource.minEvictableIdleTimeMillis}")
	private Integer minEvictableIdleTimeMillis;

	@Value("${spring.datasource.validationQuery}")
	private String validationQuery;

	@Value("${spring.datasource.testOnBorrow}")
	private boolean testOnBorrow;
	
	@Value("${spring.datasource.testWhileIdle}")
	private boolean testWhileIdle;
	
	@Value("${spring.datasource.testOnReturn}")
	private boolean testOnReturn;
	
	@Value("${spring.datasource.poolPreparedStatements}")
	private boolean poolPreparedStatements;
	
	@Value("${spring.datasource.maxPoolPreparedStatementPerConnectionSize}")
	private Integer maxPoolPreparedStatementPerConnectionSize;
	
	@Value("${spring.datasource.filters}")
	private String filters;

    @Bean(destroyMethod = "close", initMethod = "init")
    public DataSource dataSource() throws SQLException {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(url);
        dataSource.setUsername(username);//用户名
        dataSource.setPassword(password);//密码
        dataSource.setDriverClassName(driverClass);
        dataSource.setInitialSize(initialSize);
        dataSource.setMaxActive(maxActive);
        dataSource.setMinIdle(minIdle);
        dataSource.setMaxWait(maxWait);
        dataSource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        dataSource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        dataSource.setValidationQuery(validationQuery);
        dataSource.setTestOnBorrow(testOnBorrow);
        dataSource.setTestWhileIdle(testWhileIdle);
        dataSource.setTestOnReturn(testOnReturn);
        dataSource.setPoolPreparedStatements(poolPreparedStatements);
        dataSource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);
        //配置监控统计拦截的filters，去掉后监控界面sql无法统计，'wall'用于防火墙
        dataSource.setFilters(filters);
        return dataSource;
    }

    @Bean(name = "transactionManager")
    public DataSourceTransactionManager dataSourceTransactionManager() throws PropertyVetoException, SQLException {
        return new DataSourceTransactionManager(dataSource());
    }

    @Bean
    public SpringManagedTransactionFactory springManagedTransactionFactory() {
        return new SpringManagedTransactionFactory();
    }
}
