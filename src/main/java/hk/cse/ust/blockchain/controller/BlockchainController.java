package hk.cse.ust.blockchain.controller;

import hk.cse.ust.blockchain.dto.*;
import hk.cse.ust.blockchain.model.Block;
import hk.cse.ust.blockchain.model.Blockchain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Controller
@Component
@Path("/api")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class BlockchainController {

    private static Logger log = LoggerFactory.getLogger(BlockchainController.class);

    private Blockchain blockchain;
    private String responseMessage;

    @Autowired
    public BlockchainController(Blockchain blockchain) {
        this.blockchain = blockchain;
    }

    @GET
    @Path("/hello")
    public Response hello() {
        log.info("Received GET request to /blockchain/api/hello");

        responseMessage = "Hello world!";
        log.info("Responding with message: [{}]", responseMessage);
        return Response.ok(MessageDto.builder()
                    .message(responseMessage)
                    .build())
                .build();
    }

    @GET
    @Path("/mine")
    public Response mine() {
        log.info("Received GET request to /blockchain/api/mine");

        Block lastBlock = blockchain.getLastBlock();
        int proof = blockchain.mineProof(lastBlock);

        blockchain.newTransaction("0", blockchain.getIdentifier(), 1);

        String prevHash = Blockchain.getHash(lastBlock);
        if ("".equals(prevHash)) {
            responseMessage = "Error calculating hash";
            log.info("Responding with message: [{}]", responseMessage);
            return Response.status(500).entity(MessageDto.builder()
                        .message(responseMessage)
                        .build())
                    .build();
        }

        Block block = blockchain.addBlock(proof, prevHash);

        responseMessage = "New block created";
        log.info("Responding with message: [{}]", responseMessage);
        return Response.ok(MineDto.builder()
                    .message(responseMessage)
                    .index(block.getIndex())
                    .prevHash(prevHash)
                    .proof(proof)
                    .transactions(block.getTransactions())
                    .build())
                .build();
    }

    @GET
    @Path("/chain")
    public Response getChain() {
        log.info("Received GET request to /blockchain/api/chain");

        responseMessage = "Current blockchain";
        log.info("Responding with message: [{}]", responseMessage);
        return Response.ok(ChainDto.builder()
                    .message(responseMessage)
                    .chain(blockchain.getChain())
                    .build())
                .build();
    }

    @POST
    @Path("/transactions/new")
    public Response newTransaction(@Valid TransactionForm transaction) {
        log.info("Received POST request to /blockchain/api/transactions/new");

        int index = blockchain.newTransaction(transaction.getSender(),
                transaction.getRecipient(), transaction.getAmount());
        responseMessage = "Transaction will be added to block " + Integer.toString(index);

        log.info("Responding with message: [{}]", responseMessage);
        return Response.ok(TransactionDto.builder()
                    .message(responseMessage)
                    .sender(transaction.getSender())
                    .recipient(transaction.getRecipient())
                    .amount(transaction.getAmount())
                    .build())
                .build();
    }

}
