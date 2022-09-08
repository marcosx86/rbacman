package net.m21xx.labs.kubernetes;

import net.m21xx.labs.kubernetes.service.KubernetesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.util.List;
import java.util.Optional;

@SpringBootApplication
@ComponentScan(basePackages = {"net.m21xx"})
public class RBACManagerApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(RBACManagerApplication.class, args);
    }

    @Autowired
    private KubernetesService kubernetesService;

    @Override
    public void run(String... args) throws Exception {
        List<String> lst = kubernetesService.getAllResources();
        Optional.ofNullable(lst)
                .orElseThrow()
                .forEach(System.out::println);
    }

}
