package net.betterpvp.core.framework;

import net.betterpvp.core.Core;
import net.betterpvp.core.configs.Configs;
import org.bukkit.Bukkit;
import org.bukkit.Location;

public class Options {

    private Core i;

    private Location spawnA;
    private Location spawnB;

    public Options(Core i) {
        this.i = i;

        checkDefaults();
        reloadOptions();

    }


    public void reloadOptions() {
        spawnA = new Location(Bukkit.getWorld(i.getConfigManager().get(Configs.MAIN).getString("SpawnA.World")),
                i.getConfigManager().get(Configs.MAIN).getDouble("SpawnA.X"),
                i.getConfigManager().get(Configs.MAIN).getDouble("SpawnA.Y"),
                i.getConfigManager().get(Configs.MAIN).getDouble("SpawnA.Z"),
                (float) i.getConfigManager().get(Configs.MAIN).getDouble("SpawnA.Yaw"),
                (float) i.getConfigManager().get(Configs.MAIN).getDouble("SpawnA.Pitch"));
        spawnB = new Location(Bukkit.getWorld(i.getConfigManager().get(Configs.MAIN).getString("SpawnB.World")),
                i.getConfigManager().get(Configs.MAIN).getDouble("SpawnB.X"),
                i.getConfigManager().get(Configs.MAIN).getDouble("SpawnB.Y"),
                i.getConfigManager().get(Configs.MAIN).getDouble("SpawnB.Z"),
                (float) i.getConfigManager().get(Configs.MAIN).getDouble("SpawnB.Yaw"),
                (float) i.getConfigManager().get(Configs.MAIN).getDouble("SpawnB.Pitch"));
    }


    public void checkDefaults() {
        i.getConfigManager().get(Configs.MAIN).check("SpawnA.World", "world");
        i.getConfigManager().get(Configs.MAIN).check("SpawnA.X", 0);
        i.getConfigManager().get(Configs.MAIN).check("SpawnA.Y", 0);
        i.getConfigManager().get(Configs.MAIN).check("SpawnA.Z", 0);
        i.getConfigManager().get(Configs.MAIN).check("SpawnA.Yaw", 0f);
        i.getConfigManager().get(Configs.MAIN).check("SpawnA.Pitch", 0f);

        i.getConfigManager().get(Configs.MAIN).check("SpawnB.World", "world");
        i.getConfigManager().get(Configs.MAIN).check("SpawnB.X", 0);
        i.getConfigManager().get(Configs.MAIN).check("SpawnB.Y", 0);
        i.getConfigManager().get(Configs.MAIN).check("SpawnB.Z", 0);
        i.getConfigManager().get(Configs.MAIN).check("SpawnB.Yaw", 0f);
        i.getConfigManager().get(Configs.MAIN).check("SpawnB.Pitch", 0f);
    }

    public void saveLocation(Location loc){
        i.getConfigManager().get(Configs.MAIN).set("SpawnA.World", loc.getWorld().getName());
        i.getConfigManager().get(Configs.MAIN).set("SpawnA.X", loc.getX());
        i.getConfigManager().get(Configs.MAIN).set("SpawnA.Y", loc.getY());
        i.getConfigManager().get(Configs.MAIN).set("SpawnA.Z", loc.getZ());
        i.getConfigManager().get(Configs.MAIN).set("SpawnA.Yaw", loc.getYaw());
        i.getConfigManager().get(Configs.MAIN).set("SpawnA.Pitch", loc.getPitch());
    }

    public Location getSpawnA(){
        return spawnA;
    }

    public Location getSpawnB(){
        return spawnB;
    }


}
