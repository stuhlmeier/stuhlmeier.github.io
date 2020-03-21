import java.time.LocalDate
import java.time.Month
import java.time.ZoneOffset

fun about(): Map<String, Any?> {
    return mapOf(
        "title" to "about",
        "age" to LocalDate.of(2002, Month.JULY, 4)
            .until(LocalDate.now(ZoneOffset.ofHours(-4)))
            .years
    )
}
