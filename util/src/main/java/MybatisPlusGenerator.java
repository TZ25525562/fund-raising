import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import org.junit.Test;

public class MybatisPlusGenerator {

    /**
     * 代码生成器
     */
    @Test
    public void testGenerator() {
        //1.全局配置：
        GlobalConfig globalConfig = new GlobalConfig();
//            支持AP模式
        globalConfig.setActiveRecord(true)
//                    设置作者
                .setAuthor("TZ")
//                    生成路径
                .setOutputDir("D:\\尚筹网开发实例\\myfundraising\\util\\src\\main\\java")
//                    文件覆盖
                .setFileOverride(true)
//                    主键策略
                .setIdType(IdType.AUTO)
//                    设置生成的Service接口的名字的首字母是否为I，默认是IEmployeeService
                .setServiceName("%sService")
                .setBaseResultMap(true)
                .setBaseColumnList(true);
        //2.数据源配置
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setDbType(DbType.MYSQL).setDriverName("com.mysql.jdbc.Driver");
        dataSourceConfig.setUsername("root").setPassword("Tong@117038");
        dataSourceConfig.setUrl("jdbc:mysql://localhost:3306/project_crowd?serverTimezone=UTC");
        //3. 策略配置
        StrategyConfig strategyConfig = new StrategyConfig();
        //全局大写命名
        strategyConfig.setCapitalMode(true)
//                    指定表名字段名是否使用下划线
                .setColumnNaming(NamingStrategy.underline_to_camel)
                .setTablePrefix("t_")
//                    生成的表
                .setInclude("t_role");
//            4.包名策略设置
        PackageConfig packageConfig = new PackageConfig();
        packageConfig.setParent("com.tz")
                .setMapper("mapper")
                .setXml("mapper")
                .setEntity("pojo");
//            5.整合配置
        AutoGenerator generator = new AutoGenerator();
        generator.setGlobalConfig(globalConfig)
                .setDataSource(dataSourceConfig)
                .setStrategy(strategyConfig)
                .setPackageInfo(packageConfig);

        //      6.测试执行
        generator.execute();

    }
}