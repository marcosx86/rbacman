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
public class ApiResourceGroup {

    private String groupVersion;
    private List<ApiResource> apiResources;

    public String toString() {

        return String.format("%s\n%s", groupVersion,
                apiResources.stream()
                        .map(itm -> String.format("> %s\n", itm))
                        .reduce("", (acc, itm) -> acc + itm));
    }
}
