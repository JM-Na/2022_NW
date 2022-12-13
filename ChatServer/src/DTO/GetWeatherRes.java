package DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetWeatherRes {
    private String category;
    private String fcstValue;
    private String fcstDate;
    private String fcstTime;
}
