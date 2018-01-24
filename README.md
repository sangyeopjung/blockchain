# Blockchain

## About

A simple Java blockchain ledger created for studying purposes.

- [x] Blockchain
- [x] API
- [x] Consensus
- [ ] Wallet
- [ ] Signature
- [x] Unit Test
- [ ] Frontend

## Tools Used

- Java 1.8
- Spring Boot 1.5.9
- Maven 3.5.2
- Jersey 2.25.1
- Lombok 1.16.20
- Jackson 2.8.10
- Slf4j 1.7.5
- JUnit 4.12
- Mockito 1.10.19

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
            "timestamp": 1516825154660,
            "proof": 0,
            "previousHash": "Genesis Block",
            "transactions": []
        },
        {
            "index": 2,
            "timestamp": 1516825223097,
            "proof": 319779,
            "previousHash": "00009330a66fefbd524c583bc8a15e9ec5dc4f0d76f1e3af1041899e436bcde3",
            "transactions": [
                {
                    "sender": "0",
                    "recipient": "ec10a913771b433b8782a733c73e5ae2",
                    "amount": 1
                }
            ]
        },
        {
            "index": 3,
            "timestamp": 1516825263380,
            "proof": 168995,
            "previousHash": "0000f5707b9ac262cac5c4c8197f36dbcaba249257696458b28f626d8301b1ff",
            "transactions": [
                {
                    "sender": "FishMoley",
                    "recipient": "Clap",
                    "amount": 420
                },
                {
                    "sender": "0",
                    "recipient": "ec10a913771b433b8782a733c73e5ae2",
                    "amount": 1
                }
            ]
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
    "index": 3,
    "previousHash": "0000f5707b9ac262cac5c4c8197f36dbcaba249257696458b28f626d8301b1ff",
    "proof": 168995,
    "transactions": [
        {
            "sender": "FishMoley",
            "recipient": "Clap",
            "amount": 420
        },
        {
            "sender": "0",
            "recipient": "ec10a913771b433b8782a733c73e5ae2",
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
    "message": "Transaction will be added to block 3",
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
        }
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
        }
    ]
}
```

## Reference

https://en.bitcoin.it/wiki/Proof_of_work

https://hackernoon.com/learn-blockchains-by-building-one-117428612f46
