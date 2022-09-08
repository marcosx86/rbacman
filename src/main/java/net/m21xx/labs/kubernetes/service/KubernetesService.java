package net.m21xx.labs.kubernetes.service;

import net.m21xx.labs.kubernetes.model.ApiResourceGroup;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface KubernetesService {

    List<ApiResourceGroup> getAllResources();

}
