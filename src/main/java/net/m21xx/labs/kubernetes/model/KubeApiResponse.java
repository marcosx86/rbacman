package net.m21xx.labs.kubernetes.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KubeApiResponse {

//    private String kind;
//    private String apiVersion;
//    private Object metadata;
//    private String status;
    private String message;
}
