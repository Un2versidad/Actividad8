import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;

public class Actividad8 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<String> resultados = new ArrayList<>();

        while (true) {
            int figura = getValidMenuInput(sc, "Seleccione una figura (1-6): ", 6, Actividad8::mostrarMenuFiguras);

            if (figura == 6) {
                break;
            }

            int operacion = getValidMenuInput(sc, "Seleccione una operación (1-3): ", 3, Actividad8::mostrarMenuOperaciones);

            FiguraGeometrica figuraGeometrica = crearFigura(sc, figura);
            if (figuraGeometrica == null) continue;

            String resultado = switch (operacion) {
                case 1 -> String.format("Área: %.2f", figuraGeometrica.calcularArea());
                case 2 -> String.format("Perímetro: %.2f", figuraGeometrica.calcularPerimetro());
                case 3 -> {
                    double base = getValidInput(sc, "Ingrese la base para la potencia: ", Double::parseDouble, input -> input > 0);
                    int exponente = getValidInput(sc, "Ingrese el exponente: ", Integer::parseInt, input -> input >= 0);
                    yield String.format("Potencia: %.2f", figuraGeometrica.calcularPotencia(base, exponente));
                }
                default -> "";
            };

            System.out.println("\n════════ RESULTADO ════════\n" + resultado + "\n");
            resultados.add(resultado);
        }

        mostrarResumen(resultados);
        System.out.println("¡Hasta luego!");
    }

    private static FiguraGeometrica crearFigura(Scanner sc, int figura) {
        return switch (figura) {
            case 1 -> new Circulo(getValidInput(sc, "Ingrese el radio del círculo: ", Double::parseDouble, input -> input > 0));
            case 2 -> new Cuadrado(getValidInput(sc, "Ingrese el lado del cuadrado: ", Double::parseDouble, input -> input > 0));
            case 3 -> new Triangulo(
                    getValidInput(sc, "Ingrese la base del triángulo: ", Double::parseDouble, input -> input > 0),
                    getValidInput(sc, "Ingrese la altura del triángulo: ", Double::parseDouble, input -> input > 0),
                    getValidInput(sc, "Ingrese el lado 1 del triángulo: ", Double::parseDouble, input -> input > 0),
                    getValidInput(sc, "Ingrese el lado 2 del triángulo: ", Double::parseDouble, input -> input > 0),
                    getValidInput(sc, "Ingrese el lado 3 del triángulo: ", Double::parseDouble, input -> input > 0)
            );
            case 4 -> new Rectangulo(
                    getValidInput(sc, "Ingrese el largo del rectángulo: ", Double::parseDouble, input -> input > 0),
                    getValidInput(sc, "Ingrese el ancho del rectángulo: ", Double::parseDouble, input -> input > 0)
            );
            case 5 -> new Pentagono(
                    getValidInput(sc, "Ingrese el lado del pentágono: ", Double::parseDouble, input -> input > 0),
                    getValidInput(sc, "Ingrese la apotema del pentágono: ", Double::parseDouble, input -> input > 0)
            );
            default -> null;
        };
    }

    private static int getValidMenuInput(Scanner sc, String mensaje, int max, Runnable mostrarMenu) {
        int result;
        do {
            mostrarMenu.run();
            result = getValidInput(sc, mensaje, Integer::parseInt, input -> input >= 1 && input <= max);
        } while (result < 1 || result > max);
        return result;
    }

    private static <T> T getValidInput(Scanner sc, String mensaje, Function<String, T> parser, Function<T, Boolean> validator) {
        return getValidInput(sc, () -> System.out.print(mensaje), parser, validator);
    }

    private static <T> T getValidInput(Scanner sc, Runnable prompt, Function<String, T> parser, Function<T, Boolean> validator) {
        T result;
        do {
            prompt.run();
            String input = sc.nextLine().trim();
            result = null;
            try {
                result = parser.apply(input);
                if (!validator.apply(result)) {
                    throw new IllegalArgumentException("Valor fuera de rango.");
                }
            } catch (Exception e) {
                System.out.println("Entrada no válida. Intente de nuevo.");
            }
        } while (result == null);
        return result;
    }

    private static void mostrarMenuFiguras() {
        System.out.println("""
            ╔═════════════════════════════╗
            ║    CALCULADORA GEOMÉTRICA   ║
            ╠═════════════════════════════╣
            ║ 1. Círculo                  ║
            ║ 2. Cuadrado                 ║
            ║ 3. Triángulo                ║
            ║ 4. Rectángulo               ║
            ║ 5. Pentágono                ║
            ║ 6. Salir                    ║
            ╚═════════════════════════════╝
            """);
    }

    private static void mostrarMenuOperaciones() {
        System.out.println("""
            ╔═════════════════════════════╗
            ║      SELECCIONAR OPCIÓN     ║
            ╠═════════════════════════════╣
            ║ 1. Área                     ║
            ║ 2. Perímetro                ║
            ║ 3. Potencia                 ║
            ╚═════════════════════════════╝
            """);
    }

    private static void mostrarResumen(List<String> resultados) {
        System.out.println("\n═══════ RESUMEN DE RESULTADOS ═══════");
        resultados.forEach(System.out::println);
        System.out.println("═════════════════════════════════════");
    }
}