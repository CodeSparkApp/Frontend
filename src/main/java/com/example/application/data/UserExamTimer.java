package com.example.application.data;

import java.time.LocalDate;

//@Entity
public class UserExamTimer {
    //@jakarta.persistence.Id
  //  @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //@ManyToOne
    private User user;
    private LocalDate examDate;
    private int remainingTime; // verbleibende Zeit in Minuten

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    // Getter und Setter
}
