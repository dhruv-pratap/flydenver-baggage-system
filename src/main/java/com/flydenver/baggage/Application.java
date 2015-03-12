package com.flydenver.baggage;

import com.flydenver.baggage.vo.Input;
import com.flydenver.baggage.vo.Output;

import java.io.IOException;
import java.nio.file.Paths;

import static com.flydenver.baggage.vo.Input.parse;
import static java.nio.file.Files.readAllBytes;

/**
 * @author Dhruv Pratap
 */
public class Application {

    public static void main(String[] args) {

        if (args == null || args.length == 0) {
            System.out.println("Usage: java -jar flydenver-baggage-system.jar <FILE_LOCATION>");
            return;
        }

        Input input;
        try {
            input = parse(new String(readAllBytes(Paths.get(args[0]))));
        } catch (IOException e) {
            System.out.println("Unable to read file. " + e.getMessage());
            return;
        }

        new Output(input).printOptimalRoutes();
    }
}
