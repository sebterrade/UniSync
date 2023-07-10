/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package JFramePackage;

import java.time.LocalDate;

/**
 *
 * @author sebte
 */
public class Pair {
    private LocalDate date;
    private long value;

    public Pair(LocalDate date, long value) {
        this.date = date;
        this.value = value;
    }

    public LocalDate getDate() {
        return date;
    }

    public long getValue() {
        return value;
    }
}
