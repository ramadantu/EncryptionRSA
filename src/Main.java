import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        char[] hardCodedAlphabet = new char[]{
                'а','б','в','г','д','е',
                'ж','з','и','й','к','л',
                'м','н','о','п','р','с',
                'т','у','ф','х','ц','ч',
                'ш','щ','ъ','ь','ю','я'
        };

        int[] hardCodedAlphabetKeys = new int[]{
                30,29,28,27,26,25,
                24,23,22,21,20,19,
                18,17,16,15,14,13,
                12,33,34,35,11,36,
                37,38,1,47,39,40
        };

        Letter[] alphabet = new Letter[hardCodedAlphabet.length];

        for (int i = 0; i < alphabet.length; i++) {
            char c = hardCodedAlphabet[i];
            //System.out.print("Enter key for '" + c + "': ");
            //int key = Integer.parseInt(reader.readLine());
            alphabet[i] = new Letter(c, hardCodedAlphabetKeys[i]);
        }

        //for (Letter letter: alphabet) {
        //    System.out.println(letter.getValue() + ", " + letter.getKey());
        //}

        System.out.print("Enter p: ");
        int p = Integer.parseInt(reader.readLine());
        System.out.print("Enter q: ");
        int q = Integer.parseInt(reader.readLine());
        System.out.print("Enter pk: ");
        int pk = Integer.parseInt(reader.readLine());
        System.out.print("Enter the text: ");
        String plainText = reader.readLine();

        int n, phiN, sk = 0;
        n = p * q;
        phiN = (p - 1) * (q - 1);
        System.out.println("phi(N) = " + phiN);

        for (int i = 0; i <= 9; i++) {
            int x = 1 + (i * phiN);

            if (x % pk == 0) {
                sk = x / pk;
                break;
            }
        }
        System.out.println("SK = " + sk);

        System.out.print("Choose encryption/decryption: ");
        while (true) {
            String choice = (reader.readLine()).toLowerCase();
            if (choice.equals("encryption")) {
                Encryption(plainText, alphabet, n, pk);
                break;
            }
            if (choice.equals("decryption")) {
                List<BigInteger> encMsg = new ArrayList<>();
                StringBuilder num = new StringBuilder();
                for (int i = 0; i < plainText.length(); i++) {
                    if (plainText.charAt(i) == ' ') {
                        encMsg.add(BigInteger.valueOf(Integer.parseInt(num.toString())));
                        num = new StringBuilder();
                    } else {
                        num.append(plainText.charAt(i));
                    }
                }
                encMsg.add(BigInteger.valueOf(Integer.parseInt(num.toString())));
                Decryption(encMsg, alphabet, n, sk);
            }
        }
    }

    public static void Encryption(String plainText, Letter[] alphabet, int n, int pk) {
        char[] msg  = plainText.toLowerCase().toCharArray();
        List<BigInteger> encMsg = new ArrayList<>();
        BigInteger N = BigInteger.valueOf(n);

        for (char c: msg) {
            for (Letter letter: alphabet) {
                if (letter.getValue() == c) {
                    BigInteger X = BigInteger.valueOf(letter.getKey());
                    encMsg.add((X.pow(pk)).mod(N));
                }
            }
        }

        System.out.print("Encrypted message is: ");
        for (BigInteger num: encMsg) {
            System.out.print(num);
        }
        System.out.println();
    }

    public static void Decryption(List<BigInteger> encMsg, Letter[] alphabet, int n, int sk) {
        StringBuilder decMsg = new StringBuilder();
        BigInteger N = BigInteger.valueOf(n);
        for (BigInteger Y: encMsg) {
            BigInteger X = (Y.pow(sk)).mod(N);
            for (Letter letter: alphabet) {
                if ((BigInteger.valueOf(letter.getKey())).equals(X)) {
                    decMsg.append(letter.getValue());
                }
            }
        }
        System.out.println("Decrypted message is: " + decMsg);
    }
}
