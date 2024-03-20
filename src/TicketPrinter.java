public class TicketPrinter {
    private static String getProductLine(String name, int count, double price,
                                         int nameWidth, int countWidth, int priceWidth) {
        String format = " %" + nameWidth + "s  %" + countWidth + "d  %" + priceWidth + ".2f€ ";
        return String.format(format, name, count, price);
    }

    private static String getTitle(String title, int totalWidth) {
        StringBuilder lines = new StringBuilder();
        int innerSize = title.length() + 2;
        int leftPadding = (totalWidth - innerSize) / 2;
        int rightPadding = totalWidth - innerSize - leftPadding;

        if (innerSize % 2 == 1 && totalWidth % 2 == 0) {
            leftPadding++;
            rightPadding--;
        }

        lines.append(" ".repeat(leftPadding - 1) + "┏" + "━".repeat(innerSize) + "┓" + " ".repeat(rightPadding - 1)).append("\n");
        lines.append("┏" + "━".repeat(leftPadding - 2) + "┫ " + title + " ┣" + "━".repeat(rightPadding - 2) + "┓").append("\n");
        lines.append("┃" + " ".repeat(leftPadding - 2) + "┗" + "━".repeat(innerSize) + "┛" + " ".repeat(rightPadding - 2) + "┃").append("\n");

        return lines.toString();
    }

    public static void print(String[][][] tickets, String[][][][] ticketProducts, int userIndex, int ticketIndex) {
        String[][] products = ticketProducts[userIndex][ticketIndex];
        String shopName = tickets[userIndex][ticketIndex][0];
        String date = tickets[userIndex][ticketIndex][1];
        double total = 0.0;
        int nProducts = 0;

        int nameWidth = Integer.MIN_VALUE, countWidth = Integer.MIN_VALUE, priceWidth = Integer.MIN_VALUE;
        for (String[] product : products) {
            if (product[0] == null) {
                break;
            }
            String name = product[0];
            int count = Integer.parseInt(product[1]);
            double price = Double.parseDouble(product[2]);

            nameWidth = Math.max(nameWidth, name.length());
            countWidth = Math.max(countWidth, String.valueOf(count).length());
            priceWidth = Math.max(priceWidth, String.format("%.2f", price).length());

            total += price * count;
            nProducts++;
        }

        String[] productLines = new String[nProducts];
        StringBuilder lines = new StringBuilder();

        for (int i = 0; i < nProducts; i++) {
            if (products[i][0] != null) {
                String name = products[i][0];
                int count = Integer.parseInt(products[i][1]);
                double price = Double.parseDouble(products[i][2]);

                productLines[i] = getProductLine(name, count, price * count, nameWidth, countWidth, priceWidth);
            }
        }

        int width = Math.max(productLines[0].length() + 4, shopName.length() + 8);

        for (String productLine : productLines) {
            lines.append("┃ ").append(" ".repeat(width - 4 - productLines[0].length()))
                    .append(productLine).append(" ┃\n");
        }

        lines.append("┠" + "─".repeat(width - 2) + "┨\n");
        String totalLine = String.format("Total: %.2f€", total);
        lines.append(String.format("┃ %" + (width - totalLine.length() - 4) + "s%s ┃\n", " ", totalLine));
        lines.append("┗" + "━".repeat(width - 2) + "┛");

        lines.insert(0, "┣" + "━".repeat(width - 2) + "┫\n");
        lines.insert(0, String.format("┃ %" + (width - date.length() - 4) + "s%s ┃\n", " ", date));

        lines.insert(0, getTitle(shopName, width));
        System.out.println(lines);
    }
}
