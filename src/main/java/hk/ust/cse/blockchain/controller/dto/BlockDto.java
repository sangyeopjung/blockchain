package hk.ust.cse.blockchain.controller.dto;

import hk.ust.cse.blockchain.model.Transaction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BlockDto implements Serializable {

    private String message;
    private int index;
    private String previousHash;
    private int proof;
    private List<Transaction> transactions;

}
