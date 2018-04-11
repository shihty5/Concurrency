package com.peter.examples.hash;

public class Cluster {
    private static final int SERVER_SIZE_MAX = 1024;

    private Server[] servers = new Server[SERVER_SIZE_MAX];
    private int size = 0;

    //hash方法
    public void put(Entry e) {
        int index = e.hashCode() % size;
        servers[index].put(e);
    }

    public Entry get(Entry e) {
        int index = e.hashCode() % size;
        return servers[index].get(e);
    }

    public boolean addServer(Server s) {
        if (size >= SERVER_SIZE_MAX)
            return false;

        servers[size++] = s;
        return true;
    }
}
