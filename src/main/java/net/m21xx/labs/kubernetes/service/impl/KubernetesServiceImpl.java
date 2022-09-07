package net.m21xx.labs.kubernetes.service.impl;

import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.Configuration;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.V1NamespaceList;
import io.kubernetes.client.util.Config;
import net.m21xx.labs.kubernetes.model.KubeApiResponse;
import net.m21xx.labs.kubernetes.service.KubernetesService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

import static net.m21xx.labs.kubernetes.util.Util.readValueFrom;

@Service
public class KubernetesServiceImpl implements KubernetesService {

    @Value("${kubernetes.api.url:https://kubernetes.default.svc}")
    private String kubernetesUrl;

    @Value("${kubernetes.serviceAccount.token}")
    private String kubernetesToken;

    @Value("${kubernetes.api.validateSSL:false}")
    private  boolean kubernetesValidateSSL;

    private ApiClient client;

    @PostConstruct
    private void setup() {

        client = Config.fromToken(kubernetesUrl, kubernetesToken, kubernetesValidateSSL);
        Configuration.setDefaultApiClient(client);
    }

    @Override
    public List<String> getAllResources() {

        try {
            List<String> lst = new ArrayList<>();

            CoreV1Api coreV1 = new CoreV1Api();
            V1NamespaceList lst1 = coreV1.listNamespace(null, null, null, null,
                    null, null, null, null,
                    null, null);

//            List<String> lst = lst1.getItems().stream()
//                    .map(res -> res.getMetadata().getName())
//                    .collect(Collectors.toList());

//            CoreV1Api coreV1 = new CoreV1Api();
//            V1APIResourceList coreResources = coreV1.getAPIResources();
//            lst.add(String.format("\ngroupVersion: %s\n", coreResources.getGroupVersion()));
//            coreResources.getResources().stream()
//                    .map(res -> {
//                        String verbs = res.getVerbs().stream()
//                                .reduce("", (a, b) -> "".equals(a) ? b : String.format("%s, %s", a, b));
//                        String categories = Optional.ofNullable(res.getCategories())
//                                .orElse(List.of(""))
//                                .stream()
//                                .reduce("", (a, b) -> "".equals(a) ? b : String.format("%s, %s", a, b));
//                        return String.format("(core) %s -> %s: %s (%s)", res.getVersion(), res.getName(), verbs, categories);
//                    })
//                    .forEach(lst::add);
//
//            RbacAuthorizationV1Api rbacV1 = new RbacAuthorizationV1Api();
//            V1APIResourceList rbacResources = rbacV1.getAPIResources();
//            lst.add(String.format("\ngroupVersion: %s\n", rbacResources.getGroupVersion()));
//            rbacResources.getResources().stream()
//                    .map(res -> {
//                        String verbs = res.getVerbs().stream()
//                                .reduce("", (a, b) -> "".equals(a) ? b : String.format("%s, %s", a, b));
//                        String categories = Optional.ofNullable(res.getCategories())
//                                .orElse(List.of(""))
//                                .stream()
//                                .reduce("", (a, b) -> "".equals(a) ? b : String.format("%s, %s", a, b));
//                        return String.format("(core) %s -> %s: %s (%s)", res.getVersion(), res.getName(), verbs, categories);
//                    })
//                    .forEach(lst::add);
//
//            NetworkingV1Api netV1 = new NetworkingV1Api();
//            V1APIResourceList netResources = netV1.getAPIResources();
//            lst.add(String.format("\ngroupVersion: %s\n", netResources.getGroupVersion()));
//            netResources.getResources().stream()
//                    .map(res -> {
//                        String verbs = res.getVerbs().stream()
//                                .reduce("", (a, b) -> "".equals(a) ? b : String.format("%s, %s", a, b));
//                        String categories = Optional.ofNullable(res.getCategories())
//                                .orElse(List.of(""))
//                                .stream()
//                                .reduce("", (a, b) -> "".equals(a) ? b : String.format("%s, %s", a, b));
//                        return String.format("(core) %s -> %s: %s (%s)", res.getVersion(), res.getName(), verbs, categories);
//                    })
//                    .forEach(lst::add);
//
//            ApiregistrationV1Api apiregV1 = new ApiregistrationV1Api();
//            V1APIResourceList apiregResrouces = apiregV1.getAPIResources();
//            lst.add(String.format("\ngroupVersion: %s\n", apiregResrouces.getGroupVersion()));
//            apiregResrouces.getResources().stream()
//                    .map(res -> {
//                        String verbs = res.getVerbs().stream()
//                                .reduce("", (a, b) -> "".equals(a) ? b : String.format("%s, %s", a, b));
//                        String categories = Optional.ofNullable(res.getCategories())
//                                .orElse(List.of(""))
//                                .stream()
//                                .reduce("", (a, b) -> "".equals(a) ? b : String.format("%s, %s", a, b));
//                        return String.format("(core) %s -> %s: %s (%s)", res.getVersion(), res.getName(), verbs, categories);
//                    })
//                    .forEach(lst::add);
//
//            ApiextensionsV1Api apiextV1 = new ApiextensionsV1Api();
//            V1APIResourceList apiextResrouces = apiextV1.getAPIResources();
//            lst.add(String.format("\ngroupVersion: %s\n", apiextResrouces.getGroupVersion()));
//            apiextResrouces.getResources().stream()
//                    .map(res -> {
//                        String verbs = res.getVerbs().stream()
//                                .reduce("", (a, b) -> "".equals(a) ? b : String.format("%s, %s", a, b));
//                        String categories = Optional.ofNullable(res.getCategories())
//                                .orElse(List.of(""))
//                                .stream()
//                                .reduce("", (a, b) -> "".equals(a) ? b : String.format("%s, %s", a, b));
//                        return String.format("(core) %s -> %s: %s (%s)", res.getVersion(), res.getName(), verbs, categories);
//                    })
//                    .forEach(lst::add);

            return lst;
        } catch (ApiException e) {
            KubeApiResponse obj = readValueFrom(e.getResponseBody(), KubeApiResponse.class);
            System.out.println(obj.getMessage());

            e.printStackTrace();
        }

        return null;
    }

}
