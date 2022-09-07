package net.m21xx.labs.kubernetes.controller;

import lombok.extern.log4j.Log4j2;
import net.m21xx.labs.kubernetes.service.KubernetesService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<String> getAllResources() {

        List<String> resources = kubernetesService.getAllResources();

        return resources;
    }

}
