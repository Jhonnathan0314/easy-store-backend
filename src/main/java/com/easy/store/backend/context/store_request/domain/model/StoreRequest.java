package com.easy.store.backend.context.store_request.domain.model;

import com.easy.store.backend.context.user.domain.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StoreRequest {

    public static final String STATUS_PENDING = "pending";
    public static final String STATUS_APPROVED = "approved";
    public static final String STATUS_REJECTED = "rejected";

    private Long id;
    private User user;
    private String storeName;
    private String storeDescription;
    private String status;
    private Long reviewedBy;
    private Timestamp creationDate;
    private Timestamp updateDate;

    public boolean isValid() {
        if(user == null ||
                storeName == null ||
                storeDescription == null) return false;

        return user.getId() != null &&
                !storeName.isEmpty() &&
                !storeDescription.isEmpty();
    }

    public boolean isPending() {
        return STATUS_PENDING.equals(status);
    }

    @Override
    public String toString() {
        return "StoreRequest{" +
                "id=" + id +
                ", user=" + user +
                ", storeName='" + storeName + '\'' +
                ", storeDescription='" + storeDescription + '\'' +
                ", status='" + status + '\'' +
                ", reviewedBy=" + reviewedBy +
                ", creationDate=" + creationDate +
                ", updateDate=" + updateDate +
                '}';
    }
}
