package hk.ust.cse.blockchain;

import hk.ust.cse.blockchain.model.Block;
import hk.ust.cse.blockchain.model.Blockchain;
import hk.ust.cse.blockchain.model.Transaction;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.nio.charset.StandardCharsets;
import java.util.*;

import static hk.ust.cse.blockchain.controller.EncryptionHelper.keyToString;
import static hk.ust.cse.blockchain.controller.EncryptionHelper.verifySign;
import static hk.ust.cse.blockchain.model.HashHelper.getHash;
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class BlockchainTest {

    private Blockchain blockchain;

    @Before
    public void setUp() {
        blockchain = new Blockchain();
    }

    @After
    public void tearDown() {
        List<Block> chain = new ArrayList<>();
        chain.add(new Block(1, new Date().getTime(), 0,
                "Genesis Block", new ArrayList<>()));
        blockchain.setChain(chain);
        blockchain.setCurrentTransactions(new ArrayList<>());
        blockchain.setNodes(new HashSet<>());
    }

    @Test
    public void shouldAddCorrectBlockToChain() {
        Block newBlock = blockchain.addBlock(42, "totally wrong hash");

        assertEquals(newBlock.getIndex(), blockchain.getChain().size());
        assertEquals(newBlock.getProof(), blockchain.getChain().get(1).getProof());
        assertEquals(newBlock.getPreviousHash(), blockchain.getChain().get(1).getPreviousHash());
        assertEquals(newBlock.getTransactions(), blockchain.getChain().get(1).getTransactions());
    }

    @Test
    public void shouldAddCorrectTransaction() {
        String sender = keyToString(blockchain.getWallet().getPublicKey());
        String recipient = "FishMoley";
        long amount = 9999999;

        Transaction transaction = blockchain.addTransaction(sender, recipient, amount);

        assertEquals(1, blockchain.getCurrentTransactions().size());

        assertEquals(sender, blockchain.getCurrentTransactions().get(0).getSender());
        assertEquals(recipient, blockchain.getCurrentTransactions().get(0).getRecipient());
        assertEquals(amount, blockchain.getCurrentTransactions().get(0).getAmount());
        assertTrue(verifySign(transaction));
    }

    @Test
    public void shouldMineCorrectProof() {
        Block block = new Block(1, 1, 1, "", new ArrayList<>());
        int proof = blockchain.mineProof(block);

        assertTrue(Blockchain.isValidHash(getHash(getHash(block) + Integer.toString(proof))));
    }

    @Test
    public void shouldValidateHash() {
        String correctHash = "0000000000000000000000000000000000000000000000000000000000000000";
        String wrongHash = "1000000000000000000000000000000000000000000000000000000000000000";

        assertTrue(Blockchain.isValidHash(correctHash));
        assertFalse(Blockchain.isValidHash(wrongHash));
    }

    @Test
    public void shouldRegisterNodesWithNoDuplicates() {
        blockchain.registerNode("http://unique.com");
        blockchain.registerNode("http://redundant.com");
        blockchain.registerNode("http://redundant.com");
        blockchain.registerNode("http://redundant.com");

        assertEquals(2, blockchain.getNodes().size());
    }

    @Test
    public void shouldReturnFalseForInvalidSignature() {
        Block previousBlock = blockchain.getLastBlock();
        int proof = blockchain.mineProof(previousBlock);
        String previousHash = getHash(getHash(previousBlock)+Integer.toString(proof));

        List<Transaction> transactions = new ArrayList<>();
        long timestamp = new Date().getTime();
        byte[] signature = "wrong signature".getBytes(StandardCharsets.UTF_8);
        Transaction newTransaction = new Transaction(
                keyToString(blockchain.getWallet().getPublicKey()),
                "recipient", 10, timestamp, signature);
        transactions.add(newTransaction);

        Block wrongTransactionBlock = new Block(blockchain.getChain().size()+1,
                new Date().getTime(), proof, previousHash, transactions);
        blockchain.getChain().add(wrongTransactionBlock);

        assertFalse(Blockchain.isValidChain(blockchain.getChain()));
    }

    @Test
    public void shouldReturnFalseForInvalidChain() {
        Block previousBlock = blockchain.getLastBlock();
        int proof = 111;
        String previousHash = "asd";
        Block wrongBlock = new Block(blockchain.getChain().size()+1,
                new Date().getTime(), proof, previousHash, new ArrayList<>());

        blockchain.getChain().add(wrongBlock);

        assertFalse(Blockchain.isValidChain(blockchain.getChain()));
        assertFalse(Blockchain.isValidHash(getHash(getHash(previousBlock)
                + Integer.toString(wrongBlock.getProof()))));
        Assert.assertNotEquals(previousHash,
                getHash(getHash(blockchain.getLastBlock())
                        + Integer.toString(proof)));

    }

    @Test
    public void shouldReturnTrueForValidChain() {
        Block previousBlock = blockchain.getLastBlock();
        int proof = blockchain.mineProof(previousBlock);
        String previousHash = getHash(getHash(previousBlock)+Integer.toString(proof));
        blockchain.addBlock(proof, previousHash);
        Block correctBlock = blockchain.getLastBlock();

        assertTrue(Blockchain.isValidChain(blockchain.getChain()));
        assertTrue(Blockchain.isValidHash(getHash(getHash(previousBlock)
                + Integer.toString(correctBlock.getProof()))));
        Assert.assertEquals(previousHash,
                getHash(getHash(previousBlock) + Integer.toString(proof)));
    }

    @Test
    public void shouldKeepChainIfFoundChainIsNotLonger() {
        Block previousBlock = blockchain.getLastBlock();
        int proof = blockchain.mineProof(previousBlock);
        String previousHash = getHash(getHash(previousBlock)+Integer.toString(proof));
        blockchain.addBlock(proof, previousHash);

        Blockchain otherBlockchain = new Blockchain();

        List<Block> originalChain = blockchain.getChain();
        Boolean isChanged = blockchain.areConflictsResolved(otherBlockchain.getChain());
        List<Block> currentChain = blockchain.getChain();

        assertFalse(isChanged);
        assertEquals(originalChain, currentChain);
    }

    @Test
    public void shouldKeepChainIfFoundChainIsLongerButInvalid() {
        Blockchain otherBlockchain = new Blockchain();
        otherBlockchain.addBlock(1337, "this hash could not be more wrong");

        List<Block> originalChain = blockchain.getChain();
        Boolean isChanged = blockchain.areConflictsResolved(otherBlockchain.getChain());
        List<Block> currentChain = blockchain.getChain();

        assertFalse(isChanged);
        assertEquals(originalChain, currentChain);
    }

    @Test
    public void shouldReplaceChainIfFoundChainIsLongerAndValid() {
        Blockchain otherBlockchain = new Blockchain();
        Block previousBlock = otherBlockchain.getLastBlock();
        int proof = otherBlockchain.mineProof(previousBlock);
        String previousHash = getHash(getHash(previousBlock)+Integer.toString(proof));
        otherBlockchain.addBlock(proof, previousHash);

        List<Block> originalChain = blockchain.getChain();
        Boolean isChanged = blockchain.areConflictsResolved(otherBlockchain.getChain());
        List<Block> currentChain = blockchain.getChain();

        assertTrue(isChanged);
        assertNotEquals(originalChain, currentChain);
    }

}
