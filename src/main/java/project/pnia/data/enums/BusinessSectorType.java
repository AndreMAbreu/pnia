package project.pnia.data.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum BusinessSectorType {
    TECHNOLOGY("Technology"),
    BANKING("Banking"),
    CLOTHING("Clothing");


    private final String name;

    BusinessSectorType(String name) {
        this.name = name;
    }

    @JsonValue
    public String getName() {
        return name;
    }
}
