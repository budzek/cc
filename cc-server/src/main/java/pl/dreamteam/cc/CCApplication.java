package pl.dreamteam.cc;

import org.activiti.engine.IdentityService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.remoting.caucho.HessianServiceExporter;
import pl.dreamteam.cc.service.ConsultantService;
import pl.dreamteam.cc.skype.server.CallHandler;


@SpringBootApplication
@ImportResource("classpath:/spring/spring-config.xml")

/**
 * Taken from:
 * https://forums.activiti.org/content/disable-activiti-spring-rest-api-basic-authentication
 *
 * Unless this is used, Spring security requests authorization on the link that exposes hessian service.
 * No luck to find the user for such authentication.
 * I had to switch off the Spring security in th whole application.
 *
 * TOASK:
 * Probably creating a servlet (or registering some new mapping with no auth will also solve that issue.
 */
@EnableAutoConfiguration(exclude = {
        org.activiti.spring.boot.RestApiAutoConfiguration.class,
        org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration.class,
        org.activiti.spring.boot.SecurityAutoConfiguration.class,
        org.springframework.boot.actuate.autoconfigure.ManagementWebSecurityAutoConfiguration.class
})
public class CCApplication
{
    /** uncomment this for use as war in external tomcat  **/
//        extends SpringBootServletInitializer {
//    @Override
//    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
//        return application.sources(CCApplication.class);
//    }

    @Autowired
    CallHandler callHanler;

    @Autowired
    private ConsultantService consultantService;


    @Bean(name = "/ConsultantService")
//    public HessianServiceExporter dispatcherServlet() {
    public HessianServiceExporter consultantService() {

        HessianServiceExporter exporter = new HessianServiceExporter();
//        exporter.setDebug(true);
        exporter.setService(consultantService);
        exporter.setServiceInterface(ConsultantService.class);
        return exporter;
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

                System.out.println("dupa");


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


    @Bean InitializingBean skypeInitializer(final IdentityService identityService) {
        return () -> {
            System.out.println("dupa skype");
//            callHanler.callHanlder();
            System.out.println("dupa skype2");

        };
    }

}
