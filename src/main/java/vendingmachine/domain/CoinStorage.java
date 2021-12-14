package vendingmachine.domain;

import java.util.Arrays;
import java.util.LinkedHashMap;

import vendingmachine.util.Validator;

public class CoinStorage {

	private static final String ZERO_COIN_MESSAGE = "[ERROR] 코인이 남아있지 않습니다.";
	private static final int INITIAL = 0;
	private final LinkedHashMap<Coin, Integer> box = new LinkedHashMap<>();
	private final Validator validator = new Validator();

	public CoinStorage(int startCoins) {
		setInitialCoins();
		validator.validateCoin(startCoins);
		putRandomCoinsFromMoney(startCoins);
	}

	private void setInitialCoins() {
		Arrays.stream(Coin.values())
			.forEach(coin -> box.put(coin, INITIAL));
	}

	private void putRandomCoinsFromMoney(int money) {
		if (money <= 0) {
			return;
		}
		Coin coinPicked = Coin.pickRandomCoinUnderMoney(money);
		putCoin(coinPicked);
		putRandomCoinsFromMoney(money - coinPicked.getAmount());
	}

	private void putCoin(Coin coin) {
		box.put(coin, box.get(coin) + 1);
	}

	public void getChangeCoins(int money) {
		for (Coin coin : box.keySet()) {
			int coinNumberToPay = Math.min(box.get(coin), money / coin.getAmount());
			box.put(coin, box.get(coin) - coinNumberToPay);
			money -= coin.getAmount() * coinNumberToPay;
		}
	}

	public LinkedHashMap<Coin, Integer> checkbox() {
		return box;
	}
}
