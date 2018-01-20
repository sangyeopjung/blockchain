package hk.cse.ust.blockchain.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.hash.Hashing;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@Component
public class Blockchain {

    private final Logger log = LoggerFactory.getLogger(Blockchain.class);

    private static final String ZEROS = "0000";
    private final String identifier = UUID.randomUUID().toString().replace("-", "");

    private List<Block> chain;
    private List<Transaction> currentTransactions;

    public Blockchain() {
        chain = new ArrayList<>();
        currentTransactions = new ArrayList<>();

        log.info("Identifier: {}", identifier);
        addBlock(0, "Genesis Block");
    }

    public Block addBlock(int proof, String previousHash) {
        log.info("Creating a new block...");

        Block newBlock = new Block(chain.size() + 1, new Date().getTime(),
                proof, previousHash, currentTransactions);

        log.info("Created a new block at [{}]", chain.size() + 1);

        currentTransactions = new ArrayList<>();
        chain.add(newBlock);

        return newBlock;
    }

    public int newTransaction(String sender, String recipient, double amount) {
        log.info("Creating a new transaction from [{}] to [{}] of amount [{}]",
                sender, recipient, amount);

        currentTransactions.add(new Transaction(sender, recipient, amount));

        log.info("Transaction added.");

        return getLastBlock().getIndex() + 1;
    }

    public Block getLastBlock() {
        return chain.get(chain.size() - 1);
    }

    public int mineProof(Block block) {
        log.info("Mining for the proof...");

        String nonce = blockToString(block);

        int proof = 0;
        String key;
        do {
            key = nonce + Integer.toString(proof++);
        } while(! isValidProof(getHash(key)));

        log.info("Proof found: [{}]", proof);
        return proof;
    }

    private static Boolean isValidProof(String hash) {
        if (ZEROS.equals(hash.substring(0, ZEROS.length()))) {
            System.out.println("Valid hash found: " + hash);
            return true;
        } else {
            return false;
        }
    }

    public static String getHash(Block block) {
        String key = blockToString(block);

        return getHash(key);
    }

    private static String getHash(String key) {
        return Hashing.sha256()
                .hashString(key, StandardCharsets.UTF_8)
                .toString();
    }

    private static String blockToString(Block block) {
        String string = "";

        ObjectMapper mapper = new ObjectMapper();
        try {
            string = mapper.writeValueAsString(block);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return string;
    }

}