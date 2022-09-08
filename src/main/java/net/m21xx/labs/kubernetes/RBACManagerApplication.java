package net.m21xx.labs.kubernetes;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"net.m21xx"})
public class RBACManagerApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(RBACManagerApplication.class, args);
    }

//    @Autowired
//    private KubernetesService kubernetesService;

    @Override
    public void run(String... args) throws Exception {

//       Optional.ofNullable(kubernetesService.getAllResources())
//                .orElseThrow()
//                .forEach(System.out::println);
    }

}
