package hk.ust.cse.blockchain.model;

import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;

public class HashHelper {

    public static String getHash(Block block) {
        String key = block.toString();
        return getHash(key);
    }

    public static String getHash(String key) {
        return Hashing.sha256()
                .hashString(key, StandardCharsets.UTF_8)
                .toString();
    }

}
