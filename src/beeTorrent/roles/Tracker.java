package beeTorrent.roles;

import beeTorrent.utils.lifeCycleUtils;

public class Tracker extends Bee {

    // This object will assume the role of tracker
    public Tracker(){
        super(lifeCycleUtils.HiveRole.TRACKER);
    }

}
