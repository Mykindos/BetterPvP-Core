package net.betterpvp.core.database;

import net.betterpvp.core.Core;
import net.betterpvp.core.framework.BPVPListener;
import net.betterpvp.core.utility.fancymessage.utility.Reflection;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.reflections.Reflections;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

public class QueryFactory extends BPVPListener<Core> {

    private static ConcurrentLinkedQueue<Query> queries = new ConcurrentLinkedQueue<>();
    private static ConcurrentLinkedQueue<BukkitRunnable> requests = new ConcurrentLinkedQueue<>();

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
                }else{
                    // Process all data submission queries before loading in any data
                    BukkitRunnable r = requests.poll();
                    if(r != null){
                        r.runTask(getInstance());
                    }
                }



            }

        }.runTaskTimerAsynchronously(i, 0L, 1L);


    }


    /**
     *
     * @param query Runs a query statement
     */
    public static void runQuery(String query) {
        System.out.println(query);
        queries.add(new Query(query));
    }

    /**
     *
     * @param request Runs a bukkit runnable
     */
    public static void requestData(BukkitRunnable request) {
       requests.add(request);
    }

    public static void addRepository(Repository r) {
        repositories.add(r);

    }

    /**

     * @param instance
     * Loads all Repository objects in order of priority. Data that requires other data to be loaded first should be on high
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static void loadRepositories(String packageName, JavaPlugin instance) {

        Reflections reflections = new Reflections(packageName);

        Set<Class<? extends Repository>> classes = reflections.getSubTypesOf(Repository.class);
        System.out.println("Repositories : " + classes.size());
        List<Repository> temp = new ArrayList<>();
        for (Class<? extends Repository> r : classes) {
            try {
                Repository repo = r.newInstance();
                QueryFactory.addRepository(repo);
                temp.add(repo);
            } catch (InstantiationException | IllegalAccessException e) {
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
