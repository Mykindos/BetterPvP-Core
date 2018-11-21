package net.betterpvp.core.configs;


public enum Configs {
	MAIN("/config.yml"),
	SHOP("/Shops/shops.yml");
	
	private String path;
	Configs(String path){
		this.path = path;
	}
	
	public String getPath(){
		return path;
	}

}
