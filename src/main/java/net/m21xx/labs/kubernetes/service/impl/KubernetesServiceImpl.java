package net.m21xx.labs.kubernetes.service.impl;

import io.fabric8.kubernetes.api.model.APIResourceList;
import io.fabric8.kubernetes.api.model.GroupVersionForDiscovery;
import io.fabric8.kubernetes.client.*;
import lombok.extern.log4j.Log4j2;
import net.m21xx.labs.kubernetes.model.ApiResource;
import net.m21xx.labs.kubernetes.model.ApiResourceGroup;
import net.m21xx.labs.kubernetes.service.KubernetesService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
public class KubernetesServiceImpl implements KubernetesService {

    @Value("${kubernetes.api.url:https://kubernetes.default.svc}")
    private String kubernetesUrl;

    @Value("${kubernetes.serviceAccount.token}")
    private String kubernetesToken;

    @Value("${kubernetes.api.validateSSL:false}")
    private  boolean kubernetesValidateSSL;

    private KubernetesClient client;

    @PostConstruct
    private void setup() {

        Config config = new ConfigBuilder()
                .withMasterUrl(kubernetesUrl)
                .withTrustCerts(! kubernetesValidateSSL)
                .withOauthToken(kubernetesToken)
                .build();
        client = new KubernetesClientBuilder()
                .withConfig(config)
                .build();
    }

    @Override
    public List<ApiResourceGroup> getAllResources() {

        List<ApiResourceGroup> groups = new ArrayList<>();

        List<String> groupNames = client.getApiGroups().getGroups().stream()
                .map(r -> r.getVersions().stream()
                        .map(GroupVersionForDiscovery::getGroupVersion)
                        .collect(Collectors.toList()))
                .reduce(new ArrayList<>(), (lst, itm) -> {
                    lst.addAll(itm);
                    return lst;
                });
        groupNames.add(0, "v1");
//        groupNames.forEach(System.out::println);

        List<ApiResourceGroup> lst = new ArrayList<>();
        for (String group : groupNames) {
//            lst.add(String.format("\n#### ALL RESOURCES FOR %s ####", group));
            ApiResourceGroup theGroup = ApiResourceGroup.builder()
                    .groupVersion(group)
                    .apiResources(getApiResourcesFor(group))
                    .build();
            lst.add(theGroup);
        }

        return lst;
    }

    private List<ApiResource> getApiResourcesFor(String groupVersion) {

        APIResourceList apiResourceList = client.getApiResources(groupVersion);
        List<ApiResource> lst = new ArrayList<>();
        apiResourceList.getResources().stream()
                .map(res -> ApiResource.builder()
                            .resource(res.getName())
                            .verbs(res.getVerbs())
                            .build())
                .forEach(lst::add);
        return lst;
    }

}
