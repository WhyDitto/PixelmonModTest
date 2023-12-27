package your.domain.path.command;

import ca.landonjw.gooeylibs2.api.UIManager;
import ca.landonjw.gooeylibs2.api.button.Button;
import ca.landonjw.gooeylibs2.api.button.GooeyButton;
import ca.landonjw.gooeylibs2.api.button.PlaceholderButton;
import ca.landonjw.gooeylibs2.api.button.linked.LinkType;
import ca.landonjw.gooeylibs2.api.button.linked.LinkedPageButton;
import ca.landonjw.gooeylibs2.api.helpers.PaginationHelper;
import ca.landonjw.gooeylibs2.api.page.GooeyPage;
import ca.landonjw.gooeylibs2.api.page.LinkedPage;
import ca.landonjw.gooeylibs2.api.template.types.ChestTemplate;
import ca.landonjw.gooeylibs2.api.template.types.FurnaceTemplate;
import ca.landonjw.gooeylibs2.api.template.types.InventoryTemplate;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.pixelmonmod.api.pokemon.PokemonSpecification;
import com.pixelmonmod.api.pokemon.PokemonSpecificationProxy;
import com.pixelmonmod.pixelmon.Pixelmon;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.api.pokemon.PokemonBuilder;
import com.pixelmonmod.pixelmon.api.pokemon.species.Species;
import com.pixelmonmod.pixelmon.api.pokemon.stats.BattleStatsType;
import com.pixelmonmod.pixelmon.api.storage.PlayerPartyStorage;
import com.pixelmonmod.pixelmon.api.storage.StoragePosition;
import com.pixelmonmod.pixelmon.api.storage.StorageProxy;
import com.pixelmonmod.pixelmon.api.util.helpers.SpriteItemHelper;
import com.pixelmonmod.pixelmon.entities.pixelmon.PixelmonEntity;
import com.pixelmonmod.pixelmon.items.PixelmonItem;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.DyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.NBTTextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.core.jmx.Server;
import your.domain.path.Menus.PokeBuilder;
import your.domain.path.ModFile;

import java.util.ArrayList;
import java.util.List;

public class ExampleCommand {

    /**
     *
     * Used for registering the command on the {@link net.minecraftforge.event.RegisterCommandsEvent}
     *
     * For more information about brigadier, how it works, what things mean, and lots of examples please read the
     * GitHub READ ME here <a href="https://github.com/Mojang/brigadier/blob/master/README.md">URL</a>
     *
     * @param dispatcher The dispatcher from the event
     */

    public static void register(CommandDispatcher<CommandSource> dispatcher) {
        dispatcher.register(LiteralArgumentBuilder.<CommandSource>literal("inventoryShow").executes(context -> {
            CommandSource source = context.getSource();
            ServerPlayerEntity player = source.getPlayerOrException();
            ModFile.pokeBuilder().MainMenu(player);



             // Sends a message to the sender - if true it will broadcast to all ops (like how /op does)
            return 1;
        }));
    }
    public static void showInventory(ServerPlayerEntity player) {
        Pokemon firstPokemon = StorageProxy.getParty(player.getUUID()).get(0);
        ItemStack firstSprite = SpriteItemHelper.getPhoto(firstPokemon);
        ItemStack item = SpriteItemHelper.getPhoto(PokemonSpecificationProxy.create("Charmander").create());
        firstPokemon.getIVs().setStat(BattleStatsType.ATTACK, firstPokemon.getStat(BattleStatsType.HP) + 1);
        GooeyButton redGlass = GooeyButton.builder()
                .display(new ItemStack(Items.RED_STAINED_GLASS_PANE))
                .build();

        GooeyButton emerald = GooeyButton.builder()
                .display(new ItemStack(Items.EMERALD))
                .build();

        GooeyButton sprite = GooeyButton.builder()
                .display(firstSprite)
                .title(firstPokemon.getDisplayName())
                .build();

        ChestTemplate template = ChestTemplate.builder(3) // Defines a chest template with 6 rows
                 // Sets a diamond button in the first row, first column of chest
                .set(0, sprite)
                .row(1, emerald) // Fills the entire second row with the emerald button
                .fill(redGlass) // Fills any empty slots in the chest with dirt button
                .build();
        GooeyPage page =  GooeyPage.builder().template(template).title("YUHBREH SUCKS").build();

        UIManager.openUIForcefully(player, page);
    }
}
