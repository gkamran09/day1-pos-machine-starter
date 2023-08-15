package pos.machine;

import java.util.*;
import java.util.stream.Collectors;

public class PosMachine {
    private static final List<Item> items = ItemsLoader.loadAllItems();

    public String printReceipt(List<String> barcodes) {
        Map<String, Long> groupBarcodes = groupAllBarcodes(barcodes);

        return groupBarcodes.toString();
    }

    private Map<String, Long> groupAllBarcodes(List<String> barcodes) {
        return barcodes.stream().collect(Collectors.groupingBy(barcode -> barcode, Collectors.counting()));
    }

}
