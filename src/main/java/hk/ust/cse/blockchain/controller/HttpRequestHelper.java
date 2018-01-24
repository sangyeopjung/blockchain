package hk.ust.cse.blockchain.controller;

import hk.ust.cse.blockchain.controller.dto.ChainDto;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;

public class HttpRequestHelper {

    private static Client client = ClientBuilder.newClient();

    public static ChainDto getChainFromNodes(String url) throws ProcessingException {
        return client.target(url)
                .path("/blockchain/api/chain")
                .request(MediaType.APPLICATION_JSON)
                .get(ChainDto.class);
    }
}
