package com.base.running;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.exception.XMLParserException;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MybatisGeneratorCoreRunning {

    public static void main(String[] args) {
        System.out.println("MybatisGeneratorCore -> 开始生成dao及sqlmap");
        List<String> warnings = new ArrayList<String>();
        try {
            boolean overwrite = true; //是否覆盖模式.
            File configFile = new File(MybatisGeneratorCoreRunning.class.getResource("/generatorConfig.xml").getFile());
            ConfigurationParser cp = new ConfigurationParser(warnings);

            Configuration config = cp.parseConfiguration(configFile);
            DefaultShellCallback callback = new DefaultShellCallback(overwrite);
            MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
            myBatisGenerator.generate(null);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XMLParserException e) {
            e.printStackTrace();
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (!warnings.isEmpty()) {
                System.out.println("\nMybatisGeneratorCore -> 生成dao及sqlmap异常:");
                for (String warning : warnings) {
                    System.out.println(warning);
                }
                System.out.println("");
            }
            System.out.println("MybatisGeneratorCore -> 生成dao及sqlmap结束!");
        }
    }
}
