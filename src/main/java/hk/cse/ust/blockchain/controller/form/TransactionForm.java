package hk.cse.ust.blockchain.controller.form;

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
public class TransactionForm implements Serializable {

    @NotNull
    private String sender;
    @NotNull
    private String recipient;
    @NotNull
    private long amount;

}
