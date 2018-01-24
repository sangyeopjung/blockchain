package hk.ust.cse.blockchain.controller.dto;

import hk.ust.cse.blockchain.model.Block;
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
public class ConsensusDto implements Serializable {

    private String message;
    private List<Block> chain;
    private List<Block> newChain;

}
