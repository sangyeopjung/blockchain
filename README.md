# Blockchain

## About

A simple Java blockchain ledger created for studying purposes.

- [x] Blockchain
- [x] API
- [ ] Consensus

## Tools Used

* Spring Boot
* Maven
* Jersey
* Lombok
* Jackson
* Slf4j

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

Makes a new transaction

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

## Reference

https://en.bitcoin.it/wiki/Proof_of_work

https://hackernoon.com/learn-blockchains-by-building-one-117428612f46