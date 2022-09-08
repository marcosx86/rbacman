package net.m21xx.labs.kubernetes.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResource {

    private String resource;
    private List<String> verbs;

    public String toString() {

        return String.format("%s: %s", resource, verbs.stream()
                .reduce("", (acc, itm) -> "".equals(acc) ? itm : String.format("%s, %s", acc, itm)));
    }
}
