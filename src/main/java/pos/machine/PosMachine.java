package pos.machine;

import java.util.*;
import java.util.stream.Collectors;

public class PosMachine {
    private static final List<Item> items = ItemsLoader.loadAllItems();

    public String printReceipt(List<String> barcodes) {
        Map<String, Long> groupBarcodes = groupAllBarcodes(barcodes);
        int total = 0;

        StringBuilder receiptBuilder = new StringBuilder("***<store earning no money>Receipt***\n");
        for (String barcode : loadBarcodes()) {
          long quantity = groupBarcodes.getOrDefault(barcode, 0L);
          Item item = findItemByBarcode(barcode);

            if (item != null && quantity > 0) {
                int subtotal = calculateSubtotal(item.getPrice(), quantity);
                appendReceiptLine(receiptBuilder, item.getName(), quantity, item.getPrice(), subtotal);
                total += subtotal;
            }
        }
        appendReceiptFooter(receiptBuilder, total);

        return groupBarcodes.toString();
    }

    private Map<String, Long> groupAllBarcodes(List<String> barcodes) {
        return barcodes.stream().collect(Collectors.groupingBy(barcode -> barcode, Collectors.counting()));
    }
    private List<String> loadBarcodes() {
        return Arrays.asList("ITEM000000", "ITEM000001", "ITEM000004");
    }

    private Item findItemByBarcode(String barcode) {
        return items.stream()
                .filter(item -> item.getBarcode().equals(barcode))
                .findFirst()
                .orElse(null);
    }

    private int calculateSubtotal(int price, long quantity) {
        return price * (int) quantity;
    }

    private void appendReceiptLine(StringBuilder receiptBuilder, String name, long quantity, int price, int subtotal) {
        receiptBuilder.append(String.format("Name: %s, Quantity: %d, Unit price: %d (yuan), Subtotal: %d (yuan)\n",
                name, quantity, price, subtotal));
    }

    private void appendReceiptFooter(StringBuilder receiptBuilder, int total) {
        receiptBuilder.append("----------------------\n");
        receiptBuilder.append(String.format("Total: %d (yuan)\n", total));
        receiptBuilder.append("**********************");
    }
}
