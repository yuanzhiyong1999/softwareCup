package com.yzy;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.DateType;

import java.util.Collections;

public class CodeGenerator {
    static final String URL="jdbc:mysql://47.119.157.25:3306/software_cup?useUnicode=true&useSSL=false&characterEncoding=utf8&serverTimezone=Asia/Shanghai";

    public static void main(String[] args) {
        String pkgPath=System.getProperty("user.dir")+"/src/main/java";
        String pkgXml=System.getProperty("user.dir")+"/src/main/resources/mapper";
        FastAutoGenerator.create(URL,"root","cc222436")
                // 全局配置                                   指定输出目录          作者名
                .globalConfig((scanner, builder) -> builder.outputDir(pkgPath).author("yuanzhiyong")
                        //覆盖已生成文件  禁止打开输出目录       注释日期                  时间策略
                        .fileOverride().disableOpenDir().commentDate("yyyy-MM-dd").dateType(DateType.ONLY_DATE))
                // 包配置                                     父包名
                .packageConfig((scanner, builder) -> builder.parent("com.yzy")
                        //路径配置信息
                        .pathInfo(Collections.singletonMap(OutputFile.mapperXml, pkgXml)))
                // 策略配置                                    增加表匹配(内存过滤)
                .strategyConfig((scanner, builder) -> builder.addInclude(scanner.apply("请输入表名"))
                        .addTablePrefix("tb_").build())
                .execute();
    }
}

