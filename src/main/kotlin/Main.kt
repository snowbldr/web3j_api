
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.github.snowbldr.jankyhttp.Body
import com.github.snowbldr.jankyhttp.JankyHttpServer
import org.web3j.crypto.Keys
import org.web3j.crypto.Wallet
import org.web3j.crypto.WalletUtils
import org.web3j.protocol.Web3j
import org.web3j.protocol.core.DefaultBlockParameter
import org.web3j.protocol.http.HttpService
import org.web3j.tx.Transfer
import org.web3j.utils.Convert
import java.math.BigDecimal
import java.math.BigInteger

val web3j: Web3j = Web3j.build(HttpService("https://kovan.poa.network"))

fun lastBlock(): BigInteger = web3j.ethBlockNumber().send().blockNumber
val mapper = jacksonObjectMapper()
fun main() {
    JankyHttpServer { req ->
        when {
            req.path == "/" ->
                req.reply(Body.of(System.currentTimeMillis().toString()))
            req.path == "/lastBlock" ->
                req.reply(Body.of(lastBlock().toString(10)))
            req.path.startsWith("/balance/") ->
                req.reply(
                    Body.of(
                        web3j.ethGetBalance(
                            req.path.split("/balance/")[1],
                            DefaultBlockParameter.valueOf(lastBlock())
                        )
                            .send().balance.toString()
                    )
                )
            req.path == "/wallet/new" -> req.reply(
                Body(
                    mapper.writeValueAsBytes(
                        Wallet.createLight(
                            "",
                            Keys.createEcKeyPair()
                        )
                    )
                )
            )
            req.path.startsWith("/transfer/") ->
                req.reply(
                    Body.of(
                        Transfer.sendFunds(
                            web3j,
                            WalletUtils.loadJsonCredentials(
                                "",
                                Body::class.java.getResource("/wallet/wallet1.json")?.readText()
                            ),
                            req.path.split("/")[2],
                            BigDecimal(req.path.split("/")[3]),
                            Convert.Unit.ETHER
                        ).send().transactionHash
                    )
                )
            else -> req.reply(status = 404, statusMessage = "NOT FOUND")
        }
    }
}
