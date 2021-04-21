package pl.pozadr.genderdetector.dto.request;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class TokensRequestDto {

    @NotNull(message = "Given method can not be null.")
    @Min(value = 1, message = "Given page number is to low.")
    @Max(value = Integer.MAX_VALUE, message = "Given page number is to high.")
    private Integer pageNo;

    @NotNull(message = "Given method can not be null.")
    @Min(value = 1, message = "Given page size is to low.")
    @Max(value = 500, message = "Given page size is to high.")
    private Integer pageSize;

    public int getPageNo() {
        return pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }
}
