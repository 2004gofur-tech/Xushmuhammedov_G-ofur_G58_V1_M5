package uz.pdp.service;

import java.io.IOException;
import java.net.URISyntaxException;

import static uz.pdp.dependency.UtilScanner.intScanner;

public class MainService {

    public static void run() throws URISyntaxException, IOException, InterruptedException {
        System.out.println("Bizning online platformamizga xush kelibsiz");
        w:
        while (true) {
            mainMenu();
            switch (intScanner.nextInt()) {
                case 1 -> BrandService.run();
                case 2 -> CarService.run();
                case 3 -> {
                    break w;
                }
                default -> System.out.println("Noto'g'ri Amaliyotni tanladingiz!");
            }
        }
    }

    static void mainMenu() {
        System.out.println("""
                1. BrandService
                2. CarService
                3. Exit
                """);
    }
}
