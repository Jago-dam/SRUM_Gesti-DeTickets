import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    private static final int MAX_USERS = 10;
    private static final int MAX_TICKETS_PER_USER = 10;
    private static final int MAX_PRODUCTS_PER_TICKET = 10;
    Scanner sc;
    // username, password
    String[][] credentials = new String[MAX_USERS][2];
    int credentialsInsertionIndex = 0;

    // shop, date
    String[][][] tickets = new String[MAX_USERS][MAX_TICKETS_PER_USER][2];
    int[] ticketInsertionIndex = new int[MAX_USERS];

    // name, quantity, price
    String[][][][] ticketProducts = new String[MAX_USERS][MAX_TICKETS_PER_USER][MAX_PRODUCTS_PER_TICKET][3];
    int[][] productInsertionIndex = new int[MAX_USERS][MAX_TICKETS_PER_USER];
    int userIndex = -1;
    boolean run = true;

    Main() {
        this.sc = new Scanner(System.in);
    }

    public static void main(String[] args) {
        Main main = new Main();

        try {
            while (main.run) {
                main.mainMenu();
            }
        } catch (InputMismatchException e) {
            System.out.println("S'ha trobat un error al introduir els dades");
        }

        main.sc.close();
    }

    public void registerTicket(String shop, String currentDate) {
        TicketUtils.addTicket(this.tickets, this.userIndex, this.ticketInsertionIndex[userIndex], shop, currentDate);
        this.ticketInsertionIndex[userIndex]++;
    }

    public void registerProduct(String productName, int productCount, double productPrice) {
        // the most recent ticket
        int ticketIndex = this.ticketInsertionIndex[userIndex] - 1;
        int productIndex = this.productInsertionIndex[userIndex][ticketIndex];

        if (productIndex >= MAX_PRODUCTS_PER_TICKET) {
            System.out.println("Error: S'ha arribat al nombre màxim de productes per a aquest tiquet.");
            return;
        }

        TicketUtils.addProduct(this.ticketProducts, this.userIndex, ticketIndex, productIndex, productName, productCount, productPrice);
        this.productInsertionIndex[userIndex][ticketIndex]++;
    }

    public void menuAddTicket() {
        if (this.ticketInsertionIndex[userIndex] >= MAX_TICKETS_PER_USER) {
            System.out.println("Error: nombre màxim de tiquets assolit per a aquest usuari.");
            return;
        }

        System.out.print("On has comprat: ");
        String shop = this.sc.next();

        // TODO: Add option to use a custom date
        String currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        registerTicket(shop, currentDate);

        String productName = "";
        while (true) {
            System.out.print("Nom del producte (0 per sortir): ");
            productName = this.sc.next();

            if (productName.equals("0")) {
                break;
            }

            System.out.print("Quantitat: ");
            int productCount = this.sc.nextInt();

            System.out.print("Preu: ");
            double productPrice = this.sc.nextDouble();

            registerProduct(productName, productCount, productPrice);
        }

        int ticketIndex = this.ticketInsertionIndex[userIndex] - 1;
        TicketPrinter.print(this.tickets, this.ticketProducts, this.userIndex, ticketIndex);
    }

    public void menuShowTickets() {
        // TODO: Add options to filter by date, shop
        if (this.tickets.length != 0) {
            for (int i = 0; i < this.ticketInsertionIndex[userIndex]; i++) {
                TicketPrinter.print(this.tickets, this.ticketProducts, this.userIndex, i);
            }
        } else {
            System.out.println("Encara no has afegit cap tiquet");
        }
    }

    public void menuLogin() {
        String username;
        String password;

        System.out.print("Usuari: ");
        username = this.sc.next();

        System.out.print("Contrasenya: ");
        password = this.sc.next();

        if (CredentialsUtils.correctCredentials(this.credentials, username, password)) {
            System.out.println("Ven vingut " + username);
            this.userIndex = CredentialsUtils.getUserIndex(this.credentials, username);
        } else {
            System.out.println("Usuari o contrasenya incorrecte");
        }
    }

    public void registerUser(String username, String password) {
        CredentialsUtils.addCredential(this.credentials, this.credentialsInsertionIndex, username, password);
        this.credentialsInsertionIndex++;
        this.userIndex = this.credentials.length - 1;
    }

    public void menuRegister() {
        if (this.credentialsInsertionIndex > MAX_USERS) {
            System.out.println("Error: S'ha arribat al nombre màxim d'usuaris. No es permet registrar-ne més.");
            return;
        }

        String username;
        String password;

        while (true) {
            System.out.print("Usuari: ");
            username = this.sc.next();

            System.out.print("Contrasenya: ");
            password = this.sc.next();
            System.out.print("Repetiu la contrasenya: ");
            String password2 = this.sc.next();

            if (CredentialsUtils.userExists(this.credentials, username)) {
                System.out.println("Aquest usuari ja existeix prova amb un altre nom");
            } else if (!password.equals(password2)) {
                System.out.println("La constrasenya no coincideix tornar a intentar");
            } else if (password.length() < 6) {
                System.out.println("La contrasenya ha de tenir minim 6 caracters");
            } else {
                System.out.println("Registrat correctament");
                break;
            }
        }

        registerUser(username, password);
    }

    public void initialMenu() {
        System.out.println("GESTION DE TICKETS DE COMPRA");
        System.out.println("----------------------------");
        System.out.println("|    1.Log in              |");
        System.out.println("----------------------------");
        System.out.println("|    2.Sing up             |");
        System.out.println("----------------------------");
        System.out.println("|    0.exit                |");
        System.out.println("----------------------------");

        int option = this.sc.nextInt();
        switch (option) {
            case 0:
                System.out.println("a deu fins aviat");
                this.run = false;
                break;

            case 1:
                menuLogin();
                break;

            case 2:
                menuRegister();
                break;

            default:
                System.out.println("Aquesta opcio no es valida");
                break;
        }
    }

    public void leggedMenu() {
        System.out.println("GESTIO TICKETS DE LA COMPRA");
        System.out.println("---------------------------");
        System.out.println("|    1.Afegix ticket      |");
        System.out.println("---------------------------");
        System.out.println("|   2.Mostra els tickets  |");
        System.out.println("---------------------------");
        System.out.println("|    0.Tencar sessió      |");
        System.out.println("---------------------------");

        int option = this.sc.nextInt();
        switch (option) {
            case 0:
                this.userIndex = -1;
                break;

            case 1:
                menuAddTicket();
                break;

            case 2:
                menuShowTickets();
                break;

            default:
                System.out.println("Aquesta opcio no es valida");
                break;
        }
    }

    public void mainMenu() {
        System.out.println();
        if (this.userIndex == -1) {
            initialMenu();
        } else {
            leggedMenu();
        }
    }
}
