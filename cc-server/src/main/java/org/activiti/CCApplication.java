package org.activiti;

import com.skype.*;
import org.activiti.engine.IdentityService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CCApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(CCApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(CCApplication.class, args);
    }

//    @Bean
//    public CommandLineRunner init(final RepositoryService repositoryService,
//                                  final RuntimeService runtimeService,
//                                  final TaskService taskService) {
//
//        return new CommandLineRunner() {
//            @Override
//            public void run(String... strings) throws Exception {
//                Map<String, Object> variables = new HashMap<String, Object>();
//                variables.put("applicantName", "John Doe");
//                variables.put("email", "john.doe@activiti.com");
//                variables.put("phoneNumber", "123456789");
//                runtimeService.startProcessInstanceByKey("hireProcess", variables);
//            }
//        };
//
//    }

//    @Bean
//    @Primary
//    @ConfigurationProperties(prefix = "datasource.primary")
//    public DataSource primaryDataSource() {
//        return DataSourceBuilder
//          .create()
//          .url("jdbc:h2:/tmp/primary;AUTO_SERVER=TRUE")
//          .username("primary")
//          .driverClassName("org.h2.Driver")
//          .build();
//    }

    @Bean InitializingBean usersAndGroupsInitializer(final IdentityService identityService) {

        return new InitializingBean() {
            public void afterPropertiesSet() throws Exception {


                Skype.setDaemon(false);
                Skype.addCallListener(new CallAdapter() {
                    @Override
                    public void callReceived(Call receivedCall) throws SkypeException {
                        Profile.CallForwardingRule[] oldRules = Skype.getProfile().getAllCallForwardingRules();
                        Skype.getProfile().setAllCallForwardingRules(new Profile.CallForwardingRule[] { new Profile.CallForwardingRule(0, 30, "echo123") });
                        receivedCall.forward();
                        try {
                            Thread.sleep(10000); // to prevent finishing this call
                        } catch (InterruptedException e) {
                        }
                        Skype.getProfile().setAllCallForwardingRules(oldRules);
                    }
                });

//                Group group = identityService.newGroup("user");
//                group.setName("users");
//                group.setType("security-role");
//                identityService.saveGroup(group);
//
//                User admin = identityService.newUser("admin");
//                admin.setPassword("admin");
//                identityService.saveUser(admin);

            }
        };
    }

}
