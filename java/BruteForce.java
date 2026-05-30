import java.io.IOException;

public class BruteForce {

    public String decryptByBruteForce(String inputFile, String sampleFile) throws IOException {
        FileManager fileManager = new FileManager();
        CaesarCipher cipher = new CaesarCipher();

        String encryptedText = fileManager.readFile(inputFile);

        String bestDecryption = "";
        int bestScore = -1;
        int bestKey = 0;

        for (int key = 0; key < cipher.getAlphabetSize(); key++) {
            String decrypted = cipher.decrypt(encryptedText, key);
            int score = evaluateText(decrypted, sampleFile, fileManager);

            if (score > bestScore) {
                bestScore = score;
                bestDecryption = decrypted;
                bestKey = key;
            }
        }

        System.out.println("Найден ключ: " + bestKey + " (оценка: " + bestScore + ")");
        return bestDecryption;
    }

    private int evaluateText(String text, String sampleFile, FileManager fileManager) throws IOException {
        if (sampleFile != null && !sampleFile.isBlank() && fileManager.fileExists(sampleFile)) {
            String sample = fileManager.readFile(sampleFile);
            return compareWithSample(text, sample);
        }

        int score = 0;

        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == ' ') {
                score++;
            }
        }

        char[] vowels = {'а', 'е', 'ё', 'и', 'о', 'у', 'ы', 'э', 'ю', 'я'};
        for (int i = 0; i < text.length(); i++) {
            char c = Character.toLowerCase(text.charAt(i));
            for (char vowel : vowels) {
                if (c == vowel) {
                    score++;
                    break;
                }
            }
        }

        char[] punctuation = {'.', ',', '!', '?', ':', '"', '«', '»'};
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            for (char punct : punctuation) {
                if (c == punct) {
                    score += 2;
                    break;
                }
            }
        }

        return score;
    }

    private int compareWithSample(String decrypted, String sample) {
        int matches = 0;
        int length = Math.min(decrypted.length(), sample.length());

        for (int i = 0; i < length; i++) {
            if (decrypted.charAt(i) == sample.charAt(i)) {
                matches++;
            }
        }

        return matches;
    }
}
