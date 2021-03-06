package pl.dreamteam.cc;

import org.activiti.spring.SpringAsyncExecutor;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.activiti.spring.boot.AbstractProcessEngineAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.io.IOException;

/**
 * Created by abu on 22.05.2016.
 */
@Configuration
public class ActivitiConfiguration extends AbstractProcessEngineAutoConfiguration {

  @Bean
  @Primary
  @ConfigurationProperties(prefix = "datasource.activiti")
  public DataSource activitiDataSource() {
    return DataSourceBuilder
      .create()
      .url("jdbc:h2:file:C:/cc-server/activiti;AUTO_SERVER=TRUE;")
      .username("sa")
      .driverClassName("org.h2.Driver")
      .build();
  }

  @Bean
  public SpringProcessEngineConfiguration springProcessEngineConfiguration(
    PlatformTransactionManager transactionManager,
    SpringAsyncExecutor springAsyncExecutor) throws IOException {

    return baseSpringProcessEngineConfiguration(
      activitiDataSource(),
      transactionManager,
      springAsyncExecutor);
  }
}
