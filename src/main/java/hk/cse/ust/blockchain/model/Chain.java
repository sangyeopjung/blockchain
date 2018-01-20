package hk.cse.ust.blockchain.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Chain {

    private List<Block> chain;
    private int length;

}