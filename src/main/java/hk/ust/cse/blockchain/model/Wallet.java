package hk.ust.cse.blockchain.model;

import hk.ust.cse.blockchain.controller.EncryptionHelper;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.*;

@Data
public class Wallet {

    private final Logger log = LoggerFactory.getLogger(Wallet.class);

    private PublicKey publicKey;
    private PrivateKey secretKey;

    private void generateKeypair() {
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("EC");
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");

            keyGen.initialize(256, random);
            KeyPair keyPair = keyGen.generateKeyPair();

            setPublicKey(keyPair.getPublic());
            setSecretKey(keyPair.getPrivate());
        } catch(NoSuchAlgorithmException e) {
            log.error("Error generating keypair:", e);
        }
    }

    public Wallet() {
        generateKeypair();

        log.info("Secret key: {}", secretKey.getEncoded());
        log.info("Public key: {}", EncryptionHelper.keyToString(publicKey));
    }

}
