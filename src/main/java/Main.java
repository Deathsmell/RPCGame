import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        if (args.length % 2 != 1 || args.length < 3) {
            throw new IllegalArgumentException("Incorrect total number args " + args.length);
        }
        long uniqueCount = Arrays.stream(args).distinct().count();
        if (args.length != uniqueCount) {
            throw new IllegalArgumentException("You inputted not unique arg");
        }

        SecureRandom random = new SecureRandom();
        int randomCompChoice = random.nextInt(args.length);
        String key = Hmac.generateKey(random);
        String hmac = Hmac.generateHmac(args[randomCompChoice], key);
        System.out.printf("HMAC: %s\n", hmac);

        String menu = generateMenu(args);
        int playerChoice = 0;
        System.out.println(menu);
        Scanner scanner = new Scanner(System.in);
        do {
            if (scanner.hasNextInt()) {
                playerChoice = scanner.nextInt();
                if (playerChoice == 0) {
                    System.out.println("Good luck!");
                    return;
                }
                if (playerChoice <= args.length) {
                    System.out.format("Your move: %s\nComputer move: %s\n",
                            args[playerChoice - 1],
                            args[randomCompChoice]);
                }
                if (playerChoice - 1 != randomCompChoice) {
                    break;
                } else {
                    System.out.println("Ups. Draw. Try again and good luck !");
                    return;
                }
            } else {
                System.out.println("Ups. Incorrect input. Please try again: ");
                scanner.next();
            }
        } while (scanner.hasNext());
        scanner.close();

        System.out.printf("You %s!", areYouWiningSon(args, randomCompChoice, playerChoice));
        System.out.printf("\nHMAC key: %s\n", key);
    }

    private static String areYouWiningSon(String[] args, int comp, int player) {
        int middle = args.length / 2;
        while (middle != 0) {
            if (player++ == comp) return "lose";
            if (player == args.length - 1) player = 0;
            middle--;
        }
        return "win";
    }

    private static String generateMenu(String[] args) {
        StringBuilder builder = new StringBuilder();
        builder.append("Available moves:\n");
        for (int i = 1; i <= args.length; i++) {
            builder.append(i)
                    .append(" - ")
                    .append(args[i - 1])
                    .append("\n");
        }
        builder.append("0 - exit\n")
                .append("Enter your move: ");
        return builder.toString();
    }
}

