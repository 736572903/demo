//package com.test;
//
//import com.baomidou.mybatisplus.core.config.GlobalConfig;
//import com.baomidou.mybatisplus.generator.AutoGenerator;
//import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
//import com.baomidou.mybatisplus.generator.config.GlobalConfig;
//import com.baomidou.mybatisplus.generator.config.PackageConfig;
//import com.baomidou.mybatisplus.generator.config.StrategyConfig;
//import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
//import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
//import com.baomidou.mybatisplus.generator.config.rules.DbType;
//import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
//import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
//
//public class TestMybatisCreate {
//
//	public static void main(String[] args) {
//		AutoGenerator mpg = new AutoGenerator();
//        // 选择 freemarker 引擎，默认 Velocity
//        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
// 
//        // 全局配置
//        GlobalConfig gc = new GlobalConfig();
//        gc.setAuthor("Mht");
//        gc.setOutputDir("D://workspace/spring-boot-mybatis-plus/src/main/java");
//        gc.setFileOverride(false);// 是否覆盖同名文件，默认是false
//        gc.setActiveRecord(true);// 不需要ActiveRecord特性的请改为false
//        gc.setEnableCache(false);// XML 二级缓存
//        gc.setBaseResultMap(true);// XML ResultMap
//        gc.setBaseColumnList(false);// XML columList
//        /* 自定义文件命名，注意 %s 会自动填充表实体属性！ */
//        // gc.setMapperName("%sDao");
//        // gc.setXmlName("%sDao");
//        // gc.setServiceName("MP%sService");
//        // gc.setServiceImplName("%sServiceDiy");
//        // gc.setControllerName("%sAction");
//        mpg.setGlobalConfig(gc);
//	}
//
//}
