package uz.pdp.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import uz.pdp.brandEntity.Brand;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;

import static uz.pdp.dependency.UtilScanner.intScanner;
import static uz.pdp.dependency.UtilScanner.stringScanner;
import static uz.pdp.service.MainService.mainMenu;

public class BrandService {

    public static void run() throws URISyntaxException, IOException, InterruptedException {
        System.out.println("BrandService ga xush kelibsiz!");
        w:
        while (true) {
            brandMenu();
            switch (intScanner.nextInt()) {
                case 1 -> createdNewBrand();
                case 2 -> showAllBrands();
                case 3 -> showBrandsById();
                case 4 -> updetBrand();
                case 5 -> deleteBrand();
                case 6 -> {
                    break w;
                }
                default -> System.out.println("Invalid input!");
            }
        }
    }

    private static void deleteBrand() throws URISyntaxException, IOException, InterruptedException {
        System.out.print("Brand deleted id: ");
        Integer id = intScanner.nextInt();

        String url = "http://localhost:5050/api/brands" + id;

        Gson gson = new GsonBuilder().create();

        HttpClient client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(url))
                .DELETE()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String body = response.body();

        Brand brand = gson.fromJson(body, Brand.class);

        int statusCode = response.statusCode();
        if (statusCode == 204) {
            System.out.println("Brand deleted successfully!");
        } else {
            System.out.println("Brand delete failed!");
        }
    }

    private static void updetBrand() throws URISyntaxException, IOException, InterruptedException {
        System.out.print("Enter group id: ");
        long id = intScanner.nextInt();
        System.out.print("Enter Brand new name: ");
        String name = stringScanner.nextLine();
        System.out.print("Enter Brand new country: ");
        String country = stringScanner.nextLine();

        Brand newBrand = Brand.builder()
                .name(name)
                .country(country)
                .build();

        Gson gson = new GsonBuilder().create();
        String jsonBody = gson.toJson(newBrand);


        String url = "http://localhost:5050/api/brans/" + id;


        HttpClient client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(url))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        HttpResponse<String> response = client
                .send(request, HttpResponse.BodyHandlers.ofString());

        String body = response.body();
        Brand brand = gson.fromJson(body, Brand.class);
        System.out.println(brand);
        System.out.println("Brand is updated");
        System.out.println("---------------------");
    }

    private static void showBrandsById() throws URISyntaxException, IOException, InterruptedException {
        System.out.print("Brand id : ");
        Integer id = intScanner.nextInt();

        Brand brand = Brand.builder()
                .id(id)
                .build();
        Gson gson = new Gson();

        String url = "http://localhost:5050/api/brands" + id;
        HttpClient client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(url))
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String body = response.body();
        int statusCode = response.statusCode();
        if (statusCode == 404) {
            System.out.println("ERROR!");
        } else {
            Brand[] brands = gson.fromJson(body, Brand[].class);
            System.out.println(Arrays.toString(brands));
        }


    }

    private static void showAllBrands() throws URISyntaxException, IOException, InterruptedException {

        String url = "http://localhost:5050/api/brands";

        Gson gson = new GsonBuilder().create();

        HttpClient client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(url))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String body = response.body();

        Brand[] brand = gson.fromJson(body, Brand[].class);
        for (Brand brands : brand) {
            System.out.println(brands);
        }
        System.out.println("---------------------");


    }

    private static void createdNewBrand() throws URISyntaxException, IOException, InterruptedException {
        System.out.print("New Brand name: ");
        String name = stringScanner.nextLine();
        System.out.print("New Brand country: ");
        String country = stringScanner.nextLine();

        Brand newBrand = Brand.builder()
                .name(name)
                .country(country)
                .build();

        Gson gson = new GsonBuilder().create();
        String jsonBody = gson.toJson(newBrand);

        String url = "http://localhost:5050/api/brands";
        HttpClient client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(url))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.noBody())
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String body = response.body();
        Brand createdBrand = gson.fromJson(body, Brand.class);
        System.out.println(createdBrand);
    }

    private static void brandMenu() {
        System.out.println("""
                1. createdNewBrand
                2. showAllBrands
                3. showBrandsById
                4. updeteBrand
                5. DeleteBrand
                6. MainMenu
                """);
    }
}
