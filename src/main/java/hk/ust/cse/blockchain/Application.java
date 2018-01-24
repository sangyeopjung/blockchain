package hk.ust.cse.blockchain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@SpringBootApplication
public class Application extends SpringBootServletInitializer{

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String args[]) {
        new Application()
                .configure(new SpringApplicationBuilder(Application.class))
                .run(args);

        log.info("Application started successfully!");
    }

}
