import com.google.gson.Gson;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String apiUrl = "https://v6.exchangerate-api.com/v6/7bcc7e06948a0d2b26db1a34/latest/PEN";

        try {
            HttpClient usuario = HttpClient.newHttpClient();

            HttpRequest solicitud = HttpRequest.newBuilder()
                    .uri(URI.create(apiUrl))
                    .GET()
                    .build();

            HttpResponse<String> respuesta = usuario.send(solicitud, HttpResponse.BodyHandlers.ofString());

            Gson parsear = new Gson();
            CambioMonedas tasas = parsear.fromJson(respuesta.body(), CambioMonedas.class);

            Scanner scanner = new Scanner(System.in);
            boolean continuar = true;

            while (continuar) {
                System.out.println("\n--- Conversor de Monedas ---");
                System.out.println("1. Convertir a USD (Dólares)");
                System.out.println("2. Convertir a EUR (Euros)");
                System.out.println("3. Convertir a BRL (Real brasileño)");
                System.out.println("4. Salir");
                System.out.print("Seleccione una opción: ");

                int opcion = scanner.nextInt();

                switch (opcion) {
                    case 1:
                        convertirMoneda(scanner, tasas.conversion_rates.get("USD"), "USD");
                        break;
                    case 2:
                        convertirMoneda(scanner, tasas.conversion_rates.get("EUR"), "EUR");
                        break;
                    case 3:
                        convertirMoneda(scanner, tasas.conversion_rates.get("BRL"), "BRL");
                        break;
                    case 4:
                        System.out.println("\n¡Adiós!");
                        continuar = false;
                        break;
                    default:
                        System.out.println("Opción inválida, intente de nuevo.");
                }
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void convertirMoneda(Scanner scanner, Double tasa, String moneda) {
        System.out.print("Ingrese la cantidad en soles (PEN): ");
        double cantidad = scanner.nextDouble();
        double resultado = cantidad * tasa;
        System.out.println(cantidad + " PEN equivalen a " + String.format("%.2f", resultado) + " " + moneda);

        // Preguntar si desea continuar
        System.out.print("¿Deseas continuar? (y/n): ");
        String res = scanner.next();

        if (res.equalsIgnoreCase("y")) {
            convertirMoneda(scanner, tasa, moneda);
        } else {
            System.out.print("\nVolviendo al menú...");
        }
    }

    public class CambioMonedas {
        Map<String, Double> conversion_rates;
    }
}
