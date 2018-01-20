package hk.cse.ust.blockchain.configuration;

import hk.cse.ust.blockchain.controller.BlockchainController;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.ws.rs.ApplicationPath;

@Configuration
@Component
@ApplicationPath("/blockchain")
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
        register(BlockchainController.class);
        packages("hk.cse.ust.blockchain");
    }

}
