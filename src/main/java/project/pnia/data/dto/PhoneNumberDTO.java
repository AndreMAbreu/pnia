package project.pnia.data.dto;

import lombok.Data;
import project.pnia.data.enums.BusinessSectorType;



import java.io.Serializable;

@Data
public class PhoneNumberDTO implements Serializable {
    private String number;

    private BusinessSectorType sector;
}
