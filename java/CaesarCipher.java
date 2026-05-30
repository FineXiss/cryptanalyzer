public class CaesarCipher {

    private static final char[] ALPHABET = {
            'а', 'б', 'в', 'г', 'д', 'е', 'ё', 'ж', 'з', 'и', 'й', 'к', 'л', 'м',
            'н', 'о', 'п', 'р', 'с', 'т', 'у', 'ф', 'х', 'ц', 'ч', 'ш', 'щ', 'ъ',
            'ы', 'ь', 'э', 'ю', 'я', '.', ',', '«', '»', '"', '\'', ':', '!', '?', ' '
    };

    private int findCharIndex(char c) {
        for (int i = 0; i < ALPHABET.length; i++) {
            if (ALPHABET[i] == c) {
                return i;
            }
        }
        return -1;
    }

    public String encrypt(String text, int key) {
        key = key % ALPHABET.length;
        if (key < 0) {
            key = key + ALPHABET.length;
        }

        StringBuilder result = new StringBuilder(text.length());

        for (int i = 0; i < text.length(); i++) {
            char currentChar = text.charAt(i);
            int index = findCharIndex(currentChar);

            if (index != -1) {
                int newIndex = (index + key) % ALPHABET.length;
                result.append(ALPHABET[newIndex]);
            } else {
                result.append(currentChar);
            }
        }

        return result.toString();
    }

    public String decrypt(String text, int key) {
        return encrypt(text, ALPHABET.length - (key % ALPHABET.length));
    }

    public int getAlphabetSize() {
        return ALPHABET.length;
    }

    public char[] getAlphabet() {
        return ALPHABET.clone();
    }
}
