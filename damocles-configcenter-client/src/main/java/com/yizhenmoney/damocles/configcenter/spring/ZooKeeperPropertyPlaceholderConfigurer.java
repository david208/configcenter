package com.yizhenmoney.damocles.configcenter.spring;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import com.yizhenmoney.damocles.configcenter.config.Config;
import com.yizhenmoney.damocles.configcenter.utils.ZooKeeperConfig;

public class ZooKeeperPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {
	public static final String PATH = "zoo.paths";
	public static final String AUTH = "zoo.auth";
    @Override
    protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props)
            throws BeansException {
    	try {
            fillCustomProperties(props);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        super.processProperties(beanFactoryToProcess, props);
    }
 
    private void fillCustomProperties(Properties props) throws Exception {
    	List<String> datas =  getDatas(props);
    	if(datas.isEmpty()){
    		throw new Exception("读取配置文件信息数据为空!");
    	}
    	fillProperties(props, datas);
    }
    /**
     * 解析数据
     * 支持数据格式为data1=value1;data2=value2
     * */
    private void fillProperties(Properties props, List<String> data) throws UnsupportedEncodingException {
        if (!data.isEmpty()) {
        	for (String string : data) {
        		String[] cfgItem = StringUtils.split(string, ";");
                for (String string1 : cfgItem) {
    				if(StringUtils.isNotBlank(string1)){
    					String[] cfgItem1 =StringUtils.split(string1, "=");
    					if(null!=cfgItem1 && cfgItem1.length>0){
    						if(cfgItem1.length==1){
    							props.put(cfgItem1[0], "");
    						}else{
    							props.put(cfgItem1[0], cfgItem1[1]);
    						}
    						
    					}
    				}
    			}
			}
   
        }
    }
    private List<String> getDatas(Properties props) throws Exception {
        String path = props.getProperty(PATH);
        String auth = props.getProperty(AUTH);
        System.out.println("path:"+path);
        System.out.println("auth:"+auth);
        Config config = ZooKeeperConfig.getZookeeperConfig(auth);
        //Config baseConfig = ZooKeeperConfig.getBaseZookeeperConfig(auth);
        //List<String> base = baseConfig.getBaseConfig(path);
        List<String> conf = config.getConfig2(path);
        //base.addAll(conf);
        return conf;
    }
}
