package io.shubansoft.mathutil.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@JacksonXmlRootElement
public class Primes {

    @JsonProperty(value = "Initial")
    private Long initial;

    @JsonProperty(value = "Primes")
    private Set<Long> primes;

}
