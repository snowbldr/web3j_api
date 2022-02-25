# web3j test api
A project for experiments using web3j

## Create a wallet
Start the server using `gradle run`

navigate to http://localhost:22420/wallet/new

Save the contents in the file ./src/main/resources/wallet/wallet1.json

Fund your wallet using https://ethdrop.dev/ or another testnet faucet if you use a different RPC

### Check the balance

go to http://localhost:22420/balance/0xaddressgoeshere1234567890

### transfer funds to another wallet

go to http://localhost:22420/transfer/0xaddressgoeshere1234567890/0.0001

Change the address to the target wallet you want to send funds to

The last path segment is the amount to send

#### (Optional) Setup a local openethereum node

Create a volume to store eth node data in
`docker volume create --driver=local --opt o=uid=1000 --opt type=tmpfs --opt device=tmpfs oeth_kovan`

Start the openethereum kovan testnet node
`docker-compose up -d`

The testnet can be changed by changing the --chain param in docker-compose.yaml