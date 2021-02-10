package Utils;

import java.util.Random;

public class CommonUtils {
	
	public static int appendRandomNumber() {

		Random random = new Random();
		return random.nextInt(2000);

	}

}
