package hk.cse.ust.blockchain.model;

import com.google.common.hash.Hashing;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.*;

@Data
@Component
public class Blockchain {

    private final Logger log = LoggerFactory.getLogger(Blockchain.class);

    private static int difficulty = 4;
    private final String identifier = UUID.randomUUID().toString().replace("-", "");

    private List<Block> chain;
    private List<Transaction> currentTransactions;
    private Collection<String> nodes;

    public Blockchain() {
        chain = new ArrayList<>();
        currentTransactions = new ArrayList<>();
        nodes = new HashSet<>();

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

    public int addTransaction(String sender, String recipient, long amount) {
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

        String nonce = getHash(block);

        int proof = -1;
        String key;
        do {
            proof++;
            key = nonce + Integer.toString(proof);
        } while(! isValidHash(getHash(key)));

        log.info("Proof found: [{}]", proof);
        return proof;
    }

    public static Boolean isValidHash(String hash) {
        for (int i = 0; i < difficulty; i++) {
            if (! "0".equals(hash.substring(i, i+1))) {
                return false;
            }
        }

        return true;
    }

    public static String getHash(Block block) {
        String key = block.toString();
        return getHash(key);
    }

    public static String getHash(String key) {
        return Hashing.sha256()
                .hashString(key, StandardCharsets.UTF_8)
                .toString();
    }

    public void registerNode(String address) {
        log.info("Registering a new node: [{}]", address);
        nodes.add(address);
    }

    public static Boolean isValidChain(List<Block> chain) {
        Block lastBlock = chain.get(0);
        int currentIndex = 1;

        while (currentIndex < chain.size()) {
            Block block = chain.get(currentIndex);
            if (! getHash(getHash(lastBlock)+Integer.toString(block.getProof()))
                    .equals(block.getPreviousHash())) {
                return false;
            }

            String key = getHash(lastBlock) + Integer.toString(block.getProof());
            if (! isValidHash(getHash(key))) {
                return false;
            }

            lastBlock = block;
            currentIndex++;
        }

        return true;
    }

    public Boolean areConflictsResolved(List<Block> otherChain) {
        log.info("Resolving potential conflicts...");
        int maxLength = chain.size();
        int length = otherChain.size();

        if (length > maxLength && isValidChain(otherChain)) {
            setChain(otherChain);
            return true;
        }
        return false;
    }

}