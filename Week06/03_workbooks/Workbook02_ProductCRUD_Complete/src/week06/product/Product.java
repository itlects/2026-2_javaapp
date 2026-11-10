package week06.product;

public record Product(
        String productId,
        String productName,
        String category,
        int price,
        int stock,
        String maker) {
}
