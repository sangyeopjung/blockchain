package hk.ust.cse.blockchain.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDto implements Serializable {

    private String message;
    @NotNull
    private String sender;
    @NotNull
    private String recipient;
    @NotNull
    private long amount;
    private long timestamp;
    private byte[] signature;

}
