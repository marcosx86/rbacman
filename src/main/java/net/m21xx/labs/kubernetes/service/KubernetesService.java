package net.m21xx.labs.kubernetes.service;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface KubernetesService {

    List<String> getAllResources();

}
