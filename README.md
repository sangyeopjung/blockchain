# Blockchain

## About

A simple Java blockchain ledger created for studying purposes.

- [x] Blockchain
- [x] API
- [x] Consensus
- [ ] Wallet
- [ ] Signature
- [x] Test

## Tools Used

- Spring Boot
- Maven
- Jersey
- Lombok
- Jackson
- Slf4j
- JUnit
- Mockito

## API

### (GET) /blockchain/api/chain

Returns the blockchain

Response

```json
{
    "message": "Current blockchain",
    "chain": [
        {
            "index": 1,
            "timestamp": 1516489542010,
            "proof": 0,
            "previousHash": "Genesis Block",
            "transactions": []
        }
    ]
}
```

### (GET) /blockchain/api/mine

Mines the next block and returns the block

Response
```json
{
    "message": "New block created",
    "index": 4,
    "prevHash": "11088ac77ba712fc2f2a4cfa55ea0cbc9a677f3aebc2943061d972b5bf25e93d",
    "proof": 33829,
    "transactions": [
        {
            "sender": "0",
            "recipient": "07d2e5eb82404366831e2b5b5a9933bd",
            "amount": 1
        }
    ]
}
```

### (POST) /blockchain/api/transactions/new

Makes a new transaction and returns that transaction

Request

```json
{
    "sender": "FishMoley",
    "recipient": "Clap",
    "amount": 420
}
```

Response

```json
{
    "message": "Transaction will be added to block 5",
    "sender": "FishMoley",
    "recipient": "Clap",
    "amount": 420
}
```

### (POST) /blockchain/api/nodes/register

Registers a new node/peer and returns all registered nodes/peers

Request

```json
{
	"nodes": [ "http://localhost:5001" ]
}
```

Response

```json
{
    "message": "New nodes added",
    "nodes": [
        "http://localhost:1234",
        "http://localhost:5001"
    ]
}
```

### (GET) /blockchain/api/nodes/resolve

Updates the chain if possible and returns the updated chain

Response

```json
{
    "message": "The chain is authoritative",
    "chain": [
        {
            "index": 1,
            "timestamp": 1516803627844,
            "proof": 0,
            "previousHash": "Genesis Block",
            "transactions": []
        },
        ... 
    ],
    "newChain": null
}
```

```json
{
    "message": "The chain is updated",
    "chain": null,
    "newChain": [
        {
            "index": 1,
            "timestamp": 1516803627844,
            "proof": 0,
            "previousHash": "Genesis Block",
            "transactions": []
        },
        {
            "index": 2,
            "timestamp": 1516803664557,
            "proof": 90936,
            "previousHash": "0000d3b276b846572acc187f04cbfb44c1ca7ab7f9ee4383194a4e79572d0219",
            "transactions": [
                {
                    "sender": "0",
                    "recipient": "5510cb4ecc2e467b9a37db8adc2ae8cc",
                    "amount": 1
                }
            ]
        },
        ...
    ]
}
```

## Reference

https://en.bitcoin.it/wiki/Proof_of_work

https://hackernoon.com/learn-blockchains-by-building-one-117428612f46