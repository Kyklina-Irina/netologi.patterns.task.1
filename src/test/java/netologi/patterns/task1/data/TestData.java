package netologi.patterns.task1.data;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class TestData {
    private final String city;
    private final String date;
    private final String name;
    private final String phone;
}