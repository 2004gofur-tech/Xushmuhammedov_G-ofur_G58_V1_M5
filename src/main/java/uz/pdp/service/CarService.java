package uz.pdp.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import uz.pdp.brandEntity.Brand;
import uz.pdp.carEntity.Car;

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

public class CarService {
    public static void run() throws IOException, URISyntaxException, InterruptedException {

        System.out.println("CarService ga Xush kelibsiz!");
        w:
        while (true) {
            carMenu();
            switch (intScanner.nextInt()) {
                case 1 -> createdNewCar();
                case 2 -> showAllCars();
                case 3 -> showCarById();
                case 4 -> updeteCar();
                case 5 -> deleteCar();
                case 6 -> {
                    break w;
                }
                default -> System.out.println("Invalid input!");
            }
        }
    }

    private static void deleteCar() throws URISyntaxException, IOException, InterruptedException {
        System.out.print("Car deleted id: ");
        int id = intScanner.nextInt();

        String url = "http://localhost:5050/api/cars" + id;

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

        Car car = gson.fromJson(body, Car.class);

        int statusCode = response.statusCode();
        if (statusCode == 204) {
            System.out.println("Car deleted successfully!");
        } else {
            System.out.println("Car delete failed!");
        }
    }

    private static void updeteCar() throws URISyntaxException, IOException, InterruptedException {
        System.out.print("Car id: ");
        int id = intScanner.nextInt();
        System.out.print("New Car name: ");
        String name = stringScanner.nextLine();
        System.out.print("New Car year: ");
        String year = stringScanner.nextLine();
        System.out.print("New Car price: ");
        Integer price = intScanner.nextInt();
        Brand brand = new Brand();

        Car newCar = Car.builder()
                .name(name)
                .year(year)
                .price(price)
                .brand(brand)
                .build();

        Gson gson = new GsonBuilder().create();
        String jsonBody = gson.toJson(newCar);


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
        Car car = gson.fromJson(body, Car.class);
        System.out.println(car);
        System.out.println("Car is updated");
        System.out.println("---------------------");
    }

    private static void showCarById() throws URISyntaxException, IOException, InterruptedException {
        System.out.print("Car id : ");
        Integer id = intScanner.nextInt();

        Car brand = Car.builder()
                .id(id)
                .build();
        Gson gson = new Gson().newBuilder().create();

        String url = "http://localhost:5050/api/cars" + id;
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
            Car[] cars = gson.fromJson(body, Car[].class);
            System.out.println(Arrays.toString(cars));
        }


    }

    private static void showAllCars() throws URISyntaxException, IOException, InterruptedException {
        String url = "http://localhost:5050/api/cars";

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

        Car[] brand = gson.fromJson(body, Car[].class);
        for (Car cars : brand) {
            System.out.println(cars);
        }
        System.out.println("---------------------");

    }

    private static void createdNewCar() throws IOException, InterruptedException, URISyntaxException {
        System.out.print("New Car name: ");
        String name = stringScanner.nextLine();
        System.out.print("New Car year: ");
        String year = stringScanner.nextLine();
        System.out.print("New Car price: ");
        Integer price = intScanner.nextInt();
        System.out.println("New Car Brand (id): ");
        Brand brandId = new Brand();

        Car newCar = Car.builder()
                .name(name)
                .year(year)
                .price(price)
                .brand(brandId)
                .build();

        Gson gson = new GsonBuilder().create();
        String jsonBody = gson.toJson(newCar);

        String url = "http://localhost:5050/api/cars";
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
        Car createdBrand = gson.fromJson(body, Car.class);
        System.out.println(createdBrand);
    }

    private static void carMenu() {
        System.out.println("""
                1. CreateNewCar
                2. ShowAllCars
                3. ShowCarsById
                4. UpdateCar
                5. DeleteCar
                6. MainMenu
                """);
    }
}
