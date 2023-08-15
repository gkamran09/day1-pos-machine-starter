package pos.machine;

import java.util.*;
import java.util.stream.Collectors;

public class PosMachine {
    private static final List<Item> items = ItemsLoader.loadAllItems();

    public String printReceipt(List<String> barcodes) {
        Map<String, Long> groupBarcodes = groupAllBarcodes(barcodes);

        for (String barcode : loadBarcodes()) {
            groupBarcodes.getOrDefault(barcode, 0L);
            findItemByBarcode(barcode);
        }

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

}
