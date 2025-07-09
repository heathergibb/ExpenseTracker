public enum DateRangeOption {
    // Enum constants for date range options
    // Each constant has a number and a description
    // Numbers must be sequential starting from 1!
    ALL_DATES(1, "All Dates"),
    YTD(2, "Year to Date"),
    CUSTOM_RANGE(3, "Custom Range");

    private final int number;
    private final String description;

    DateRangeOption(int number, String description) {
        this.number = number;
        this.description = description;
    }

    public int getNumber() {
        return number;
    }

    public String getDescription() {
        return description;
    }

    public static int getMaxNumber() {
        return values().length;
    }

    public static DateRangeOption fromNumber(int number) {
        for (DateRangeOption range : values()) {
            if (range.getNumber() == number) {
                return range;
            }
        }
        throw new IllegalArgumentException("Invalid date range option: " + number);
    }
}