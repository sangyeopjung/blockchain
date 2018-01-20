package hk.cse.ust.blockchain.dto;

import hk.cse.ust.blockchain.model.Transaction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BlockDto {

    private int index;
    private long timestamp;
    private List<Transaction> transactions;
    private int proof;
    private String previousHash;

}
