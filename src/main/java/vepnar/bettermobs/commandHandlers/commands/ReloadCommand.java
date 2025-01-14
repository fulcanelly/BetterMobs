package vepnar.bettermobs.commandHandlers.commands;

import org.bukkit.command.CommandSender;
import vepnar.bettermobs.Main;
import vepnar.bettermobs.commandHandlers.ICommandExecuteAble;
import vepnar.bettermobs.commandHandlers.ICommandGroup;
import vepnar.bettermobs.genericMobs.IMobListener;

public class ReloadCommand implements ICommandExecuteAble {

    private final ICommandGroup parent;

    public ReloadCommand(ICommandGroup parent){
        this.parent = parent;
    }

    @Override
    public String getHelp() {
        return "§r<feature>§7 reload one or more features.";
    }

    @Override
    public String getName() {
        return "reload";
    }

    @Override
    public String[] getAlias() {
        return new String [] {"r", "restart"};
    }

    @Override
    public int getMinimalArguments() {
        return 0;
    }

    @Override
    public String[] getTabCompletion() {
        return new String[0];
    }

    @Override
    public String getPermission() {
        return "bettermobs.features.reload";
    }

    @Override
    public ICommandGroup getParent() {
        return parent;
    }

    public void executeArguments(Main core, CommandSender sender, String[] args) {
        String output = "";

        for(String feature : args) {
            for(IMobListener listener :  core.mobListeners) {
                if (listener.getName().equalsIgnoreCase(feature) && listener.isEnabled()) {
                    output += "§a" + listener.getName() + "§r, ";
                    listener.reloadConfig();
                    break;
                }
            }
        }
        sender.sendMessage(core.prefix + "The following features have been reloaded:");
        if(output.length() != 0) {
            output = output.substring(0, output.length() - 4);
            sender.sendMessage(output);
        } else {
            sender.sendMessage("§cNone, because your input is invalid.");
        }
    }

    @Override
    public boolean execute(Main core, CommandSender sender, String[] args) {
        if(args.length != 0) {
            executeArguments(core, sender, args);
            return true;
        }
        core.reloadConfig();

        for(IMobListener listener :  core.mobListeners) {
            listener.reloadConfig();
        }
        sender.sendMessage(core.prefix + "All the features have been reloaded.");
        return true;


    }
}
