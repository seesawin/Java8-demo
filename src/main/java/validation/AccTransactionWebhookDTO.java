package validation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author frank.hsu
 */
@Data
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class AccTransactionWebhookDTO implements Serializable {

    private String context;

    @Valid
    private AccTransactionDTO data;

    @Data
    public static class AccTransactionDTO {
        private String id;

        private String shipmentNo;

        private String branch;

        private String department;

        private String ledger;

        private String transactionType;

        @NotBlank
        private String jobInvoiceNumber;

        private String referenceNumber;

        private String currency;

        private BigDecimal exchangeRate;

        private BigDecimal excludedTaxAmount;

        private BigDecimal taxAmount;

        private BigDecimal totalAmount;

        private String localCurrency;

        private BigDecimal localExcludedTaxAmount;

        private BigDecimal localTaxAmount;

        private BigDecimal localTotalAmount;

        private BigDecimal outstandingAmount;

        @NotNull
        private LocalDateTime dueDate;

        private LocalDateTime postDate;

        private LocalDateTime fullyPaidDate;

        @NotNull
        private LocalDateTime invoiceDate;

        private Boolean isCanceled;

        private String description;

        private List<AccTransactionLineDTO> transactionLines;

        @Valid
        @NotNull
        private PartyDTO organization;
    }

    @Data
    public static class AccTransactionLineDTO {
        private String id;

        private String shipmentNo;

        @NotBlank
        private String branch;

        @NotBlank
        private String department;

        @NotBlank
        private String chargeCode;

        private String chargeDescription;

        @NotBlank
        private String glAccountCode;

        private String glAccountDescription;

        private String currency;

        private BigDecimal exchangeRate;

        private BigDecimal excludedTaxAmount;

        private BigDecimal taxRate;

        private BigDecimal taxAmount;

        private BigDecimal totalAmount;

        private String localCurrency;

        private BigDecimal localExcludedTaxAmount;

        private BigDecimal localTaxAmount;

        @NotNull
        private BigDecimal localTotalAmount;

        @NotNull
        private Integer sequence;

        @NotBlank
        private String jobNumber;
    }

    @Data
    public static class PartyDTO {
        @NotBlank
        private String companyName;
    }

}