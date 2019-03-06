package net.betterpvp.core.database;

import net.betterpvp.core.Core;
import net.betterpvp.core.framework.BPVPListener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.reflections.Reflections;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;

public class QueryFactory extends BPVPListener<Core> {

    private static ConcurrentLinkedQueue<Query> queries = new ConcurrentLinkedQueue<>();

    @SuppressWarnings("rawtypes")
    private static List<Repository> repositories = new ArrayList<>();

    public QueryFactory(Core i) {
        super(i);



        new BukkitRunnable() {

            @Override
            public void run() {
                Query q = queries.poll();
                if (q != null) {
                    q.execute();
                }

            }

        }.runTaskTimerAsynchronously(i, 0L, 1L);


    }


    public static void runQuery(String query) {
        queries.add(new Query(query));
    }

    public static void addRepository(Repository r) {
        repositories.add(r);

    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    public static void loadRepositories(String packageName, JavaPlugin instance) {

        Reflections reflections = new Reflections(packageName);
        Set<Class<? extends Repository>> classes = reflections.getSubTypesOf(Repository.class);
        System.out.println("Repositories: " + classes.size());
        List<Repository> temp = new ArrayList<>();
        for (Class<? extends Repository> r : classes) {
            try {
                Repository repo = r.newInstance();
                QueryFactory.addRepository(repo);
                temp.add(repo);
            } catch (InstantiationException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        temp.sort(Comparator.comparingInt(r2 -> r2.getLoadPriority().getPriority()));

        temp.forEach(r -> {

            r.initialize();
            r.load(instance);

        });

    }

}
