package com.rachit.searchsuggestion;

import com.rachit.searchsuggestion.updater.CacheToDBSyncManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class SearchSuggestionApplication {

	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(SearchSuggestionApplication.class, args);
		for(String bean: ctx.getBeanDefinitionNames()){
			Object o = ctx.getBean(bean);
			if(o instanceof CacheToDBSyncManager) {
				System.out.println(o.toString());
				CacheToDBSyncManager syncManager = (CacheToDBSyncManager) o;
				new Thread(syncManager).start();
			}
		}
	}

}
