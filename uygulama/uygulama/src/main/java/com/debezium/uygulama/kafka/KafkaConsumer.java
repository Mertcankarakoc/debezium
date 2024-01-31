package com.debezium.uygulama.kafka;

import com.debezium.uygulama.model.ProductMessageCDC;
import com.debezium.uygulama.model.ProductReader;
import com.debezium.uygulama.repository.ProductReaderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {

    Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);
    private final ProductReaderRepository productReaderRepository;

    public KafkaConsumer(ProductReaderRepository productReaderRepository) {
        this.productReaderRepository = productReaderRepository;
    }

    @KafkaListener(
            topics = "${kafka.topic}",
            groupId = "product-group"
    )

    public void debeziumListener(ProductMessageCDC productMessageCDC) {
        String operation = productMessageCDC.getOp();

        if (operation.equals("c") || operation.equals("u")) {
            if (productMessageCDC.getAfter() != null) {
                ProductReader productReader = new ProductReader();
                productReader.setId(productMessageCDC.getAfter().getId());
                productReader.setName(productMessageCDC.getAfter().getName());
                productReader.setPrice(productMessageCDC.getAfter().getPrice());
                productReader.setStock(productMessageCDC.getAfter().getStock());
                productReaderRepository.save(productReader);
            }
        } else if (operation.equals("d")) {
            if (productMessageCDC.getBefore() != null) {
                productReaderRepository.deleteById(productMessageCDC.getBefore().getId());
            }
        }

        if (operation.equals("c")) {
            int tempStock = productMessageCDC.getAfter().getStock();
            String productName = productMessageCDC.getAfter().getName();
            Double productPrice = Double.valueOf(productMessageCDC.getAfter().getPrice());
            logger.info("Debeziumdan alınan değişiklik : Yeni ürün oluşturuldu, ad: {}, fiyat: {}, stock: {}", productName, productPrice, tempStock);
        }

        if (operation.equals("u")) {
            String productName = productMessageCDC.getAfter().getName();
            Double productPrice = productMessageCDC.getAfter().getPrice();
            logger.info("Debeziumdan alınan değişiklik : Ürün güncellendi, ad: {}, fiyat: {}, stock: {} dan {}'a değişti",
                    productName, productPrice, productMessageCDC.getBefore().getStock(), productMessageCDC.getAfter().getStock());
        }

        if (operation.equals("d")) {
            String productName = productMessageCDC.getBefore().getName();
            Double productPrice = productMessageCDC.getBefore().getPrice();
            logger.info("Debeziumdan alınan değişiklik : Ürün silindi, ad: {}, fiyat: {}, son stock: {}",
                    productName, productPrice, productMessageCDC.getBefore().getStock());
        }
    }
}
