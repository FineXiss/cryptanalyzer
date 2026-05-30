import java.util.Scanner;

public class MainApp {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CaesarCipher cipher = new CaesarCipher();
        FileManager fileManager = new FileManager();
        Validator validator = new Validator();

        while (true) {
            System.out.println("\n=== ШИФР ЦЕЗАРЯ ===");
            System.out.println("1. Зашифровать текст");
            System.out.println("2. Расшифровать текст с ключом");
            System.out.println("3. Brute force (перебор)");
            System.out.println("4. Статистический анализ");
            System.out.println("0. Выход");
            System.out.print("Выберите режим: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 0) {
                System.out.println("Программа завершена.");
                break;
            }

            try {
                switch (choice) {
                    case 1 -> handleEncrypt(scanner, cipher, fileManager, validator);
                    case 2 -> handleDecrypt(scanner, cipher, fileManager, validator);
                    case 3 -> handleBruteForce(scanner, fileManager, validator);
                    case 4 -> handleStatisticalAnalysis(scanner, fileManager, validator);
                    default -> System.out.println("Неверный выбор!");
                }
            } catch (Exception e) {
                System.out.println("Произошла ошибка: " + e.getMessage());
            }
        }

        scanner.close();
    }

    private static void handleEncrypt(Scanner scanner,
                                      CaesarCipher cipher,
                                      FileManager fileManager,
                                      Validator validator) throws Exception {
        System.out.print("Путь к файлу с текстом: ");
        String inputFile = scanner.nextLine();
        System.out.print("Путь к файлу для результата: ");
        String outputFile = scanner.nextLine();
        System.out.print("Ключ (сдвиг): ");
        int key = scanner.nextInt();

        if (!validator.isFileExists(inputFile)) {
            System.out.println("Ошибка: файл не найден!");
            return;
        }

        if (!validator.isValidKey(key, cipher.getAlphabetSize())) {
            System.out.println("Ошибка: ключ должен быть от 0 до " + (cipher.getAlphabetSize() - 1));
            return;
        }

        String text = fileManager.readFile(inputFile);
        String encrypted = cipher.encrypt(text, key);
        fileManager.writeFile(encrypted, outputFile);
        System.out.println("Готово! Зашифрованный текст сохранён в " + outputFile);
    }

    private static void handleDecrypt(Scanner scanner,
                                      CaesarCipher cipher,
                                      FileManager fileManager,
                                      Validator validator) throws Exception {
        System.out.print("Путь к зашифрованному файлу: ");
        String inputFile = scanner.nextLine();
        System.out.print("Путь для сохранения: ");
        String outputFile = scanner.nextLine();
        System.out.print("Ключ (сдвиг): ");
        int key = scanner.nextInt();

        if (!validator.isFileExists(inputFile)) {
            System.out.println("Ошибка: файл не найден!");
            return;
        }

        if (!validator.isValidKey(key, cipher.getAlphabetSize())) {
            System.out.println("Ошибка: ключ должен быть от 0 до " + (cipher.getAlphabetSize() - 1));
            return;
        }

        String text = fileManager.readFile(inputFile);
        String decrypted = cipher.decrypt(text, key);
        fileManager.writeFile(decrypted, outputFile);
        System.out.println("Готово! Расшифрованный текст сохранён в " + outputFile);
    }

    private static void handleBruteForce(Scanner scanner,
                                         FileManager fileManager,
                                         Validator validator) throws Exception {
        System.out.print("Путь к зашифрованному файлу: ");
        String inputFile = scanner.nextLine();
        System.out.print("Путь для сохранения: ");
        String outputFile = scanner.nextLine();
        System.out.print("Путь к файлу-образцу (Enter если нет): ");
        String sampleFile = scanner.nextLine();

        if (!validator.isFileExists(inputFile)) {
            System.out.println("Ошибка: файл не найден!");
            return;
        }

        BruteForce bruteForce = new BruteForce();
        String result = bruteForce.decryptByBruteForce(inputFile, sampleFile);
        fileManager.writeFile(result, outputFile);
        System.out.println("Готово! Результат сохранён в " + outputFile);
    }

    private static void handleStatisticalAnalysis(Scanner scanner,
                                                  FileManager fileManager,
                                                  Validator validator) throws Exception {
        System.out.print("Путь к зашифрованному файлу: ");
        String inputFile = scanner.nextLine();
        System.out.print("Путь для сохранения: ");
        String outputFile = scanner.nextLine();
        System.out.print("Путь к файлу-образцу (Enter если нет): ");
        String sampleFile = scanner.nextLine();

        if (!validator.isFileExists(inputFile)) {
            System.out.println("Ошибка: файл не найден!");
            return;
        }

        StatisticalAnalyzer analyzer = new StatisticalAnalyzer();
        String result = analyzer.decryptByStatisticalAnalysis(inputFile, sampleFile);
        fileManager.writeFile(result, outputFile);
        System.out.println("Готово! Результат сохранён в " + outputFile);
    }
}
