package net.m21xx.labs.kubernetes.service.impl;

import io.fabric8.kubernetes.api.model.APIResourceList;
import io.fabric8.kubernetes.api.model.GroupVersionForDiscovery;
import io.fabric8.kubernetes.client.*;
import lombok.extern.log4j.Log4j2;
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
    public List<String> getAllResources() {

        List<String> groups = client.getApiGroups().getGroups().stream()
                .map(r -> r.getVersions().stream()
                        .map(GroupVersionForDiscovery::getGroupVersion)
                        .collect(Collectors.toList()))
                .reduce(new ArrayList<>(), (lst, itm) -> {
                    lst.addAll(itm);
                    return lst;
                });
        groups.add(0, "v1");
        groups.forEach(System.out::println);

        List<String> lst = new ArrayList<>();
        for (String group : groups) {
            lst.add(String.format("\n#### ALL RESOURCES FOR %s ####", group));
            lst.addAll(getApiResourcesFor(group));
        }

        return lst;
    }

    private List<String> getApiResourcesFor(String groupVersion) {
        List<String> lst;
        APIResourceList apiResourceList = client.getApiResources(groupVersion);
        lst = apiResourceList.getResources().stream()
                .map(res -> {
                    String verbs = res.getVerbs().stream()
                            .reduce("", (a, b) -> "".equals(a) ? b : String.format("%s, %s", a, b));
                    String categories = Optional.ofNullable(res.getCategories())
                            .orElse(List.of(""))
                            .stream()
                            .reduce("", (a, b) -> "".equals(a) ? b : String.format("%s, %s", a, b));
                    return String.format("(core) %s -> %s: %s (%s)", res.getVersion(), res.getName(), verbs, categories);
                }).collect(Collectors.toList());
        return lst;
    }

}
