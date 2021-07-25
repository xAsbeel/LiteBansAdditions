package pl.minecodes.litebansadditions.abstraction;

import litebans.api.Entry;
import litebans.api.Events;

public abstract class AbstractPunishmentListener extends Events.Listener {

    public abstract void onEntryAdded(Entry entry);

    public abstract void onEntryRemoved(Entry entry);

    @Override
    public void entryAdded(Entry entry) {
        super.entryAdded(entry);
        onEntryAdded(entry);
    }

    @Override
    public void entryRemoved(Entry entry) {
        super.entryRemoved(entry);
        onEntryRemoved(entry);
    }

    public void register() {
        Events.get().register(this);
    }

    public void unregister() {
        Events.get().unregister(this);
    }

}
