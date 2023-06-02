package pt.ubi.wordle;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;

public class WordlistEncryptor {
    private static final String currDir = System.getProperty("user.dir");
    private static final String[] language = {"en", "pt", "fr"};

    public static void main(String[] args) throws Exception {
        for (int i = 3; i < 8; i++) {
            for (int j = 0; j < 3; j++) {
                String inputFile = currDir + "/wordlists/" + i + "-" + language[j] + ".txt.old";
                String outputFile = currDir + "/wordlists/" + i + "-" + language[j] + ".txt";

                BufferedReader reader = new BufferedReader(new FileReader(inputFile));

                BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));

                String line;
                while ((line = reader.readLine()) != null) {
                    String modifiedLine = Encryption.aesAlgorithm(line, 1);
                    assert modifiedLine != null;
                    writer.write(modifiedLine);
                    writer.newLine();
                }

                reader.close();
                writer.close();

                System.out.println("File rewriting completed successfully!");
            }
        }
    }
}

