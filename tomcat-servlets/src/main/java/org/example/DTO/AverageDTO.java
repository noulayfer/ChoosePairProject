package org.example.DTO;

import lombok.Data;

@Data
public class AverageDTO {
    private double averageMark1;
    private double averageMark2;

    public AverageDTO(double averageMark1, double averageMark2) {
        this.averageMark1 = averageMark1;
        this.averageMark2 = averageMark2;
    }
}
