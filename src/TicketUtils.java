public class TicketUtils {
    /**
     * Adds a ticket for a specific user at the specified index in the tickets array.
     *
     * @param tickets     The 3D array containing user tickets.
     * @param userIndex   The index of the user.
     * @param ticketIndex The index where the ticket should be added.
     * @param shopName    The name of the shop.
     * @param date        The date of the ticket.
     * @throws IllegalArgumentException If the user index or ticket index is out of bounds.
     */
    public static void addTicket(String[][][] tickets, int userIndex, int ticketIndex, String shopName, String date) throws IllegalArgumentException {
        if (userIndex >= 0 && userIndex < tickets.length) {
            if (ticketIndex >= 0 && ticketIndex < tickets[userIndex].length) {
                tickets[userIndex][ticketIndex] = new String[]{shopName, date};
            } else {
                throw new IllegalArgumentException("Invalid ticket index: " + ticketIndex);
            }
        } else {
            throw new IllegalArgumentException("Invalid user index: " + userIndex);
        }
    }

    /**
     * Adds a product to a specific ticket for a user at the specified index in the ticketProducts array.
     *
     * @param ticketProducts The 4D array containing user ticket products.
     * @param userIndex      The index of the user.
     * @param ticketIndex    The index of the ticket.
     * @param productIndex   The index where the product should be added.
     * @param name           The name of the product.
     * @param count          The quantity of the product.
     * @param price          The price of the product.
     * @throws IllegalArgumentException If the user index, ticket index, or product index is out of bounds.
     */
    public static void addProduct(String[][][][] ticketProducts, int userIndex, int ticketIndex, int productIndex, String name, int count, double price) throws IllegalArgumentException {
        if (userIndex >= 0 && userIndex < ticketProducts.length) {
            if (ticketIndex >= 0 && ticketIndex < ticketProducts[userIndex].length) {
                if (productIndex >= 0 && productIndex < ticketProducts[userIndex][ticketIndex].length) {
                    ticketProducts[userIndex][ticketIndex][productIndex] = new String[]{name, String.valueOf(count), String.valueOf(price)};
                } else {
                    throw new IllegalArgumentException("Invalid product index: " + productIndex);
                }
            } else {
                throw new IllegalArgumentException("Invalid ticket index: " + ticketIndex);
            }
        } else {
            throw new IllegalArgumentException("Invalid user index: " + userIndex);
        }
    }

}
