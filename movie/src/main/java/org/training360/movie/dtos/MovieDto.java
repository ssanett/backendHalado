package org.training360.movie.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MovieDto {

    private String title;
    private int length;
    private List<Integer> ratings = new ArrayList<>();
    private double averageRating;
}
