# Scrypto - Crypto screener
 This app lists various cryptocurrencies in market.
 
[CoinListActivity](https://github.com/jajoomr/Scrypto/blob/main/app/src/main/java/com/mayurjajoo/screepto/view/CoinListActivity.kt) is the home activity.
It is responsible for displaying a list of coins.
It observes data from [CoinListViewModel](https://github.com/jajoomr/Scrypto/blob/main/app/src/main/java/com/mayurjajoo/screepto/viewmodel/CoinListViewModel.kt) and updates the UI when data changes.

On item click, it parses data to [CoinDetailActivity](https://github.com/jajoomr/Scrypto/blob/main/app/src/main/java/com/mayurjajoo/screepto/view/CoinDetailActivity.kt).
[CoinDetailActivity](https://github.com/jajoomr/Scrypto/blob/main/app/src/main/java/com/mayurjajoo/screepto/view/CoinDetailActivity.kt) uses the parsed object and updates the detailed information of the selected coin.
Note: New ViewModel or shared ViewModel can be used to populate DetailedView using the id of coin, but for keeping things simple and for learning purposes, parceable is used to parse data into activity.

[CoinListViewModel](https://github.com/jajoomr/Scrypto/blob/main/app/src/main/java/com/mayurjajoo/screepto/viewmodel/CoinListViewModel.kt) requests data from CoinsRepository and updates [CoinListActivity](https://github.com/jajoomr/Scrypto/blob/main/app/src/main/java/com/mayurjajoo/screepto/view/CoinListActivity.kt).

[CoinsRepository](https://github.com/jajoomr/Scrypto/tree/main/app/src/main/java/com/mayurjajoo/screepto/repository) is responsible for managing data.
It makes an API call and stores data in DB if the internet is available.
The repository returns the data stored in DB.
Room DB is used to manage storage.
