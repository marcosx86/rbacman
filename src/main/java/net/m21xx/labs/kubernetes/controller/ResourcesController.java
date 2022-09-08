package net.m21xx.labs.kubernetes.controller;

import lombok.extern.log4j.Log4j2;
import net.m21xx.labs.kubernetes.model.ApiResourceGroup;
import net.m21xx.labs.kubernetes.service.KubernetesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/resources")
@Log4j2
public class ResourcesController {

    @Autowired
    private KubernetesService kubernetesService;

    @RequestMapping(method = RequestMethod.GET, path = "/")
    public ResponseEntity<List<ApiResourceGroup>> getAllResources() {

        return ResponseEntity.ok(kubernetesService.getAllResources());
    }

}
