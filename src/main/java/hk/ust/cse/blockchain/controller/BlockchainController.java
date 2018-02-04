package hk.ust.cse.blockchain.controller;

import hk.ust.cse.blockchain.controller.dto.*;
import hk.ust.cse.blockchain.model.Block;
import hk.ust.cse.blockchain.model.Blockchain;
import hk.ust.cse.blockchain.controller.form.NodesForm;
import hk.ust.cse.blockchain.controller.form.TransactionForm;
import hk.ust.cse.blockchain.model.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Collection;

import static hk.ust.cse.blockchain.controller.EncryptionHelper.keyToString;
import static hk.ust.cse.blockchain.model.HashHelper.getHash;

@Controller
@Component
@Path("/api")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class BlockchainController {

    private static Logger log = LoggerFactory.getLogger(BlockchainController.class);

    private Blockchain blockchain;
    private String responseMessage;
    private String defaultKey;

    @Autowired
    public BlockchainController(Blockchain blockchain) {
        this.blockchain = blockchain;
        this.defaultKey = "0";
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

        blockchain.addTransaction(defaultKey, keyToString(blockchain.getWallet().getPublicKey()), 50);

        String previousHash = getHash(
                getHash(lastBlock)+Integer.toString(proof));
        if ("".equals(previousHash)) {
            responseMessage = "Error calculating hash";
            log.info("Responding with message: [{}]", responseMessage);
            return Response.status(500).entity(MessageDto.builder()
                        .message(responseMessage)
                        .build())
                    .build();
        }

        Block block = blockchain.addBlock(proof, previousHash);

        responseMessage = "New block created";
        log.info("Responding with message: [{}]", responseMessage);
        return Response.ok(BlockDto.builder()
                    .message(responseMessage)
                    .index(block.getIndex())
                    .previousHash(previousHash)
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

        String sender = keyToString(blockchain.getWallet().getPublicKey());

        Transaction newTransaction = blockchain.addTransaction(sender, transaction.getRecipient(), transaction.getAmount());
        responseMessage = "Transaction will be added to block " + Integer.toString(blockchain.getChain().size()+1);

        log.info("Responding with message: [{}]", responseMessage);
        return Response.ok(TransactionDto.builder()
                    .message(responseMessage)
                    .sender(newTransaction.getSender())
                    .recipient(newTransaction.getRecipient())
                    .amount(newTransaction.getAmount())
                    .timestamp(newTransaction.getTimestamp())
                    .signature(newTransaction.getSignature())
                    .build())
                .build();
    }

    @POST
    @Path("/nodes/register")
    public Response registerNode(@Valid NodesForm nodes) {
        log.info("Received POST request to /blockchain/api/nodes/register");

        if (null == nodes || nodes.getNodes().size() == 0) {
            responseMessage = "Error: No nodes found";
            log.info("Responding with message: [{}]", responseMessage);

            return Response.status(400).entity(MessageDto.builder()
                        .message(responseMessage)
                        .build())
                    .build();
        }

        for (String node : nodes.getNodes()) {
            blockchain.registerNode(node);
        }

        responseMessage = "New nodes added";
        log.info("Responding with message: [{}]", responseMessage);
        return Response.ok(NodesDto.builder()
                    .message(responseMessage)
                    .nodes(new ArrayList<>(blockchain.getNodes()))
                    .build())
                .build();
    }

    @GET
    @Path("/nodes/resolve")
    public Response resolveNode() {
        log.info("Received GET request to /blockchain/api/nodes/resolve");

        Collection<String> nodes = blockchain.getNodes();

        Boolean shouldReplace = false;
        for (String node : nodes) {
            ChainDto response;
            try {
                response = HttpRequestHelper.getChainFromNodes(node);
            } catch (ProcessingException e) {
                continue;
            }

            shouldReplace = shouldReplace || blockchain.areConflictsResolved(response.getChain());
        }

        if (shouldReplace) {
            responseMessage = "The chain is updated";
            log.info("Responding with message: [{}]", responseMessage);

            return Response.ok(ConsensusDto.builder()
                        .message(responseMessage)
                        .newChain(blockchain.getChain())
                        .build())
                    .build();
        } else {
            responseMessage = "The chain is authoritative";
            log.info("Responding with message: [{}]", responseMessage);

            return Response.ok(ConsensusDto.builder()
                        .message(responseMessage)
                        .chain(blockchain.getChain())
                        .build())
                    .build();
        }
    }

}
