package hk.ust.cse.blockchain;

import hk.ust.cse.blockchain.controller.BlockchainController;
import hk.ust.cse.blockchain.controller.dto.MessageDto;
import hk.ust.cse.blockchain.model.Blockchain;
import hk.ust.cse.blockchain.model.Transaction;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.ws.rs.core.Response;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BlockchainControllerTest {

    @InjectMocks
    private BlockchainController blockchainController;

    @Mock
    private Blockchain blockchain;
    private String responseMessage;

    @Test
    public void shouldReturnHello() {

    }

    @Test
    public void shouldReturnBlockWhenMinedSuccessfully() {

    }

    @Test
    public void shouldReturnErrorWhenMinedUnsuccessfully() {

    }

    @Test
    public void shouldReturnChain() {

    }

    @Test
    public void shouldReturnNewTransaction() {

    }

    @Test
    public void shouldReturnNodesWhenNotEmpty() {

    }

    @Test
    public void shouldReturnErrorWhenNodesAreNullOrEmpty() {

    }

    @Test
    public void shouldReturnNewChainWhenResolved() {

    }

    @Test
    public void shouldReturnOldChainWhenNotResolved() {

    }

}
