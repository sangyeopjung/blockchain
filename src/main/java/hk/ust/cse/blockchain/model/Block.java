package hk.ust.cse.blockchain.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Block {

    private int index;
    private long timestamp;
    private int proof;
    private String previousHash;
    private List<Transaction> transactions;

    @Override
    public String toString() {
        String string = "";

        ObjectMapper mapper = new ObjectMapper();
        try {
            string = mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return string;
    }

}
