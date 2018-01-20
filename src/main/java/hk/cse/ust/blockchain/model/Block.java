package hk.cse.ust.blockchain.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class Block {

    private int index;
    private long timestamp;
    private int proof;
    private String previousHash;
    private List<Transaction> transactions;

}
