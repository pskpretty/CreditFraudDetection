# CreditFraudDetection

## A credit card transaction comprises the following elements.

-hashed credit card number -timestamp - of format year-month-dayThour:minute:second -amount - of format dollars.cents
Transactions are to be received in a ﬁle as a comma separated string of elements, one per line,

```
eg:
10d7ce2f43e35fa57d1bbf8b1e2, 2014-04-29T13:15:54, 10.00
```

A credit card will be identified as fraudulent if the sum of amounts for a unique hashed credit card number over a
24-hour sliding window period exceeds the price threshold. Write a command line application which takes a price
threshold argument and a ﬁle-name, eg: your-app 150.00 filename.csv The file passed to your app will contain a sequence
of transactions in chronological order.

### Run the application
```
mvn spring-boot:run
curl --location --request POST 'localhost:8080/api/uploadCsv' \
--form 'file="/home/pretty/Documents/transactionList.csv"' \
--form 'amount="50"'
```

## Built With

* [Intellij](https://www.jetbrains.com/idea/download/)- The Java IDE used
* [GitHub](https://github.com/) - Home of the Repository

