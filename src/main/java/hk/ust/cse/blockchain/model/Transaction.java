package hk.ust.cse.blockchain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.security.PublicKey;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    @NotNull
    private String sender;
    @NotNull
    private String recipient;
    @NotNull
    private long amount;
    private long timestamp;
    private byte[] signature;

}
