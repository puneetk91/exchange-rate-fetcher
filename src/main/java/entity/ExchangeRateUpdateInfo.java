package entity;

import dto.CurrencyCode;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

@Table(name = "rate_update_info")
public class ExchangeRateUpdateInfo extends TrackableEntity {

    @Enumerated(EnumType.STRING)
    @Column(name = "source_currency", nullable = false)
    private CurrencyCode sourceCurrency;

    @Enumerated(EnumType.STRING)
    @Column(name = "target_currency", nullable = false)
    private CurrencyCode targetCurrency;

    @Column(name = "last_updated_at", nullable = false)
    private Timestamp lastUpdatedAt;

    @Column(name = "last_updated_till", nullable = false)
    private Timestamp lastUpdatedTill;


    //This can later be extended for tracking backfill
    public enum FillType {
        LATEST,
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "fill_type", nullable = false)
    private FillType fillType;

    public ExchangeRateUpdateInfo(CurrencyCode sourceCurrency, CurrencyCode targetCurrency, Timestamp lastUpdatedAt,
                                  Timestamp lastUpdatedTill, FillType fillType) {
        super.sanitizeTimestamps();
        this.sourceCurrency = sourceCurrency;
        this.targetCurrency = targetCurrency;
        this.lastUpdatedAt = lastUpdatedAt;
        this.lastUpdatedTill = lastUpdatedTill;
        this.fillType = fillType;
    }

    public Timestamp getLastUpdatedAt() {
        return lastUpdatedAt;
    }

    public Timestamp getLastUpdatedTill() {
        return lastUpdatedTill;
    }

    public void setLastUpdatedAt(Timestamp lastUpdatedAt) {
        this.lastUpdatedAt = lastUpdatedAt;
    }

    public void setLastUpdatedTill(Timestamp lastUpdatedTill) {
        this.lastUpdatedTill = lastUpdatedTill;
    }

    public CurrencyCode getSourceCurrency() {
        return sourceCurrency;
    }

    public void setSourceCurrency(CurrencyCode sourceCurrency) {
        this.sourceCurrency = sourceCurrency;
    }

    public CurrencyCode getTargetCurrency() {
        return targetCurrency;
    }

    public void setTargetCurrency(CurrencyCode targetCurrency) {
        this.targetCurrency = targetCurrency;
    }

    public FillType getFillType() {
        return fillType;
    }
}
