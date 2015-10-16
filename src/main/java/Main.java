public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("Starting server...");
        new HttpServer(8888).run();
    }
}