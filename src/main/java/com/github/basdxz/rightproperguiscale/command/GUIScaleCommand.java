package com.github.basdxz.rightproperguiscale.command;

import com.github.basdxz.rightproperguiscale.GUIScale;
import lombok.*;
import net.minecraft.command.*;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.client.ClientCommandHandler;

import java.util.Optional;

import static lombok.AccessLevel.PROTECTED;

@NoArgsConstructor(access = PROTECTED)
public class GUIScaleCommand extends CommandBase {
    protected static final GUIScaleCommand INSTANCE = new GUIScaleCommand();
    protected static final String GUI_SCALE_COMMAND_NAME = "guiscale";
    protected static final String GUI_SCALE_COMMAND_USE_UNLOCALIZED = "commands.guiscale.usage";
    protected static final String GUI_SCALE_COMMAND_SUCCESS_UNLOCALIZED = "commands.guiscale.success";
    protected static final String GUI_SCALE_COMMAND_NUMBER_INVALID = "commands.guiscale.number_invalid";
    protected static final int GUI_SCALE_ARGUMENT_INDEX = 0;
    protected static final int GUI_SCALE_COMMAND_ARGUMENT_COUNT = 1;

    public static void register() {
        ClientCommandHandler.instance.registerCommand(GUIScaleCommand.INSTANCE);
    }

    public String getCommandName() {
        return GUI_SCALE_COMMAND_NAME;
    }

    public String getCommandUsage(ICommandSender sender) {
        return GUI_SCALE_COMMAND_USE_UNLOCALIZED;
    }

    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }

    public void processCommand(ICommandSender sender, String[] args) {
        if (argsNull(sender, args) || argsWrongLength(args))
            throw newWrongUsageException();
        val guiScale = parseGUIScale(args);
        if (!guiScale.isPresent())
            throw newNumberInvalidGUIScaleValueException(args);
        updateGUIScale(guiScale.get());
        sendSuccessMessage(sender);
    }

    protected boolean argsNull(ICommandSender sender, String[] args) {
        return sender == null || args == null;
    }

    protected boolean argsWrongLength(@NonNull String[] args) {
        return args.length != GUI_SCALE_COMMAND_ARGUMENT_COUNT;
    }

    protected CommandException newWrongUsageException() {
        return new WrongUsageException(GUI_SCALE_COMMAND_USE_UNLOCALIZED);
    }

    protected Optional<Float> parseGUIScale(@NonNull String[] args) {
        try {
            return Optional.of(Float.parseFloat(args[GUI_SCALE_ARGUMENT_INDEX]));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    protected CommandException newNumberInvalidGUIScaleValueException(@NonNull String[] args) {
        return new NumberInvalidException(GUI_SCALE_COMMAND_NUMBER_INVALID, args[GUI_SCALE_ARGUMENT_INDEX]);
    }

    protected void updateGUIScale(float guiScale) {
        GUIScale.set(guiScale);
        GUIScale.save();
    }

    protected void sendSuccessMessage(@NonNull ICommandSender sender) {
        sender.addChatMessage(newSuccessMessage());
    }

    protected IChatComponent newSuccessMessage() {
        return new ChatComponentTranslation(GUI_SCALE_COMMAND_SUCCESS_UNLOCALIZED, GUIScale.asFloat());
    }
}