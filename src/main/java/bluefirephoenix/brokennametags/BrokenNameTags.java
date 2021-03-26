package bluefirephoenix.brokennametags;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class BrokenNameTags implements ModInitializer {

    public static final Item BROKEN_NAMETAG = new BrokenNameTagItem(new FabricItemSettings().group(ItemGroup.MISC).maxCount(1));

    @Override
    public void onInitialize() {
        Registry.register(Registry.ITEM, new Identifier("brokennametags", "broken_nametag"),  BROKEN_NAMETAG);
    }
}
