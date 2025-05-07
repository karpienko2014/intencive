package ru.aston.karpenko_ds.jdbc.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reader {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
}
