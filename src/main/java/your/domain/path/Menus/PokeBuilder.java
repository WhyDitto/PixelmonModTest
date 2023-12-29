package your.domain.path.Menus;

import ca.landonjw.gooeylibs2.api.UIManager;
import ca.landonjw.gooeylibs2.api.button.Button;
import ca.landonjw.gooeylibs2.api.button.GooeyButton;
import ca.landonjw.gooeylibs2.api.page.GooeyPage;
import ca.landonjw.gooeylibs2.api.template.types.ChestTemplate;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.api.pokemon.stats.BattleStatsType;
import com.pixelmonmod.pixelmon.api.registries.PixelmonItems;
import com.pixelmonmod.pixelmon.api.storage.StorageProxy;
import com.pixelmonmod.pixelmon.api.util.helpers.SpriteItemHelper;

import net.minecraft.entity.player.ServerPlayerEntity;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Util;
import net.minecraft.util.text.StringTextComponent;


import java.util.ArrayList;
import java.util.List;


public class PokeBuilder {
    private ItemStack spriteOne;
    private ItemStack spriteTwo;
    private ItemStack spriteThree;
    private ItemStack spriteFour;
    private ItemStack spriteFive;
    private ItemStack spriteSix;
    public void MainMenu(ServerPlayerEntity player) {
        if(slotFull(player, 0)) {
             spriteOne = SpriteItemHelper.getPhoto(getPokemon(player, 0));
        } else {
             spriteOne = new ItemStack(Items.BARRIER);
        }
        if(slotFull(player, 1)) {
            spriteTwo = SpriteItemHelper.getPhoto(getPokemon(player, 1));
        } else {
            spriteTwo = new ItemStack(Items.BARRIER);
        }
        if(slotFull(player, 2)) {
            spriteThree = SpriteItemHelper.getPhoto(getPokemon(player, 2));
        } else {
            spriteThree = new ItemStack(Items.BARRIER);
        }
        if(slotFull(player, 3)) {
            spriteFour = SpriteItemHelper.getPhoto(getPokemon(player, 3));
        } else {
            spriteFour = new ItemStack(Items.BARRIER);
        }
        if(slotFull(player, 4)) {
            spriteFive = SpriteItemHelper.getPhoto(getPokemon(player, 4));
        } else {
            spriteFive = new ItemStack(Items.BARRIER);
        }
        if(slotFull(player, 5)) {
            spriteSix = SpriteItemHelper.getPhoto(getPokemon(player, 5));
        } else {
            spriteSix = new ItemStack(Items.BARRIER);
        }
        GooeyButton buttonOne = GooeyButton.builder().display(spriteOne).onClick((buttonAction -> {
            if(slotFull(buttonAction.getPlayer(), 0)) {
                ChooseIv(player, 0);
            }
        })).build();
        GooeyButton buttonTwo = GooeyButton.builder().display(spriteTwo).onClick((buttonAction -> {
            if(slotFull(buttonAction.getPlayer(), 1)) {
                ChooseIv(player, 1);
            }
        })).build();
        GooeyButton buttonThree = GooeyButton.builder().display(spriteThree).onClick((buttonAction -> {
            if(slotFull(buttonAction.getPlayer(), 2)) {
                ChooseIv(player, 2);
            }
        })).build();
        GooeyButton buttonFour = GooeyButton.builder().display(spriteFour).onClick((buttonAction -> {
            if(slotFull(buttonAction.getPlayer(), 3)) {
                ChooseIv(player, 3);
            }
        })).build();
        GooeyButton buttonFive = GooeyButton.builder().display(spriteFive).onClick((buttonAction -> {
            if(slotFull(buttonAction.getPlayer(), 4)) {
                ChooseIv(player, 4);
            }
        })).build();
        GooeyButton buttonSix = GooeyButton.builder().display(spriteSix).onClick((buttonAction -> {
            if(slotFull(buttonAction.getPlayer(), 5)) {
                ChooseIv(player, 5);
            }
        })).build();



        List<String> infoString = new ArrayList<>();
        infoString.add("test 1");
        infoString.add("test 2");
        GooeyButton info = GooeyButton.builder().display(new ItemStack(Items.NETHER_STAR)).title("INFO").lore(infoString).build();
        Button filler = GooeyButton.builder().display(new ItemStack(Items.RED_STAINED_GLASS_PANE)).build();
        ChestTemplate template = ChestTemplate.builder(3).set(1,1, buttonOne).set(1,2, buttonTwo).set(1,3,buttonThree).set(1, 4, info).set(1,5,buttonFour).set(1,6, buttonFive).set(1, 7,buttonSix).fill(filler).build();
        GooeyPage mainMenu = GooeyPage.builder().title("PokeBuilder").template(template).build();
        UIManager.openUIForcefully(player, mainMenu);

    }


    private void ChooseIv(ServerPlayerEntity player, int slot) {

        Button hp = GooeyButton.builder().display(new ItemStack(Items.DIAMOND)).title("HP").onClick(buttonAction -> IvMenu(player, slot, BattleStatsType.HP)).build();
        Button attack = GooeyButton.builder().display(new ItemStack(Items.DIAMOND)).title("ATTACK").onClick(buttonAction -> IvMenu(player, slot, BattleStatsType.ATTACK)).build();
        Button defense = GooeyButton.builder().display(new ItemStack(Items.DIAMOND)).title("DEFENSE").onClick(buttonAction -> IvMenu(player, slot, BattleStatsType.DEFENSE)).build();
        Button satk = GooeyButton.builder().display(new ItemStack(Items.DIAMOND)).title("SPECIAL ATTACK").onClick(buttonAction -> IvMenu(player, slot, BattleStatsType.SPECIAL_ATTACK)).build();
        Button sdef = GooeyButton.builder().display(new ItemStack(Items.DIAMOND)).title("SPECIAL DEFENSE").onClick(buttonAction -> IvMenu(player, slot, BattleStatsType.SPECIAL_DEFENSE)).build();
        Button speed = GooeyButton.builder().display(new ItemStack(Items.DIAMOND)).title("SPEED").onClick(buttonAction -> IvMenu(player, slot, BattleStatsType.SPEED)).build();
        Button filler = GooeyButton.builder().display(new ItemStack(Items.RED_STAINED_GLASS_PANE)).build();
        ChestTemplate template = ChestTemplate.builder(3).set(1, 1, hp).set(1, 2, attack).set(1, 3, defense).set(1, 5, satk).set(1, 6, sdef).set(1, 7, speed).fill(filler).build();
        GooeyPage page = GooeyPage.builder().template(template).title("Choose IV").build();
        UIManager.openUIForcefully(player, page);
    }



    private void IvMenu(ServerPlayerEntity player, int slot, BattleStatsType type) {

        GooeyButton increaseOne = GooeyButton.builder().display(new ItemStack(PixelmonItems.insect_plate)).title("Increase by 1").onClick((buttonAction -> {

            if(canIncrease(player, slot, 1, type)) {
                ConfirmMenu(player, slot, type, Amount.INCREASEONE);

            } else {
                player.sendMessage(new StringTextComponent("Error"), Util.NIL_UUID);
            }
        })).build();
        GooeyButton increaseFive = GooeyButton.builder().display(new ItemStack(PixelmonItems.meadow_plate)).title("Increase by 5").onClick((buttonAction -> {
            if(canIncrease(player, slot, 5, type)) {
                ConfirmMenu(player, slot, type, Amount.INCREASEFIVE);

            } else {
                player.sendMessage(new StringTextComponent("Error"), Util.NIL_UUID);
            }
        })).build();
        GooeyButton decreaseOne = GooeyButton.builder().display(new ItemStack(PixelmonItems.flame_plate)).title("Decrease by 1").onClick(buttonAction -> {
            if(canDecrease(player, slot, 1, type)) {
                ConfirmMenu(player, slot, type, Amount.DECREASEONE);

            } else {
                player.sendMessage(new StringTextComponent("Error"), Util.NIL_UUID);
            }
        }).build();
        GooeyButton decreaseFive = GooeyButton.builder().display(new ItemStack(PixelmonItems.draco_plate)).title("Decrease by 5").onClick(buttonAction -> {
            if(canDecrease(player, slot, 5, type)) {
                ConfirmMenu(player, slot, type, Amount.DECREASEFIVE);

            } else {
                player.sendMessage(new StringTextComponent("Error"), Util.NIL_UUID);
            }
        }).build();
        Button filler = GooeyButton.builder().display(new ItemStack(Items.RED_STAINED_GLASS_PANE)).build();
        GooeyButton sprite = GooeyButton.builder().display(SpriteItemHelper.getPhoto(getPokemon(player, slot))).build();
        ChestTemplate template = ChestTemplate.builder(3).set(1, 2, increaseFive).set(1, 3, increaseOne).set(1, 4, sprite).set(1,5,decreaseOne).set(1,6, decreaseFive).fill(filler).build();
        GooeyPage page = GooeyPage.builder().template(template).title("Select IV Change").build();
        UIManager.openUIForcefully(player, page);
    }
    private void ConfirmMenu(ServerPlayerEntity player, int slot, BattleStatsType type, Amount amount) {
        GooeyButton confirm = GooeyButton.builder().display(new ItemStack(Items.GREEN_WOOL)).onClick(buttonAction -> {
            changeStat(buttonAction.getPlayer(), slot, type, amount);
            UIManager.closeUI(player);
        }).build();
        GooeyButton decline = GooeyButton.builder().display(new ItemStack(Items.RED_WOOL)).onClick(buttonAction -> {
            ChooseIv(player, slot);
        }).build();
        ChestTemplate template = ChestTemplate.builder(3).set(0, 1, confirm).set(0, 2, decline).build();
        GooeyPage page = GooeyPage.builder().title("Change " + type.getLocalizedName() + " by " + amount).template(template).build();
        UIManager.openUIForcefully(player, page);
    }
    private boolean slotFull(ServerPlayerEntity player, int slot) {
        return StorageProxy.getParty(player.getUUID()).get(slot) != null;
    }

    private boolean canIncrease(ServerPlayerEntity player, int slot, int value, BattleStatsType type) {
       int number = StorageProxy.getParty(player.getUUID()).get(slot).getIVs().getStat(type);
        return number + value <= 31;
    }
    private boolean canDecrease(ServerPlayerEntity player, int slot, int value, BattleStatsType type) {
        int number = StorageProxy.getParty(player.getUUID()).get(slot).getIVs().getStat(type);
        return number - value >= 0;
    }


    private void changeStat(ServerPlayerEntity player, int slot, BattleStatsType type, Amount amount) {
        int lastValue =  StorageProxy.getParty(player.getUUID()).get(slot).getIVs().getStat(type);
        if(amount == Amount.INCREASEONE) {
            StorageProxy.getParty(player.getUUID()).get(slot).getIVs().setStat(type, lastValue + 1);
            player.sendMessage(new StringTextComponent("Changed " + type.name() + " by " + 1), Util.NIL_UUID);
        } else if (amount == Amount.INCREASEFIVE) {
            StorageProxy.getParty(player.getUUID()).get(slot).getIVs().setStat(type, lastValue + 5);
            player.sendMessage(new StringTextComponent("Changed " + type.name() + " by " + 5), Util.NIL_UUID);
        } else if(amount == Amount.DECREASEONE) {
            StorageProxy.getParty(player.getUUID()).get(slot).getIVs().setStat(type, lastValue - 1);
            player.sendMessage(new StringTextComponent("Changed " + type.name() + " by " + -1), Util.NIL_UUID);
        } else if(amount == Amount.DECREASEFIVE) {
            StorageProxy.getParty(player.getUUID()).get(slot).getIVs().setStat(type, lastValue - 5);
            player.sendMessage(new StringTextComponent("Changed " + type.name() + " by " + -5), Util.NIL_UUID);
        }



    }
    private Pokemon getPokemon(ServerPlayerEntity player, int slot) {
        return StorageProxy.getParty(player).get(slot);
    }




}
