package org.example.DTO;

import lombok.Data;

@Data
public class MarkDTO {
    private double mark1;
    private double mark2;

    public MarkDTO(double mark1, double mark2) {
        this.mark1 = mark1;
        this.mark2 = mark2;
    }
}
