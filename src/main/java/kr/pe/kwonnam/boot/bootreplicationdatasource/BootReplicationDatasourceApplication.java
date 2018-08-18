package kr.pe.kwonnam.boot.bootreplicationdatasource;

import kr.pe.kwonnam.boot.bootreplicationdatasource.jpa.User;
import kr.pe.kwonnam.boot.bootreplicationdatasource.jpa.UserOuterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.util.Assert;

@SpringBootApplication
public class BootReplicationDatasourceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BootReplicationDatasourceApplication.class, args);
    }

    private Logger log = LoggerFactory.getLogger(BootReplicationDatasourceApplication.class);

    @Autowired
    private UserOuterService userOuterService;


    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {
            User readUser = userOuterService.findByIdRead(1);
            log.info("### findByIdRead : {}", readUser.getName());
            Assert.isTrue(readUser.getName().equals("read_1"), "user 1 from read mustbe read_1");

            User writeUser = userOuterService.findByIdWrite(1);
            log.info("### findByIdWrite : {}", writeUser.getName());
            Assert.isTrue(writeUser.getName().equals("write_1"), "user 1 from write mustbe write_1");
        };
    }
}
