import com.creditcardFraudAlgorithm.model.Transaction
import com.creditcardFraudAlgorithm.service.FraudService
import com.creditcardFraudAlgorithm.service.TransactionService
import java.io.IOException
import java.math.BigDecimal
import java.time.format.DateTimeParseException

class Main{
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            if (args.isEmpty()) {
                print("Please add some command line arguments")
                return
            }
            val threshold = args[0]
            val filePath = args[1]
            val fraudDetection = FraudService()
            val transactionService = TransactionService()
            var transactionList = emptyList<Transaction>()
            try {
                transactionList = transactionService.readTransactionsFromFile(filePath)
            } catch (e: DateTimeParseException) {
                throw Exception("Incorrect datetime format in the sample file")
            } catch (e: NumberFormatException) {
                throw Exception("Threshold amount is in incorrect format in the sample file")
            } catch (e: IOException) {
                throw Exception("Incorrect path. File not found")
            }
            val fraudCardNumbers = fraudDetection.findFraudAccounts(transactionList, BigDecimal(threshold))
            transactionService.listFraudCardNumbers(fraudCardNumbers)
        }
    }
}