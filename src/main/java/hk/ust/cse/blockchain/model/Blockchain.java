package hk.ust.cse.blockchain.model;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.*;

import static hk.ust.cse.blockchain.controller.EncryptionHelper.sign;
import static hk.ust.cse.blockchain.controller.EncryptionHelper.verifySign;
import static hk.ust.cse.blockchain.model.HashHelper.getHash;

@Data
@Component
public class Blockchain {

    private final Logger log = LoggerFactory.getLogger(Blockchain.class);

    private static int difficulty = 4;

    private List<Block> chain;
    private List<Transaction> currentTransactions;
    private Collection<String> nodes;
    private Wallet wallet;

    public Blockchain() {
        chain = new ArrayList<>();
        currentTransactions = new ArrayList<>();
        nodes = new HashSet<>();
        wallet = new Wallet();

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

    public Transaction addTransaction(String sender, String recipient, long amount) {
        long timestamp = new Date().getTime();

        log.info("Creating a new transaction from [{}] to [{}] of amount [{}] at time [{}]",
                sender, recipient, amount, timestamp);

        String transactionData = sender + recipient + Long.toString(amount) + Long.toString(timestamp);
        byte[] signature = sign(transactionData, wallet.getSecretKey());
        Transaction newTransaction = new Transaction(sender, recipient, amount, timestamp, signature);
        currentTransactions.add(newTransaction);

        log.info("Transaction added.");

        return newTransaction;
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

    public void registerNode(String address) {
        log.info("Registering a new node: [{}]", address);
        nodes.add(address);
    }

    public static Boolean isValidChain(List<Block> chain) {
        Block lastBlock = chain.get(0);
        for (int i = 1; i < chain.size(); i++) {
            Block block = chain.get(i);

            List<Transaction> transactions = block.getTransactions();
            for (Transaction transaction : transactions) {
                if (! verifySign(transaction)) {
                    return false;
                }
            }

            if (! getHash(getHash(lastBlock)+Integer.toString(block.getProof()))
                    .equals(block.getPreviousHash())) {
                return false;
            }

            String key = getHash(lastBlock) + Integer.toString(block.getProof());
            if (! isValidHash(getHash(key))) {
                return false;
            }

            lastBlock = block;
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