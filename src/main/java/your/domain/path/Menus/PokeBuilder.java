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
import java.util.concurrent.atomic.AtomicInteger;

public class PokeBuilder {
    private ItemStack spriteOne;
    private ItemStack spriteTwo;
    private ItemStack spriteThree;
    private ItemStack spriteFour;
    private ItemStack spriteFive;
    private ItemStack spriteSix;
    private int currentSlot; //use later to shorten code
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

        GooeyButton buttonTwo = GooeyButton.builder().display(spriteTwo).build();
        GooeyButton buttonThree = GooeyButton.builder().display(spriteThree).build();
        GooeyButton buttonFour = GooeyButton.builder().display(spriteFour).build();
        GooeyButton buttonFive = GooeyButton.builder().display(spriteFive).build();
        GooeyButton buttonSix = GooeyButton.builder().display(spriteSix).build();
        ChestTemplate template = ChestTemplate.builder(3).set(0, buttonOne).set(1, buttonTwo).set(2,buttonThree).set(3,buttonFour).set(4, buttonFive).set(5,buttonSix).build();
        GooeyPage mainMenu = GooeyPage.builder().template(template).build();
        UIManager.openUIForcefully(player, mainMenu);

    }


    private void ChooseIv(ServerPlayerEntity player, int slot) {
        List<Button> buttons = new ArrayList<>();
        Button hp = GooeyButton.builder().display(new ItemStack(Items.DIAMOND)).title("HP").onClick(buttonAction -> IvMenu(player, slot, BattleStatsType.HP)).build();
        Button accuracy = GooeyButton.builder().display(new ItemStack(Items.DIAMOND)).title("ACCURACY").onClick(buttonAction -> IvMenu(player, slot, BattleStatsType.ACCURACY)).build();
        Button attack = GooeyButton.builder().display(new ItemStack(Items.DIAMOND)).title("ATTACK").onClick(buttonAction -> IvMenu(player, slot, BattleStatsType.ATTACK)).build();
        Button defense = GooeyButton.builder().display(new ItemStack(Items.DIAMOND)).title("DEFENSE").onClick(buttonAction -> IvMenu(player, slot, BattleStatsType.DEFENSE)).build();
        Button satk = GooeyButton.builder().display(new ItemStack(Items.DIAMOND)).title("SPECIAL ATTACK").onClick(buttonAction -> IvMenu(player, slot, BattleStatsType.SPECIAL_ATTACK)).build();
        Button sdef = GooeyButton.builder().display(new ItemStack(Items.DIAMOND)).title("SPECIAL DEFENSE").onClick(buttonAction -> IvMenu(player, slot, BattleStatsType.SPECIAL_DEFENSE)).build();
        Button speed = GooeyButton.builder().display(new ItemStack(Items.DIAMOND)).title("SPEED").onClick(buttonAction -> IvMenu(player, slot, BattleStatsType.SPEED)).build();
        buttons.add(hp);
        buttons.add(accuracy);
        buttons.add(attack);
        buttons.add(defense);
        buttons.add(satk);
        buttons.add(sdef);
        buttons.add(speed);
        ChestTemplate template = ChestTemplate.builder(3).fillFromList(buttons).build();
        GooeyPage page = GooeyPage.builder().template(template).title("Choose IV").build();
        UIManager.openUIForcefully(player, page);
    }



    private void IvMenu(ServerPlayerEntity player, int slot, BattleStatsType type) {

        GooeyButton increaseOne = GooeyButton.builder().display(new ItemStack(PixelmonItems.insect_plate)).onClick((buttonAction -> {

            if(canIncrease(player, slot, 1, type)) {
                ConfirmMenu(player, slot, type, 1);

            } else {
                player.sendMessage(new StringTextComponent("Error"), Util.NIL_UUID);
            }
        })).build();
        GooeyButton increaseFive = GooeyButton.builder().display(new ItemStack(PixelmonItems.insect_plate)).onClick((buttonAction -> {
            if(canIncrease(player, slot, 5, type)) {
                ConfirmMenu(player, slot, type, 5);

            } else {
                player.sendMessage(new StringTextComponent("Error"), Util.NIL_UUID);
            }
        })).build();
        GooeyButton decreaseOne = GooeyButton.builder().display(new ItemStack(PixelmonItems.flame_plate)).onClick(buttonAction -> {
            if(canDecrease(player, slot, 1, type)) {
                ConfirmMenu(player, slot, type, -1);

            } else {
                player.sendMessage(new StringTextComponent("Error"), Util.NIL_UUID);
            }
        }).build();
        GooeyButton decreaseFive = GooeyButton.builder().display(new ItemStack(PixelmonItems.draco_plate)).onClick(buttonAction -> {
            if(canDecrease(player, slot, 5, type)) {
                ConfirmMenu(player, slot, type, -5);

            } else {
                player.sendMessage(new StringTextComponent("Error"), Util.NIL_UUID);
            }
        }).build();
        GooeyButton sprite = GooeyButton.builder().display(SpriteItemHelper.getPhoto(getPokemon(player, slot))).build();
        ChestTemplate template = ChestTemplate.builder(3).set(1, 2, increaseFive).set(1, 3, increaseOne).set(1, 4, sprite).set(1,5,decreaseOne).set(1,6, decreaseFive).build();
        GooeyPage page = GooeyPage.builder().template(template).build();
        UIManager.openUIForcefully(player, page);
    }
    private void ConfirmMenu(ServerPlayerEntity player, int slot, BattleStatsType type, int amount) {

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
    private void increaseStatOne(ServerPlayerEntity player, int slot, BattleStatsType type) {
        int lastValue =  StorageProxy.getParty(player.getUUID()).get(slot).getIVs().getStat(type);
        StorageProxy.getParty(player.getUUID()).get(slot).getIVs().setStat(type, lastValue + 1);
        player.sendMessage(new StringTextComponent("Increased " + type.name() + " by 1"), Util.NIL_UUID);
    }
    private void increaseStatFive(ServerPlayerEntity player, int slot, BattleStatsType type) {
        int lastValue =  StorageProxy.getParty(player.getUUID()).get(slot).getIVs().getStat(type);
        StorageProxy.getParty(player.getUUID()).get(slot).getIVs().setStat(type, lastValue + 5);
        player.sendMessage(new StringTextComponent("Increased " + type.name() + " by 5"), Util.NIL_UUID);
    }
    private Pokemon getPokemon(ServerPlayerEntity player, int slot) {
        return StorageProxy.getParty(player).get(slot);
    }




}
