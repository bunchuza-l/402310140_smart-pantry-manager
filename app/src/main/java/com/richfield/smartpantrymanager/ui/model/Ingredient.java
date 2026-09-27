package com.richfield.smartpantrymanager.ui.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * Model class representing a pantry ingredient item with expiration status helpers.
 */
public class Ingredient {
    private int id;
    private String name;
    private int quantity;
    private String unit;
    private String expiryDate;

    /**
     * Default constructor.
     */
    public Ingredient() {}

    /**
     * Parameterized constructor for creating an Ingredient instance.
     *
     * @param id          Unique database ID
     * @param name        Ingredient name
     * @param quantity    Quantity count
     * @param unit        Unit of measurement (e.g. kg, pcs)
     * @param expiryDate Expiration date string (YYYY-MM-DD)
     */
    public Ingredient(int id, String name, int quantity, String unit, String expiryDate) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
        this.expiryDate = expiryDate;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public String getUnit() { return unit; }
    public void setUnit(String unit) { this.unit = unit; }

    public String getExpiryDate() { return expiryDate; }
    public void setExpiryDate(String expiryDate) { this.expiryDate = expiryDate; }

    /**
     * Checks if the ingredient has passed its expiration date.
     *
     * @return true if expired, false otherwise.
     */
    public boolean isExpired() {
        long days = getDaysUntilExpiry();
        return days < 0;
    }

    /**
     * Checks if the ingredient expires within 3 days.
     *
     * @return true if expiring soon (0 to 3 days remaining), false otherwise.
     */
    public boolean isNearExpiry() {
        long days = getDaysUntilExpiry();
        return days >= 0 && days <= 3;
    }

    /**
     * Calculates the number of days remaining until expiration.
     *
     * @return difference in days (negative if already expired, Long.MAX_VALUE if invalid date).
     */
    private long getDaysUntilExpiry() {
        if (expiryDate == null || expiryDate.trim().isEmpty()) {
            return Long.MAX_VALUE;
        }
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            sdf.setLenient(false);
            Date date = sdf.parse(expiryDate.trim());

            Calendar today = Calendar.getInstance();
            today.set(Calendar.HOUR_OF_DAY, 0);
            today.set(Calendar.MINUTE, 0);
            today.set(Calendar.SECOND, 0);
            today.set(Calendar.MILLISECOND, 0);

            Calendar expCal = Calendar.getInstance();
            if (date != null) {
                expCal.setTime(date);
            }
            expCal.set(Calendar.HOUR_OF_DAY, 0);
            expCal.set(Calendar.MINUTE, 0);
            expCal.set(Calendar.SECOND, 0);
            expCal.set(Calendar.MILLISECOND, 0);

            long diffMs = expCal.getTimeInMillis() - today.getTimeInMillis();
            return TimeUnit.MILLISECONDS.toDays(diffMs);
        } catch (ParseException e) {
            return Long.MAX_VALUE;
        }
    }
}
