package membership;

import java.security.SecureRandom;

public class AuthCodeGenerator {
	private static final String CHARACTERS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final int CODE_LENGTH = 6;

    public static String generateAuthCode() {
        SecureRandom random = new SecureRandom();
        StringBuilder authCode = new StringBuilder(CODE_LENGTH);

        for (int i = 0; i < CODE_LENGTH; i++) {
            authCode.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return authCode.toString();
    }
}
