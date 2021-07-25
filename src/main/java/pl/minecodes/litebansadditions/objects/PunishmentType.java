package pl.minecodes.litebansadditions.objects;

public enum PunishmentType {

    BAN("ban", EntryListeningType.ADDED),
    MUTE("mute", EntryListeningType.ADDED),
    KICK("kick", EntryListeningType.ADDED),
    WARN("warn", EntryListeningType.ADDED),
    UNBAN("ban", EntryListeningType.REMOVED),
    UNMUTE("warn", EntryListeningType.REMOVED),
    UNWARN("warn", EntryListeningType.REMOVED);

    private final String listenerName;
    private final EntryListeningType listenerType;

    PunishmentType(String listenerName, EntryListeningType listenerType) {
        this.listenerName = listenerName;
        this.listenerType = listenerType;
    }

    public static PunishmentType get(String listenerName, EntryListeningType listenerType) {
        for (PunishmentType value : PunishmentType.values()) {
            if(value.getListenerName().equalsIgnoreCase(listenerName) && value.getListenerType() == listenerType) {
                return value;
            }
        }

        return null;
    }

    public String getListenerName() {
        return listenerName;
    }

    public EntryListeningType getListenerType() {
        return listenerType;
    }

    public enum EntryListeningType {

        ADDED,
        REMOVED

    }

}
