# Blockchain

## About

A simple Java blockchain ledger created for studying purposes.

- [x] Blockchain
- [x] API
- [x] Consensus
- [x] Wallet
- [x] Signature
- [x] Unit Test
- [ ] Frontend

## Tools Used

- Java 1.8
- Spring Boot 2.0.0
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
            "timestamp": 1517754597241,
            "proof": 0,
            "previousHash": "Genesis Block",
            "transactions": []
        },
        {
            "index": 2,
            "timestamp": 1517754890547,
            "proof": 62420,
            "previousHash": "0000290e43a261d7927f1c8316bc3f998d1f7d1152787ea877c90ba2be7c475b",
            "transactions": [
                {
                    "sender": "0",
                    "recipient": "MFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAEueZrj8UrgLCBToTmjn3TxpGvYlROrrvvbNyNPXEQXk9gZY3W/Tqk6CswuJlthRH/onvy5kEkciiu1T3PQkHUCA==",
                    "amount": 50,
                    "timestamp": 1517754890542,
                    "signature": "MEUCIQC5nN4GLTYTHvmvAgZRyL1eSJmyzaq2NUVHwmKVZlK1nQIgDYwD1QnFOFaqscicXzcbbQcYSnHyw8p9m5KSn8d/en8="
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
    "index": 2,
    "previousHash": "00002c1656afe7b4bd0c0495ff79b577f1370520e4fb9dab2cdb87c00dca9965",
    "proof": 163043,
    "transactions": [
        {
            "sender": "MFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAENbcuJ/jZ8jUxQ27bHAceInANvpJuLLNaTqTpgnGa63sj5NRxruWjMwwC8dsJS2xr4KO+wycBGV0hEOkzwNbVKw==",
            "recipient": "MFkwKlB0ba4c6ff/fPI9QTPr47ZhEsst/ae7lAbWpWaJjoHLLUg7mPgpbxs71waxAqDg==",
            "amount": 420,
            "timestamp": 1517762432303,
            "signature": "MEUCIQCaPkfNkQopvq0NRpJYGEO+T94kzV0fo6aSH1ftVyFeUgIgYY7O4ppZnDUvZcKbiXWRvKd/u20kDE8Gr6S2mbmpoBA="
        },
        {
            "sender": "0",
            "recipient": "MFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAENbcuJ/jZ8jUxQ27bHAceInANvpJuLLNaTqTpgnGa63sj5NRxruWjMwwC8dsJS2xr4KO+wycBGV0hEOkzwNbVKw==",
            "amount": 50,
            "timestamp": 1517762461563,
            "signature": "MEUCIF1qsHMqNZoBeMrX1mShstairTjqmi7/HhK1IXPtOLs9AiEAjWpbck96tQuyOaKIYYLICricUq/rZcdO/iytCUhQ8X4="
        }
    ]
}
```

### (POST) /blockchain/api/transactions/new

Makes a new transaction and returns that transaction

Request

```json
{
	   "recipient": "MFkwKlB0ba4c6ff/fPI9QTPr47ZhEsst/ae7lAbWpWaJjoHLLUg7mPgpbxs71waxAqDg==",
    "amount": 420
}
```

Response

```json
{
    "message": "Transaction will be added to block 2",
    "sender": "MFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAENbcuJ/jZ8jUxQ27bHAceInANvpJuLLNaTqTpgnGa63sj5NRxruWjMwwC8dsJS2xr4KO+wycBGV0hEOkzwNbVKw==",
    "recipient": "MFkwKlB0ba4c6ff/fPI9QTPr47ZhEsst/ae7lAbWpWaJjoHLLUg7mPgpbxs71waxAqDg==",
    "amount": 420,
    "timestamp": 1517762432303,
    "signature": "MEUCIQCaPkfNkQopvq0NRpJYGEO+T94kzV0fo6aSH1ftVyFeUgIgYY7O4ppZnDUvZcKbiXWRvKd/u20kDE8Gr6S2mbmpoBA="
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
            "timestamp": 1517761798163,
            "proof": 0,
            "previousHash": "Genesis Block",
            "transactions": []
        },
        {
            "index": 2,
            "timestamp": 1517762461568,
            "proof": 163043,
            "previousHash": "00002c1656afe7b4bd0c0495ff79b577f1370520e4fb9dab2cdb87c00dca9965",
            "transactions": [
                {
                    "sender": "MFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAENbcuJ/jZ8jUxQ27bHAceInANvpJuLLNaTqTpgnGa63sj5NRxruWjMwwC8dsJS2xr4KO+wycBGV0hEOkzwNbVKw==",
                    "recipient": "MFkwKlB0ba4c6ff/fPI9QTPr47ZhEsst/ae7lAbWpWaJjoHLLUg7mPgpbxs71waxAqDg==",
                    "amount": 420,
                    "timestamp": 1517762432303,
                    "signature": "MEUCIQCaPkfNkQopvq0NRpJYGEO+T94kzV0fo6aSH1ftVyFeUgIgYY7O4ppZnDUvZcKbiXWRvKd/u20kDE8Gr6S2mbmpoBA="
                },
                {
                    "sender": "0",
                    "recipient": "MFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAENbcuJ/jZ8jUxQ27bHAceInANvpJuLLNaTqTpgnGa63sj5NRxruWjMwwC8dsJS2xr4KO+wycBGV0hEOkzwNbVKw==",
                    "amount": 50,
                    "timestamp": 1517762461563,
                    "signature": "MEUCIF1qsHMqNZoBeMrX1mShstairTjqmi7/HhK1IXPtOLs9AiEAjWpbck96tQuyOaKIYYLICricUq/rZcdO/iytCUhQ8X4="
                }
            ]
        }
    ]
}
```

## Reference

https://en.bitcoin.it/wiki/Proof_of_work

https://hackernoon.com/learn-blockchains-by-building-one-117428612f46
