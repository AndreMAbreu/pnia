package project.pnia.data.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.pnia.data.enums.BusinessSectorType;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@Builder
public class PhoneNumberModel {
    private String prefix;
    private String number;
    private BusinessSectorType sector;

    public PhoneNumberModel(String prefix, String number, BusinessSectorType sector) {
        this.number = number;
        this.prefix = prefix;
        this.sector = sector;
    }
}
