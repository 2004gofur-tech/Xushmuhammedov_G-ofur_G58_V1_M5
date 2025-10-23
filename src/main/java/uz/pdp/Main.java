package uz.pdp;

import uz.pdp.service.MainService;

import java.io.IOException;
import java.net.URISyntaxException;

public class Main {
    public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException {
        MainService.run();
    }
}
