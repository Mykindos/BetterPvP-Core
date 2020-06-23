package net.betterpvp.core.donation;

import net.betterpvp.core.command.Command;
import net.betterpvp.core.command.CommandManager;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.reflections.Reflections;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class DonationManager {

    private static List<IDonation> donationList = new ArrayList<>();

    public static void addDonation(IDonation d) {
        donationList.add(d);
    }

    public static IDonation getDonation(String name) {
        return donationList.stream().filter(d -> d.getName().equalsIgnoreCase(name)
                || d.getDisplayName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    public static void registerDonations(String packageName, Plugin instance) {
        int count = 0;
        Reflections reflections = new Reflections(packageName);
        Set<Class<? extends IDonation>> classes = reflections.getSubTypesOf(IDonation.class);
        for (Class<? extends IDonation> d : classes) {
            try {
                if (Listener.class.isAssignableFrom(d)) {
                    IDonation donation = d.newInstance();
                    Bukkit.getPluginManager().registerEvents((Listener) donation, instance);
                    System.out.println("Registered donation + listener");
                    DonationManager.addDonation(donation);

                } else {
                    if (d.getConstructors()[0].getParameterCount() > 0) {
                        System.out.println("Skipped Command (Requires arguments): " + d.getName());
                        continue;
                    }

                    DonationManager.addDonation(d.newInstance());
                }
                count++;

            } catch (InstantiationException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }

}
