package hk.ust.cse.blockchain.controller;

import hk.ust.cse.blockchain.model.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class EncryptionHelper {

    private static final Logger log = LoggerFactory.getLogger(EncryptionHelper.class);

    public static PublicKey stringToKey(String string) {
        byte[] bytes = Base64.getDecoder().decode(string);
        return bytesToKey(bytes);
    }

    public static PublicKey bytesToKey(byte[] bytes) {
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(bytes);
        PublicKey publicKey = null;
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("EC");
            publicKey = keyFactory.generatePublic(keySpec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            log.error("Error converting String to Key", e);
        }

        return publicKey;
    }

    public static String keyToString(PublicKey key) {
        return Base64.getEncoder().encodeToString(key.getEncoded());
    }

    public static byte[] sign(String string, PrivateKey key) {
        byte[] signature = "To be signed".getBytes(StandardCharsets.UTF_8);

        try {
            Signature ecdsa = Signature.getInstance("SHA256withECDSA");
            ecdsa.initSign(key);
            ecdsa.update(string.getBytes(StandardCharsets.UTF_8));
            signature = ecdsa.sign();
        } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
            log.error("Error signing transaction", e);
        }

        return signature;
    }

    public static Boolean verifySign(Transaction transaction) {
        byte[] signature = transaction.getSignature();//.getBytes(StandardCharsets.UTF_8);
        String data = transaction.getSender() + transaction.getRecipient()
                + Long.toString(transaction.getAmount())
                + Long.toString(transaction.getTimestamp());

        try {
            Signature ecdsa = Signature.getInstance("SHA256withECDSA");
            ecdsa.initVerify(stringToKey(transaction.getSender()));
            ecdsa.update(data.getBytes(StandardCharsets.UTF_8));
            return ecdsa.verify(signature);
        }catch(NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
            log.error("Error verifying signature", e);
            return false;
        }
    }

}
