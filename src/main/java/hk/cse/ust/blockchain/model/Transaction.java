package hk.cse.ust.blockchain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

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

}
