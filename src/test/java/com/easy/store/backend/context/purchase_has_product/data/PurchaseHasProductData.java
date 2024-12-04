package com.easy.store.backend.context.purchase_has_product.data;

import com.easy.store.backend.context.category.application.dto.CategoryDTO;
import com.easy.store.backend.context.category.application.dto.CategoryResponseDTO;
import com.easy.store.backend.context.category.domain.model.Category;
import com.easy.store.backend.context.category.infrastructure.persistence.CategoryEntity;
import com.easy.store.backend.context.payment_type.application.dto.PaymentTypeDTO;
import com.easy.store.backend.context.payment_type.application.dto.PaymentTypeResponseDTO;
import com.easy.store.backend.context.payment_type.domain.model.PaymentType;
import com.easy.store.backend.context.payment_type.infrastructure.persistence.PaymentTypeEntity;
import com.easy.store.backend.context.product.application.dto.ProductDTO;
import com.easy.store.backend.context.product.application.dto.ProductResponseDTO;
import com.easy.store.backend.context.product.domain.model.Product;
import com.easy.store.backend.context.product.infrastructure.persistence.ProductEntity;
import com.easy.store.backend.context.purchase.application.dto.PurchaseDTO;
import com.easy.store.backend.context.purchase.application.dto.PurchaseResponseDTO;
import com.easy.store.backend.context.purchase.domain.model.Purchase;
import com.easy.store.backend.context.purchase.infrastructure.persistence.PurchaseEntity;
import com.easy.store.backend.context.purchase_has_product.application.dto.PurchaseHasProductAddDTO;
import com.easy.store.backend.context.purchase_has_product.application.dto.PurchaseHasProductDTO;
import com.easy.store.backend.context.purchase_has_product.application.dto.PurchaseHasProductResponseDTO;
import com.easy.store.backend.context.purchase_has_product.domain.model.PurchaseHasProduct;
import com.easy.store.backend.context.purchase_has_product.infrastructure.persistence.PurchaseHasProductEntity;
import com.easy.store.backend.context.roles.application.dto.RoleDTO;
import com.easy.store.backend.context.roles.application.dto.RoleResponseDTO;
import com.easy.store.backend.context.roles.domain.model.Role;
import com.easy.store.backend.context.roles.infrastructure.persistence.RoleEntity;
import com.easy.store.backend.context.subcategory.application.dto.SubcategoryDTO;
import com.easy.store.backend.context.subcategory.application.dto.SubcategoryResponseDTO;
import com.easy.store.backend.context.subcategory.domain.model.Subcategory;
import com.easy.store.backend.context.subcategory.infrastructure.persistence.SubcategoryEntity;
import com.easy.store.backend.context.user.application.dto.UserDTO;
import com.easy.store.backend.context.user.application.dto.UserResponseDTO;
import com.easy.store.backend.context.user.domain.model.User;
import com.easy.store.backend.context.user.infrastructure.persistence.UserEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@Data
public class PurchaseHasProductData {

    //Correct information
    private final PurchaseHasProduct purchaseHasProductResponseOne = PurchaseHasProduct.builder()
            .id(1L)
            .quantity(5)
            .purchase(Purchase.builder()
                    .id(1L)
                    .user(User.builder()
                            .id(1L)
                            .username("test@test.com")
                            .name("test")
                            .lastName("test")
                            .password("12345")
                            .role(Role.builder()
                                    .id(1L)
                                    .name("test")
                                    .build())
                            .build())
                    .paymentType(PaymentType.builder()
                            .id(1L)
                            .name("test")
                            .build())
                    .date(Timestamp.valueOf(LocalDateTime.now()))
                    .total(new BigDecimal(3000))
                    .build())
            .product(Product.builder()
                    .id(1L)
                    .name("test")
                    .description("test")
                    .price(new BigDecimal(1000))
                    .quantity(5)
                    .qualification(5)
                    .subcategory(Subcategory.builder()
                            .id(1L)
                            .name("test")
                            .category(Category.builder()
                                    .id(1L)
                                    .name("test")
                                    .build())
                            .build())
                    .build())
            .build();

    private final PurchaseHasProduct purchaseHasProductResponseTwo = PurchaseHasProduct.builder()
            .id(2L)
            .quantity(10)
            .purchase(Purchase.builder()
                    .id(1L)
                    .user(User.builder()
                            .id(1L)
                            .username("test@test.com")
                            .name("test")
                            .lastName("test")
                            .password("12345")
                            .role(Role.builder()
                                    .id(1L)
                                    .name("test")
                                    .build())
                            .build())
                    .paymentType(PaymentType.builder()
                            .id(1L)
                            .name("test")
                            .build())
                    .date(Timestamp.valueOf(LocalDateTime.now()))
                    .total(new BigDecimal(3000))
                    .build())
            .product(Product.builder()
                    .id(1L)
                    .name("test")
                    .description("test")
                    .price(new BigDecimal(1000))
                    .quantity(5)
                    .qualification(5)
                    .subcategory(Subcategory.builder()
                            .id(1L)
                            .name("test")
                            .category(Category.builder()
                                    .id(1L)
                                    .name("test")
                                    .build())
                            .build())
                    .build())
            .build();

    private final PurchaseHasProduct purchaseHasProductEmpty = PurchaseHasProduct.builder()
            .id(1L)
            .build();

    private final PurchaseHasProduct purchaseHasProductAddValid = PurchaseHasProduct.builder()
            .id(1L)
            .quantity(5)
            .purchase(Purchase.builder()
                    .id(1L)
                    .user(User.builder()
                            .id(1L)
                            .username("test@test.com")
                            .name("test")
                            .lastName("test")
                            .password("12345")
                            .role(Role.builder()
                                    .id(1L)
                                    .name("test")
                                    .build())
                            .build())
                    .paymentType(PaymentType.builder()
                            .id(1L)
                            .name("test")
                            .build())
                    .date(Timestamp.valueOf(LocalDateTime.now()))
                    .total(new BigDecimal(3000))
                    .build())
            .product(Product.builder()
                    .id(1L)
                    .name("test")
                    .description("test")
                    .price(new BigDecimal(1000))
                    .quantity(5)
                    .qualification(5)
                    .subcategory(Subcategory.builder()
                            .id(1L)
                            .name("test")
                            .category(Category.builder()
                                    .id(1L)
                                    .name("test")
                                    .build())
                            .build())
                    .build())
            .build();

    private final PurchaseHasProduct purchaseHasProductAddInvalid = PurchaseHasProduct.builder().build();

    private List<PurchaseHasProduct> purchaseHasProductsList;
    private List<PurchaseHasProduct> purchaseHasProductsListInvalid;
    private List<PurchaseHasProduct> purchaseHasProductsEmptyList;

    //To mapper test
    private final PurchaseHasProductEntity purchaseHasProductEntity = PurchaseHasProductEntity.builder()
            .id(1L)
            .quantity(5)
            .purchase(PurchaseEntity.builder()
                    .id(1L)
                    .user(UserEntity.builder()
                            .id(1L)
                            .username("test@test.com")
                            .name("test")
                            .lastName("test")
                            .password("12345")
                            .role(RoleEntity.builder()
                                    .id(1L)
                                    .name("test")
                                    .build())
                            .build())
                    .paymentType(PaymentTypeEntity.builder()
                            .id(1L)
                            .name("test")
                            .build())
                    .date(Timestamp.valueOf(LocalDateTime.now()))
                    .total(new BigDecimal(3000))
                    .build())
            .product(ProductEntity.builder()
                    .id(1L)
                    .name("test")
                    .description("test")
                    .price(new BigDecimal(1000))
                    .quantity(5)
                    .qualification(5)
                    .subcategory(SubcategoryEntity.builder()
                            .id(1L)
                            .name("test")
                            .category(CategoryEntity.builder()
                                    .id(1L)
                                    .name("test")
                                    .build())
                            .build())
                    .build())
            .build();

    private final PurchaseHasProductDTO purchaseHasProductDTO = PurchaseHasProductDTO.builder()
            .id(1L)
            .quantity(5)
            .purchase(PurchaseDTO.builder()
                    .id(1L)
                    .user(UserDTO.builder()
                            .id(1L)
                            .username("test@test.com")
                            .name("test")
                            .lastName("test")
                            .password("12345")
                            .role(RoleDTO.builder()
                                    .id(1L)
                                    .name("test")
                                    .build())
                            .build())
                    .paymentType(PaymentTypeDTO.builder()
                            .id(1L)
                            .name("test")
                            .build())
                    .date(Timestamp.valueOf(LocalDateTime.now()))
                    .total(new BigDecimal(3000))
                    .build())
            .product(ProductDTO.builder()
                    .id(1L)
                    .name("test")
                    .description("test")
                    .price(new BigDecimal(1000))
                    .quantity(5)
                    .qualification(5)
                    .subcategory(SubcategoryDTO.builder()
                            .id(1L)
                            .name("test")
                            .category(CategoryDTO.builder()
                                    .id(1L)
                                    .name("test")
                                    .build())
                            .build())
                    .build())
            .build();

    private final PurchaseHasProductAddDTO purchaseHasProductAddDTO = PurchaseHasProductAddDTO.builder()
            .quantity(5)
            .build();

    private final PurchaseHasProductResponseDTO purchaseHasProductResponseDTO = PurchaseHasProductResponseDTO.builder()
            .id(1L)
            .quantity(5)
            .purchase(PurchaseResponseDTO.builder()
                    .id(1L)
                    .user(UserResponseDTO.builder()
                            .id(1L)
                            .username("test@test.com")
                            .name("test")
                            .lastName("test")
                            .role(RoleResponseDTO.builder()
                                    .id(1L)
                                    .name("test")
                                    .build())
                            .build())
                    .paymentType(PaymentTypeResponseDTO.builder()
                            .id(1L)
                            .name("test")
                            .build())
                    .date(Timestamp.valueOf(LocalDateTime.now()))
                    .total(new BigDecimal(3000))
                    .build())
            .product(ProductResponseDTO.builder()
                    .id(1L)
                    .name("test")
                    .description("test")
                    .price(new BigDecimal(1000))
                    .quantity(5)
                    .qualification(5)
                    .subcategory(SubcategoryResponseDTO.builder()
                            .id(1L)
                            .name("test")
                            .category(CategoryResponseDTO.builder()
                                    .id(1L)
                                    .name("test")
                                    .build())
                            .build())
                    .build())
            .build();

    private final PurchaseHasProduct purchaseHasProductModel = PurchaseHasProduct.builder()
            .id(1L)
            .quantity(5)
            .purchase(Purchase.builder()
                    .id(1L)
                    .user(User.builder()
                            .id(1L)
                            .username("test@test.com")
                            .name("test")
                            .lastName("test")
                            .password("12345")
                            .role(Role.builder()
                                    .id(1L)
                                    .name("test")
                                    .build())
                            .build())
                    .paymentType(PaymentType.builder()
                            .id(1L)
                            .name("test")
                            .build())
                    .date(Timestamp.valueOf(LocalDateTime.now()))
                    .total(new BigDecimal(3000))
                    .build())
            .product(Product.builder()
                    .id(1L)
                    .name("test")
                    .description("test")
                    .price(new BigDecimal(1000))
                    .quantity(5)
                    .qualification(5)
                    .subcategory(Subcategory.builder()
                            .id(1L)
                            .name("test")
                            .category(Category.builder()
                                    .id(1L)
                                    .name("test")
                                    .build())
                            .build())
                    .build())
            .build();

    private List<Long> productsId = List.of(1L, 1L);

    public PurchaseHasProductData() {
        purchaseHasProductsEmptyList = new LinkedList<>();

        purchaseHasProductsList = new LinkedList<>();
        purchaseHasProductsList.add(purchaseHasProductResponseOne);
        purchaseHasProductsList.add(purchaseHasProductResponseTwo);

        purchaseHasProductsListInvalid = new LinkedList<>();
        purchaseHasProductsListInvalid.add(purchaseHasProductResponseOne);
        purchaseHasProductsListInvalid.add(purchaseHasProductEmpty);
    }
}
