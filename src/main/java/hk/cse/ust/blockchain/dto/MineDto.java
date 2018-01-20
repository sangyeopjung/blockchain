package hk.cse.ust.blockchain.dto;

import hk.cse.ust.blockchain.model.Transaction;
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
public class MineDto implements Serializable {

    private String message;
    private int index;
    private String prevHash;
    private int proof;
    private List<Transaction> transactions;

}
