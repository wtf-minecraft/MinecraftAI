package me.immortal.mcai;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.ai.*;
import net.citizensnpcs.api.ai.speech.SpeechContext;
import net.citizensnpcs.api.ai.tree.Behavior;
import net.citizensnpcs.api.ai.tree.BehaviorStatus;
import net.citizensnpcs.api.npc.BlockBreaker;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.npc.NPCRegistry;
import net.citizensnpcs.api.trait.Trait;
import net.citizensnpcs.api.trait.trait.Equipment;
import net.citizensnpcs.trait.ArmorStandTrait;
import net.minecraft.world.level.ClipContext;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;

import java.util.Random;
import java.util.function.BiConsumer;

public class CreateCommandNPC implements CommandExecutor {
    Random random;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        random = new Random();
        Player player = (Player) sender;
        NPCRegistry registry = CitizensAPI.getNPCRegistry();
        registry.deregisterAll();
        NPC npc = registry.createNPC(EntityType.PLAYER, ChatColor.BOLD + "" + ChatColor.RED + "Your Mom");
        npc.setProtected(false);
        npc.setUseMinecraftAI(true);

        Equipment equipment = npc.getTrait(Equipment.class);
        equipment.set(Equipment.EquipmentSlot.HELMET, new ItemStack(Material.DIAMOND_HELMET));
        equipment.set(Equipment.EquipmentSlot.CHESTPLATE, new ItemStack(Material.DIAMOND_CHESTPLATE));
        equipment.set(Equipment.EquipmentSlot.LEGGINGS, new ItemStack(Material.DIAMOND_LEGGINGS));
        equipment.set(Equipment.EquipmentSlot.BOOTS, new ItemStack(Material.DIAMOND_BOOTS));
        equipment.set(Equipment.EquipmentSlot.HAND, new ItemStack(Material.NETHERITE_SWORD));
        npc.addRunnable(new Runnable() {
            @Override
            public void run() {

                npc.getEntity().setCustomNameVisible(true);
                npc.getNavigator().getDefaultParameters().run();
                LivingEntity pitch = (LivingEntity) npc.getEntity();
                pitch.getEyeLocation().setPitch(30.0f);
                pitch.getEyeLocation().setYaw(random.nextFloat(360.0f));
                npc.getEntity().getNearbyEntities(10, 10, 10).forEach(entity -> {
                    if (entity instanceof LivingEntity) {
                        if(entity instanceof Monster){
                            npc.getNavigator().setTarget(entity, true);
                        }
                        else{
                            player.sendMessage(pitch.getTargetBlockExact(10).getType().toString());
                            npc.getNavigator().setTarget(pitch.getTargetBlockExact(10).getLocation());

                        }
                    }
                });


            }
        });

        npc.spawn(player.getLocation());

        npc.getDefaultSpeechController().speak(new SpeechContext(npc, "Hello " + player.getName() + " I am a Bot"));

        return false;
    }
}