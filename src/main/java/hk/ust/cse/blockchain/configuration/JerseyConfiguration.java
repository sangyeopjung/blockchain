package hk.ust.cse.blockchain.configuration;

import hk.ust.cse.blockchain.controller.BlockchainController;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.ws.rs.ApplicationPath;

@Configuration
@Component
@ApplicationPath("/blockchain")
public class JerseyConfiguration extends ResourceConfig {

    public JerseyConfiguration() {
        register(BlockchainController.class);
        packages("hk.cse.ust.blockchain");
    }

}
